package com.formandera.convocatorias.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
	private final String nombre;
	private double credito;
	private final List<Curso> cursosCreados = new ArrayList<>();
	private final List<Inscripcion> inscripciones = new ArrayList<>();
	private final List<Curso> cursosCompletados = new ArrayList<>();
	
	public Usuario(String nombre, double creditoInicial) {
		this.nombre = Objects.requireNonNull(nombre);
		this.credito = creditoInicial;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public double getCredito() {
		return this.credito;
	}
	
	public List<Curso> getCursosCreados(){
		return new ArrayList<>(this.cursosCreados);
	}
	
	public List<Inscripcion> getInscripciones(){
		return new ArrayList<>(this.inscripciones);
	}
	
	public List<Curso> getCursosCompletados(){
		return new ArrayList<>(this.cursosCompletados);
	}
	
	public Curso crearCurso(String nombre, String temática, double precio) {
		Curso curso = new Curso(nombre, temática, precio, this);
		this.cursosCreados.add(curso);
		return curso;
	}
	
	public void agregarCredito(double creditoAdicional) {
		if(creditoAdicional < 0) {
			throw new IllegalArgumentException("El credito adicional no puede ser negativo");
		}
		this.credito += creditoAdicional;
	}
	
	public void retirarCredito(double cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad a retirar debe ser positiva.");
        }
        if (cantidad > credito) {
            throw new IllegalStateException("Crédito insuficiente.");
        }
        credito -= cantidad;
    }
		
	
	public void registrarCursoCompletado(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("El curso no puede ser nulo.");
        }
        if (!cursosCompletados.contains(curso)) {
            cursosCompletados.add(curso);
        }
    }
	
	/**
     * Devuelve el número de cursos completados por el usuario en la temática indicada.
     */
    public int getCursosCompletadosPorTematica(String tematica) {
        if (tematica == null) {
            throw new IllegalArgumentException("La temática no puede ser nula.");
        }
        return (int) cursosCompletados.stream()
                .filter(curso -> tematica.equalsIgnoreCase(curso.getTematica()))
                .count();
    }
	
	void registrarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true; 
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false; 
	    }
	    Usuario other = (Usuario) obj;

	    // Consideramos que dos usuarios son iguales si tienen el mismo nombre.
	    return nombre.equalsIgnoreCase(other.nombre);
	}

	@Override
	public int hashCode() {
	    return java.util.Objects.hash(nombre.toLowerCase());
	}


}
