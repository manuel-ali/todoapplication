package com.manuel.todoapp.controller;

import com.manuel.todoapp.dto.TaskRequest;
import com.manuel.todoapp.dto.TaskResponse;
import com.manuel.todoapp.model.Task;
import com.manuel.todoapp.model.User;
import com.manuel.todoapp.service.TaskService;
import com.manuel.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @PostMapping
    public TaskResponse createTask (@AuthenticationPrincipal UserDetails userDetails, @RequestBody TaskRequest taskRequest) {
        User user = getUserByUserDetails(userDetails);

        return taskService.createTask(user, taskRequest);
    }

    @GetMapping
    public List<TaskResponse> getUserTasks (@AuthenticationPrincipal UserDetails userDetails) {
        User user = getUserByUserDetails(userDetails);

        return taskService.getTasksByUser(user.getId());
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById (@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        User user = getUserByUserDetails(userDetails);

        return taskService.getTaskById(user, id);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTaskById (@AuthenticationPrincipal UserDetails userDetails, @RequestBody TaskRequest taskRequest, @PathVariable Long id) {
        User user = getUserByUserDetails(userDetails);

        return taskService.updateTask(user, taskRequest, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskById (@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        User user = getUserByUserDetails(userDetails);

        taskService.deleteTask(user, id);
    }

    private User getUserByUserDetails(UserDetails userDetails) {
        String username = userDetails.getUsername();

        return userService.findUserByEmail(username);
    }
}
