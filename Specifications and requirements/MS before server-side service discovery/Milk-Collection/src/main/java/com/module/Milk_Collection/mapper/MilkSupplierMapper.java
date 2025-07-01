package com.module.Milk_Collection.mapper;

import com.module.Milk_Collection.model.dtos.MilkSupplierDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierInDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierOutDto;
import com.module.Milk_Collection.model.dtos.PersonOutDto;
import com.module.Milk_Collection.model.entities.MilkSupplier;
import org.springframework.http.ResponseEntity;

public class MilkSupplierMapper {

    public static MilkSupplierOutDto mapToMilkSupplierOutDto(MilkSupplier milkSupplier, MilkSupplierOutDto milkSupplierOutDto) {
        milkSupplierOutDto.setMilkSupplierId(milkSupplier.getMilkSupplierId());
        milkSupplierOutDto.setPersonId(milkSupplier.getPersonId());
        return milkSupplierOutDto;
    }

    public static MilkSupplier mapToMilkSupplier(MilkSupplierInDto milkSupplierInDto, MilkSupplier milkSupplier) {
        //milkSupplier.setMilkSupplierId(milkSupplierInDto.getMilkSupplierId());
        milkSupplier.setPersonId(milkSupplierInDto.getPersonId());
        return milkSupplier;
    }

    public static MilkSupplierDetailsDto mapToMilkSupplierDetailsDto(MilkSupplier milkSupplier, ResponseEntity<PersonOutDto> personOutDtoResponseEntity, String greetingFinancialManagement) {

        MilkSupplierDetailsDto milkSupplierDetailsDto = new MilkSupplierDetailsDto();
        milkSupplierDetailsDto.setMilkSupplierId(milkSupplier.getMilkSupplierId());
        if(personOutDtoResponseEntity != null) {
            milkSupplierDetailsDto.setPersonOutDto(personOutDtoResponseEntity.getBody());
        }
        milkSupplierDetailsDto.setGreetingFinancialManagement(greetingFinancialManagement);

        return milkSupplierDetailsDto;
    }

}
