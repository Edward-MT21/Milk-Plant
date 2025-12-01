package com.module.Milk_Collection.services.impl;

import com.module.Milk_Collection.model.dtos.MilkCollectionDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;
import com.module.Milk_Collection.model.dtos.MilkSupplierDetailsDto;
import com.module.Milk_Collection.model.entities.MilkCollection;
import com.module.Milk_Collection.repositories.IMilkCollectionRepository;
import com.module.Milk_Collection.services.IMilkSupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

public class MilkCollectionServiceImplTest {

    @Mock
    private IMilkCollectionRepository iMilkCollectionRepository;

    @Mock
    private IMilkSupplierService iMilkSupplierService;

    @InjectMocks
    private MilkCollectionServiceImpl milkCollectionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchAllMilkCollection_Test() {
        // Arrange: datos simulados
        LocalDate.of(2025, 11, 30);
        MilkCollection entity1 = new MilkCollection(
                1L, 1L, 5, LocalDate.of(2025, 11, 30));
        MilkCollection entity2 = new MilkCollection(
                2L, 2L, 10, LocalDate.of(2025, 11, 30));

        when(iMilkCollectionRepository.findAll())
                .thenReturn(Arrays.asList(entity1, entity2));

        // Act: ejecutar el método
        List<MilkCollectionDto> result = milkCollectionService.fetchAllMilkCollection();

        // Assert: verificar resultados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getMilkCollectionId());
        assertEquals(5, result.get(0).getLitersMilk());

        // Verificar interacción con el repositorio
        verify(iMilkCollectionRepository, times(1)).findAll();
    }

    @Test
    void fetchAllMilkCollectionDetails_Test() {
        // Arrange: datos simulados
        LocalDate.of(2025, 11, 30);
        MilkCollection entity1 = new MilkCollection(
                1L, 1L, 5, LocalDate.of(2025, 11, 30));
        MilkCollection entity2 = new MilkCollection(
                2L, 2L, 10, LocalDate.of(2025, 11, 30));

        when(iMilkCollectionRepository.findAll())
                .thenReturn(Arrays.asList(entity1, entity2));

        MilkSupplierDetailsDto milkSupplierDetailsDto = new MilkSupplierDetailsDto();
        milkSupplierDetailsDto.setMilkSupplierId(1L);
        milkSupplierDetailsDto.setPricePerLiter(new BigDecimal(1000));

        when(iMilkSupplierService.fetchMilkSupplierDetailsById(any(Long.class), any(String.class)))
                .thenReturn(milkSupplierDetailsDto);

        // Act: ejecutar el método
        List<MilkCollectionDetailsDto> result = milkCollectionService.fetchAllMilkCollectionDetails();

        // Assert: verificar resultados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getMilkCollectionId());
        assertEquals(5, result.get(0).getLitersMilk());
        assertEquals(BigDecimal.valueOf(1000), result.get(0).getMilkSupplierDetailsDto().getPricePerLiter());

        // Verificar interacción con el repositorio
        verify(iMilkCollectionRepository, times(1)).findAll();
    }

}
