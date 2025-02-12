package com.example.HotelManagemntSystem.service.implimentation;

import com.example.HotelManagemntSystem.dao.BookingDTO;
import com.example.HotelManagemntSystem.dao.Response;
import com.example.HotelManagemntSystem.exception.OurException;
import com.example.HotelManagemntSystem.models.Booking;
import com.example.HotelManagemntSystem.models.Room;
import com.example.HotelManagemntSystem.models.User;
import com.example.HotelManagemntSystem.repository.BookingRepository;
import com.example.HotelManagemntSystem.repository.RoomRepository;
import com.example.HotelManagemntSystem.repository.UserRepository;
import com.example.HotelManagemntSystem.service.interfaces.BookingInterfaceService;
import com.example.HotelManagemntSystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service

public class BookingService implements BookingInterfaceService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {

        Response response=new Response();
       try {
           if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
               throw new IllegalArgumentException("check out date must be after check in date");
           }
           Room room=roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room not found"));
           User user=userRepository.findById(userId).orElseThrow(()-> new OurException("User not found"));
           List<Booking> existingBookings=room.getBookings();
           if(!roomIsAvailable(bookingRequest,existingBookings)){
               throw new OurException("Room is not available");
           }
           bookingRequest.setRoom(room);
           bookingRequest.setUser(user);
           bookingRequest.setBookingConfirmationCode(Utils.generateRandomConfirmationCode(10));
           Booking savedBooking=bookingRepository.save(bookingRequest);
           response.setStatusCode(200);
           response.setMessage("Successfully Booked Room");
       } catch (IllegalArgumentException e) {
           response.setMessage(e.getMessage());
           response.setStatusCode(404);
       } catch (Exception e) {
           response.setMessage("Error Occurred While Booking Room "+e.getMessage());
           response.setStatusCode(500);
       }
       return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {

        Response response = new Response();

        try {
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new OurException("Booking Not Found"));
            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, true);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBooking(bookingDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Finding a booking: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();

        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingList(bookingDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Getting all bookings: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();

        try {
            bookingRepository.findById(bookingId).orElseThrow(() -> new OurException("Booking Does Not Exist"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Cancelling a booking: " + e.getMessage());

        }
        return response;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        !bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckInDate())
                                && !bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckOutDate())
                );
    }

}
