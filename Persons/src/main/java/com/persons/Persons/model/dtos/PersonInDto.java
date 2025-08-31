package com.persons.Persons.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(
        name = "PersonInDto",
        description = "Schema to PersonInDto information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonInDto {

    private Long idPerson;

    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the names should be between 5 and 30")
    private String names;

    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the lastNames should be between 5 and 30")
    private String lastNames;

    private String identificationNumber;

    private LocalDate birthdate;

    private String gender;

    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

}
