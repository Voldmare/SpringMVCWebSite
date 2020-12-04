package com.yablon.volodymyr.controller;

import com.yablon.volodymyr.model.Priority;
import com.yablon.volodymyr.model.Task;
import com.yablon.volodymyr.service.StateService;
import com.yablon.volodymyr.service.TaskService;
import com.yablon.volodymyr.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;
    @Autowired
    ToDoService toDoService;
    @Autowired
    StateService stateService;

    @GetMapping("/create/todos/{todo_id}")
    public String create(Model model, @PathVariable(name = "todo_id") Integer id) {
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("todoId", id);
        return "create-task";
    }

    @PostMapping("/create/todos/{todo_id}")
    public String create(@ModelAttribute(name = "task") Task task,
                         @PathVariable(name = "todo_id") Integer id) {
        task.setState(stateService.readById(5L));
        task.setTodo(toDoService.readById(id));
        taskService.create(task);
        return "redirect:/todos/" + id + "/tasks";
    }

    @GetMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable(name = "task_id") Integer taskId,
                         @PathVariable(name = "todo_id") Integer todoId,
                         Model model) {
        model.addAttribute("task", taskService.readById(taskId));
        model.addAttribute("todoId", todoId);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("states", stateService.getAll());
        return "update-task";
    }

    @PostMapping("/{task_id}/update/todos/{todo_id}")
    public String update(@PathVariable(name = "task_id") Integer taskId,
                         @PathVariable(name = "todo_id") Integer todoId,
                         @ModelAttribute(name = "task") Task task) {
        task.setTodo(toDoService.readById(todoId));
        task.setId(taskId);
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
