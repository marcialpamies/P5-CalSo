package com.formandera.convocatorias.domain.model;

import java.util.Comparator;
import java.util.Objects;


public class ConvocatoriaLimitada extends Convocatoria {

    protected int maxAdmitidos;

    public ConvocatoriaLimitada(Curso curso, int maxAdmitidos) {
        super(curso);
        if (maxAdmitidos <= 0) {
            throw new IllegalArgumentException("El número máximo de admitidos debe ser mayor que 0.");
        }
        this.maxAdmitidos = maxAdmitidos;
    }


    public int getMaxAdmitidos() {
        return maxAdmitidos;
    }

    public void setMaxAdmitidos(int maxAdmitidos) {
        if (maxAdmitidos <= 0) {
            throw new IllegalArgumentException("El número máximo de admitidos debe ser mayor que 0.");
        }
        this.maxAdmitidos = maxAdmitidos;
    }

    @Override
    protected void admitirInscripciones() {
 
        inscripciones.forEach(Inscripcion::marcarComoNoAdmitido);

        inscripciones.stream()
                .sorted(Comparator.comparingInt(Inscripcion::getCursosCompletadosTematica))
                .limit(maxAdmitidos)
                .forEach(Inscripcion::marcarComoAdmitido);
    }
    
    @Override
    protected void transferirDinero() {
    	propietario.agregarCredito(this.inscripciones.size()*curso.getPrecio());
    	inscripciones.stream().filter(i->i.isAdmitido()).forEach(ins->ins.restituirCredito(curso.getPrecio()));
    }
    
    @Override
    public boolean cerrar() {
        if (!esAbierta || esCancelada) {
            return false;
        }
        admitirInscripciones();
        esAbierta = false;
        transferirDinero();
        return true;
    }

    @Override
    public String toString() {
        return "ConvocatoriaLimitada{" +
                "curso=" + curso.getNombre() +
                ", propietario=" + propietario.getNombre() +
                ", maxAdmitidos=" + maxAdmitidos +
                ", inscripciones=" + inscripciones.size() +
                ", esAbierta=" + esAbierta +
                ", esCancelada=" + esCancelada +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ConvocatoriaLimitada)) return false;
        ConvocatoriaLimitada other = (ConvocatoriaLimitada) obj;
        return super.equals(obj) && maxAdmitidos == other.maxAdmitidos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxAdmitidos);
    }
}

