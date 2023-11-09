package com.example.taskservice.service;

import com.example.taskservice.config.RabbitMQConfig;
import com.example.taskservice.dto.TaskDto;
import com.example.taskservice.dto.statisticDto.TaskChangeDto;
import com.example.taskservice.model.Compilation;
import com.example.taskservice.model.Task;
import com.example.taskservice.repository.TaskRepository;
import com.example.taskservice.util.Status;
import com.example.taskservice.util.jwt.Role;
import com.example.taskservice.util.statisticMessagesEnum.TaskChangeMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    private final CompilationService compilationService;

    private final TaskRepository taskRepository;

    private final JwtService jwtService;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TaskServiceImpl(CompilationService compilationService, TaskRepository taskRepository,
                           JwtService jwtService, RabbitTemplate rabbitTemplate) {
        this.compilationService = compilationService;
        this.taskRepository = taskRepository;
        this.jwtService = jwtService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Iterable<TaskDto> findAll() {
        Iterable<Task> taskList = taskRepository.findAllByIsDeleted(false);
        return taskListToDtoList(taskList);
    }

    @Override
    public TaskDto findDtoById(Long id) {
        Task task = findTaskById(id);
        return taskToDto(task);
    }

    @Override
    public Iterable<TaskDto> findTaskDtosByCompilationId(Long id) {
        return taskListToDtoList(findTasksByCompilationId(id));
    }

    @Override
    public TaskDto create(TaskDto dto) {
        Task task = new Task();
        sendAddedTaskDataToMessageBroker(dto.getCompilationId(), Status.fromString(dto.getStatus()));
        return saveDtoToTask(dto, task);
    }

    @Override
    public TaskDto update(TaskDto dto) {
        Task task = findTaskById(dto.getId());
        Long previousCompilationId = task.getCompilation().getId();
        sendEditedTaskDataToMessageBroker(dto.getCompilationId(), task.getStatus(), Status.fromString(dto.getStatus()));
        TaskDto taskDto = saveDtoToTask(dto, task);
        processCompilationChange(previousCompilationId); //previous compilation recalculation
        return taskDto;
    }

    @Override
    public void delete(Long id) {
        Task task = findTaskById(id);
        task.setDeleted(true);
        taskRepository.save(task);
        processCompilationChange(task.getCompilation().getId());
        sendDeletedTaskDataToMessageBroker(task.getCompilation().getId(), task.getStatus());
    }

    private Task findTaskById(Long id) {
        return taskRepository.findTaskByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " can't be found"));
    }

    private List<Task> findTasksByCompilationId(Long id) {
        return taskRepository.findAllByCompilationIdAndIsDeleted(id, false);
    }

    private TaskDto saveDtoToTask(TaskDto dto, Task task) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(Status.fromString(dto.getStatus()));
        task.setCompilation(compilationService.findCompilationById(dto.getCompilationId())); //change compilationId
        taskRepository.save(task);
        processCompilationChange(task.getCompilation().getId()); //current compilation recalculation
        return taskToDto(task);
    }

    private Iterable<TaskDto> taskListToDtoList(Iterable<Task> taskList) {
        // create a stream from the source iterable
        return StreamSupport.stream(taskList.spliterator(), false)
                .map(this::taskToDto) // apply method to each task
                .collect(Collectors.toList()); // collect the result into a list
    }

    private TaskDto taskToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().toString(),
                task.getCompilation().getId()
        );
    }

    private void sendAddedTaskDataToMessageBroker(Long compilationId, Status currStatus) {
        Set<Long> userIds = compilationService.findAllUserIdsThatOwnCurrentCompilation(compilationId);
        TaskChangeDto newTaskDto = new TaskChangeDto(userIds, TaskChangeMessage.ADD, null, currStatus);
        HttpHeaders headers = constructAuthorizationHeader();
        HttpEntity<TaskChangeDto> entity = new HttpEntity<>(newTaskDto, headers);
        sendDataToMessageBroker(entity);
    }

    private void sendEditedTaskDataToMessageBroker(Long compilationId, Status prevStatus, Status currStatus) {
        Set<Long> userIds = compilationService.findAllUserIdsThatOwnCurrentCompilation(compilationId);
        TaskChangeDto changeTaskDto = new TaskChangeDto(userIds, TaskChangeMessage.EDIT, prevStatus, currStatus);
        HttpHeaders headers = constructAuthorizationHeader();
        HttpEntity<TaskChangeDto> entity = new HttpEntity<>(changeTaskDto, headers);
        sendDataToMessageBroker(entity);
    }

    private void sendDeletedTaskDataToMessageBroker(Long compilationId, Status prevStatus) {
        Set<Long> userIds = compilationService.findAllUserIdsThatOwnCurrentCompilation(compilationId);
        TaskChangeDto deleteTaskDto = new TaskChangeDto(userIds, TaskChangeMessage.DELETE, prevStatus, null);
        HttpHeaders headers = constructAuthorizationHeader();
        HttpEntity<TaskChangeDto> entity = new HttpEntity<>(deleteTaskDto, headers);
        sendDataToMessageBroker(entity);
    }

    private void processCompilationChange(Long id) {
        Compilation compilation = compilationService.findCompilationById(id);
        compilation.setCompleteness(calculateCompleteness(compilation.getId()));
        compilationService.save(compilation);
    }

    private void sendDataToMessageBroker(HttpEntity<?> entity) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.TASK_ROUTING_KEY, entity);
    }

    private HttpHeaders constructAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtService.generateAuthorizationHeader(Role.USER));
        return headers;
    }

    private int calculateCompleteness(Long id) {
        Iterable<Task> tasks = findTasksByCompilationId(id);
        int taskCount = 0;
        int completedCount = 0;
        int inProgressCount = 0;
        for (Task task : tasks) {
            taskCount++;
            if (task.getStatus().equals(Status.IN_PROGRESS)) {
                inProgressCount++;
            } else if (task.getStatus().equals(Status.COMPLETED)) {
                completedCount++;
            }
        }
        if (taskCount == 0) {
            return 0;
        }
        float taskValue = 100f / taskCount;
        float completeness = taskValue * (completedCount + inProgressCount / 2f);
        return (int) completeness;
    }
}
