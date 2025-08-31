package com.module.Common.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(
        name = "PersonOutDto",
        description = "Schema to PersonOutDto information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonOutDto {

    private Long idPerson;

    private String names;

    private String lastNames;

    private String identificationNumber;

    private LocalDate birthdate;

    @Schema(
            description = "Person gender", example = "FEMALE"
    )
    private String gender;

    private String email;
    private String mobileNumber;

}
