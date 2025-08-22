package com.module.Financial_Management.controllers;

import com.module.Common.dtos.MilkSupplierCollectionDTO;
import com.module.Financial_Management.model.dtos.InfoMilkSupplierPaymentDto;
import com.module.Financial_Management.model.dtos.ProductDto;
import com.module.Financial_Management.model.enums.ProductEnum;
import com.module.Financial_Management.services.IProductService;
import com.module.Financial_Management.services.client.IMilkCollectionFeignClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/MilkSupplierPaymentController")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MilkSupplierPaymentController {

    private final IMilkCollectionFeignClient iMilkCollectionFeignClient;
    private final IProductService iProductService;

    @GetMapping("/getBiweeklyInfoMilkSupplierPayment")
    public List<InfoMilkSupplierPaymentDto> getBiweeklyInfoMilkSupplierPayment(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        ResponseEntity<List<MilkSupplierCollectionDTO>> responseEntityListMilkSupplierCollectionDTO =
                iMilkCollectionFeignClient.fetchMilkSupplierCollectionByDateRange(startDate, endDate);

        List<MilkSupplierCollectionDTO> listMilkSupplierCollectionDTO = responseEntityListMilkSupplierCollectionDTO.getBody();


        Long rawMilkProductId = ProductEnum.RAW_MILK.getIdProduct(); // Ajusta según corresponda
        ProductDto product = iProductService.getProductById(rawMilkProductId);
        BigDecimal pricePerLiter = product.getPrice();

        return listMilkSupplierCollectionDTO.stream()
                .map(collection -> {
                    InfoMilkSupplierPaymentDto dto = new InfoMilkSupplierPaymentDto();
                    dto.setMilkSupplierId(collection.getMilkSupplierId());
                    dto.setPersonOutDto(collection.getPersonOutDto());
                    dto.setCollections(collection.getCollections());
                    dto.setTotalLitersMilk(collection.getTotalLitersMilk());

                    // Calcular el monto total
                    if (collection.getTotalLitersMilk() != null && pricePerLiter != null) {
                        BigDecimal totalAmount = pricePerLiter.multiply(BigDecimal.valueOf(collection.getTotalLitersMilk()));
                        dto.setTotalAmount(totalAmount);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }


}
