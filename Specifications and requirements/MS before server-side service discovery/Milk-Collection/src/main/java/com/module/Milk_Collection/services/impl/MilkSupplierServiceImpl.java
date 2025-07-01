package com.module.Milk_Collection.services.impl;

import com.module.Milk_Collection.exception.MilkSupplierAlreadyExistsException;
import com.module.Milk_Collection.exception.ResourceNotFoundException;
import com.module.Milk_Collection.mapper.MilkSupplierMapper;
import com.module.Milk_Collection.model.dtos.*;
import com.module.Milk_Collection.model.entities.MilkSupplier;
import com.module.Milk_Collection.repositories.IMilkSupplierRepository;
import com.module.Milk_Collection.services.IMilkSupplierService;
import com.module.Milk_Collection.services.client.IFinancialManagementFeingClient;
import com.module.Milk_Collection.services.client.IPersonsFeingClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MilkSupplierServiceImpl implements IMilkSupplierService {

    private static final Logger log = LoggerFactory.getLogger(MilkSupplierServiceImpl.class);

    IMilkSupplierRepository iMilkSupplierRepository;
    IPersonsFeingClient iPersonsFeingClient;
    IFinancialManagementFeingClient iFinancialManagementFeingClient;
    private final StreamBridge streamBridge;

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
        MilkSupplier savedMilkSupplier = iMilkSupplierRepository.saveAndFlush(milkSupplier);
        sendCommunication(savedMilkSupplier);
    }

    private void sendCommunication(MilkSupplier milkSupplier) {
        var milkCollectionMsgDto = new MilkCollectionMsgDto(milkSupplier.getMilkSupplierId(), milkSupplier.getPersonId());
        log.info("Sending Communication request for the details: {}", milkCollectionMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", milkCollectionMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
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

    @Override
    public MilkSupplierDetailsDto fetchMilkSupplierDetailsById(Long milkSupplierId, String correlationId) {

        MilkSupplier milkSupplier = iMilkSupplierRepository.findById(milkSupplierId).orElseThrow(
                () -> new ResourceNotFoundException("MilkSupplier", "milkSupplierId", milkSupplierId.toString())
        );

        ResponseEntity<PersonOutDto> personOutDtoResponseEntity = iPersonsFeingClient.fetchPersonById(correlationId, milkSupplier.getPersonId());
        String greetingFinancialManagement = iFinancialManagementFeingClient.getGreeting(correlationId);

        return MilkSupplierMapper.mapToMilkSupplierDetailsDto(milkSupplier, personOutDtoResponseEntity, greetingFinancialManagement);
    }

    @Override
    public boolean updateCommunicationStatus(Long milkSupplierId) {
        boolean isUpdated = false;
        if(milkSupplierId !=null ){
            MilkSupplier milkSupplier = iMilkSupplierRepository.findById(milkSupplierId).orElseThrow(
                    () -> new ResourceNotFoundException("milkSupplier", "milkSupplierId", milkSupplierId.toString())
            );
            milkSupplier.setCommunicationSw(true);
            iMilkSupplierRepository.save(milkSupplier);
            isUpdated = true;
        }
        return  isUpdated;
    }




}
