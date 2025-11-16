package com.formandera.convocatorias.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Convocatoria {

    protected final Curso curso;
    protected final Usuario propietario;
    protected final List<Inscripcion> inscripciones = new ArrayList<>();

    protected boolean esAbierta = true;
    protected boolean esCancelada = false;

    public Convocatoria(Curso curso) {
        this.curso = Objects.requireNonNull(curso);
        this.propietario = curso.getAutor();
    }

    public Curso getCurso() {
        return curso;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public List<Inscripcion> getInscripciones() {
        return this.inscripciones;
    }

    public boolean getEsAbierta() {
        return esAbierta;
    }

    public boolean getEsCancelada() {
        return esCancelada;
    }
    
    public Inscripcion getInscripcionPorNombre(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            return null;
        }

        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getUsuario().getNombre() == nombreUsuario) {
                return inscripcion;
            }
        }
        return null;
    }

    public boolean inscribir(Usuario usuario) {
    	if (usuario == null) {
            return false;
        }
    	usuario.retirarCredito(curso.getPrecio());
    	if (!esAbierta || esCancelada) {
            return false;
        }
        
        if (usuario.equals(propietario)) {
            return false;
        }
        if (usuario.getCredito() < curso.getPrecio()) {
            return false;
        }
        if(getInscripcionPorNombre(usuario.getNombre()) != null) {
        	return false;
        }
        

        int completados = usuario.getCursosCompletadosPorTematica(curso.getTematica());
        Inscripcion inscripcion = new Inscripcion(usuario, completados);
        inscripciones.add(inscripcion);

        return true;
    }


    protected void admitirInscripciones() {
        for (Inscripcion i : inscripciones) {
            i.marcarComoAdmitido();
        }
    }


    public List<Inscripcion> getAdmitidas() {
        return inscripciones.stream()
                .filter(Inscripcion::isAdmitido)
                .collect(Collectors.toUnmodifiableList());
    }
    
    protected void transferirDinero() {
    	double cantidad = (inscripciones.size()+1) * curso.getPrecio();
    	propietario.agregarCredito(cantidad);
    }

    public boolean cerrar() {
        if (!esAbierta) {
            return false;
        }

        admitirInscripciones();

        esAbierta = false;

        transferirDinero();
        
        return true;
    }
    
    public boolean cancelar() {
        if (!esAbierta || esCancelada) {
            return false;
        }
        esAbierta = false;
        esCancelada = true;
        
        for(Inscripcion inscripcion : getInscripciones()) {
        	inscripcion.getUsuario().agregarCredito(curso.getPrecio());
        }
        
        return true;
    }
    
 


}

