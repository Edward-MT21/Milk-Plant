package com.module.Financial_Management.services.impl;

import com.module.Common.dtos.MilkSupplierCollectionDTO;
import com.module.Financial_Management.model.dtos.InfoMilkSupplierPaymentDto;
import com.module.Financial_Management.model.enums.ProductEnum;
import com.module.Financial_Management.services.IMilkSupplierPaymentService;
import com.module.Financial_Management.services.IProductService;
import com.module.Financial_Management.services.client.IMilkCollectionFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MilkSupplierPaymentServiceImpl implements IMilkSupplierPaymentService {

    private final IMilkCollectionFeignClient iMilkCollectionFeignClient;
    private final IProductService iProductService;

    public List<InfoMilkSupplierPaymentDto> getBiweeklyInfoMilkSupplierPayment(LocalDate startDate, LocalDate endDate) {
        ResponseEntity<List<MilkSupplierCollectionDTO>> response =
                iMilkCollectionFeignClient.fetchMilkSupplierCollectionByDateRange(startDate, endDate);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Error al obtener datos de recolección de leche");
        }

        BigDecimal pricePerLiter = iProductService.getProductById(ProductEnum.RAW_MILK.getIdProduct()).getPrice();

        return response.getBody().stream()
                .map(collection -> {
                    BigDecimal totalAmount = Optional.ofNullable(collection.getTotalLitersMilk())
                            .map(liters -> pricePerLiter.multiply(BigDecimal.valueOf(liters)))
                            .orElse(BigDecimal.ZERO);

                    return new InfoMilkSupplierPaymentDto(
                            collection.getMilkSupplierId(),
                            collection.getPersonOutDto(),
                            collection.getCollections(),
                            collection.getTotalLitersMilk(),
                            totalAmount,
                            pricePerLiter
                    );
                })
                .collect(Collectors.toList());
    }

}
