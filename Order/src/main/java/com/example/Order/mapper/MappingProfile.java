package com.example.Order.mapper;

import com.example.Order.dto.RentalItemRequestDto;
import com.example.Order.dto.RentalItemResponseDto;
import com.example.Order.dto.RentalRequestDto;
import com.example.Order.dto.RentalResponseDto;
import com.example.Order.entity.Rental;
import com.example.Order.entity.RentalItem;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;


public class MappingProfile {
    private static final ModelMapper modelMapper = new ModelMapper();



    static {
        modelMapper.addMappings(new PropertyMap<RentalItem, RentalItemResponseDto>() {
            @Override
            protected void configure() {
                // Map RentalItem's Rental's RentalId to RentalItemResponseDto's RentalId
                map().setRentalId(source.getRental().getRentalId());
            }
        });

        modelMapper.addMappings(new PropertyMap<RentalItemRequestDto, RentalItem>() {
            @Override
            protected void configure() {
                // Map productId from RentalItemRequestDto to RentalItem
                map().setProductId(source.getProductId());
            }
        });
    }



    public static Rental mapToRentalEntity(RentalRequestDto rentalRequest){
        return modelMapper.map(rentalRequest, Rental.class);
    }

    public static RentalResponseDto mapToRentalDto(Rental rental){
        return modelMapper.map(rental, RentalResponseDto.class);
    }

    public static RentalItem mapToRentalItemEntity(RentalItemRequestDto rentalItemRequest){
        return modelMapper.map(rentalItemRequest, RentalItem.class);
    }
    public static RentalItemResponseDto mapToRentalItemDto(RentalItem rentalItem){
        return modelMapper.map(rentalItem, RentalItemResponseDto.class);
    }
}
