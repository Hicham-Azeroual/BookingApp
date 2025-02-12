package com.example.HotelManagemntSystem.service.implimentation;

import com.example.HotelManagemntSystem.dao.Response;
import com.example.HotelManagemntSystem.models.Booking;
import com.example.HotelManagemntSystem.service.interfaces.BookingInterfaceService;

public class BookingService implements BookingInterfaceService {
    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        return null;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        return null;
    }

    @Override
    public Response getAllBookings() {
        return null;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        return null;
    }
}
