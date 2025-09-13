package com.module.Milk_Collection.services;

import com.module.Common.dtos.MilkSupplierCollectionDTO;
import com.module.Milk_Collection.model.dtos.MilkCollectionDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;

import java.time.LocalDate;
import java.util.List;

public interface IMilkCollectionService {

    List<MilkCollectionDto> fetchAllMilkCollection();

    List<MilkCollectionDetailsDto> fetchAllMilkCollectionDetails();

    List<MilkCollectionDetailsDto> fetchAllMilkCollectionDetailsByCollectionDate(LocalDate date);

    void createMilkCollection(MilkCollectionDto milkCollectionDto);

    void updateMilkCollection(MilkCollectionDto milkCollectionDto);

    List<MilkSupplierCollectionDTO> fetchMilkSupplierCollectionByCollectionDateRange(LocalDate startDate, LocalDate endDate);


    boolean deleteMilkCollectionById(Long milkCollectionId);
}
