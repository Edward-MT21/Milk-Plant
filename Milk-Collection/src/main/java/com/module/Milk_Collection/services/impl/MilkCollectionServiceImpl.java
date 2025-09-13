package com.module.Milk_Collection.services.impl;

import com.module.Common.dtos.MilkCollectionRecordDTO;
import com.module.Common.dtos.MilkSupplierCollectionDTO;
import com.module.Milk_Collection.exception.ResourceNotFoundException;
import com.module.Milk_Collection.mapper.MilkCollectionMapper;
import com.module.Milk_Collection.model.dtos.*;
import com.module.Milk_Collection.model.entities.MilkCollection;
import com.module.Milk_Collection.repositories.IMilkCollectionRepository;
import com.module.Milk_Collection.services.IMilkCollectionService;
import com.module.Milk_Collection.services.IMilkSupplierService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MilkCollectionServiceImpl implements IMilkCollectionService {

    private static final Logger logger = LoggerFactory.getLogger(MilkCollectionServiceImpl.class);
    private final IMilkCollectionRepository iMilkCollectionRepository;
    private final IMilkSupplierService iMilkSupplierService;

    @Override
    public List<MilkCollectionDto> fetchAllMilkCollection() {
        logger.debug("Start fetchAllMilkCollection");

        logger.debug("End fetchAllMilkCollection");
        return iMilkCollectionRepository.findAll().stream().map(MilkCollectionMapper::mapToMilkCollectionDto).toList();
    }

    @Override
    public List<MilkCollectionDetailsDto> fetchAllMilkCollectionDetails() {
        logger.debug("Start fetchAllMilkCollectionDetails");

        logger.debug("End fetchAllMilkCollectionDetails");
        return iMilkCollectionRepository.findAll().stream().map(
                milkCollection -> {
                    MilkCollectionDetailsDto milkCollectionDetailsDto = getMilkCollectionDetailsDto(milkCollection);
                    return milkCollectionDetailsDto;
                }
        ).toList();
    }

    private MilkCollectionDetailsDto getMilkCollectionDetailsDto(MilkCollection milkCollection) {
        logger.debug("Start getMilkCollectionDetailsDto");

        MilkCollectionDetailsDto milkCollectionDetailsDto = new MilkCollectionDetailsDto();
        milkCollectionDetailsDto.setMilkCollectionId(milkCollection.getMilkCollectionId());
        MilkSupplierDetailsDto milkSupplierDetailsDto = iMilkSupplierService.fetchMilkSupplierDetailsById(milkCollection.getMilkSupplierId(), "0");
        milkCollectionDetailsDto.setMilkSupplierDetailsDto(milkSupplierDetailsDto);
        milkCollectionDetailsDto.setLitersMilk(milkCollection.getLitersMilk());

        logger.debug("End getMilkCollectionDetailsDto");
        return milkCollectionDetailsDto;
    }

    @Override
    @Transactional
    public void createMilkCollection(MilkCollectionDto milkCollectionDto) {
        logger.debug("Start createMilkCollection");

        MilkCollection milkCollection = MilkCollectionMapper.mapToMilkCollection(milkCollectionDto);

        Optional<MilkCollection> existing = iMilkCollectionRepository
                .findByMilkSupplierIdAndCollectionDate(milkCollectionDto.getMilkSupplierId(), milkCollectionDto.getCollectionDate());

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Ya existe un registro para este proveedor y fecha.");
        }

        iMilkCollectionRepository.saveAndFlush(milkCollection);

        logger.debug("End createMilkCollection");
    }

    @Override
    @Transactional
    public void updateMilkCollection(MilkCollectionDto milkCollectionDto) {
        logger.debug("Start updateMilkCollection");

        iMilkCollectionRepository.findById(milkCollectionDto.getMilkCollectionId())
                .ifPresentOrElse(
                        milkCollection -> {
                            milkCollection.setLitersMilk(milkCollectionDto.getLitersMilk());
                            iMilkCollectionRepository.saveAndFlush(milkCollection);
                            },
                        () -> { throw new ResourceNotFoundException("MilkCollection", "MilkCollectionId", milkCollectionDto.getMilkCollectionId().toString()); }
                );

        logger.debug("End updateMilkCollection");
    }

    @Override
    public List<MilkCollectionDetailsDto> fetchAllMilkCollectionDetailsByCollectionDate(LocalDate date) {
        logger.debug("Start fetchAllMilkCollectionDetailsByCollectionDate");

        logger.debug("End fetchAllMilkCollectionDetailsByCollectionDate");
        return iMilkCollectionRepository.findAllByCollectionDate(date)
                .stream()
                .map(this::getMilkCollectionDetailsDto)
                .toList();
    }

    @Override
    public List<MilkSupplierCollectionDTO> fetchMilkSupplierCollectionByCollectionDateRange(LocalDate startDate, LocalDate endDate) {
        logger.debug("Start fetchMilkSupplierCollectionByCollectionDateRange");

        List<MilkCollection> collections = iMilkCollectionRepository.findAllByCollectionDateBetween(startDate, endDate);

        // Agrupar por milkSupplierId
        Map<Long, List<MilkCollection>> grouped = collections.stream()
                .collect(Collectors.groupingBy(MilkCollection::getMilkSupplierId));

        List<MilkSupplierCollectionDTO> result = new ArrayList<>();

        for (Map.Entry<Long, List<MilkCollection>> entry : grouped.entrySet()) {
            Long milkSupplierId = entry.getKey();
            List<MilkCollection> milkCollections = entry.getValue();

            // Calcular el total de litros para este proveedor
            Integer totalLitersMilk = milkCollections.stream()
                    .mapToInt(MilkCollection::getLitersMilk)
                    .sum();

            List<MilkCollectionRecordDTO> recordDTOs = milkCollections.stream().map(mc -> {
                MilkCollectionRecordDTO dto = new MilkCollectionRecordDTO();
                dto.setCreatedAt(mc.getCreatedAt());
                dto.setLitersMilk(mc.getLitersMilk());
                dto.setCollectionDate(mc.getCollectionDate());
                return dto;
            }).collect(Collectors.toList());

            MilkSupplierDetailsDto milkSupplierDetailsDto = iMilkSupplierService.fetchMilkSupplierDetailsById(milkSupplierId, "0");
            result.add(MilkSupplierCollectionDTO.builder()
                    .milkSupplierId(milkSupplierId)
                    .personOutDto(milkSupplierDetailsDto.getPersonOutDto())
                    .collections(recordDTOs)
                    .totalLitersMilk(totalLitersMilk)
                    .build());
        }

        logger.debug("End fetchMilkSupplierCollectionByCollectionDateRange");
        return result;
    }

    @Override
    @Transactional
    public boolean deleteMilkCollectionById(Long milkCollectionId) {
        logger.debug("Start deleteMilkCollectionById");

        logger.debug("End deleteMilkCollectionById");
        iMilkCollectionRepository.deleteById(milkCollectionId);
        return true;
    }


}
