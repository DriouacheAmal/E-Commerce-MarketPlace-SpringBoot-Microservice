package com.example.Payment.mapper;

import com.example.Payment.dto.PaymentDto;
import com.example.Payment.entity.Payment;
import org.modelmapper.ModelMapper;

public class MappingProfile {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Payment mapToPaymentEntity(PaymentDto paymentDto){
        return modelMapper.map(paymentDto, Payment.class);
    }

    public static PaymentDto mapToPaymentDto(Payment payment){
        return modelMapper.map(payment, PaymentDto.class);
    }



}
