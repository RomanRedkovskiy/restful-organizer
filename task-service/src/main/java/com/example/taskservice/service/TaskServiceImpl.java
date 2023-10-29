package com.example.taskservice.service;

import com.example.taskservice.config.RabbitMQConfig;
import com.example.taskservice.dto.statisticDto.TaskChangeDto;
import com.example.taskservice.model.Compilation;
import com.example.taskservice.model.Task;
import com.example.taskservice.repository.CompilationRepository;
import com.example.taskservice.repository.TaskRepository;
import com.example.taskservice.dto.TaskDto;
import com.example.taskservice.util.Status;
import com.example.taskservice.util.statisticMessagesEnum.TaskChangeMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    CompilationService compilationService;

    @Autowired
    private CompilationRepository compilationRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Iterable<TaskDto> findAll() {
        Iterable<Task> taskList = taskRepository.findAllByIsDeleted(false);
        return taskListToDtoList(taskList);
    }

    @Override
    public Task findTaskById(Long id) {
        return taskRepository.findTaskByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " can't be found"));
    }

    @Override
    public TaskDto findDtoById(Long id) {
        Task task = findTaskById(id);
        return taskToDto(task);
    }

    @Override
    public Iterable<Task> findTasksByCompilationId(Long id) {
        return taskRepository.findAllByCompilationIdAndIsDeleted(id, false);
    }

    @Override
    public Iterable<TaskDto> findTaskDtosByCompilationId(Long id) {
        return taskListToDtoList(findTasksByCompilationId(id));
    }

    @Override
    public TaskDto create(TaskDto dto) {
        Task task = new Task();
        sendNewTaskDataToMessageBroker(dto.getCompilation_id(), Status.fromString(dto.getStatus()));
        return saveDtoToTask(dto, task);
    }

    @Override
    public TaskDto update(TaskDto dto) {
        Task task = findTaskById(dto.getId());
        sendEditedTaskDataToMessageBroker(dto.getCompilation_id(), task.getStatus(), Status.fromString(dto.getStatus()));
        return saveDtoToTask(dto, task);
    }

    @Override
    public void delete(Long id) {
        Task task = findTaskById(id);
        task.setDeleted(true);
        taskRepository.save(task);
        processCompilationChange(task.getCompilation().getId());
        sendDeletedTaskDataToMessageBroker(task.getCompilation().getId(), task.getStatus());
    }

    public TaskDto saveDtoToTask(TaskDto dto, Task task) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(Status.fromString(dto.getStatus()));
        task.setCompilation(compilationRepository.findById(dto.getCompilation_id()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Compilation with id " + dto.getCompilation_id()
                        + " can't be found")));
        taskRepository.save(task);
        processCompilationChange(task.getCompilation().getId());
        return taskToDto(task);
    }

    Iterable<TaskDto> taskListToDtoList(Iterable<Task> taskList) {
        // create a stream from the source iterable
        return StreamSupport.stream(taskList.spliterator(), false)
                .map(this::taskToDto) // apply method to each task
                .collect(Collectors.toList()); // collect the result into a list
    }

    public TaskDto taskToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().toString(),
                task.getCompilation().getId()
        );
    }

    public void sendNewTaskDataToMessageBroker(Long compilationId, Status currStatus){
        Set<Long> userIds = compilationService.findAllUserIdsThatOwnCurrentCompilation(compilationId);
        TaskChangeDto newTaskDto = new TaskChangeDto(userIds, TaskChangeMessage.ADD, null, currStatus);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.TASK_ROUTING_KEY, newTaskDto);
    }

    public void sendEditedTaskDataToMessageBroker(Long compilationId, Status prevStatus, Status currStatus){
        Set<Long> userIds = compilationService.findAllUserIdsThatOwnCurrentCompilation(compilationId);
        TaskChangeDto changeTaskDto = new TaskChangeDto(userIds, TaskChangeMessage.EDIT, prevStatus, currStatus);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.TASK_ROUTING_KEY, changeTaskDto);
    }

    public void sendDeletedTaskDataToMessageBroker(Long compilationId, Status prevStatus){
        Set<Long> userIds = compilationService.findAllUserIdsThatOwnCurrentCompilation(compilationId);
        TaskChangeDto deleteTaskDto = new TaskChangeDto(userIds, TaskChangeMessage.DELETE, prevStatus, null);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.TASK_ROUTING_KEY, deleteTaskDto);
    }

    void processCompilationChange(Long id) {
        Compilation compilation = compilationRepository.findCompilationByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Compilation with id " + id + " can't be found"));
        compilation.setCompleteness(calculateCompleteness(compilation.getId()));
        compilationRepository.save(compilation);
    }

    int calculateCompleteness(Long id) {
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
