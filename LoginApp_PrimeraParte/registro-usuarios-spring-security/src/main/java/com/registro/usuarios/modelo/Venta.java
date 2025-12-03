package com.registro.usuarios.modelo;

import javax.persistence.*;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "json_venta", columnDefinition = "LONGTEXT")
    private String jsonVenta;

    @Column(name = "activo", columnDefinition = "boolean default true")
    private boolean activo = true;

    public Venta() {
    }

    public Venta(String jsonVenta) {
        this.jsonVenta = jsonVenta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJsonVenta() {
        return jsonVenta;
    }

    public void setJsonVenta(String jsonVenta) {
        this.jsonVenta = jsonVenta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}