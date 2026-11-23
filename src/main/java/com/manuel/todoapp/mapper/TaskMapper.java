package com.manuel.todoapp.mapper;

import com.manuel.todoapp.dto.TaskRequest;
import com.manuel.todoapp.dto.TaskResponse;
import com.manuel.todoapp.model.Task;
import com.manuel.todoapp.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {

    public TaskResponse toDtoFromEntity(Task task) {
        return new TaskResponse(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt(),
                task.getDueDate());
    }

    public List<TaskResponse> toDtoFromEntity(List<Task> tasks) {
        return tasks.stream().map(this::toDtoFromEntity).toList();
    }

    public Task updateEntityFromDto(Task taskToUpdate, TaskRequest request) {
        taskToUpdate.setTitle(request.title());
        taskToUpdate.setDescription(request.description());
        taskToUpdate.setStatus(request.status());
        taskToUpdate.setPriority(request.priority());
        taskToUpdate.setDueDate(request.dueDate());

        return taskToUpdate;
    }

    public Task toEntityFromDto(User user, TaskRequest taskRequest) {
        Task task = new Task();

        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setStatus(taskRequest.status());
        task.setPriority(taskRequest.priority());
        task.setDueDate(taskRequest.dueDate());

        task.setUser(user);

        return task;
    }
}
