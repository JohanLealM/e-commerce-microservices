package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository;

import com.ecommerce.productos.domain.model.Producto;
import com.ecommerce.productos.domain.model.gateway.ProductoGateway;
import com.ecommerce.productos.infraestructure.mapper.MapperProducto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository //indica que esta clase utiliza SQL y accede a la BD
@RequiredArgsConstructor
public class ProductoDataGatewayImpl implements ProductoGateway {

    private final MapperProducto mapperProducto; //Obj q convierte producto a productoData y viceversa
    private final ProductoDataJpaRepository repository; //Obj de Spring Data JPA que tiene metodos de save, findBy, deleteBy...


    @Override
    public Producto guardar (Producto producto) {
        ProductoData productoData = mapperProducto.toProductoData(producto); //Convierte Producto a ProductoData para usarlo y guardarlo
        return mapperProducto.toProducto(repository.save(productoData)); //Guarda en la BD y retorna Producto.
    }


    @Override
    public void eliminarPorID(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("El producto no existe"); //Si no hay un producto con ese ID, lanza excepcion
        }
        repository.deleteById(id); //Si existe, procede a eliminar
    }


    @Override
    public Producto consultarPorID(Long id) {
        return repository.findById(id)
                .map(productoData -> mapperProducto.toProducto(productoData))//Mapea el producto en caso de que exista
                .orElseThrow(() -> new RuntimeException("Producto no encontrado")); //Si no existe, lanza excepcion
    }


    @Override
    public Producto actualizarPorID(Producto producto) {
        ProductoData productoData = mapperProducto.toProductoData(producto); //Convierte el producto a ProductoData

        if(!repository.existsById(producto.getId())){
            throw new RuntimeException("Producto no encontrado"); //Verifica si existe. Si no existe, lanza excepcion
        }
        return mapperProducto.toProducto(repository.save(productoData)); //Si existe, actualiza y retorna Producto
    }


    @Override
    public List<Producto> listarProductos(int page, int size) {
        //crea un obj Pageable con la página y el tamaño deseado
        Pageable pageable = PageRequest.of(page, size);

        //se usa el mét0do findAll(Pageable) de Spring Data JPA para obtener los datos paginados
        Page<ProductoData> productos = repository.findAll(pageable);

        //Convierte cada ProductoData que consiguió en Producto utilizando el mnapper
        //El getContetn() devuelve solo la lista de elementos de la página que se haya seleccionado
        return productos.getContent()
                .stream().map(productoData -> mapperProducto.toProducto(productoData))
                .collect(Collectors.toList());

    }


}
