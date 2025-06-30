#!/bin/bash

# Directorios específicos donde se construirán las dependencias
SERVICES=("config-server" "eureka-server" "milk-collection" "persons" "financial-management" "gateway-server" "messages")  # Agrega aquí los nombres de los directorios

# Recorre los directorios especificados
for SERVICE in "${SERVICES[@]}"; do
    if [ -d "$SERVICE" ]; then
        echo "Construyendo dependencias para: $SERVICE"
        cd "$SERVICE" || exit 1
        helm dependencies build
        cd ..
    else
        echo "El directorio $SERVICE no existe, omitiendo..."
    fi
done

echo "Proceso completado 🚀"