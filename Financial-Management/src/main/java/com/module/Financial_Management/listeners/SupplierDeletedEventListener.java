package com.module.Financial_Management.listeners;

import com.module.Financial_Management.services.IMilkSupplierPaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class SupplierDeletedEventListener {

    private final IMilkSupplierPaymentService iMilkSupplierPaymentService;

    public SupplierDeletedEventListener(IMilkSupplierPaymentService iMilkSupplierPaymentService) {
        this.iMilkSupplierPaymentService = iMilkSupplierPaymentService;
    }


    @Bean
    Consumer<Long> supplierDeleted() {
        return event -> {
            System.out.println("Supplier deleted event received: " + event);
            iMilkSupplierPaymentService.deleteAllMilkSupplierPaymentByMilkSupplierId(event);
        };
    }

}
