package com.module.Milk_Collection.controllers;

import com.module.Common.dtos.ResponseDto;
import com.module.Milk_Collection.constants.AccountsConstants;
import com.module.Milk_Collection.model.dtos.MilkCollectionDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierCollectionDTO;
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
    public ResponseEntity<List<MilkCollectionDetailsDto>> fetchAllMilkCollectionDetailsByCollectionDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(iMilkCollectionService.fetchAllMilkCollectionDetailsByCollectionDate(date));
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

    @GetMapping("/fetch-milk-supplier-collection-by-date-range")
    public ResponseEntity<List<MilkSupplierCollectionDTO>> fetchMilkSupplierCollectionByCollectionDateRange(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                                                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(iMilkCollectionService.fetchMilkSupplierCollectionByCollectionDateRange(startDate, endDate));
    }

    @DeleteMapping("/deleteAllMilkCollectionByMilkSupplierId")
    public ResponseEntity<ResponseDto> deleteAllMilkCollectionByMilkSupplierId(@RequestParam("milkSupplierId") Long milkSupplierId) {
        boolean result = iMilkCollectionService.deleteAllMilkCollectionByMilkSupplierId(milkSupplierId);
        if (!result) {
            return ResponseEntity.badRequest().body(new ResponseDto("400", "Error deleting milk collection"));
        }
        return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));

    }




}
