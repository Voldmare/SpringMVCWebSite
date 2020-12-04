package com.yablon.volodymyr.service;

import com.yablon.volodymyr.model.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User readById(long id);
    User update(User user);
    void delete(long id);
    List<User> getAll();

}
