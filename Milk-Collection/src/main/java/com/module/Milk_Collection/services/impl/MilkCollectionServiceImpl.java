package com.module.Milk_Collection.services.impl;

import com.module.Common.dtos.ResponseDto;
import com.module.Milk_Collection.exception.ResourceNotFoundException;
import com.module.Milk_Collection.mapper.MilkCollectionMapper;
import com.module.Milk_Collection.model.dtos.*;
import com.module.Milk_Collection.model.entities.MilkCollection;
import com.module.Milk_Collection.repositories.IMilkCollectionRepository;
import com.module.Milk_Collection.repositories.IMilkSupplierRepository;
import com.module.Milk_Collection.services.IMilkCollectionService;
import com.module.Milk_Collection.services.client.IFinancialManagementFeingClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.module.Milk_Collection.mapper.MilkSupplierMapper;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class MilkCollectionServiceImpl implements IMilkCollectionService {

    private IMilkCollectionRepository iMilkCollectionRepository;

    private MilkSupplierServiceImpl milkSupplierServiceImpl;

    private final IFinancialManagementFeingClient iMilkCollectionFeingClient;

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

        Optional<MilkCollection> existing = iMilkCollectionRepository
                .findByMilkSupplierIdAndCollectionDate(milkCollectionDto.getMilkSupplierId(), milkCollectionDto.getCollectionDate());

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Ya existe un registro para este proveedor y fecha.");
        }

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
    public List<MilkCollectionDetailsDto> fetchAllMilkCollectionDetailsByCollectionDate(LocalDate date) {
        return iMilkCollectionRepository.findAllByCollectionDate(date)
                .stream()
                .map(this::getMilkCollectionDetailsDto)
                .toList();
    }

    @Override
    public List<MilkSupplierCollectionDTO> fetchMilkSupplierCollectionByCollectionDateRange(LocalDate startDate, LocalDate endDate) {

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
                return dto;
            }).collect(Collectors.toList());

            MilkSupplierDetailsDto milkSupplierDetailsDto = milkSupplierServiceImpl.fetchMilkSupplierDetailsById(milkSupplierId, "0");
            result.add(MilkSupplierCollectionDTO.builder()
                    .milkSupplierId(milkSupplierId)
                    .personOutDto(milkSupplierDetailsDto.getPersonOutDto())
                    .collections(recordDTOs)
                    .totalLitersMilk(totalLitersMilk)
                    .build());
        }

        return result;

    }

    @Override
    public boolean deleteAllMilkCollectionByMilkSupplierId(Long milkSupplierId) {

        ResponseEntity<ResponseDto> feignResponse = iMilkCollectionFeingClient.deleteAllMilkSupplierPaymentByMilkSupplierId(milkSupplierId);

        boolean feignSuccess = feignResponse.getStatusCode() == HttpStatus.OK &&
                "200".equals(feignResponse.getBody().getStatusCode());

        if (!feignSuccess) {
            return false;
        }

        iMilkCollectionRepository.deleteAllByMilkSupplierId(milkSupplierId);
        return true;
    }

}
