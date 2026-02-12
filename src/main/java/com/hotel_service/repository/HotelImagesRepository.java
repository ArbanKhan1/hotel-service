package com.hotel_service.repository;

import com.hotel_service.model.Hotel;
import com.hotel_service.model.HotelImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelImagesRepository extends JpaRepository<HotelImages, Long> {
    List<HotelImages> findByHotelId(Long id);
}