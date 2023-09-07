package com.example.restfulservice.service;

import com.example.restfulservice.model.Compilation;
import com.example.restfulservice.model.Task;
import com.example.restfulservice.repository.CompilationRepository;
import com.example.restfulservice.repository.TaskRepository;
import com.example.restfulservice.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskServiceImpl implements TaskService {

//    @Autowired
//    private ModelMapper mapper;

/*    @Autowired
    private CompilationService compilationService;*/

    @Autowired
    private CompilationRepository compilationRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAllByIsDeleted(false);
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findTaskByIdAndIsDeleted(id, false).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with id " + id + " can't be found"));
    }

    @Override
    public Iterable<Task> findTasksByCompilationId(Long id) {
        return taskRepository.findAllByCompilationIdAndIsDeleted(id, false);
    }

    @Override
    public Task create(TaskDto dto) {
        Task task = new Task();
        return saveDtoToTask(dto, task);
    }

    @Override
    public Task update(TaskDto dto, Long id) {
        Task task = findById(id);
        return saveDtoToTask(dto, task);
    }

    @Override
    public void delete(Long id) {
        Task task = findById(id);
        task.setDeleted(true);
        taskRepository.save(task);
        processCompilationChange(task.getCompilation().getId());
    }

    public Task saveDtoToTask(TaskDto dto, Task task) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setCompilation(compilationRepository.findById(dto.getCompilation_id()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Compilation with id " + dto.getCompilation_id()
                        + " can't be found")));
        taskRepository.save(task);
        processCompilationChange(task.getCompilation().getId());
        return task;
    }

    void processCompilationChange(Long id){
        Compilation compilation = compilationRepository.findCompilationByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Compilation with id " + id + " can't be found"));
        compilation.setCompleteness(calculateCompleteness(compilation.getId()));
        compilationRepository.save(compilation);
    }

    byte calculateCompleteness(Long id){
        Iterable<Task> tasks = findTasksByCompilationId(id);
        int taskCount = 0;
        int completedCount = 0;
        int inProgressCount = 0;
        for(Task task: tasks){
            taskCount++;
            if(task.getStatus().equals("In Progress")){
                inProgressCount++;
            } else if(task.getStatus().equals("Completed")){
                completedCount++;
            }
        }
        if(taskCount == 0){
            return 0;
        }
        float taskValue = 100f / taskCount;
        float completeness = taskValue * (completedCount + inProgressCount / 2f);
        return (byte) completeness;
    }
}
