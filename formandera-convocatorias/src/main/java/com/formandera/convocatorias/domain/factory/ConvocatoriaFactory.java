package com.formandera.convocatorias.domain.factory;

import com.formandera.convocatorias.domain.model.Convocatoria;
import com.formandera.convocatorias.domain.model.ConvocatoriaLimitada;
import com.formandera.convocatorias.domain.model.ConvocatoriaMinima;
import com.formandera.convocatorias.domain.model.Curso;

/**
 * Factoría de convocatorias del sistema formandera.com.
 * 
 * Su responsabilidad es encapsular la lógica de creación de instancias
 * de los distintos tipos de convocatoria: ordinaria, limitada y mínima.
 * 
 * Esta clase se utiliza desde {@link Curso}, que delega aquí la creación
 * para mantener un bajo acoplamiento con las clases concretas de convocatoria.</p>
 */
public class ConvocatoriaFactory {

    private ConvocatoriaFactory() {
        // Constructor privado para evitar instanciación
    }

    /**
     * Crea una convocatoria ordinaria.
     *
     * @param curso el curso asociado
     * @return una nueva convocatoria ordinaria en estado abierta
     */
    public static Convocatoria crearConvocatoria(Curso curso) {
        return new Convocatoria(curso);
    }

    /**
     * Crea una convocatoria limitada, que solo admitirá un número máximo de participantes.
     *
     * @param curso el curso asociado
     * @param maxAdmitidos número máximo de admitidos
     * @return una nueva convocatoria limitada en estado abierta
     */
    public static ConvocatoriaLimitada crearConvocatoria(
            Curso curso, int maxAdmitidos) {
        return new ConvocatoriaLimitada(curso, maxAdmitidos);
    }

    /**
     * Crea una convocatoria mínima, que solo podrá cerrarse si se alcanzan
     * los mínimos establecidos de inscripciones y cursos completados.
     *
     * @param curso el curso asociado
     * @param minimoInscripciones número mínimo de inscripciones necesarias para cerrar
     * @param minimoCursosCompletados número mínimo de cursos completados que debe tener cada inscrito
     * @return una nueva convocatoria mínima en estado abierta
     */
    public static ConvocatoriaMinima crearConvocatoria(
            Curso curso,
            int minimoInscripciones, int minimoCursosCompletados) {
        return new ConvocatoriaMinima(curso, minimoInscripciones, minimoCursosCompletados);
    }
}

