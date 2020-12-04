package com.yablon.volodymyr.dto;

import com.yablon.volodymyr.model.Role;
import com.yablon.volodymyr.model.ToDo;
import com.yablon.volodymyr.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserTransformer {
    public static UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getId(),
                user.getMyTodos().stream().map(ToDo::getId).collect(Collectors.toList()),
                user.getOtherTodos().stream().map(ToDo::getId).collect(Collectors.toList())
        );
    }

    public static User convertToEntity(UserDto userDto, List<ToDo> myTodos, List<ToDo> myOtherTodos, Role role) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(role);
        user.setMyTodos(myTodos);
        user.setOtherTodos(myOtherTodos);
        return user;
    }
}
