package com.module.Milk_Collection.services.impl;

import com.module.Common.dtos.ResponseDto;
import com.module.Milk_Collection.exception.MilkSupplierAlreadyExistsException;
import com.module.Milk_Collection.exception.ResourceNotFoundException;
import com.module.Milk_Collection.mapper.MilkSupplierMapper;
import com.module.Milk_Collection.model.dtos.*;
import com.module.Milk_Collection.model.entities.MilkSupplier;
import com.module.Milk_Collection.repositories.IMilkCollectionRepository;
import com.module.Milk_Collection.repositories.IMilkSupplierRepository;
import com.module.Milk_Collection.services.IMilkSupplierService;
import com.module.Milk_Collection.services.client.IFinancialManagementFeingClient;
import com.module.Milk_Collection.services.client.IPersonsFeingClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.module.Common.dtos.PersonOutDto;

@Service
@AllArgsConstructor
public class MilkSupplierServiceImpl implements IMilkSupplierService {

    private static final Logger logger = LoggerFactory.getLogger(MilkSupplierServiceImpl.class);
    private final IMilkSupplierRepository iMilkSupplierRepository;
    private final IPersonsFeingClient iPersonsFeingClient;
    private final IFinancialManagementFeingClient iFinancialManagementFeingClient;
    private final StreamBridge streamBridge;
    private final IMilkCollectionRepository iMilkCollectionRepository;

    @Override
    @Transactional
    public void createMilkSupplier(MilkSupplierInDto milkSupplierInDto) {
        logger.debug("Start createMilkSupplier");

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

        logger.debug("End createMilkSupplier");
    }

    private void sendCommunication(MilkSupplier milkSupplier) {
        logger.debug("Start sendCommunication");

        var milkCollectionMsgDto = new MilkCollectionMsgDto(milkSupplier.getMilkSupplierId(), milkSupplier.getPersonId());
        logger.info("Sending Communication request for the details: {}", milkCollectionMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", milkCollectionMsgDto);
        logger.info("Is the Communication request successfully triggered ? : {}", result);

        logger.debug("End sendCommunication");
    }

    @Override
    public MilkSupplierOutDto fetchMilkSupplierById(Long milkSupplierId) {
        logger.debug("Start fetchMilkSupplierById");

        MilkSupplier milkSupplier = iMilkSupplierRepository.findById(milkSupplierId).orElseThrow(
                () -> new ResourceNotFoundException("MilkSupplier", "milkSupplierId", milkSupplierId.toString())
        );

        logger.debug("End fetchMilkSupplierById");
        return MilkSupplierMapper.mapToMilkSupplierOutDto(milkSupplier, new MilkSupplierOutDto());
    }

    @Override
    public MilkSupplierOutDto fetchMilkSupplierByPersonId(Long personId) {
        logger.debug("Start fetchMilkSupplierByPersonId");

        MilkSupplier milkSupplier = iMilkSupplierRepository.findByPersonId(personId).orElseThrow(
                () -> new ResourceNotFoundException("MilkSupplier", "personId", personId.toString())
        );

        logger.debug("End fetchMilkSupplierByPersonId");
        return MilkSupplierMapper.mapToMilkSupplierOutDto(milkSupplier, new MilkSupplierOutDto());
    }

    @Override
    @Transactional
    public boolean updateMilkSupplier(MilkSupplierInDto milkSupplierInDto) {
        logger.debug("Start updateMilkSupplier");

        Long milkSupplierId = milkSupplierInDto.getMilkSupplierId();
        MilkSupplier milkSupplier = iMilkSupplierRepository.findById(milkSupplierId).orElseThrow(
                () -> new ResourceNotFoundException("MilkSupplier", "MilkSupplierId", milkSupplierId.toString())
        );
        MilkSupplierMapper.mapToMilkSupplier(milkSupplierInDto, milkSupplier);
        iMilkSupplierRepository.save(milkSupplier);

        logger.debug("End updateMilkSupplier");
        return true;
    }

    @Override
    @Transactional
    public boolean deleteMilkSupplierById(Long milkSupplierId) {
        logger.debug("Start deleteMilkSupplierById");

        ResponseEntity<ResponseDto> feignResponse = iFinancialManagementFeingClient
                .deleteAllMilkSupplierPaymentByMilkSupplierId(milkSupplierId);

        boolean feignSuccess = feignResponse.getStatusCode() == HttpStatus.OK &&
                "200".equals(feignResponse.getBody().getStatusCode());

        if (!feignSuccess) {
            return false;
        }

        iMilkCollectionRepository.deleteAllByMilkSupplierId(milkSupplierId);
        iMilkSupplierRepository.deleteById(milkSupplierId);

        logger.debug("End deleteMilkSupplierById");
        return true;
    }

    @Override
    @Transactional
    public boolean deleteMilkSupplierByPersonId(Long personId) {
        logger.debug("Start deleteMilkSupplierByPersonId");

        MilkSupplier milkSupplier = iMilkSupplierRepository.findByPersonId(personId).orElseThrow(() -> new RuntimeException("MilkSupplier not found"));

        logger.debug("End deleteMilkSupplierByPersonId");
        return deleteMilkSupplierById(milkSupplier.getMilkSupplierId());
    }

    @Override
    public MilkSupplierDetailsDto fetchMilkSupplierDetailsById(Long milkSupplierId, String correlationId) {
        logger.debug("Start fetchMilkSupplierDetailsById");

        MilkSupplier milkSupplier = iMilkSupplierRepository.findById(milkSupplierId).orElseThrow(
                () -> new ResourceNotFoundException("MilkSupplier", "milkSupplierId", milkSupplierId.toString())
        );

        ResponseEntity<PersonOutDto> personOutDtoResponseEntity = iPersonsFeingClient.fetchPersonById(correlationId, milkSupplier.getPersonId());
        String greetingFinancialManagement = iFinancialManagementFeingClient.getGreeting(correlationId);

        logger.debug("End fetchMilkSupplierDetailsById");
        return MilkSupplierMapper.mapToMilkSupplierDetailsDto(milkSupplier, personOutDtoResponseEntity, greetingFinancialManagement);
    }

    @Override
    @Transactional
    public boolean updateCommunicationStatus(Long milkSupplierId) {
        logger.debug("Start updateCommunicationStatus");

        if (milkSupplierId == null) {
            logger.debug("End updateCommunicationStatus - milkSupplierId is null");
            return false;
        }

        MilkSupplier milkSupplier = iMilkSupplierRepository.findById(milkSupplierId)
                .orElseThrow(() -> new ResourceNotFoundException("milkSupplier", "milkSupplierId", milkSupplierId.toString()));

        milkSupplier.setCommunicationSw(true);
        iMilkSupplierRepository.save(milkSupplier);

        logger.debug("End updateCommunicationStatus - Communication status updated for milkSupplierId: {}", milkSupplierId);
        return true;
    }

    @Override
    public List<MilkSupplierDetailsDto> fetchAllMilkSupplierDetails() {
        logger.debug("Start fetchAllMilkSupplierDetails");

        logger.debug("End fetchAllMilkSupplierDetails");
        return iMilkSupplierRepository.findAll().stream().map(milkSupplier -> fetchMilkSupplierDetailsById(milkSupplier.getMilkSupplierId(), "0")).toList();
    }




}
