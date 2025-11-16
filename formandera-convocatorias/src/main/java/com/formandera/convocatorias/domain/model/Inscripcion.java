package com.formandera.convocatorias.domain.model;

import java.util.Objects;


public class Inscripcion {


    private final Usuario usuario;
    private final int cursosCompletadosTematica;
    private boolean admitido;

    /**
     * Crea una nueva inscripción de un usuario en una convocatoria.
     *
     * @param usuario usuario que se inscribe (no puede ser null)
     * @param cursosCompletadosTematica número de cursos completados en la temática correspondiente
     */
    public Inscripcion(Usuario usuario, int cursosCompletadosTematica) {
        this.usuario = Objects.requireNonNull(usuario);
        if (cursosCompletadosTematica < 0) {
            throw new IllegalArgumentException("El número de cursos completados no puede ser negativo.");
        }
        this.cursosCompletadosTematica = cursosCompletadosTematica;
        this.admitido = false;
    }
    
    public void restituirCredito(double cantidad) {
    	this.usuario.agregarCredito(cantidad);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getCursosCompletadosTematica() {
        return cursosCompletadosTematica;
    }

    public boolean isAdmitido() {
        return admitido;
    }

    public void marcarComoAdmitido() {
        this.admitido = true;
    }

    public void marcarComoNoAdmitido() {
        this.admitido = false;
    }


    @Override
    public String toString() {
        return "Inscripcion{" +
                "usuario=" + usuario.getNombre() +
                ", cursosCompletadosTematica=" + cursosCompletadosTematica +
                ", admitido=" + admitido +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Inscripcion)) return false;
        Inscripcion other = (Inscripcion) obj;
        return usuario.equals(other.usuario)
                && cursosCompletadosTematica == other.cursosCompletadosTematica
                && admitido == other.admitido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, cursosCompletadosTematica, admitido);
    }
}

