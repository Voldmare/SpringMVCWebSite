package com.yablon.volodymyr.service.impl;

import com.yablon.volodymyr.model.Task;
import com.yablon.volodymyr.repository.TaskRepository;
import com.yablon.volodymyr.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task create(Task user) {
            return taskRepository.save(user);
    }

    @Override
    public Task readById(long id) {
        Optional<Task> optional = taskRepository.findById(id);
            return optional.get();
    }

    @Override
    public Task update(Task task) {
            Task oldTask = readById(task.getId());
                return taskRepository.save(task);
    }

    @Override
    public void delete(long id) {
        Task task = readById(id);
            taskRepository.delete(task);
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        List<Task> tasks = taskRepository.getByTodoId(todoId);
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }
}
