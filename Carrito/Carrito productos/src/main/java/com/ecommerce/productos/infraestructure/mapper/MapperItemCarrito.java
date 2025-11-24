package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.ItemCarrito;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.ItemCarritoData;
import org.springframework.stereotype.Component;

@Component
public class MapperItemCarrito {

    public ItemCarrito toItem(ItemCarritoData data) {
        ItemCarrito item = new ItemCarrito();
        item.setIdItemCarrito(data.getIdItemCarrito());
        item.setProductoId(data.getProductoId());
        item.setNombreProducto(data.getNombreProducto());
        item.setPrecioUnitario(data.getPrecioUnitario());
        item.setCantidad(data.getCantidad());
        item.setSubtotal(data.getSubtotal());
        item.setCarritoId(data.getCarrito().getIdCarrito());
        return item;
    }

    public ItemCarritoData toItemData(ItemCarrito item) {
        ItemCarritoData itemData = new ItemCarritoData();
        itemData.setIdItemCarrito(item.getIdItemCarrito());
        itemData.setProductoId(item.getProductoId());
        itemData.setNombreProducto(item.getNombreProducto());
        itemData.setPrecioUnitario(item.getPrecioUnitario());
        itemData.setCantidad(item.getCantidad());
        itemData.setSubtotal(item.getSubtotal());
        return itemData;
    }
}
