package com.example.taskservice.service;

import com.example.taskservice.auxillary.UserCompilationId;
import com.example.taskservice.config.RabbitMQConfig;
import com.example.taskservice.dto.CompilationDto;
import com.example.taskservice.dto.statisticDto.CompilationChangeDto;
import com.example.taskservice.model.Compilation;
import com.example.taskservice.model.Task;
import com.example.taskservice.model.User;
import com.example.taskservice.model.UserCompilation;
import com.example.taskservice.repository.CompilationRepository;
import com.example.taskservice.repository.TaskRepository;
import com.example.taskservice.repository.UserCompilationRepository;
import com.example.taskservice.util.Status;
import com.example.taskservice.util.statisticMessagesEnum.CompilationChangeMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompilationServiceImpl implements CompilationService {

    @Autowired
    private final CompilationRepository compilationRepository;

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserCompilationRepository userCompilationRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public CompilationServiceImpl(CompilationRepository compilationRepository, TaskRepository taskRepository, UserService userService, UserCompilationRepository userCompilationRepository) {
        this.compilationRepository = compilationRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.userCompilationRepository = userCompilationRepository;
    }

    @Override
    public Iterable<Compilation> findAllCompilations() {
        return compilationRepository.findAllByIsDeleted(false);
    }

    @Override
    public Iterable<CompilationDto> findAllDtos() {
        return compilationListToDtoList(findAllCompilations());
    }

    @Override
    public Compilation findCompilationById(Long id) {
        return compilationRepository.findCompilationByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Compilation with id " + id + " can't be found"));
    }

    @Override
    public Iterable<Task> findTasksByCompilationId(Long id) {
        return taskRepository.findAllByCompilationIdAndIsDeleted(id, false);
    }

    @Override
    public CompilationDto findDtoById(Long id) {
        return compilationToDto(findCompilationById(id));
    }

    @Override
    public Iterable<CompilationDto> findByUserIdAndNotShared(Long id) {
        return compilationListToDtoList(compilationRepository.findCompilationsByUserIdAndParams(
                id,
                false,
                false,
                false
        ));
    }

    @Override
    public Iterable<CompilationDto> findByUserIdAndSharedAndChangeable(Long id) {
        return compilationListToDtoList(compilationRepository.findCompilationsByUserIdAndParams(
                id,
                true,
                false,
                false
        ));
    }

    @Override
    public Iterable<CompilationDto> findByUserIdAndSharedAndNotChangeable(Long id) {
        return compilationListToDtoList(compilationRepository.findCompilationsByUserIdAndParams(
                id,
                true,
                true,
                false
        ));
    }

    @Override
    public Set<Long> findAllUserIdsThatOwnCurrentCompilation(Long id) {
        Compilation compilation = findCompilationById(id);
        return castUsersSetToIdsSet(compilation.getUsers());
    }

    @Override
    public Set<Long> castUsersSetToIdsSet(Set<UserCompilation> users) {
        Set<Long> userIds = new HashSet<>();
        for (UserCompilation user : users) {
            userIds.add(user.getUser().getId());
        }
        return userIds;
    }

    @Override
    public CompilationDto create(CompilationDto dto) {
        Compilation compilation = new Compilation();
        return saveDtoToCompilation(dto, compilation);
    }

    @Override
    public void delete(Long id) {
        Compilation compilation = findCompilationById(id);
        sendCompilationDataToMessageBroker(
                compilation,
                findAllUserIdsThatOwnCurrentCompilation(compilation.getId()),
                CompilationChangeMessage.DELETE
        );
        compilation.setDeleted(true);
        save(compilation);
    }

    @Override
    public void save(Compilation compilation) {
        compilationRepository.save(compilation);
    }

    @Override
    public Iterable<CompilationDto> compilationListToDtoList(Iterable<Compilation> taskList) {
        // create a stream from the source iterable
        return StreamSupport.stream(taskList.spliterator(), false)
                .map(this::compilationToDto) // apply method to each task
                .collect(Collectors.toList()); // collect the result into a list (or any other collection)
    }

    @Override
    public CompilationDto compilationToDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(), compilation.getName());
    }

    @Override
    public CompilationDto saveDtoToCompilation(CompilationDto dto, Compilation compilation) {
        User user = userService.findUserById(dto.getUser_id());
        compilation.setName(dto.getName());
        compilationRepository.save(compilation);
        saveUserCompilation(user, compilation, false, false);
        return compilationToDto(compilation);
    }

    @Override
    public void saveUserCompilation(User user, Compilation compilation, boolean readOnly, boolean isShared) {
        UserCompilation userCompilation = new UserCompilation(
                new UserCompilationId(user.getId(), compilation.getId()),
                readOnly,
                isShared,
                user,
                compilation
        );
        userCompilationRepository.save(userCompilation);
    }

    @Override
    public void sendCompilationDataToMessageBroker(Compilation compilation, Set<Long> userIds,
                                                   CompilationChangeMessage message) {
        CompilationChangeDto compilationData = new CompilationChangeDto(
                userIds,
                message,
                getStatusListFromCompilation(compilation.getTasks())
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.COMPILATION_ROUTING_KEY, compilationData);
    }

    @Override
    public List<Status> getStatusListFromCompilation(Set<Task> taskList) {
        List<Status> statusList = new ArrayList<>();
        for (Task task : taskList) {
            statusList.add(task.getStatus());
        }
        return statusList;
    }
}