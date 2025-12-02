package com.registro.usuarios.modelo;

import javax.persistence.*;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "correo_electronico", nullable = false, length = 100)
    private String correoElectronico;

    @Column(name = "alergias_conocidas", columnDefinition = "TEXT")
    private String alergiasConocidas;

    @Column(name = "patologias_condiciones", columnDefinition = "TEXT")
    private String patologiasCondiciones;

    @Column(name = "fecha_preferida", nullable = false, length = 10)
    private String fechaPreferida; 

    @Column(name = "hora_preferida", nullable = false, length = 5)
    private String horaPreferida;

    @Column(name = "instrucciones_notas", columnDefinition = "TEXT")
    private String instruccionesNotas;

    @Column(name = "acepta_terminos", nullable = false)
    private boolean aceptaTerminos;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicios servicio;

    public Cita() {
    }

    public Cita(String correoElectronico, String alergiasConocidas, String patologiasCondiciones,
            String fechaPreferida, String horaPreferida, String instruccionesNotas,
            boolean aceptaTerminos, Mascota mascota, Servicios servicio) {
        this.correoElectronico = correoElectronico;
        this.alergiasConocidas = alergiasConocidas;
        this.patologiasCondiciones = patologiasCondiciones;
        this.fechaPreferida = fechaPreferida;
        this.horaPreferida = horaPreferida;
        this.instruccionesNotas = instruccionesNotas;
        this.aceptaTerminos = aceptaTerminos;
        this.mascota = mascota;
        this.servicio = servicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getAlergiasConocidas() {
        return alergiasConocidas;
    }

    public void setAlergiasConocidas(String alergiasConocidas) {
        this.alergiasConocidas = alergiasConocidas;
    }

    public String getPatologiasCondiciones() {
        return patologiasCondiciones;
    }

    public void setPatologiasCondiciones(String patologiasCondiciones) {
        this.patologiasCondiciones = patologiasCondiciones;
    }

    public String getFechaPreferida() {
        return fechaPreferida;
    }

    public void setFechaPreferida(String fechaPreferida) {
        this.fechaPreferida = fechaPreferida;
    }

    public String getHoraPreferida() {
        return horaPreferida;
    }

    public void setHoraPreferida(String horaPreferida) {
        this.horaPreferida = horaPreferida;
    }

    public String getInstruccionesNotas() {
        return instruccionesNotas;
    }

    public void setInstruccionesNotas(String instruccionesNotas) {
        this.instruccionesNotas = instruccionesNotas;
    }

    public boolean isAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Servicios getServicio() {
        return servicio;
    }

    public void setServicio(Servicios servicio) {
        this.servicio = servicio;
    }

    // Métodos auxiliares para obtener objetos LocalDate/LocalTime si los necesitas
    public java.time.LocalDate getFechaPreferidaAsLocalDate() {
        if (this.fechaPreferida != null && !this.fechaPreferida.isEmpty()) {
            return java.time.LocalDate.parse(this.fechaPreferida);
        }
        return null;
    }

    public java.time.LocalTime getHoraPreferidaAsLocalTime() {
        if (this.horaPreferida != null && !this.horaPreferida.isEmpty()) {
            return java.time.LocalTime.parse(this.horaPreferida);
        }
        return null;
    }
}