package com.module.Financial_Management.model.dtos;

import com.module.Common.dtos.MilkCollectionRecordDTO;
import com.module.Common.dtos.PersonOutDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoMilkSupplierPaymentDto {
    private Long milkSupplierId;
    private PersonOutDto personOutDto;
    private List<MilkCollectionRecordDTO> collections;
    private Integer totalLitersMilk;
    private BigDecimal totalAmount;
}
