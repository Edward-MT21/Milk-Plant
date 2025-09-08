package com.module.Financial_Management.controllers;

import com.module.Common.dtos.MilkSupplierCollectionDTO;
import com.module.Common.dtos.ResponseDto;
import com.module.Financial_Management.model.dtos.InfoMilkSupplierPaymentDto;
import com.module.Financial_Management.model.dtos.ProductDto;
import com.module.Financial_Management.model.enums.ProductEnum;
import com.module.Financial_Management.services.IFortnightClosureSchedulerService;
import com.module.Financial_Management.services.IMilkSupplierPaymentService;
import com.module.Financial_Management.services.IProductService;
import com.module.Financial_Management.services.client.IMilkCollectionFeignClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/MilkSupplierPaymentController")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MilkSupplierPaymentController {

    private static final Logger logger = LoggerFactory.getLogger(MilkSupplierPaymentController.class);

    private final IMilkSupplierPaymentService iMilkSupplierPaymentService;
    private final IFortnightClosureSchedulerService iFortnightClosureSchedulerService;

    @GetMapping("/getBiweeklyInfoMilkSupplierPayment")
    public List<InfoMilkSupplierPaymentDto> getBiweeklyInfoMilkSupplierPayment(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        return iMilkSupplierPaymentService.getBiweeklyInfoMilkSupplierPayment(startDate, endDate);

    }

    @PostMapping("/executeMilkSupplierFortnightClosure")
    public void executeClosureManually(@RequestParam("closureDate") LocalDate closureDate) {
        iFortnightClosureSchedulerService.executeMilkSupplierFortnightClosure(closureDate);
    }

    @DeleteMapping("/deleteMilkSupplierPaymentById")
    public ResponseEntity<ResponseDto> deleteMilkSupplierPaymentById(@RequestParam("milkSupplierPaymentId") Long milkSupplierPaymentId) {
        boolean deleted = iMilkSupplierPaymentService.deleteMilkSupplierPaymentById(milkSupplierPaymentId);
        if (!deleted) {
            return ResponseEntity.badRequest().body(new ResponseDto("400", "MilkSupplierPayment not deleted"));
        }
        return ResponseEntity.ok().body(new ResponseDto("200", "MilkSupplierPayment deleted successfully"));

    }




}
