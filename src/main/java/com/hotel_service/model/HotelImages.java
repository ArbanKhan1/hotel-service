package com.hotel_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hotel_images")
public class HotelImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "content_type")
    private String content_type;



}