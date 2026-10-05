#!/bin/bash

# Carga las variables del .env si existe
if [ -f .env ]; then
  export $(cat .env | grep -v '^#' | xargs)
fi

# Toma DOCKER_HUB_TAG del .env o usa 'latest' por defecto
TAG=${DOCKER_HUB_TAG:-latest}
USER=${DOCKER_HUB_USER:-undefined}
echo "🚀 Construyendo imágenes con tag: $TAG"

# Lista de imágenes Docker a subir
IMAGES=("config-server" "eureka-server" "milk-collection" "persons" "financial-management" "gateway-server" "messages")

# Registro de Docker (modifica según tu necesidad)
DOCKER_REGISTRY="docker.io/$USER"

# Recorre cada imagen y la sube al registro
for IMAGE in "${IMAGES[@]}"; do
    echo "Subiendo imagen: $IMAGE"
    docker image push "$DOCKER_REGISTRY/$IMAGE:$TAG"
done
echo "Todas las imágenes han sido subidas 🚀"