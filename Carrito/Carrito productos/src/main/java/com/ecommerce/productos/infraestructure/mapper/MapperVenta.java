package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.Venta;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.VentaData;
import org.springframework.stereotype.Component;

@Component
public class MapperVenta {

    public Venta toVenta(VentaData data){
        if (data == null) return null;
        return new Venta(data.getId(), data.getUsuarioId(), data.getTotal(), data.getFecha());
    }

    public VentaData toVentaData(Venta venta){
        if (venta == null) return null;
        return new VentaData(venta.getId(), venta.getUsuarioId(), venta.getTotal(), venta.getFecha());
    }
}