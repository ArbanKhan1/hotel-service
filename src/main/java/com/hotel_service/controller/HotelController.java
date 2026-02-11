package com.hotel_service.controller;

import com.hotel_service.dto.HotelCreateRequest;
import com.hotel_service.dto.HotelResponse;
import com.hotel_service.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelResponse> addHotel(@Valid  @RequestBody HotelCreateRequest hotelCreateRequest) {
        HotelResponse response = hotelService.createHotel(hotelCreateRequest);
        return ResponseEntity.ok(response);
    }

}
