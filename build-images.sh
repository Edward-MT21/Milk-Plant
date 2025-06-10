#!/bin/bash

# Directorios específicos donde se construirán las imágenes
SERVICES=("Config-Server" "Eureka-Server" "Milk-Collection" "Persons" "Financial-Management" "Gateway-Server" "Messages")  # Agrega aquí los nombres de los directorios

# Recorre los directorios especificados
for SERVICE in "${SERVICES[@]}"; do
    if [ -d "$SERVICE" ]; then
        echo "Construyendo imagen para: $SERVICE"
        cd "$SERVICE" || exit 1
        mvn compile jib:dockerBuild
        cd ..
    else
        echo "El directorio $SERVICE no existe, omitiendo..."
    fi
done

echo "Proceso completado 🚀"
