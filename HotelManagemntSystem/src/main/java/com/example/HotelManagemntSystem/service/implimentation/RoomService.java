package com.example.HotelManagemntSystem.service.implimentation;

import com.example.HotelManagemntSystem.dao.Response;
import com.example.HotelManagemntSystem.service.interfaces.RoomInterfaceService;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RoomService implements RoomInterfaceService {
    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        return null;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return List.of();
    }

    @Override
    public Response getAllRooms() {
        return null;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        return null;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        return null;
    }

    @Override
    public Response getRoomById(Long roomId) {
        return null;
    }

    @Override
    public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        return null;
    }

    @Override
    public Response getAllAvailableRooms() {
        return null;
    }
}
