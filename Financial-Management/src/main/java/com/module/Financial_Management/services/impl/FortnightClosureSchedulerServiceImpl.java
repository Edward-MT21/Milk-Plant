package com.module.Financial_Management.services.impl;

import com.module.Financial_Management.model.dtos.InfoMilkSupplierPaymentDto;
import com.module.Financial_Management.model.entities.MilkSupplierPayment;
import com.module.Financial_Management.model.enums.PaymentStatusEnum;
import com.module.Financial_Management.repositories.IMilkSupplierPaymentRepository;
import com.module.Financial_Management.services.IMilkSupplierPaymentService;
import com.module.Financial_Management.services.IFortnightClosureSchedulerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FortnightClosureSchedulerServiceImpl implements IFortnightClosureSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(FortnightClosureSchedulerServiceImpl.class);
    private final IMilkSupplierPaymentService iMilkSupplierPaymentService;
    private final IMilkSupplierPaymentRepository iMilkSupplierPaymentRepository;

    @Override
    @Transactional
    public void executeMilkSupplierFortnightClosure(LocalDate closureDate) {
        logger.info("Start executeMilkSupplierFortnightClosure, closureDate: {}", closureDate);

        LocalDate startDate = closureDate.getDayOfMonth() <= 15
                ? closureDate.withDayOfMonth(1)
                : closureDate.withDayOfMonth(16);
        LocalDate endDate = closureDate;

        List<InfoMilkSupplierPaymentDto> payments = iMilkSupplierPaymentService.getBiweeklyInfoMilkSupplierPayment(startDate, endDate);
        logger.debug("Found {} payments to process for the period {} to {}.", payments.size(), startDate, endDate);

        if (payments.isEmpty()) {
            logger.info("No payments to process for the period. Exiting.");
            return;
        }

        List<MilkSupplierPayment> entities = payments.stream()
                .map(dto -> MilkSupplierPayment.builder()
                        .milkSupplierId(dto.getMilkSupplierId())
                        .startDate(startDate)
                        .endDate(endDate)
                        .totalLitersMilk(dto.getTotalLitersMilk())
                        .pricePerLiter(dto.getPricePerLiter())
                        .totalAmount(dto.getTotalAmount())
                        .paymentStatusEnum(PaymentStatusEnum.PENDING)
                        .build())
                .collect(Collectors.toList());

        iMilkSupplierPaymentRepository.saveAll(entities);
        logger.info("End executeMilkSupplierFortnightClosure, saved: {}", entities.size());
    }
}
