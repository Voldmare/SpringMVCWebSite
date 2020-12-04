package com.yablon.volodymyr.controller;

import com.yablon.volodymyr.model.Role;
import com.yablon.volodymyr.model.User;
import com.yablon.volodymyr.service.RoleService;
import com.yablon.volodymyr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/create")
    public String create(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "create-user";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute(name = "user") User user) {
        user.setRole(roleService.readById(2L));
        userService.create(user);
        return "redirect:/todos/all/users/" + user.getId();
    }

    //
//    @GetMapping("/{id}/read")
//    public String read(//add needed parameters) {
//        //ToDo
//        return " ";
//    }
//
    @GetMapping("/update/{userId}")
    public String update(@PathVariable(name = "userId") Integer id, Model model) {
        model.addAttribute("user", userService.readById(id));
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        return "update-user";
    }

    @PostMapping("/update/{userId}")
    public String update(@PathVariable(name = "userId") Integer id, @ModelAttribute(name = "user") User user) {
        user.setId(id);
        userService.update(user);
        return "redirect:/home";
    }

    @GetMapping("/delete/{userId}")
    public String delete(@PathVariable(name = "userId") Integer id) {
        userService.delete(id);
        return "redirect:/home";
    }

    /*@GetMapping("/all")
    public String getAll() {

        return " ";
    }*/
}
