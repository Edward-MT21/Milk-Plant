package com.module.Financial_Management.services.impl;

import com.module.Common.dtos.MilkSupplierCollectionDTO;
import com.module.Financial_Management.model.dtos.InfoMilkSupplierPaymentDto;
import com.module.Financial_Management.model.entities.MilkSupplierPayment;
import com.module.Financial_Management.model.enums.ProductEnum;
import com.module.Financial_Management.repositories.IMilkSupplierPaymentRepository;
import com.module.Financial_Management.services.IMilkSupplierPaymentService;
import com.module.Financial_Management.services.IProductService;
import com.module.Financial_Management.services.client.IMilkCollectionFeignClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MilkSupplierPaymentServiceImpl implements IMilkSupplierPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(MilkSupplierPaymentServiceImpl.class);
    private final IMilkCollectionFeignClient iMilkCollectionFeignClient;
    private final IProductService iProductService;
    private final IMilkSupplierPaymentRepository iMilkSupplierPaymentRepository;

    public List<InfoMilkSupplierPaymentDto> getBiweeklyInfoMilkSupplierPayment(LocalDate startDate, LocalDate endDate) {
        logger.debug("Start getBiweeklyInfoMilkSupplierPayment");

        ResponseEntity<List<MilkSupplierCollectionDTO>> response =
                iMilkCollectionFeignClient.fetchMilkSupplierCollectionByCollectionDateRange(startDate, endDate);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Error al obtener datos de recolección de leche");
        }

        BigDecimal pricePerLiter = iProductService.getProductById(ProductEnum.RAW_MILK.getIdProduct()).getPrice();

        logger.debug("End getBiweeklyInfoMilkSupplierPayment");
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

    @Override
    @Transactional
    public boolean deleteMilkSupplierPaymentById(Long milkSupplierPaymentId) {
        logger.debug("Start deleteMilkSupplierPaymentById");

        MilkSupplierPayment milkSupplierPayment = iMilkSupplierPaymentRepository.findById(milkSupplierPaymentId).orElseThrow(() -> new RuntimeException("MilkSupplierPayment not found"));
        iMilkSupplierPaymentRepository.delete(milkSupplierPayment);

        logger.debug("End deleteMilkSupplierPaymentById");
        return true;
    }

    @Override
    @Transactional
    public boolean deleteAllMilkSupplierPaymentByMilkSupplierId(Long milkSupplierId) {
        logger.debug("Start deleteAllMilkSupplierPaymentByMilkSupplierId");

        iMilkSupplierPaymentRepository.deleteAllByMilkSupplierId(milkSupplierId);

        logger.debug("End deleteAllMilkSupplierPaymentByMilkSupplierId");
        return true;
    }

}
