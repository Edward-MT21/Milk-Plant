package com.module.Milk_Collection.mapper;

import com.module.Common.dtos.PersonOutDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierInDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierOutDto;
import com.module.Milk_Collection.model.entities.MilkSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class MilkSupplierMapper {

    private static final Logger logger = LoggerFactory.getLogger(MilkSupplierMapper.class);

    public static MilkSupplierOutDto mapToMilkSupplierOutDto(MilkSupplier milkSupplier, MilkSupplierOutDto milkSupplierOutDto) {
        logger.debug("Start mapToMilkSupplierOutDto");

        milkSupplierOutDto.setMilkSupplierId(milkSupplier.getMilkSupplierId());
        milkSupplierOutDto.setPersonId(milkSupplier.getPersonId());

        logger.debug("End mapToMilkSupplierOutDto");
        return milkSupplierOutDto;
    }

    public static MilkSupplier mapToMilkSupplier(MilkSupplierInDto milkSupplierInDto, MilkSupplier milkSupplier) {
        logger.debug("Start mapToMilkSupplier");

        //milkSupplier.setMilkSupplierId(milkSupplierInDto.getMilkSupplierId());
        milkSupplier.setPersonId(milkSupplierInDto.getPersonId());

        logger.debug("End mapToMilkSupplier");
        return milkSupplier;
    }

    public static MilkSupplierDetailsDto mapToMilkSupplierDetailsDto(MilkSupplier milkSupplier, ResponseEntity<PersonOutDto> personOutDtoResponseEntity, String greetingFinancialManagement) {
        logger.debug("Start mapToMilkSupplierDetailsDto");

        MilkSupplierDetailsDto milkSupplierDetailsDto = new MilkSupplierDetailsDto();
        milkSupplierDetailsDto.setMilkSupplierId(milkSupplier.getMilkSupplierId());
        if(personOutDtoResponseEntity != null) {
            milkSupplierDetailsDto.setPersonOutDto(personOutDtoResponseEntity.getBody());
        }
        milkSupplierDetailsDto.setGreetingFinancialManagement(greetingFinancialManagement);

        logger.debug("End mapToMilkSupplierDetailsDto");
        return milkSupplierDetailsDto;
    }


}
