package com.registro.usuarios.servicio;

import com.registro.usuarios.controlador.ProductoVentaDTO;
import com.registro.usuarios.modelo.Venta;
import com.registro.usuarios.repositorio.VentaRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class VentaServicio {

    @Autowired
    private VentaRepositorio ventaRepositorio;

    @Autowired
    private ProductoServicio productoServicio;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Venta> listarTodasLasVentas() {
        return ventaRepositorio.findAll();
    }

    public Venta obtenerVentaPorId(Long id) {
        return ventaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    // Crear y guardar venta con JSON
    public Venta crearVenta(String clienteNombre, String clienteEmail, String clienteTelefono,
            String metodoPago, String observaciones, List<ProductoVentaDTO> productos) {
        try {
            // Crear objeto JSON
            ObjectNode jsonVenta = objectMapper.createObjectNode();

            jsonVenta.put("clienteNombre", clienteNombre);
            if (clienteEmail != null)
                jsonVenta.put("clienteEmail", clienteEmail);
            if (clienteTelefono != null)
                jsonVenta.put("clienteTelefono", clienteTelefono);

            // Información de la venta
            jsonVenta.put("fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            jsonVenta.put("metodoPago", metodoPago);
            if (observaciones != null)
                jsonVenta.put("observaciones", observaciones);

            // Calcular totales
            double subtotal = 0;
            double iva = 0;
            double total = 0;

            // Agregar productos
            com.fasterxml.jackson.databind.node.ArrayNode productosArray = objectMapper.createArrayNode();
            for (ProductoVentaDTO producto : productos) {
                ObjectNode productoNode = objectMapper.createObjectNode();
                productoNode.put("productoId", producto.getProductoId());
                productoNode.put("productoNombre", producto.getProductoNombre());
                productoNode.put("precio", producto.getPrecio());
                productoNode.put("cantidad", producto.getCantidad());

                double subtotalProducto = producto.getPrecio() * producto.getCantidad();
                double ivaProducto = subtotalProducto * 0.16; // 16% IVA
                double totalProducto = subtotalProducto + ivaProducto;

                productoNode.put("subtotal", subtotalProducto);
                productoNode.put("iva", ivaProducto);
                productoNode.put("total", totalProducto);

                productosArray.add(productoNode);

                subtotal += subtotalProducto;
                iva += ivaProducto;
                total += totalProducto;
            }

            jsonVenta.set("productos", productosArray);
            jsonVenta.put("subtotal", subtotal);
            jsonVenta.put("iva", iva);
            jsonVenta.put("total", total);

            // Convertir a string JSON
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonVenta);

            // Crear y guardar venta
            Venta venta = new Venta();
            venta.setJsonVenta(jsonString);

            return ventaRepositorio.save(venta);

        } catch (Exception e) {
            throw new RuntimeException("Error al crear venta: " + e.getMessage());
        }
    }

    public Venta guardarVenta(String jsonVenta) {
        Venta venta = new Venta();
        venta.setJsonVenta(jsonVenta);
        return ventaRepositorio.save(venta);
    }

    public void desactivarVenta(Long id) {
        Venta venta = obtenerVentaPorId(id);
        venta.setActivo(false);
        ventaRepositorio.save(venta);
    }

    public void eliminarVenta(Long id) {
        ventaRepositorio.deleteById(id);
    }

    // Método para crear venta desde formulario
    public Venta crearVentaDesdeFormulario(String clienteNombre, String clienteEmail, String clienteTelefono,
            String metodoPago, String observaciones, String productosJson) {
        try {
            // Parsear productos JSON
            List<Map<String, Object>> productos = objectMapper.readValue(
                    productosJson,
                    new TypeReference<List<Map<String, Object>>>() {
                    });

            // Calcular totales
            double subtotal = 0;
            double iva = 0;
            double total = 0;

            for (Map<String, Object> producto : productos) {
                double precio = ((Number) producto.get("precio")).doubleValue();
                int cantidad = ((Number) producto.get("cantidad")).intValue();
                double subtotalProducto = precio * cantidad;
                double ivaProducto = subtotalProducto * 0.16;

                producto.put("subtotal", subtotalProducto);
                producto.put("iva", ivaProducto);
                producto.put("total", subtotalProducto + ivaProducto);

                subtotal += subtotalProducto;
                iva += ivaProducto;
                total += subtotalProducto + ivaProducto;
            }

            // Crear JSON completo de la venta
            Map<String, Object> ventaData = new HashMap<>();
            ventaData.put("clienteNombre", clienteNombre);
            if (clienteEmail != null && !clienteEmail.isEmpty()) {
                ventaData.put("clienteEmail", clienteEmail);
            }
            if (clienteTelefono != null && !clienteTelefono.isEmpty()) {
                ventaData.put("clienteTelefono", clienteTelefono);
            }
            ventaData.put("fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ventaData.put("metodoPago", metodoPago);
            if (observaciones != null && !observaciones.isEmpty()) {
                ventaData.put("observaciones", observaciones);
            }
            ventaData.put("productos", productos);
            ventaData.put("subtotal", subtotal);
            ventaData.put("iva", iva);
            ventaData.put("total", total);

            String jsonVenta = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(ventaData);

            // Crear y guardar venta
            Venta venta = new Venta();
            venta.setJsonVenta(jsonVenta);
            // venta.setTotalVenta(total);

            return ventaRepositorio.save(venta);

        } catch (Exception e) {
            throw new RuntimeException("Error al crear venta: " + e.getMessage());
        }
    }
}