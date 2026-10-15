package com.module.Milk_Collection.listeners;

import com.module.Milk_Collection.services.IMilkSupplierService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import java.util.function.Consumer;

@Configuration
public class PersonDeletedEventListener {


    private final IMilkSupplierService iMilkSupplierService;

    public PersonDeletedEventListener(IMilkSupplierService iMilkSupplierService) {
        this.iMilkSupplierService = iMilkSupplierService;
    }

    // Este Bean escucha el evento que viene de persons (configurado en el yml como personDeleted-in-0)
    @Bean
    public Consumer<Long> personDeleted() {
//        return event -> {
//            // Llamamos al método lógico pasando el personId recibido del evento
//            iMilkSupplierService.inactivateMilkSupplierByPersonId(event);
//        };
        return iMilkSupplierService::inactivateMilkSupplierByPersonId; //Simplify
    }

}
