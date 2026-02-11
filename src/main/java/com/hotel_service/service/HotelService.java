package com.hotel_service.service;

import com.hotel_service.dto.HotelCreateRequest;
import com.hotel_service.dto.HotelResponse;
import com.hotel_service.model.Hotel;
import com.hotel_service.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelResponse createHotel(HotelCreateRequest request) {
        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(request,hotel);
        hotelRepository.save(hotel);
        HotelResponse response = new HotelResponse();
        BeanUtils.copyProperties(hotel,response);

        return response;

    }
}
