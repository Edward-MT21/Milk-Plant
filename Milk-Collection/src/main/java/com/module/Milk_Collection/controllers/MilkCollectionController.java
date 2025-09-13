package com.module.Milk_Collection.controllers;

import com.module.Common.dtos.MilkSupplierCollectionDTO;
import com.module.Common.dtos.ResponseDto;
import com.module.Milk_Collection.constants.AccountsConstants;
import com.module.Milk_Collection.model.dtos.MilkCollectionDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;
import com.module.Milk_Collection.services.IMilkCollectionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path="/MilkCollectionController", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class MilkCollectionController {

    private final static Logger logger = LoggerFactory.getLogger(MilkCollectionController.class);
    private final IMilkCollectionService iMilkCollectionService;

    @GetMapping("/fetchAllMilkCollection")
    public ResponseEntity<List<MilkCollectionDto>> fetchAllMilkCollection() {
        logger.debug("Start fetchAllMilkCollection");

        logger.debug("End fetchAllMilkCollection");
        return ResponseEntity.ok(iMilkCollectionService.fetchAllMilkCollection());
    }

    @GetMapping("/fetchAllMilkCollectionDetails")
    public ResponseEntity<List<MilkCollectionDetailsDto>> fetchAllMilkCollectionDetails() {
        logger.debug("Start fetchAllMilkCollectionDetails");

        logger.debug("End fetchAllMilkCollectionDetails");
        return ResponseEntity.ok(iMilkCollectionService.fetchAllMilkCollectionDetails());
    }

    @GetMapping("/fetchAllMilkCollectionDetailsByCollectionDate")
    public ResponseEntity<List<MilkCollectionDetailsDto>> fetchAllMilkCollectionDetailsByCollectionDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.debug("Start fetchAllMilkCollectionDetailsByCollectionDate");

        logger.debug("End fetchAllMilkCollectionDetailsByCollectionDate");
        return ResponseEntity.ok(iMilkCollectionService.fetchAllMilkCollectionDetailsByCollectionDate(date));
    }

    @PostMapping("/createMilkCollection")
    public ResponseEntity<ResponseDto> createMilkCollection(@RequestBody MilkCollectionDto milkCollectionDto) {
        logger.debug("Start createMilkCollection");

        iMilkCollectionService.createMilkCollection(milkCollectionDto);

        logger.debug("End createMilkCollection");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @PostMapping("/updateMilkCollection")
    public ResponseEntity<ResponseDto> updateMilkCollection(@RequestBody MilkCollectionDto milkCollectionDto) {
        logger.debug("Start updateMilkCollection");

        iMilkCollectionService.updateMilkCollection(milkCollectionDto);

        logger.debug("End updateMilkCollection");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetchMilkSupplierCollectionByCollectionDateRange")
    public ResponseEntity<List<MilkSupplierCollectionDTO>> fetchMilkSupplierCollectionByCollectionDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                                                            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        logger.debug("Start fetchMilkSupplierCollectionByCollectionDateRange");

        logger.debug("End fetchMilkSupplierCollectionByCollectionDateRange");
        return ResponseEntity.ok(iMilkCollectionService.fetchMilkSupplierCollectionByCollectionDateRange(startDate, endDate));
    }

    @DeleteMapping("/deleteMilkCollectionById")
    public ResponseEntity<ResponseDto> deleteMilkCollectionById(@RequestParam("milkCollectionId") Long milkCollectionId) {
        logger.debug("Start deleteMilkCollectionById");

        boolean result = iMilkCollectionService.deleteMilkCollectionById(milkCollectionId);
        if (!result) {
            logger.debug("End deleteMilkCollectionById, result: false");
            return ResponseEntity.badRequest().body(new ResponseDto("400", "Error deleting milk collection"));
        }

        logger.debug("End deleteMilkCollectionById");
        return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }

}
