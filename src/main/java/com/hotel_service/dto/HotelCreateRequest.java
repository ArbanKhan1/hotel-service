package com.hotel_service.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class HotelCreateRequest {
    @NotBlank(message = "Hotel name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "100.0", message = "Price must be greater than 100")
    private BigDecimal pricePerNight;

    @NotBlank(message = "City is required")
    private String city;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5,message = "Rating must be between 1 and 5")
    private Integer rating;
}
