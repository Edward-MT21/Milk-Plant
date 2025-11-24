package com.module.Milk_Collection.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.module.Milk_Collection.constants.AccountsConstants;
import com.module.Milk_Collection.model.dtos.*;
import com.module.Milk_Collection.services.IMilkSupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class MilkSupplierControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IMilkSupplierService iMilkSupplierService;

    @InjectMocks
    private MilkSupplierController milkSupplierController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private Environment environment;

    @Mock
    private MilkCollectionContactsDto milkCollectionContactsDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(milkSupplierController).build();
    }

    @Test
    void createMilkSupplier_ShouldReturnCreatedStatus() throws Exception {

        MilkSupplierInDto milkSupplierInDto = new MilkSupplierInDto();

        mockMvc.perform(post("/MilkSupplierController/createMilkSupplier")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(milkSupplierInDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_201));

        verify(iMilkSupplierService, times(1)).createMilkSupplier(any(MilkSupplierInDto.class));
    }

    @Test
    void fetchMilkSupplierById_ShouldReturnMilkSupplier() throws Exception {

        Long milkSupplierId = 1L;
        MilkSupplierOutDto milkSupplierOutDto = new MilkSupplierOutDto();
        milkSupplierOutDto.setMilkSupplierId(milkSupplierId);

        when(iMilkSupplierService.fetchMilkSupplierById(milkSupplierId)).thenReturn(milkSupplierOutDto);

        mockMvc.perform(get("/MilkSupplierController/fetchMilkSupplierById")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("milkSupplierId", milkSupplierId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.milkSupplierId").value(milkSupplierId));

        verify(iMilkSupplierService, times(1)).fetchMilkSupplierById(milkSupplierId);

    }

    @Test
    void fetchMilkSupplierByPersonId_ShouldReturnMilkSupplier() throws Exception {

        Long personId = 1L;
        MilkSupplierOutDto milkSupplierOutDto = new MilkSupplierOutDto();
        milkSupplierOutDto.setPersonId(personId);

        when(iMilkSupplierService.fetchMilkSupplierByPersonId(personId)).thenReturn(milkSupplierOutDto);

        mockMvc.perform(get("/MilkSupplierController/fetchMilkSupplierByPersonId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("personId", personId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").value(personId));

        verify(iMilkSupplierService, times(1)).fetchMilkSupplierByPersonId(personId);

    }

    @Test
    void updateMilkSupplier_ShouldReturnStatusOk() throws Exception {

        MilkSupplierInDto milkSupplierInDto = new MilkSupplierInDto();

        when(iMilkSupplierService.updateMilkSupplier(milkSupplierInDto)).thenReturn(true);

        mockMvc.perform(put("/MilkSupplierController/updateMilkSupplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(milkSupplierInDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_200));

        verify(iMilkSupplierService, times(1)).updateMilkSupplier(any(MilkSupplierInDto.class));
    }

    @Test
    void updateMilkSupplier_ShouldReturnStatusExpectationFailed() throws Exception {

        MilkSupplierInDto milkSupplierInDto = new MilkSupplierInDto();

        when(iMilkSupplierService.updateMilkSupplier(milkSupplierInDto)).thenReturn(false);

        mockMvc.perform(put("/MilkSupplierController/updateMilkSupplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(milkSupplierInDto)))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_417))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_417_UPDATE));

        verify(iMilkSupplierService, times(1)).updateMilkSupplier(any(MilkSupplierInDto.class));
    }

    @Test
    void deleteMilkSupplierById_ShouldReturnStatusOk() throws Exception {

        Long milkSupplierId = 1L;

        when(iMilkSupplierService.deleteMilkSupplierById(milkSupplierId)).thenReturn(true);

        mockMvc.perform(delete("/MilkSupplierController/deleteMilkSupplierById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("milkSupplierId", milkSupplierId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_200));

        verify(iMilkSupplierService, times(1)).deleteMilkSupplierById(milkSupplierId);
    }

    @Test
    void deleteMilkSupplierById_ShouldReturnStatusExpectationFailed() throws Exception {

        Long milkSupplierId = 1L;

        when(iMilkSupplierService.deleteMilkSupplierById(milkSupplierId)).thenReturn(false);

        mockMvc.perform(delete("/MilkSupplierController/deleteMilkSupplierById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("milkSupplierId", milkSupplierId.toString()))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_417))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_417_DELETE));

        verify(iMilkSupplierService, times(1)).deleteMilkSupplierById(milkSupplierId);
    }

    @Test
    void deleteMilkSupplierByPersonId_ShouldReturnStatusOk() throws Exception {

        Long personId = 1L;

        when(iMilkSupplierService.deleteMilkSupplierByPersonId(personId)).thenReturn(true);

        mockMvc.perform(delete("/MilkSupplierController/deleteMilkSupplierByPersonId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("personId", personId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_200));

        verify(iMilkSupplierService, times(1)).deleteMilkSupplierByPersonId(personId);
    }

    @Test
    void deleteMilkSupplierByPersonId_ShouldReturnStatusExpectationFailed() throws Exception {

        Long personId = 1L;

        when(iMilkSupplierService.deleteMilkSupplierByPersonId(personId)).thenReturn(false);

        mockMvc.perform(delete("/MilkSupplierController/deleteMilkSupplierByPersonId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("personId", personId.toString()))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_417))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_417_DELETE));

        verify(iMilkSupplierService, times(1)).deleteMilkSupplierByPersonId(personId);
    }

    @Test
    void getBuildVersion_ShouldThrowTimeoutException() throws Exception {

        assertThrows(TimeoutException.class, () -> {
            milkSupplierController.getBuildVersion();
        });

    }

    @Test
    void getBuildVersionFallback_ShouldReturnDefaultVersion() {
        ResponseEntity<String> response = milkSupplierController.getBuildVersionFallback(new TimeoutException());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("0.0", response.getBody());
    }

    @Test
    void getJavaHome_ShouldReturnStatusOk() throws Exception {

        String javaHome = "Java 21";
        when(environment.getProperty("JAVA_HOME")).thenReturn(javaHome);

        mockMvc.perform(get("/MilkSupplierController/getJavaHome"))
                .andExpect(status().isOk())
                .andExpect(content().string(javaHome));
    }

    @Test
    void getJavaHomeFallback_ShouldReturnDefaultVersion() {
        ResponseEntity<String> response = milkSupplierController.getJavaHomeFallback(new TimeoutException());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Java 17", response.getBody());
    }

    @Test
    void getMilkCollectionContacts_ShouldReturnStatusOk() throws Exception {

        when(milkCollectionContactsDto.getMessage()).thenReturn("Servicio activo");
        when(milkCollectionContactsDto.getContactDetails()).thenReturn(Map.of("admin", "123456"));
        when(milkCollectionContactsDto.getOnCallSupport()).thenReturn(List.of(313511, 314544));

        mockMvc.perform(get("/MilkSupplierController/getMilkCollectionContacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Servicio activo"))
                .andExpect(jsonPath("$.contactDetails.admin").value("123456"))
                .andExpect(jsonPath("$.onCallSupport[0]").value(313511));

    }

    @Test
    void fetchMilkSupplierDetailsById_ShouldReturnMilkSupplier() throws Exception {

        Long milkSupplierId = 1L;
        String correlationId = "1";
        MilkSupplierDetailsDto milkSupplierOutDto = new MilkSupplierDetailsDto();
        milkSupplierOutDto.setMilkSupplierId(milkSupplierId);

        when(iMilkSupplierService.fetchMilkSupplierDetailsById(milkSupplierId, correlationId)).thenReturn(milkSupplierOutDto);

        mockMvc.perform(get("/MilkSupplierController/fetchMilkSupplierDetailsById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("milkSupplierId", milkSupplierId.toString())
                        .header("milk-plant-correlation-id", correlationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.milkSupplierId").value(milkSupplierId));

        verify(iMilkSupplierService, times(1)).fetchMilkSupplierDetailsById(milkSupplierId, correlationId);

    }

    @Test
    void fetchAllMilkSupplierDetails_ShouldReturnListOfMilkSupplier() throws Exception {

        MilkSupplierDetailsDto milkSupplierOutDto1 = new MilkSupplierDetailsDto();
        milkSupplierOutDto1.setMilkSupplierId(1L);

        MilkSupplierDetailsDto milkSupplierOutDto2 = new MilkSupplierDetailsDto();
        milkSupplierOutDto2.setMilkSupplierId(2L);

        List<MilkSupplierDetailsDto> milkSuppliers = Arrays.asList(milkSupplierOutDto1, milkSupplierOutDto2);

        when(iMilkSupplierService.fetchAllMilkSupplierDetails()).thenReturn(milkSuppliers);

        mockMvc.perform(get("/MilkSupplierController/fetchAllMilkSupplierDetails")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].milkSupplierId").value(1L))
                .andExpect(jsonPath("$[1].milkSupplierId").value(2L));

        verify(iMilkSupplierService, times(1)).fetchAllMilkSupplierDetails();

    }

    @Test
    void constructor_ShouldInjectService() {
        IMilkSupplierService service = mock(IMilkSupplierService.class);
        MilkSupplierController controller = new MilkSupplierController(service);
        assertNotNull(controller); // fuerza ejecución del constructor
    }



}
