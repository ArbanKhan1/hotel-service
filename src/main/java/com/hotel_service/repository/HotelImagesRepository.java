package com.hotel_service.repository;

import com.hotel_service.model.HotelImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelImagesRepository extends JpaRepository<HotelImages, Long> {
}