package com.persons.Persons.repositories;


import com.module.Common.dtos.PersonOutDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersonJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    // Spring Boot inyecta automáticamente el JdbcTemplate aquí
    public PersonJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<PersonOutDto> fetchPersonByIdentificationNumber(String identificationNumber) {
        String sql = "SELECT person_id, names, identification_number, email FROM persons WHERE identification_number = ?";

        try {
            // Usamos queryForObject para traer un solo registro y un RowMapper para mapearlo al DTO
            PersonOutDto personDto = jdbcTemplate.queryForObject(sql, personRowMapper, identificationNumber);
            return Optional.ofNullable(personDto);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            // Si la consulta no encuentra resultados, queryForObject lanza esta excepción.
            // Retornamos Optional.empty() para manejarlo de manera limpia.
            return Optional.empty();
        }
    }

    // El RowMapper se encarga de transformar cada fila de la base de datos a tu DTO
    private final RowMapper<PersonOutDto> personRowMapper = (rs, rowNum) -> {
        PersonOutDto dto = new PersonOutDto();
        dto.setIdPerson(rs.getLong("person_id"));
        dto.setNames(rs.getString("names"));
        dto.setIdentificationNumber(rs.getString("identification_number"));
        dto.setEmail(rs.getString("email"));
        return dto;
    };
}
