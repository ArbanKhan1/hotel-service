package com.hotel_service.service;

import com.hotel_service.dto.GeneralResponse;
import com.hotel_service.dto.HotelCreateRequest;
import com.hotel_service.dto.HotelResponse;
import com.hotel_service.dto.ImagesDto;
import com.hotel_service.exceptions.NoHotelFound;
import com.hotel_service.model.Hotel;
import com.hotel_service.model.HotelImages;
import com.hotel_service.repository.HotelImagesRepository;
import com.hotel_service.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {

    private static final String BUCKET = "my-s3-imageupload";
    private final HotelRepository hotelRepository;
    private final S3Presigner presigner;
    private final HotelImagesRepository imagesRepository;

    public HotelResponse createHotel(HotelCreateRequest request) {
        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(request,hotel);
        hotelRepository.save(hotel);
        HotelResponse response = new HotelResponse();
        BeanUtils.copyProperties(hotel,response);

        return response;

    }

    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new NoHotelFound("No Hotel Found"));
        HotelResponse response = new HotelResponse();
        BeanUtils.copyProperties(hotel,response);
        return response;
    }

    public Page<Hotel> getHotels(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return hotelRepository.findByIsActiveTrue(pageable);
    }


    public HotelResponse updateHotel(HotelCreateRequest request, Long id) {
        HotelResponse response = new HotelResponse();
        Hotel hotel = hotelRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new NoHotelFound("No Hotel Found"));
        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setCity(request.getCity());
        hotel.setPricePerNight(request.getPricePerNight());
        hotel.setRating(request.getRating());
        hotel.setUpdatedAt(LocalDateTime.now());
        Hotel save = hotelRepository.save(hotel);
        BeanUtils.copyProperties(save,response);
        return response;
    }

    public GeneralResponse deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findByIdAndIsActiveTrue(id).orElseThrow(()->new NoHotelFound("No hotel found"));
        hotel.setActive(false);
        hotelRepository.save(hotel);
        GeneralResponse response = new GeneralResponse();
        response.setMessage("deleted hotel with id = "+ id);
        return response;
    }

    public String generatePreSignedUrl(Long hotelId, String fileName) {
        Hotel hotel = hotelRepository.findByIdAndIsActiveTrue(hotelId).orElseThrow(()->new NoHotelFound("No Hotel Found"));
        String objectKey = "hotels/" + hotelId + "/" + fileName;
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(objectKey)
                .contentType("image/jpeg")
                .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .putObjectRequest(objectRequest)
                        .build();

        PresignedPutObjectRequest presignedRequest =
                presigner.presignPutObject(presignRequest);

        HotelImages images = new HotelImages();
        images.setUrl(objectKey);
        images.setHotel(hotel);
        images.setContent_type("image/jpeg");

        imagesRepository.save(images);

        return presignedRequest.url().toString();
    }

    public ImagesDto getImagesById(Long hotelId) {
        List<HotelImages> hotelImages = imagesRepository.findByHotelId(hotelId);

        ImagesDto res = new ImagesDto();

        if (hotelImages.isEmpty()) {
            throw new RuntimeException("No Images Found");
        }
        hotelImages.forEach(hotel->res.getImages().add("https://my-s3-imageupload.s3.eu-north-1.amazonaws.com/"+
                hotel.getUrl()));

        return res;

    }

}
