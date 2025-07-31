package com.module.Milk_Collection.services;

import com.module.Milk_Collection.model.dtos.MilkCollectionDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;
import com.module.Milk_Collection.model.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IMilkCollectionService {

    List<MilkCollectionDto> fetchAllMilkCollection();

    List<MilkCollectionDetailsDto> fetchAllMilkCollectionDetails();

    List<MilkCollectionDetailsDto> fetchAllMilkCollectionDetailsByDate(LocalDate date);

    void createMilkCollection(MilkCollectionDto milkCollectionDto);

    void updateMilkCollection(MilkCollectionDto milkCollectionDto);
}
