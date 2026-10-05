#!/bin/bash

# Carga las variables del .env si existe
if [ -f .env ]; then
  export $(cat .env | grep -v '^#' | xargs)
fi

# Toma DOCKER_HUB_TAG del .env o usa 'latest' por defecto
TAG=${DOCKER_HUB_TAG:-latest}
echo "🚀 Construyendo imágenes con tag: $TAG"

# Directorios específicos donde se construirán las imágenes
SERVICES=("Config-Server" "Eureka-Server" "Milk-Collection" "Persons" "Financial-Management" "Gateway-Server" "Messages")  # Agrega aquí los nombres de los directorios

# Recorre los directorios especificados
for SERVICE in "${SERVICES[@]}"; do
    if [ -d "$SERVICE" ]; then
        echo "Construyendo imagen para: $SERVICE"
        cd "$SERVICE" || exit 1
        # Agregamos -DskipTests para compilar más rápido las imágenes
        mvn compile jib:dockerBuild -DskipTests
        cd ..
    else
        echo "El directorio $SERVICE no existe, omitiendo..."
    fi
done

echo "Proceso completado 🚀"
