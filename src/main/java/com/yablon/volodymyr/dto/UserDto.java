package com.yablon.volodymyr.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UserDto {

    private long id;

    @NotBlank(message = "The 'firstName' cannot be empty")
    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    private String firstName;

    @NotBlank(message = "The 'lastName' cannot be empty")
    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    private String lastName;

    @NotBlank(message = "The 'email' cannot be empty")
    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String email;

    @NotBlank(message = "The 'password' cannot be empty")
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
            message = "Must be minimum 6 characters, at least one letter and one number")
    private String password;

    private long roleId;

    private List<Long> myTodosId;

    private List<Long> otherTodosId;

    public UserDto() {
    }

    public UserDto(long id, String firstName, String lastName, String email, String password,
                   long roleId, List<Long> myTodosId, List<Long> otherTodosId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.myTodosId = myTodosId;
        this.otherTodosId = otherTodosId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMyTodosId() {
        return myTodosId;
    }

    public void setMyTodosId(List<Long> myTodosId) {
        this.myTodosId = myTodosId;
    }

    public List<Long> getOtherTodosId() {
        return otherTodosId;
    }

    public void setOtherTodosId(List<Long> otherTodosId) {
        this.otherTodosId = otherTodosId;
    }
}
