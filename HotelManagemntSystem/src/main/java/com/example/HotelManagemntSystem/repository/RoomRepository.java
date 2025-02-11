package com.example.HotelManagemntSystem.repository;

import com.example.HotelManagemntSystem.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();

    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.id NOT IN " +
            "(SELECT bk.room.id FROM Booking bk WHERE " +
            "(:checkInDate <= bk.checkOutDate AND :checkOutDate >= bk.checkInDate))")
    List<Room> findAvailableRoomsByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    @Query("SELECT r FROM Room r LEFT JOIN Booking b ON r.id = b.room.id " +
            "WHERE b.room.id IS NULL")
    List<Room> getAllAvailableRooms();
}
