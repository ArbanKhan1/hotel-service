package com.hotel_service.controller;

import com.hotel_service.dto.HotelCreateRequest;
import com.hotel_service.dto.HotelResponse;
import com.hotel_service.service.HotelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Validated
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable("id") @Min(1) Long id) {
        HotelResponse response = hotelService.getHotelById(id);
        return ResponseEntity.ok(response);
    }

}
