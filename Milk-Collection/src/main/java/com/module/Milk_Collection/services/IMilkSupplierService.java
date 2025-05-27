package com.module.Milk_Collection.services;

import com.module.Milk_Collection.model.dtos.MilkSupplierDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierInDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierOutDto;

public interface IMilkSupplierService {

    /**
     * Creates a new milk supplier entry in the system.
     *
     * @param milkSupplierInDto the data transfer object containing the
     *                          details of the milk supplier to be created
     */
    void createMilkSupplier(MilkSupplierInDto milkSupplierInDto);

    MilkSupplierOutDto fetchMilkSupplierById(Long milkSupplierId);

    MilkSupplierOutDto fetchMilkSupplierByPersonId(Long personId);

    boolean updateMilkSupplier(MilkSupplierInDto milkSupplierInDto);

    boolean deleteMilkSupplierById(Long milkSupplierId);

    boolean deleteMilkSupplierByPersonId(Long personId);

    MilkSupplierDetailsDto fetchMilkSupplierDetailsById(Long milkSupplierId, String correlationId);

}
