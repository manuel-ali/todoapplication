package com.manuel.todoapp.service;

import com.manuel.todoapp.dto.TaskRequest;
import com.manuel.todoapp.dto.TaskResponse;
import com.manuel.todoapp.exception.ResourceNotFoundException;
import com.manuel.todoapp.mapper.TaskMapper;
import com.manuel.todoapp.model.Task;
import com.manuel.todoapp.model.User;
import com.manuel.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponse> getAllTasks() {
        List <Task> taskList = taskRepository.findAll();
        return taskMapper.toDtoFromEntity(taskList);
    }

    public List<TaskResponse> getTasksByUser (Long userId) {
        List<Task> taskList = taskRepository.findByUserId(userId);

        return taskMapper.toDtoFromEntity(taskList);
    }

    public TaskResponse getTaskById (User user, Long id) {
        Task task = findTaskById(id);

        validateOwnership(user, task);

        return taskMapper.toDtoFromEntity(task);
    }

    public TaskResponse createTask (User user, TaskRequest taskRequest) {
        Task task = taskMapper.toEntityFromDto(user, taskRequest);
        task = taskRepository.save(task);

        return taskMapper.toDtoFromEntity(task);
    }

    public TaskResponse updateTask (User user, TaskRequest request, Long id) {
        Task taskToUpdate = findTaskById(id);

        validateOwnership(user, taskToUpdate);

        taskMapper.updateEntityFromDto(taskToUpdate, request);

        Task task = taskRepository.save(taskToUpdate);

        return taskMapper.toDtoFromEntity(task);
    }

    public void deleteTask (User user, Long id){
        Task taskToDelete = findTaskById(id);

        validateOwnership(user, taskToDelete);

        taskRepository.deleteById(id);
    }

    public Task findTaskById (Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public void validateOwnership(User user, Task task) {
        if (!task.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Task not found");
        }
    }
}
