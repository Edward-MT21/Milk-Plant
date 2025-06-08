package com.module.Milk_Collection.services.impl;

import com.module.Milk_Collection.exception.MilkSupplierAlreadyExistsException;
import com.module.Milk_Collection.exception.ResourceNotFoundException;
import com.module.Milk_Collection.mapper.MilkSupplierMapper;
import com.module.Milk_Collection.model.dtos.MilkSupplierInDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierOutDto;
import com.module.Milk_Collection.model.entities.MilkSupplier;
import com.module.Milk_Collection.repositories.IMilkSupplierRepository;
import com.module.Milk_Collection.services.IMilkSupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MilkSupplierServiceImpl implements IMilkSupplierService {

    IMilkSupplierRepository iMilkSupplierRepository;

    @Override
    public void createMilkSupplier(MilkSupplierInDto milkSupplierInDto) {
        MilkSupplier milkSupplier = MilkSupplierMapper.mapToMilkSupplier(milkSupplierInDto, new MilkSupplier());
        if(milkSupplierInDto.getMilkSupplierId() != null) {
            Optional<MilkSupplier> optionalCustomer = iMilkSupplierRepository.findById(milkSupplierInDto.getMilkSupplierId());
            if(optionalCustomer.isPresent()) {
                throw new MilkSupplierAlreadyExistsException("MilkSupplier already registered with given MilkSupplierId "
                        + milkSupplierInDto.getMilkSupplierId());
            }
        }
        iMilkSupplierRepository.saveAndFlush(milkSupplier);
    }

    @Override
    public MilkSupplierOutDto fetchMilkSupplierById(Long milkSupplierId) {
        MilkSupplier milkSupplier = iMilkSupplierRepository.findById(milkSupplierId).orElseThrow(
                () -> new ResourceNotFoundException("MilkSupplier", "milkSupplierId", milkSupplierId.toString())
        );

        return MilkSupplierMapper.mapToMilkSupplierOutDto(milkSupplier, new MilkSupplierOutDto());
    }

    @Override
    public MilkSupplierOutDto fetchMilkSupplierByPersonId(Long personId) {
        MilkSupplier milkSupplier = iMilkSupplierRepository.findByPersonId(personId).orElseThrow(
                () -> new ResourceNotFoundException("MilkSupplier", "personId", personId.toString())
        );

        return MilkSupplierMapper.mapToMilkSupplierOutDto(milkSupplier, new MilkSupplierOutDto());
    }

    @Override
    public boolean updateMilkSupplier(MilkSupplierInDto milkSupplierInDto) {
        boolean isUpdated = false;

        Long milkSupplierId = milkSupplierInDto.getMilkSupplierId();
        MilkSupplier milkSupplier = iMilkSupplierRepository.findById(milkSupplierId).orElseThrow(
                () -> new ResourceNotFoundException("MilkSupplier", "MilkSupplierId", milkSupplierId.toString())
        );
        MilkSupplierMapper.mapToMilkSupplier(milkSupplierInDto, milkSupplier);
        iMilkSupplierRepository.save(milkSupplier);
        isUpdated = true;

        return isUpdated;
    }

    @Override
    public boolean deleteMilkSupplierById(Long milkSupplierId) {
        iMilkSupplierRepository.deleteById(milkSupplierId);
        return true;
    }

    @Override
    public boolean deleteMilkSupplierByPersonId(Long personId) {
        iMilkSupplierRepository.deleteByPersonId(personId);
        return true;
    }


}
