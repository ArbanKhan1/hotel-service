package com.hotel_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImagesDto {
    private List<String> images = new ArrayList<>();
}
