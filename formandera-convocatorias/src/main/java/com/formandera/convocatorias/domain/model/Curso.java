package com.formandera.convocatorias.domain.model;

import com.formandera.convocatorias.domain.factory.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Curso {
	
	private final String nombre;
	private final String tematica;
	private final double precio;
	private final Usuario autor;
	private double valoracionMedia = 0;
	private final List<Convocatoria> convocatorias = new ArrayList<>();
	
	public Curso (String nombre, String tematica, double precio, Usuario autor) {
		this.nombre = Objects.requireNonNull(nombre);
		this.tematica = Objects.requireNonNull(tematica);
		if(precio<0) {
			throw new IllegalArgumentException("El precio no puede ser negativo");
		}
		this.precio = precio;
		this.autor = Objects.requireNonNull(autor);
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getTematica() {
		return this.tematica;
	}
	
	public double getPrecio() {
        return precio;
    }
	
	public Usuario getAutor() {
		return this.autor;
	}
	
	public double getValoracionMedia() {
        return valoracionMedia;
    }
	
	public List<Convocatoria> getConvocatorias() {
	    return new ArrayList<>(convocatorias);
	}

    public void setValoracionMedia(double valoracionMedia) {
        this.valoracionMedia = valoracionMedia;
    }
    
    // Convocatoria ordinaria
    public Convocatoria abrirConvocatoria() {
        Convocatoria c = ConvocatoriaFactory.crearConvocatoria(this);
        this.convocatorias.add(c);
        return c;
    }

    // Convocatoria limitada
    public Convocatoria abrirConvocatoria(int maxAdmitidos) {
    	Convocatoria c = ConvocatoriaFactory.crearConvocatoria(this, maxAdmitidos);
        this.convocatorias.add(c);
        return c;
    }

    // Convocatoria mínima
    public Convocatoria abrirConvocatoria(int minimoInscripciones, int minimoCursosCompletados) {
    	Convocatoria c = ConvocatoriaFactory.crearConvocatoria(this, minimoInscripciones, minimoCursosCompletados);
    	this.convocatorias.add(c);
        return c;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Mismo objeto en memoria
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Nulo o de otra clase
        }
        Curso other = (Curso) obj;

        // Consideramos que dos cursos son iguales si:
        // - Tienen el mismo nombre,
        // - Pertenecen a la misma temática,
        // - Y tienen el mismo autor (propietario).
        // El precio y la valoración media pueden variar en distintas ediciones.
        return nombre.equalsIgnoreCase(other.nombre)
                && tematica.equalsIgnoreCase(other.tematica)
                && autor.equals(other.autor);
    }

    @Override
    public int hashCode() {
        // Generamos el hash basándonos en los atributos usados en equals()
        return java.util.Objects.hash(nombre.toLowerCase(), tematica.toLowerCase(), autor);
    }


}
