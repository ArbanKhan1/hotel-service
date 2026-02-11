package com.hotel_service.exceptions;

public class NoHotelFound extends RuntimeException{
    public NoHotelFound(String message) {
        super(message);
    }
}
