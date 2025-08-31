package com.module.Financial_Management.schedulers;

import com.module.Financial_Management.model.dtos.InfoMilkSupplierPaymentDto;
import com.module.Financial_Management.model.entities.MilkSupplierPayment;
import com.module.Financial_Management.model.enums.PaymentStatusEnum;
import com.module.Financial_Management.repositories.IMilkSupplierPaymentRepository;
import com.module.Financial_Management.services.IMilkSupplierPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FortnightClosureScheduler {

    private final IMilkSupplierPaymentService iMilkSupplierPaymentService;
    private final IMilkSupplierPaymentRepository iMilkSupplierPaymentRepository;

    @Scheduled(cron = "59 23 15,28,30,31 * ?")
    public void executeMilkSupplierFortnightClosure() {

        LocalDate today = LocalDate.now();
        boolean isDay15 = today.getDayOfMonth() == 15;
        boolean isLastDay = today.equals(today.withDayOfMonth(today.lengthOfMonth()));

        if (isDay15 || isLastDay) {
            executeClosure(today);
        }
    }


    private void executeClosure(LocalDate cierreDate) {
        LocalDate startDate = cierreDate.getDayOfMonth() <= 15
                ? cierreDate.withDayOfMonth(1)
                : cierreDate.withDayOfMonth(16);
        LocalDate endDate = cierreDate;

        List<InfoMilkSupplierPaymentDto> pagos = iMilkSupplierPaymentService.getBiweeklyInfo(startDate, endDate);

        List<MilkSupplierPayment> entities = pagos.stream()
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
    }


}
