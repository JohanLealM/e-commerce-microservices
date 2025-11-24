#!/bin/bash

# Colores para la salida
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # Sin color

echo -e "${BLUE}============================================${NC}"
echo -e "${BLUE}  INICIANDO MICROSERVICIOS E-COMMERCE${NC}"
echo -e "${BLUE}============================================${NC}"
echo ""

# Función mejorada que busca el pom.xml automáticamente
start_service() {
    SERVICE_NAME=$1
    SERVICE_BASE_DIR=$2
    SERVICE_PORT=$3
    
    # Buscar el pom.xml dentro de la carpeta del servicio
    POM_PATH=$(find "$SERVICE_BASE_DIR" -maxdepth 2 -name "pom.xml" 2>/dev/null | head -1)
    
    if [ -z "$POM_PATH" ]; then
        echo -e "${RED}[ERROR] No se encontró pom.xml en $SERVICE_BASE_DIR${NC}"
        return 1
    fi
    
    # Obtener el directorio donde está el pom.xml
    POM_DIR=$(dirname "$POM_PATH")
    
    echo -e "${GREEN}[$SERVICE_NAME] Encontrado en: $POM_DIR${NC}"
    echo -e "${YELLOW}[$SERVICE_NAME] Iniciando en puerto $SERVICE_PORT...${NC}"
    
    gnome-terminal --tab --title="$SERVICE_NAME - Puerto $SERVICE_PORT" -- bash -c "cd '$POM_DIR' && echo 'Compilando $SERVICE_NAME...' && mvn clean spring-boot:run; exec bash"
    sleep 8
}

# Iniciar servicios
start_service "Usuario Service" "Usuario" "9090"
start_service "Carrito Service" "Carrito" "8882"
start_service "Pedidos Service" "Pedidos" "8883"

echo ""
echo -e "${GREEN}============================================${NC}"
echo -e "${GREEN}  TODOS LOS MICROSERVICIOS INICIADOS${NC}"
echo -e "${GREEN}============================================${NC}"
echo ""
echo "Servicios disponibles:"
echo "  - Usuario Service:  http://localhost:9090"
echo "  - Carrito Service:  http://localhost:8882"
echo "  - Pedidos Service:  http://localhost:8883"
echo ""
echo -e "${BLUE}Para detener: cierra las pestañas o ejecuta ./detener-microservicios.sh${NC}"
echo ""

read -p "Presiona Enter para salir..."
