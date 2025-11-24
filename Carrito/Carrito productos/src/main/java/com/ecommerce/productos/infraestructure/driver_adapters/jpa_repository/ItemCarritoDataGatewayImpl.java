package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository;

import com.ecommerce.productos.domain.model.ItemCarrito;
import com.ecommerce.productos.domain.model.gateway.ItemCarritoGateway;
import com.ecommerce.productos.infraestructure.mapper.MapperItemCarrito;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemCarritoDataGatewayImpl implements ItemCarritoGateway {

    private final ItemCarritoDataJpaRepository repository;
    private final MapperItemCarrito mapper;

    @Override
    public ItemCarrito guardar(ItemCarrito item) {
        ItemCarritoData data = mapper.toItemData(item);
        ItemCarritoData saved = repository.save(data);
        return mapper.toItem(saved);     }
}