package com.module.Common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilkCollectionRecordDTO {

    private LocalDateTime createdAt;
    private Integer litersMilk;
}
