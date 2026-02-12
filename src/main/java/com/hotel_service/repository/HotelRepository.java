package com.hotel_service.repository;

import com.hotel_service.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByIdAndIsActiveTrue(Long id);
    @Override
    @NonNull
    Page<Hotel> findAll(@NonNull Pageable pageable);
    Page<Hotel> findByIsActiveTrue(Pageable pageable);


}