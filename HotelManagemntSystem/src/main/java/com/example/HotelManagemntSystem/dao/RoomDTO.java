package com.example.HotelManagemntSystem.dao;

import com.example.HotelManagemntSystem.dao.BookingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON response
public class RoomDTO {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;
    private List<BookingDTO> bookings;
}
