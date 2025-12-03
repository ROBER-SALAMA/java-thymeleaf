package com.registro.usuarios.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.registro.usuarios.modelo.Venta;
import com.registro.usuarios.servicio.ProductoServicio;
import com.registro.usuarios.servicio.VentaServicio;

@Controller
@RequestMapping("/ventas")
public class VentaControlador {

    @Autowired
    private VentaServicio ventaServicio;

    @Autowired
    private ProductoServicio productoServicio;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Listar todas las ventas
    @GetMapping
    public String listarVentas(Model modelo) {
        List<Venta> ventas = ventaServicio.listarTodasLasVentas();
        modelo.addAttribute("ventas", ventas);
        return "ventas/lista";
    }

    // Mostrar formulario para nueva venta
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaVenta(Model modelo) {
        // Pasar la lista de productos al modelo
        modelo.addAttribute("listaProductos", productoServicio.listarTodosLosProductos());
        modelo.addAttribute("metodosPago", List.of("EFECTIVO", "TARJETA", "TRANSFERENCIA"));
        return "ventas/formulario";
    }

    // Guardar nueva venta
    @PostMapping("/guardar")
    public String guardarVenta(
            @RequestParam("clienteNombre") String clienteNombre,
            @RequestParam(value = "clienteEmail", required = false) String clienteEmail,
            @RequestParam(value = "clienteTelefono", required = false) String clienteTelefono,
            @RequestParam("metodoPago") String metodoPago,
            @RequestParam(value = "observaciones", required = false) String observaciones,
            @RequestParam("productos") String productosJson) {

        try {
            // Crear venta usando el nuevo método
            ventaServicio.crearVentaDesdeFormulario(
                    clienteNombre,
                    clienteEmail,
                    clienteTelefono,
                    metodoPago,
                    observaciones,
                    productosJson);

            return "redirect:/ventas";

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar venta: " + e.getMessage());
        }
    }

    // Ver detalles de venta
    @GetMapping("/detalles/{id}")
    public String verDetallesVenta(@PathVariable Long id, Model modelo) {
        Venta venta = ventaServicio.obtenerVentaPorId(id);
        modelo.addAttribute("venta", venta);

        try {
            Object jsonObject = objectMapper.readValue(venta.getJsonVenta(), Object.class);
            String jsonFormateado = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonObject);
            modelo.addAttribute("jsonFormateado", jsonFormateado);
        } catch (Exception e) {
            modelo.addAttribute("jsonFormateado", venta.getJsonVenta());
        }

        return "ventas/detalles";
    }

    // Eliminar venta
    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        ventaServicio.desactivarVenta(id);
        return "redirect:/ventas";
    }
}