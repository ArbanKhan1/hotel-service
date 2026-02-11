package com.hotel_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class HotelResponse {
    private String name;
    private String description;
    private String city;
    private BigDecimal pricePerNight;
    private Integer rating;
    private boolean isActive;
    private LocalDateTime createdAt;
}
