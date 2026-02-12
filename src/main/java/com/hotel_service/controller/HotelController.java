package com.hotel_service.controller;

import com.hotel_service.dto.*;
import com.hotel_service.model.Hotel;
import com.hotel_service.service.HotelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping
    public ResponseEntity<Page<Hotel>> getAllHotels(@RequestBody PageDto pageDto) {
        Page<Hotel> hotels = hotelService.getHotels(pageDto.getPage(), pageDto.getSize());
        return ResponseEntity.ok(hotels);
    }

    @Validated
    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(@Valid @RequestBody HotelCreateRequest request,@PathVariable("id") @Min(1) Long id) {
        HotelResponse response = hotelService.updateHotel(request, id);
        return ResponseEntity.ok(response);
    }

    @Validated
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteHotel(@PathVariable @Min(1) Long id) {
        GeneralResponse response = hotelService.deleteHotel(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/images/presigned-url")
    public ResponseEntity<Map<String,String>> getPresigned(
            @PathVariable Long id,
            @RequestParam String fileName
    ) {
        String url = hotelService.generatePreSignedUrl(id, fileName);

        return ResponseEntity.ok(
                Map.of("uploadUrl", url)
        );
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<ImagesDto> getImagesByHotelId(@PathVariable("id") Long id) {
        ImagesDto imagesById = hotelService.getImagesById(id);
        return ResponseEntity.ok(imagesById);
    }

}
