CREATE TABLE IF NOT EXISTS persons (
    person_id SERIAL PRIMARY KEY,
    names VARCHAR(100) NOT NULL,
    last_names VARCHAR(100) NOT NULL,
    identification_number VARCHAR(20) UNIQUE NOT NULL,
    birthdate DATE NOT NULL,
    gender VARCHAR(20) CHECK (gender IN ('MASCULINO', 'FEMENINO', 'OTRO', 'M', 'F')),
    email VARCHAR(100) UNIQUE NOT NULL,
    mobile_number VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(20) NOT NULL,
    updated_at TIMESTAMP DEFAULT NULL,
    updated_by VARCHAR(20) DEFAULT NULL
);