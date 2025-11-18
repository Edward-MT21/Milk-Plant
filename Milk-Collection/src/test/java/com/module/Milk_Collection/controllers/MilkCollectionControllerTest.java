package com.module.Milk_Collection.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.module.Common.dtos.MilkSupplierCollectionDTO;
import com.module.Milk_Collection.constants.AccountsConstants;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;
import com.module.Milk_Collection.services.IMilkCollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MilkCollectionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IMilkCollectionService milkCollectionService;

    @InjectMocks
    private MilkCollectionController milkCollectionController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(milkCollectionController).build();
    }

    @Test
    void fetchAllMilkCollection_ShouldReturnListOfMilkCollections() throws Exception {
        // Arrange
        MilkCollectionDto milkCollection1 = new MilkCollectionDto();
        milkCollection1.setMilkCollectionId(1L);
        MilkCollectionDto milkCollection2 = new MilkCollectionDto();
        milkCollection2.setMilkCollectionId(2L);
        List<MilkCollectionDto> milkCollections = Arrays.asList(milkCollection1, milkCollection2);

        when(milkCollectionService.fetchAllMilkCollection()).thenReturn(milkCollections);

        // Act & Assert
        mockMvc.perform(get("/MilkCollectionController/fetchAllMilkCollection")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].milkCollectionId").value(1))
                .andExpect(jsonPath("$[1].milkCollectionId").value(2));

        verify(milkCollectionService, times(1)).fetchAllMilkCollection();
    }

    @Test
    void createMilkCollection_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        MilkCollectionDto milkCollectionDto = new MilkCollectionDto();
        milkCollectionDto.setMilkCollectionId(1L);

        // Act & Assert
        mockMvc.perform(post("/MilkCollectionController/createMilkCollection")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(milkCollectionDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_201));

        verify(milkCollectionService, times(1)).createMilkCollection(any(MilkCollectionDto.class));
    }

    @Test
    void deleteMilkCollectionById_WhenExists_ShouldReturnOk() throws Exception {
        // Arrange
        Long milkCollectionId = 1L;
        when(milkCollectionService.deleteMilkCollectionById(milkCollectionId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/MilkCollectionController/deleteMilkCollectionById")
                .param("milkCollectionId", milkCollectionId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_200));

        verify(milkCollectionService, times(1)).deleteMilkCollectionById(milkCollectionId);
    }

    @Test
    void deleteMilkCollectionById_WhenNotExists_ShouldReturnBadRequest() throws Exception {
        // Arrange
        Long milkCollectionId = 999L;
        when(milkCollectionService.deleteMilkCollectionById(milkCollectionId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/MilkCollectionController/deleteMilkCollectionById")
                .param("milkCollectionId", milkCollectionId.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value("400"))
                .andExpect(jsonPath("$.statusMsg").value("Error deleting milk collection"));

        verify(milkCollectionService, times(1)).deleteMilkCollectionById(milkCollectionId);
    }

    @Test
    void fetchMilkSupplierCollectionByCollectionDateRange_ShouldReturnFilteredList() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);

        MilkSupplierCollectionDTO dto1 = new MilkSupplierCollectionDTO();
        dto1.setMilkSupplierId(1L);

        List<MilkSupplierCollectionDTO> result = List.of(dto1);

        when(milkCollectionService.fetchMilkSupplierCollectionByCollectionDateRange(startDate, endDate))
            .thenReturn(result);

        // Act & Assert
        mockMvc.perform(get("/MilkCollectionController/fetchMilkSupplierCollectionByCollectionDateRange")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].milkSupplierId").value(1));

        verify(milkCollectionService, times(1))
            .fetchMilkSupplierCollectionByCollectionDateRange(startDate, endDate);
    }
}
