#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

echo -e "${RED}============================================${NC}"
echo -e "${RED}  DETENIENDO MICROSERVICIOS${NC}"
echo -e "${RED}============================================${NC}"
echo ""

# Matar todos los procesos de Maven Spring Boot
pkill -f "spring-boot:run"

echo -e "${GREEN}Todos los microservicios han sido detenidos.${NC}"
echo ""
