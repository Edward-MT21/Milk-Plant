package com.module.Milk_Collection.services.impl;

import com.module.Milk_Collection.exception.ResourceNotFoundException;
import com.module.Milk_Collection.mapper.MilkCollectionMapper;
import com.module.Milk_Collection.model.dtos.MilkCollectionDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierDetailsDto;
import com.module.Milk_Collection.model.entities.MilkCollection;
import com.module.Milk_Collection.repositories.IMilkCollectionRepository;
import com.module.Milk_Collection.repositories.IMilkSupplierRepository;
import com.module.Milk_Collection.services.IMilkCollectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.module.Milk_Collection.mapper.MilkSupplierMapper;

import java.time.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class MilkCollectionServiceImpl implements IMilkCollectionService {

    private IMilkCollectionRepository iMilkCollectionRepository;

    private MilkSupplierServiceImpl milkSupplierServiceImpl;

    @Override
    public List<MilkCollectionDto> fetchAllMilkCollection() {
        return iMilkCollectionRepository.findAll().stream().map(MilkCollectionMapper::mapToMilkCollectionDto).toList();
    }

    @Override
    public List<MilkCollectionDetailsDto> fetchAllMilkCollectionDetails() {

        return iMilkCollectionRepository.findAll().stream().map(
                milkCollection -> {
                    MilkCollectionDetailsDto milkCollectionDetailsDto = getMilkCollectionDetailsDto(milkCollection);
                    return milkCollectionDetailsDto;
                }
        ).toList();
    }

    private MilkCollectionDetailsDto getMilkCollectionDetailsDto(MilkCollection milkCollection) {
        MilkCollectionDetailsDto milkCollectionDetailsDto = new MilkCollectionDetailsDto();
        milkCollectionDetailsDto.setMilkCollectionId(milkCollection.getMilkCollectionId());
        MilkSupplierDetailsDto milkSupplierDetailsDto = milkSupplierServiceImpl.fetchMilkSupplierDetailsById(milkCollection.getMilkSupplierId(), "0");
        milkCollectionDetailsDto.setMilkSupplierDetailsDto(milkSupplierDetailsDto);
        milkCollectionDetailsDto.setLitersMilk(milkCollection.getLitersMilk());
        return milkCollectionDetailsDto;
    }

    @Override
    public void createMilkCollection(MilkCollectionDto milkCollectionDto) {
        MilkCollection milkCollection = MilkCollectionMapper.mapToMilkCollection(milkCollectionDto);
        iMilkCollectionRepository.saveAndFlush(milkCollection);
    }

    @Override
    public void updateMilkCollection(MilkCollectionDto milkCollectionDto) {
        iMilkCollectionRepository.findById(milkCollectionDto.getMilkCollectionId())
                .ifPresentOrElse(
                        milkCollection -> {
                            milkCollection.setLitersMilk(milkCollectionDto.getLitersMilk());
                            iMilkCollectionRepository.saveAndFlush(milkCollection);
                            },
                        () -> { throw new ResourceNotFoundException("MilkCollection", "MilkCollectionId", milkCollectionDto.getMilkCollectionId().toString()); }
                );

    }

    @Override
    public List<MilkCollectionDetailsDto> fetchAllMilkCollectionDetailsByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return iMilkCollectionRepository.findAllByCreatedAtBetween(start, end)
                .stream()
                .map(this::getMilkCollectionDetailsDto)
                .toList();
    }
}
