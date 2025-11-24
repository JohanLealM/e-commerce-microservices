#!/bin/bash

# Script para iniciar los 3 microservicios de e-commerce
# Autor: Generado para el proyecto e-commerce
# Fecha: 2025-11-24

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para imprimir mensajes con color
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

# Verificar si SDKMAN está instalado
if [ ! -d "$HOME/.sdkman" ]; then
    print_error "SDKMAN no está instalado. Por favor instálalo primero."
    exit 1
fi

# Inicializar SDKMAN
export SDKMAN_DIR="$HOME/.sdkman"
[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"

# Verificar que SDKMAN se haya cargado correctamente
if ! command -v sdk &> /dev/null; then
    print_error "No se pudo inicializar SDKMAN"
    exit 1
fi

print_info "Configurando Java 17..."
sdk use java 17.0.9-tem

# Verificar versión de Java
JAVA_VERSION=$(java -version 2>&1 | head -n 1)
print_success "Java configurado: $JAVA_VERSION"

# Directorios de los microservicios
BASE_DIR="$HOME/Documentos/Frank/e-commerce/MicroServicios"
USUARIO_DIR="$BASE_DIR/Usuario/Usuario"
CARRITO_DIR="$BASE_DIR/Carrito/Carrito productos"
PEDIDOS_DIR="$BASE_DIR/Pedidos"

# Archivos de log
LOG_DIR="$BASE_DIR/logs"
mkdir -p "$LOG_DIR"

USUARIO_LOG="$LOG_DIR/usuario.log"
CARRITO_LOG="$LOG_DIR/carrito.log"
PEDIDOS_LOG="$LOG_DIR/pedidos.log"

# Limpiar logs antiguos
> "$USUARIO_LOG"
> "$CARRITO_LOG"
> "$PEDIDOS_LOG"

# Array para almacenar los PIDs
declare -a PIDS

# Función para limpiar procesos al salir
cleanup() {
    print_warning "\n\n🛑 Deteniendo todos los microservicios..."
    for pid in "${PIDS[@]}"; do
        if ps -p $pid > /dev/null 2>&1; then
            print_info "Deteniendo proceso $pid..."
            kill -TERM $pid 2>/dev/null
        fi
    done
    
    # Esperar un poco y forzar si es necesario
    sleep 3
    for pid in "${PIDS[@]}"; do
        if ps -p $pid > /dev/null 2>&1; then
            print_warning "Forzando detención del proceso $pid..."
            kill -9 $pid 2>/dev/null
        fi
    done
    
    print_success "Todos los servicios han sido detenidos"
    exit 0
}

# Capturar Ctrl+C
trap cleanup SIGINT SIGTERM

# Función para iniciar un microservicio
start_service() {
    local name=$1
    local dir=$2
    local log_file=$3
    local port=$4
    
    print_info "🚀 Iniciando $name..."
    
    if [ ! -d "$dir" ]; then
        print_error "El directorio $dir no existe"
        return 1
    fi
    
    cd "$dir"
    
    # Iniciar el servicio en background
    mvn clean spring-boot:run > "$log_file" 2>&1 &
    local pid=$!
    PIDS+=($pid)
    
    print_success "$name iniciado (PID: $pid, Puerto: $port)"
    print_info "Log: $log_file"
    
    cd - > /dev/null
}

# Banner
echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║                                                            ║"
echo "║          🚀 E-COMMERCE MICROSERVICES STARTER 🚀           ║"
echo "║                                                            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Iniciar los microservicios
print_info "Iniciando microservicios..."
echo ""

start_service "Usuario Service" "$USUARIO_DIR" "$USUARIO_LOG" "9090"
sleep 3

start_service "Carrito Service" "$CARRITO_DIR" "$CARRITO_LOG" "8080"
sleep 3

start_service "Pedidos Service" "$PEDIDOS_DIR" "$PEDIDOS_LOG" "8081"

echo ""
print_success "═══════════════════════════════════════════════════════"
print_success "   ✅ Todos los microservicios están iniciando..."
print_success "═══════════════════════════════════════════════════════"
echo ""

print_info "📊 Estado de los servicios:"
echo ""
echo "  1. Usuario Service  → http://localhost:9090"
echo "  2. Carrito Service  → http://localhost:8080"
echo "  3. Pedidos Service  → http://localhost:8081"
echo ""

print_info "📝 Logs disponibles en:"
echo "  • Usuario: $USUARIO_LOG"
echo "  • Carrito: $CARRITO_LOG"
echo "  • Pedidos: $PEDIDOS_LOG"
echo ""

print_warning "💡 Consejos:"
echo "  • Espera 30-60 segundos para que todos los servicios estén listos"
echo "  • Para ver logs en tiempo real: tail -f $LOG_DIR/*.log"
echo "  • Para detener todos los servicios: Presiona Ctrl+C"
echo ""

print_info "⏳ Los servicios están corriendo... (Presiona Ctrl+C para detener)"
echo ""

# Mostrar logs en tiempo real (opcional - comentado por defecto)
# tail -f "$LOG_DIR"/*.log

# Esperar indefinidamente
wait
