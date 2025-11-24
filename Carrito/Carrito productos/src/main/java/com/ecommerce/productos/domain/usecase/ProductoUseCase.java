package com.ecommerce.productos.domain.usecase;

import com.ecommerce.productos.domain.model.Producto;
import com.ecommerce.productos.domain.model.gateway.ProductoGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor //la única etiqueta que puede ir en el caso de uso
public class ProductoUseCase {
    //Logica de negocio
    //clase que se conecta con el productogateway
    private final ProductoGateway productoGateway;


    public Producto guardarProducto(Producto producto){
        //Condicional para hacer que el nombre y el precio sean obligatorios en el producto
        //si ambos son nulos, no se guarda el producto
        if (producto.getNombre() == null && producto.getPrecio() == null){
            throw new NullPointerException("Ingrese atributos correctamente - guardarProducto");
        }
        return productoGateway.guardar(producto); //Si todo esta bien, guarda el producto
    }


    public void eliminarProducto(Long id){
        //Elimina el producto por el ID
        //Retorna un vacío
        //Lanza excepcion en caso de haber error
        try {
            productoGateway.eliminarPorID(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al eliminar el producto. No existe");
        }
    }

    public Producto consultarProducto(Long id){
        //Busca un producto por su ID
        //Si no lo encuentra, retorna un producto vacío
        //Si lo encuentra, retorna todo el producto
        try{
            return productoGateway.consultarPorID(id);
        } catch (Exception e){
            System.out.println("Error al consultar el producto");

            //crear un producto vacío para retornarlo
            Producto productoVacio = new Producto();
            return productoVacio;
        }
    }

    public Producto actualizarProducto(Producto producto){
        //Valida primero que el ID no sea nulo. Si lo es, muestra una excepcion
        //Si no es nulo, actualiza el producto existente en la BD
        //retorna el producto actualizado
        if (producto.getId() == null){
            throw new NullPointerException("Revise que el ID del producto exista - actualizarProducto");
        }
        return productoGateway.actualizarPorID(producto);
    }

    //este metodo llama al gateway para obtener productos paginados
    public List<Producto> consultarProductos(int page, int size) //No necesita parametros, porque retorna todos los productos
    {
        //Obtiene todos los productos existentes en la BD
        //Retorna una lista
        //si hay error en la consulta, atrapa la excepcion
        try {
            return productoGateway.listarProductos(page, size);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al consultar los productos");
        }
    }
}


