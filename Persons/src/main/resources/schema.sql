CREATE TABLE IF NOT EXISTS persons (
    person_id INT AUTO_INCREMENT PRIMARY KEY,  -- Identificador único
    names VARCHAR(100) NOT NULL,  -- Nombres
    last_names VARCHAR(100) NOT NULL,  -- Apellidos
    identification_number VARCHAR(20) UNIQUE NOT NULL,  -- Número de identificación (único)
    age INT NOT NULL,  -- Edad
    gender VARCHAR(20) CHECK (gender IN ('MASCULINO', 'FEMENINO', 'OTRO')),  -- Género ('M', 'F' o 'O' para otro)
    email VARCHAR(100) UNIQUE NOT NULL,  -- Correo electrónico (único),
    mobile_number VARCHAR(100) UNIQUE NOT NULL,  -- Correo electrónico (único)
    `created_at` timestamp NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` timestamp DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);