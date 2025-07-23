package com.module.Milk_Collection.controllers;

import com.module.Milk_Collection.constants.AccountsConstants;
import com.module.Milk_Collection.model.dtos.MilkCollectionDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;
import com.module.Milk_Collection.model.dtos.ResponseDto;
import com.module.Milk_Collection.services.IMilkCollectionService;
import lombok.AllArgsConstructor;
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

    private final IMilkCollectionService iMilkCollectionService;

    @GetMapping("/fetch-all-milk-collection")
    public ResponseEntity<List<MilkCollectionDto>> fetchAllMilkCollection() {
        return ResponseEntity.ok(iMilkCollectionService.fetchAllMilkCollection());
    }

    @GetMapping("/fetch-all-milk-collection-details")
    public ResponseEntity<List<MilkCollectionDetailsDto>> fetchAllMilkCollectionDetails() {
        return ResponseEntity.ok(iMilkCollectionService.fetchAllMilkCollectionDetails());
    }

    @GetMapping("/fetch-milk-collection-details-by-date")
    public ResponseEntity<List<MilkCollectionDetailsDto>> fetchMilkCollectionDetailsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(iMilkCollectionService.fetchAllMilkCollectionDetailsByDate(date));
    }

    @PostMapping("/create-milk-collection")
    public ResponseEntity<ResponseDto> createMilkCollection(@RequestBody MilkCollectionDto milkCollectionDto) {
        iMilkCollectionService.createMilkCollection(milkCollectionDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @PostMapping("/update-milk-collection")
    public ResponseEntity<ResponseDto> updateMilkCollection(@RequestBody MilkCollectionDto milkCollectionDto) {
        iMilkCollectionService.updateMilkCollection(milkCollectionDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }
}
