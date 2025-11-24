package com.ecommerce.productos.application.config;

import com.ecommerce.productos.domain.model.gateway.CarritoGateway;
import com.ecommerce.productos.domain.model.gateway.ProductoGateway;
import com.ecommerce.productos.domain.model.gateway.UsuarioGateway;
import com.ecommerce.productos.domain.model.gateway.ItemCarritoGateway;
import com.ecommerce.productos.domain.usecase.CarritoUseCase;
import com.ecommerce.productos.domain.usecase.ItemCarritoUseCase;
import com.ecommerce.productos.domain.usecase.ProductoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarritoUseCaseConfig {

    @Bean
    public ProductoUseCase productoUseCase(ProductoGateway productoGateway){
        return new ProductoUseCase(productoGateway);
    }

    @Bean
    public CarritoUseCase carritoUseCase(CarritoGateway carritoGateway,
                                         ProductoUseCase productoUseCase,
                                         UsuarioGateway usuarioGateway,
                                         ItemCarritoUseCase itemCarritoUseCase) {
        return new CarritoUseCase(carritoGateway, productoUseCase, usuarioGateway, itemCarritoUseCase);
    }

    @Bean
    public ItemCarritoUseCase itemCarritoUseCase(ItemCarritoGateway itemCarritoGateway) {
        return new ItemCarritoUseCase(itemCarritoGateway);
    }
}