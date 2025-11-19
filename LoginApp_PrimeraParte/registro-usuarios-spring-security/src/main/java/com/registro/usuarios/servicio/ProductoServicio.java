package com.registro.usuarios.servicio;

import com.registro.usuarios.modelo.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.registro.usuarios.controlador.ProductoDTO;
import com.registro.usuarios.repositorio.ProductoRepositorio;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    public List<ProductoDTO> listarTodosLosProductos() {
        return productoRepositorio.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertirADTO(producto);
    }

    public ProductoDTO guardarProducto(ProductoDTO productoDTO) {
        Producto producto = convertirAEntidad(productoDTO);
        Producto productoSaved = productoRepositorio.save(producto);
        return convertirADTO(productoSaved);
    }

    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto productoExistente = productoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setUnidadMedida(productoDTO.getUnidadMedida());
        productoExistente.setCategoria(productoDTO.getCategoria());
        productoExistente.setStock(productoDTO.getStock());

        Producto productoActualizado = productoRepositorio.save(productoExistente);
        return convertirADTO(productoActualizado);
    }

    public void eliminarProducto(Long id) {
        productoRepositorio.deleteById(id);
    }

    // Métodos de conversión
    private ProductoDTO convertirADTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getUnidadMedida(),
                producto.getCategoria(),
                producto.getStock());
    }

    private Producto convertirAEntidad(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setUnidadMedida(productoDTO.getUnidadMedida());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setStock(productoDTO.getStock());
        return producto;
    }
}