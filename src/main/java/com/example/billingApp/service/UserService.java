package com.example.billingApp.service;


import com.example.billingApp.io.User.UserRequest;
import com.example.billingApp.io.User.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> getUsers();

    void deleteUser(String id);

}
