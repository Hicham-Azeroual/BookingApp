package com.example.HotelManagemntSystem.service.interfaces;

import com.example.HotelManagemntSystem.dao.Response;
import com.example.HotelManagemntSystem.models.Booking;

public interface BookingInterfaceService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
