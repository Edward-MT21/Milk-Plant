package com.module.Milk_Collection.controllers;

import com.module.Milk_Collection.constants.AccountsConstants;
import com.module.Milk_Collection.model.dtos.MilkSupplierInDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierOutDto;
import com.module.Milk_Collection.model.dtos.ResponseDto;
import com.module.Milk_Collection.services.IMilkSupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/MilkSupplierController", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class MilkSupplierController {

    IMilkSupplierService iMilkSupplierService;

    @GetMapping("/getSomeData")
    public String getSomeData() {
        return "Hello World since getSomeData";
    }

    @PostMapping("/createMilkSupplier")
    public ResponseEntity<ResponseDto> createMilkSupplier(@RequestBody MilkSupplierInDto milkSupplierInDto) {
        iMilkSupplierService.createMilkSupplier(milkSupplierInDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetchMilkSupplierById")
    public ResponseEntity<MilkSupplierOutDto> fetchMilkSupplierById(@RequestParam Long milkSupplierId) {
        MilkSupplierOutDto milkSupplierOutDto = iMilkSupplierService.fetchMilkSupplierById(milkSupplierId);
        return ResponseEntity.status(HttpStatus.OK).body(milkSupplierOutDto);
    }

    @GetMapping("/fetchMilkSupplierByPersonId")
    public ResponseEntity<MilkSupplierOutDto> fetchMilkSupplierByPersonId(@RequestParam Long personId) {
        MilkSupplierOutDto milkSupplierOutDto = iMilkSupplierService.fetchMilkSupplierByPersonId(personId);
        return ResponseEntity.status(HttpStatus.OK).body(milkSupplierOutDto);
    }

    @PutMapping("/updateMilkSupplier")
    public ResponseEntity<ResponseDto> updateMilkSupplier(@RequestBody MilkSupplierInDto milkSupplierInDto) {
        boolean isUpdated = iMilkSupplierService.updateMilkSupplier(milkSupplierInDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/deleteMilkSupplierById")
    public ResponseEntity<ResponseDto> deleteMilkSupplierById(@RequestParam Long milkSupplierId) {
        boolean isDeleted = iMilkSupplierService.deleteMilkSupplierById(milkSupplierId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @DeleteMapping("/deleteMilkSupplierByPersonId")
    public ResponseEntity<ResponseDto> deleteMilkSupplierByPersonId(@RequestParam Long personId) {
        boolean isDeleted = iMilkSupplierService.deleteMilkSupplierByPersonId(personId);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

}
