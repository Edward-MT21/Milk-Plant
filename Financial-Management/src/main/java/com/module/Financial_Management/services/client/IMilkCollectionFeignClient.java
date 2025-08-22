package com.module.Financial_Management.services.client;

import com.module.Common.dtos.MilkSupplierCollectionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 El atributo name en la anotación @FeignClient tiene dos propósitos principales:
 Identificación del cliente: Es un identificador único para este cliente Feign en el contexto de la aplicación.
 Este nombre se usa internamente por Spring Cloud para:
 Registrar el cliente en el contexto de la aplicación
 Crear un bean con este nombre
 Usarlo para propósitos de logging
 Service Discovery (opcional): Si estás usando un servicio de descubrimiento como Eureka,
 el name se usa para buscar la URL del servicio en el registro de servicios.
 */
@FeignClient(name = "Milk-Collection")
public interface IMilkCollectionFeignClient {

    @GetMapping(value = "/MilkCollectionController/fetch-milk-supplier-collection-by-date-range", produces = "application/json")
    ResponseEntity<List<MilkSupplierCollectionDTO>> fetchMilkSupplierCollectionByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

}


