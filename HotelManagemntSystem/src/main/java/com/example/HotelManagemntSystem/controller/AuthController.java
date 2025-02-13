package com.example.HotelManagemntSystem.controller;

import com.example.HotelManagemntSystem.dao.LoginRequest;
import com.example.HotelManagemntSystem.dao.Response;
import com.example.HotelManagemntSystem.models.User;
import com.example.HotelManagemntSystem.service.implimentation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")

    public ResponseEntity<Response> register(@RequestBody User user) {
        Response response = userService.registre(user);
        System.out.println(response);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
