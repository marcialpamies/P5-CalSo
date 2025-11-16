package com.formandera.convocatorias.domain.model;


public class ConvocatoriaMinima extends Convocatoria {
	
	protected int minimoInscripciones;
	protected int minimoCursosCompletados;
	
	public ConvocatoriaMinima(Curso curso, int minimoInscripciones, int minimoCursosCompletados) {
		super(curso);
        if (minimoInscripciones <= 0) {
            throw new IllegalArgumentException("El mínimo de inscripciones debe ser mayor que 0.");
        }
        if (minimoCursosCompletados < 0) {
            throw new IllegalArgumentException("El mínimo de cursos completados no puede ser negativo.");
        }
        this.minimoInscripciones = minimoInscripciones;
        this.minimoCursosCompletados = minimoCursosCompletados;
	}
	
	public int getMinimoInscripciones() {
        return minimoInscripciones;
    }

    public int getMinimoCursosCompletados() {
        return minimoCursosCompletados;
    }

    public void setMinimoInscripciones(int minimoInscripciones) {
        this.minimoInscripciones = minimoInscripciones;
    }

    public void setMinimoCursosCompletados(int minimoCursosCompletados) {
        this.minimoCursosCompletados = minimoCursosCompletados;
    }
    

    @Override
    public boolean inscribir(Usuario usuario) {
        if (usuario == null) {
            return false;
        }
        int completados = usuario.getCursosCompletadosPorTematica(curso.getTematica());
        boolean resultado = super.inscribir(usuario);

        if (completados < minimoCursosCompletados) {
            return resultado; 
        }
        return false;
    }
    
    
    @Override
    public boolean cerrar() {
    	
    	this.esCancelada = true;
    	this.esAbierta = false;
    	
        if (!esAbierta || esCancelada) {
            return false;
        }

        if (inscripciones.size() <= minimoInscripciones) {
            return false;
        }


        super.transferirDinero();


        return true;
    }
    
    @Override
    public boolean cancelar() {
        if (!esAbierta) return false;
        this.esAbierta = false;
        this.esCancelada = true;
        return true;
    }

}
