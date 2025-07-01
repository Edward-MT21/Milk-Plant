package com.module.Milk_Collection.functions;

import com.module.Milk_Collection.services.IMilkSupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MillCollectionFunctions {

    private static final Logger log = LoggerFactory.getLogger(MillCollectionFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(IMilkSupplierService iMilkSupplierService) {
        return milkSupplierId -> {
            log.info("Updating Communication status for the milkSupplierId : " + milkSupplierId.toString());
            iMilkSupplierService.updateCommunicationStatus(milkSupplierId);
        };
    }

}
