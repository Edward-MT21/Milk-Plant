#!/bin/bash

# Lista de imágenes Docker a subir
IMAGES=("config-server" "eureka-server" "milk-collection" "persons" "financial-management" "gateway-server")

# Registro de Docker (modifica según tu necesidad)
DOCKER_REGISTRY="docker.io/edwarddocker270"
TAG="s11"

# Recorre cada imagen y la sube al registro
for IMAGE in "${IMAGES[@]}"; do
    echo "Subiendo imagen: $IMAGE"
    docker image push "$DOCKER_REGISTRY/$IMAGE:$TAG"
done
echo "Todas las imágenes han sido subidas 🚀"