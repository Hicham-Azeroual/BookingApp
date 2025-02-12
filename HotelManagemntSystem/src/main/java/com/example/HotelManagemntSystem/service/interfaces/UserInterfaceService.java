package com.example.HotelManagemntSystem.service.interfaces;

import com.example.HotelManagemntSystem.dao.LoginRequest;
import com.example.HotelManagemntSystem.dao.Response;
import com.example.HotelManagemntSystem.models.User;

public interface UserInterfaceService {

    Response registre(User user);
    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
}
