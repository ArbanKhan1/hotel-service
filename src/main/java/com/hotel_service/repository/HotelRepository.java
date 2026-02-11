package com.hotel_service.repository;

import com.hotel_service.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByIdAndIsActiveTrue(Long id);

}