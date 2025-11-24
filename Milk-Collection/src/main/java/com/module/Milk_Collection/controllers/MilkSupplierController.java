package com.module.Milk_Collection.controllers;

import com.module.Common.dtos.ResponseDto;
import com.module.Milk_Collection.constants.AccountsConstants;
import com.module.Milk_Collection.model.dtos.*;
import com.module.Milk_Collection.services.IMilkSupplierService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.module.Common.dtos.ErrorResponseDto;

import java.util.List;
import java.util.concurrent.TimeoutException;

@Tag(
        name = "CRUD REST APIs for MilkSupplier",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE fromMilkSupplier details"
)
@RestController
@RequestMapping(path="/MilkSupplierController", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class MilkSupplierController {

    private static final Logger logger = LoggerFactory.getLogger(MilkSupplierController.class);

    private final IMilkSupplierService iMilkSupplierService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private MilkCollectionContactsDto milkCollectionContactsDto;

    @Autowired
    public MilkSupplierController(IMilkSupplierService iMilkSupplierService) {
        this.iMilkSupplierService = iMilkSupplierService;
    }

    @Operation(
            summary = "Create Milk Supplier REST API",
            description = "REST API to create new Milk Supplier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/createMilkSupplier")
    public ResponseEntity<ResponseDto> createMilkSupplier(@RequestBody MilkSupplierInDto milkSupplierInDto) {
        logger.debug("Start createMilkSupplier");

        iMilkSupplierService.createMilkSupplier(milkSupplierInDto);

        logger.debug("End createMilkSupplier");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Milk Supplier Details by Id REST API",
            description = "REST API to fetch Milk Supplier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetchMilkSupplierById")
    public ResponseEntity<MilkSupplierOutDto> fetchMilkSupplierById(@RequestParam("milkSupplierId") Long milkSupplierId) {
        logger.debug("Start fetchMilkSupplierById");

        MilkSupplierOutDto milkSupplierOutDto = iMilkSupplierService.fetchMilkSupplierById(milkSupplierId);

        logger.debug("End fetchMilkSupplierById");
        return ResponseEntity.status(HttpStatus.OK).body(milkSupplierOutDto);
    }

    @Operation(
            summary = "Fetch Milk Supplier Details by Id of Person REST API",
            description = "REST API to fetch Milk Supplier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetchMilkSupplierByPersonId")
    public ResponseEntity<MilkSupplierOutDto> fetchMilkSupplierByPersonId(@RequestParam("personId") Long personId) {
        logger.debug("Start fetchMilkSupplierByPersonId");

        MilkSupplierOutDto milkSupplierOutDto = iMilkSupplierService.fetchMilkSupplierByPersonId(personId);

        logger.debug("End fetchMilkSupplierByPersonId");
        return ResponseEntity.status(HttpStatus.OK).body(milkSupplierOutDto);
    }

    @Operation(
            summary = "Update Milk Supplier Details REST API",
            description = "REST API to update Milk Supplier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/updateMilkSupplier")
    public ResponseEntity<ResponseDto> updateMilkSupplier(@RequestBody MilkSupplierInDto milkSupplierInDto) {
        logger.debug("Start updateMilkSupplier");

        boolean isUpdated = iMilkSupplierService.updateMilkSupplier(milkSupplierInDto);
        if(isUpdated) {

            logger.debug("End updateMilkSupplier, isUpdated: true");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            logger.debug("End updateMilkSupplier, isUpdated: false");
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Milk Supplier Details by Id REST API",
            description = "REST API to delete Milk Supplier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/deleteMilkSupplierById")
    public ResponseEntity<ResponseDto> deleteMilkSupplierById(@RequestParam("milkSupplierId") Long milkSupplierId) {
        logger.debug("Start deleteMilkSupplierById");

        boolean isDeleted = iMilkSupplierService.deleteMilkSupplierById(milkSupplierId);
        if(isDeleted) {
            logger.debug("End deleteMilkSupplierById, isDeleted: true");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            logger.debug("End deleteMilkSupplierById, isDeleted: false");
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Delete Milk Supplier Details by Id of Person REST API",
            description = "REST API to delete Milk Supplier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/deleteMilkSupplierByPersonId")
    public ResponseEntity<ResponseDto> deleteMilkSupplierByPersonId(@Positive @RequestParam("personId") Long personId) {
        logger.debug("Start deleteMilkSupplierByPersonId");

        boolean isDeleted = iMilkSupplierService.deleteMilkSupplierByPersonId(personId);
        if(isDeleted) {
            logger.debug("End deleteMilkSupplierByPersonId, isDeleted: true");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            logger.debug("End deleteMilkSupplierByPersonId, isDeleted: false");
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @Retry(name = "retryGetBuildVersion", fallbackMethod = "getBuildVersionFallback")
    @GetMapping("/getBuildVersion")
    public ResponseEntity<String> getBuildVersion() throws TimeoutException {
        logger.debug("Start getBuildVersion");

        //throw new NullPointerException();
        throw new TimeoutException();
        //return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    public ResponseEntity<String> getBuildVersionFallback(Throwable throwable) {
        logger.debug("Start getBuildVersionFallback");

        logger.debug("End getBuildVersionFallback");
        return ResponseEntity.status(HttpStatus.OK).body("0.0");
    }

    @RateLimiter(name = "rateLimiterGetJavaHome", fallbackMethod = "getJavaHomeFallback")
    @GetMapping("/getJavaHome")
    public ResponseEntity<String> getJavaHome() {
        logger.debug("Start getJavaHome");

        logger.debug("End getJavaHome");
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    public ResponseEntity<String> getJavaHomeFallback(Throwable throwable) {
        logger.debug("Start getJavaHomeFallback");

        logger.debug("End getJavaHomeFallback");
        return ResponseEntity.status(HttpStatus.OK).body("Java 17");
    }

    @GetMapping("/getMilkCollectionContacts")
    public ResponseEntity<MilkCollectionContactsDto> getMilkCollectionContacts() {
        logger.debug("Start getMilkCollectionContacts");

        logger.debug("End getMilkCollectionContacts");
        return ResponseEntity.status(HttpStatus.OK).body(milkCollectionContactsDto);
    }

    @GetMapping("/fetchMilkSupplierDetailsById")
    public ResponseEntity<MilkSupplierDetailsDto> fetchMilkSupplierDetailsById(
            @RequestParam("milkSupplierId") Long milkSupplierId, @RequestHeader("milk-plant-correlation-id") String correlationId) {

        logger.debug("Start fetchMilkSupplierDetailsById");

        MilkSupplierDetailsDto milkSupplierDetailsDto = iMilkSupplierService.fetchMilkSupplierDetailsById(milkSupplierId, correlationId);

        logger.debug("End fetchMilkSupplierDetailsById");
        return ResponseEntity.status(HttpStatus.OK).body(milkSupplierDetailsDto);
    }

    @GetMapping("/fetchAllMilkSupplierDetails")
    public ResponseEntity<List<MilkSupplierDetailsDto>> fetchAllMilkSupplierDetails() {
        logger.debug("Start fetchAllMilkSupplierDetails");

        List<MilkSupplierDetailsDto> listMilkSupplierDetailsDto= iMilkSupplierService.fetchAllMilkSupplierDetails();

        logger.debug("End fetchAllMilkSupplierDetails");
        return ResponseEntity.status(HttpStatus.OK).body(listMilkSupplierDetailsDto);
    }


}
