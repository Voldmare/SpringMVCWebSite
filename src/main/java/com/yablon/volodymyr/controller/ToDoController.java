package com.yablon.volodymyr.controller;

import com.yablon.volodymyr.model.ToDo;
import com.yablon.volodymyr.model.User;
import com.yablon.volodymyr.service.TaskService;
import com.yablon.volodymyr.service.ToDoService;
import com.yablon.volodymyr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
   

    @GetMapping("/create/users/{owner_id}") // +
    public String create(Model model, @PathVariable String owner_id) {
        ToDo toDo = new ToDo();
        model.addAttribute("todo", toDo);
        model.addAttribute("owner_id", owner_id);
        model.addAttribute("users", userService.getAll());
        return "create-todo";
    }

    @PostMapping("/create/users/{owner_id}") //+
    public String create(@ModelAttribute("toDo") ToDo toDo, @PathVariable Integer owner_id) {
        toDo.setOwner(userService.readById(owner_id));
        toDoService.create(toDo);
        return "redirect:/todos/all/users/" + owner_id;
    }

    @GetMapping("/{id}/tasks")
    public String read(Model model, @PathVariable Integer id) {
        ToDo toDo = toDoService.readById(id);
        List<User> collaborators = toDo.getCollaborators();
        List<User> potentialCollaborators = userService.getAll();
        potentialCollaborators.remove(toDo.getOwner());
        potentialCollaborators.removeAll(collaborators);

        model.addAttribute("tasks", taskService.getByTodoId(id));
        model.addAttribute("toDo", toDo);
        model.addAttribute("collaborators", collaborators);
        model.addAttribute("potentialCollaborators", potentialCollaborators);
        model.addAttribute("newCollaborator", new User());

        return "todo-tasks";
    }

    @GetMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@PathVariable(name="todo_id") long todoId, Model model, @PathVariable(name="owner_id") Integer ownerId) {
        ToDo toDo = toDoService.readById(todoId);
        model.addAttribute("toDo", toDo);
        List<ToDo> toDos = toDoService.getByUserId(ownerId);
        model.addAttribute("todos", toDos);
        model.addAttribute("ownerId", ownerId);
        return "update-todo";
    }

    @PostMapping("/{todo_id}/update/users/{owner_id}")
    public String update(@ModelAttribute("toDo") ToDo toDo, @PathVariable Integer owner_id, @PathVariable Integer todo_id) {
        toDo.setId(todo_id);
        toDo.setOwner(userService.readById(owner_id));
        toDoService.update(toDo);
        return "redirect:/todos/all/users/" + owner_id;
    }

    @GetMapping("/{todo_id}/delete/users/{owner_id}")
    public String delete(@PathVariable(name="todo_id") long id, @PathVariable(name="owner_id") long owner_id) {
       this.toDoService.delete(id);
       return  "redirect:/todos/all/users/" + owner_id;
    }

    @GetMapping("/all/users/{user_id}")
    public String getAll(Model model, @PathVariable Integer user_id) {
        model.addAttribute("user", userService.readById(user_id));
        model.addAttribute("todos", toDoService.getByUserId(user_id));
        return "todo-lists";
    }

    @GetMapping("/{id}/add")
    public String addCollaborator(@PathVariable long id,
                                  @RequestParam(name="id", required=true, defaultValue="unknown") Integer collaborId,
                                  Model model) {
        ToDo todo = toDoService.readById(id);
        todo.getCollaborators().add(userService.readById(collaborId));
        toDoService.update(todo);
        return "redirect:/todos/" + id + "/tasks";
    }

    @GetMapping("/{todo_id}/remove/{collaborator_id}")
    public String removeCollaborator(@PathVariable(name = "todo_id") long todoId,
                                     @PathVariable(name = "collaborator_id") long collaboratorId) {
      ToDo toDo = toDoService.readById(todoId);
      User collaborator = userService.readById(collaboratorId);

      toDo.getCollaborators().remove(collaborator);
      toDoService.update(toDo);
      return "redirect:/todos/" + todoId + "/tasks";
    }
}
