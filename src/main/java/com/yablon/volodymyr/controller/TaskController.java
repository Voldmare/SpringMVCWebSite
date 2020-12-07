package com.yablon.volodymyr.controller;

import com.yablon.volodymyr.dto.TaskDto;
import com.yablon.volodymyr.dto.TaskTransformer;
import com.yablon.volodymyr.model.Priority;
import com.yablon.volodymyr.model.Task;
import com.yablon.volodymyr.service.StateService;
import com.yablon.volodymyr.service.TaskService;
import com.yablon.volodymyr.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ToDoService todoService;
    private final StateService stateService;

    public TaskController(TaskService taskService, ToDoService todoService, StateService stateService) {
        this.taskService = taskService;
        this.todoService = todoService;
        this.stateService = stateService;
    }

    @GetMapping("/create/todos/{todo_id}")
    public String create(Model model, @PathVariable(name = "todo_id") Integer id) {
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("todoId", id);
        return "create-task";
    }

    @PostMapping("/create/todos/{todo_id}")
    public String create(@PathVariable("todo_id") long todoId, Model model,
                         @Validated @ModelAttribute("task") TaskDto taskDto, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("todo", todoService.readById(todoId));
            model.addAttribute("priorities", Priority.values());
            return "create-task";
        }
        Task task = TaskTransformer.convertToEntity(
                taskDto,
                todoService.readById(taskDto.getTodoId()),
                stateService.getByName("New")
        );
        taskService.create(task);
        return "redirect:/todos/" + todoId + "/tasks";
    }

    @GetMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable(name = "task_id") Integer taskId,
                         @PathVariable(name = "todo_id") Integer todoId,
                         Model model) {
        model.addAttribute("task", TaskTransformer.convertToDto(taskService.readById(taskId)));
        model.addAttribute("todoId", todoId);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("states", stateService.getAll());
        return "update-task";
    }

    @PostMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable("task_id") long taskId,
                         @PathVariable("todo_id") long todoId,
                         Model model,
                         @Validated @ModelAttribute("task") TaskDto taskDto,
                         BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("states", stateService.getAll());
            return "update-task";
        }
        //todoService.readById(todoId);
        taskDto.setId(taskId);
        Task task = TaskTransformer.convertToEntity(
                taskDto,
                todoService.readById(todoId),
                stateService.readById(taskDto.getStateId())
        );
        taskService.update(task);
        return "redirect:/todos/" + todoId + "/tasks";
    }

    @GetMapping("/{task_id}/delete/todos/{todo_id}")
    public String delete(@PathVariable(name = "task_id") Integer taskId,
                         @PathVariable(name = "todo_id") Integer todoId) {
        taskService.delete(taskId);
        return "redirect:/todos/" + todoId + "/tasks";
    }
}
