# P5- Calso

# Descripción del sistema de convocatorias (formandera.com)

El proyecto simula el comportamiento de un sistema de gestión de convocatorias de cursos de la plataforma educativa formandera.com, que se representa esquemáticamente en el diagrama siguiente.

En este sistema, un usuario registrado (caracterizado por su nombre y el crédito disponible) puede crear un curso y posteriormente abrir una convocatoria para ofrecer una nueva edición de dicho curso al resto de usuarios.
El único usuario que puede crear una convocatoria es el autor de curso y se convertirá en el propietario de la convocatoria.

En el momento en que el propietario abre una convocatoria, esta pasa a estar en estado “abierta” (ver Diagrama de estados), estado en el que los demás usuarios pueden inscribirse para participar en la edición del curso.

Una inscripción se caracteriza por el usuario que la realiza y por el número de cursos completados por el usuario en la misma temática que el curso ofertado.

```mermaid
classDiagram

    %% ==== Estilos ====
    classDef rojo fill:#ffcccc,stroke:#ff0000,stroke-width:2px;

    %% ==== Clases principales ====

    class Usuario {
        - String nombre
        - double credito
        - List~Curso~ cursosCreados
        - List~Inscripcion~ inscripciones
        + Usuario(nombre: String)
        + getNombre(): String
        + getCredito(): double
        + agregarCredito(cantidad: double): void
        + retirarCredito(cantidad: double): void
        + crearCurso(nombre: String, tematica: String, precio: double): Curso
        + registrarInscripcion(inscripcion: Inscripcion): void
        + getCursosCompletadosPorTematica(tematica: String): int
    }

    class Curso {
        - String nombre
        - String tematica
        - double precio
        - Usuario autor
        + Curso(nombre: String, tematica: String, precio: double, autor: Usuario)
        + getNombre(): String
        + getTematica(): String
        + getPrecio(): double
        + getAutor(): Usuario
        + abrirConvocatoria(): Convocatoria
    }

    class Convocatoria {
        - Curso curso
        - Usuario propietario
        - List~Inscripcion~ inscripciones
        - boolean esAbierta
        - boolean esCancelada
        + Convocatoria(curso: Curso, propietario: Usuario)
        + getCurso(): Curso
        + getPropietario(): Usuario
        + getInscripciones(): List~Inscripcion~
        + getEsAbierta(): boolean
        + getEsCancelada(): boolean
        + getInscripcionPorNombre(nombreUsuario: String): Inscripcion
        + inscribir(usuario: Usuario): boolean
        + cerrar(): boolean
        + cancelar(): boolean
        # admitirInscripciones(): void
        # transferirDinero(): void
    }

    class ConvocatoriaLimitada {
        - int maxAdmitidos
        - int totalInscripciones
        + ConvocatoriaLimitada(curso: Curso, propietario: Usuario, maxAdmitidos: int)
        + getMaxAdmitidos(): int
        + setMaxAdmitidos(max: int): void
        + getTotalInscripciones(): int
        + cerrar(): boolean
        + admitirInscripciones(): void
    }

    class ConvocatoriaMinima {
        - int minimoInscripciones
        - int minimoCursosCompletados
        + ConvocatoriaMinima(curso: Curso, propietario: Usuario, minIns: int, minCursos: int)
        + getMinimoInscripciones(): int
        + getMinimoCursosCompletados(): int
        + setMinimoInscripciones(v: int): void
        + setMinimoCursosCompletados(v: int): void
        + cerrar(): boolean
        + cancelar(): boolean
        + inscribir(usuario: Usuario): boolean
        + admitirInscripciones(): void
    }

    class Inscripcion {
        - Usuario usuario
        - int cursosCompletadosTematica
        - boolean admitido
        + Inscripcion(usuario: Usuario, cursosCompletados: int)
        + marcarComoAdmitido(): void
        + isAdmitido(): boolean
        + getUsuario(): Usuario
        + getCursosCompletadosTematica(): int
    }

    class ConvocatoriaFactory {
        + crearConvocatoria(curso: Curso, propietario: Usuario): Convocatoria
        + crearConvocatoriaLimitada(curso: Curso, propietario: Usuario, maxAdmitidos: int): ConvocatoriaLimitada
        + crearConvocatoriaMinima(curso: Curso, propietario: Usuario, minIns: int, minCursos: int): ConvocatoriaMinima
    }

    %% ==== Relaciones entre clases ====
    Usuario "1" -- "0..*" Curso : crea
    Usuario "1" -- "0..*" Inscripcion : realiza
    Curso "1" -- "0..*" Convocatoria : genera
    Convocatoria "1" -- "0..*" Inscripcion
    Convocatoria <|-- ConvocatoriaLimitada
    Convocatoria <|-- ConvocatoriaMinima
    Curso ..> ConvocatoriaFactory : delega creación
    ConvocatoriaFactory ..> Convocatoria

    %% ==== Aplicar estilos rojos a tres clases ====
    class Convocatoria rojo
    class ConvocatoriaLimitada rojo
    class ConvocatoriaMinima rojo

```

  ```mermaid
  classDiagram
      %% ==== Clases principales ====
  
      class Usuario {
          - String nombre
          - double credito
          - List~Curso~ cursosCreados
          - List~Inscripcion~ inscripciones
          + crearCurso(nombre: String, tematica: String, precio: double): Curso
          + agregarCredito(cantidad: double): void
          + retirarCredito(cantidad: double): void
          + getCursosCompletadosPorTematica(tematica: String): int
          + getCredito(): double
      }
  
      class Curso {
          - String nombre
          - String tematica
          - double precio
          - Usuario autor
          - double valoracionMedia
          + abrirConvocatoria(): Convocatoria
          + abrirConvocatoria(maxAdmitidos: int): ConvocatoriaLimitada
          + abrirConvocatoria(minimoInscripciones: int, minimoCursosCompletados: int): ConvocatoriaMinima
          + getTematica(): String
          + getPrecio(): double
          + getAutor(): Usuario
      }
  
      class Convocatoria {
        - Curso curso
        - Usuario propietario
        - List~Inscripcion~ inscripciones
        - boolean esAbierta
        - boolean esCancelada
        + Convocatoria(curso: Curso)
        + getCurso(): Curso
        + getPropietario(): Usuario
        + getInscripciones(): List~Inscripcion~
        + getEsAbierta(): boolean
        + getEsCancelada(): boolean
        + getInscripcionPorNombre(nombreUsuario: String): Inscripcion
        + inscribir(usuario: Usuario): boolean
        + getAdmitidas(): List~Inscripcion~
        + cerrar(): boolean
        + cancelar(): boolean
        # admitirInscripciones(): void
        # transferirDinero(): void
    }
  
      class ConvocatoriaLimitada {
          - int maxAdmitidos
          + getMaxAdmitidos(): int
          + setMaxAdmitidos(): void
          + cerrar(): boolean
          + admitirInscripciones(): void
      }
  
      class ConvocatoriaMinima {
          - int minimoInscripciones
          - int minimoCursosCompletados
          + getMinimoInscripciones(): int
          + getMinimoCursosCompletados(): int
          + setMinimoInscripciones(): void
          + setMinimoCursosCompletados(): void
          + cerrar(): boolean
          + cancelar(): void
          + inscribir(usuario: Usuario): boolean
          + admitirInscripciones(): void
      }
  
      class ConvocatoriaFactory {
          + crearConvocatoria(curso: Curso, propietario: Usuario): Convocatoria
          + crearConvocatoria(curso: Curso, propietario: Usuario, maxAdmitidos: int): ConvocatoriaLimitada
          + crearConvocatoria(curso: Curso, propietario: Usuario, minimoInscripciones: int, minimoCursosCompletados: int): ConvocatoriaMinima
      }
  
      class Inscripcion {
          - Usuario usuario
          - int cursosCompletadosTematica
          - boolean admitido
          + marcarComoAdmitido(): void
          + isAdmitido(): boolean
          + getUsuario(): Usuario
          + getCursosCompletadosTematica(): int
      }
  
      %% ==== Relaciones entre clases ====
  
      Usuario "1" -- "0..*" Curso
      Usuario "1" -- "0..*" Inscripcion
      Curso "1" -- "0..*" Convocatoria
      Convocatoria "1" -- "0..*" Inscripcion
      Convocatoria <|-- ConvocatoriaLimitada
      Convocatoria <|-- ConvocatoriaMinima
      Curso ..> ConvocatoriaFactory : delega creación
      ConvocatoriaFactory ..> Convocatoria : crea
  
  
  
  ```

Para poder inscribirse en una convocatoria ordinaria, deben cumplirse las siguientes condiciones:

  1. La convocatoria debe estar abierta.
  2. El usuario que se inscribe debe ser distinto del propietario del curso.
  3. El usuario debe disponer de crédito suficiente para pagar el precio del curso.
  4. El usuario no debe estar ya inscrito previamente en la misma convocatoria.

Cuando se cumplen todas las condiciones, se registra la inscripción y se descuenta el precio del curso del crédito del usuario.
En cualquier momento, el propietario puede cerrar la convocatoria, con lo que esta pasa a estar en estado “cerrada”.
Cerrar una convocatoria implica admitir las inscripciones registradas y transferir el crédito correspondiente (precio del curso × número de alumnos admitidos) al propietario.
Si no existen inscripciones admitidas, la convocatoria no puede cerrarse.
Asimismo, el propietario puede cancelar la convocatoria, lo que hace que pase a estado “cancelada”.
Cancelar una convocatoria significa devolver el crédito previamente descontado a todos los usuarios inscritos y finalizar el proceso sin admisiones.

  ```mermaid
  stateDiagram
      [*] --> Abierta : creación inicial
      Abierta --> Cancelada : cancelar()
      Cancelada --> [*]
      Cerrada --> [*]
      Abierta --> Cerrada : cerrar()[resultado == true]
      Abierta --> Abierta : cerrar()[resultado == false]<br> inscribir(Usuario usuario) <br> getInscripciones() <br> getCurso() <br> getPropietario() <br> getEsAbierta() <br> getEsCancelada()
      Cerrada --> Cerrada : getAdmitidas() <br> getInscripciones() <br> getCurso() <br> getPropietario() <br> getEsAbierta() <br> getEsCancelada()
      
      note right of Abierta
        Estado inicial tras crear la convocatoria.
        - Se permiten inscripciones válidas.
        - cerrar() intenta cerrar y devuelve true/false.
        - cancelar() cambia el estado a Cancelada.
      end note
  
      note right of Cerrada
        Estado final exitoso:
        - Las inscripciones admitidas son definitivas.
        - Se transfieren los créditos al propietario.
      end note
  
      note right of Cancelada
        Estado final no exitoso:
        - No se transfieren créditos.
        - No se permiten nuevas inscripciones.
      end note
  
  
  ```
## Tipos de convocatorias

El sistema contempla tres tipos de convocatorias, todas ellas heredadas de la clase base Convocatoria:

### 1. Convocatoria ordinaria

Es el tipo de convocatoria por defecto.
Todas las inscripciones registradas se consideran admitidas cuando se cierra, siempre que haya al menos una.
El cierre puede ejecutarse en cualquier momento y transfiere el crédito total al propietario del curso.

### 2. Convocatoria limitada

Es una especialización de Convocatoria que restringe el número máximo de inscripciones admitidas.
Este número máximo se fija en el constructor.

En el momento del cierre, se seleccionan como admitidos los usuarios con mayor número de cursos completados en la temática del curso, en orden descendente, hasta agotar el número de plazas disponibles.
Si no se alcanza al menos una inscripción admitida, la convocatoria no puede cerrarse.

### 3. Convocatoria mínima

Es un tipo de convocatoria que establece dos condiciones mínimas en el momento de su creación:

  - Un número mínimo de inscripciones para poder cerrar la convocatoria.
  - Un mínimo de cursos completados en la temática del curso que deben tener los usuarios para poder inscribirse.

La convocatoria mínima solo puede cerrarse si se cumple el número mínimo de inscripciones.
Si no se alcanza ese mínimo, permanecerá abierta hasta que el propietario decida cancelarla, en cuyo caso los créditos de los usuarios inscritos son devueltos.

El comportamiento de admisión en el cierre es idéntico al de la convocatoria ordinaria: todas las inscripciones válidas se admiten.

```mermaid
stateDiagram-v2
    Cancelada --> [*]
    Cerrada --> [*]
    Cancelada --> Cancelada : inscribir()/cerrar()/cancelar()<br>retorna false
    

    [*] --> Abierta : new Convocatoria(curso)
    
    Abierta --> Abierta : inscribir(usuario)<br>[condición NO se cumple]<br>retorna false
    Abierta --> Abierta : cerrar()<br>[sin inscripciones admitidas]<br>retorna false

    Abierta --> Cerrada : cerrar()<br>[existen inscripciones admitidas]<br>retorna true<br>/transferirDinero()
    Abierta --> Cancelada : cancelar()<br>retorna true<br>/restituir créditos

    Cerrada --> Cerrada : inscribir()/cerrar()/cancelar()<br>retorna false
    

    
    

    %% Notas explicativas
    note right of Abierta
      Estado inicial tras crear la convocatoria.<br>
      esAbierta = true, esCancelada = false.<br>
      Permite inscripciones válidas.<br>
      cerrar() sólo cambia de estado si hay inscripciones admitidas.
    end note

    note right of Cerrada
      Estado final exitoso.<br>
      esAbierta = false, esCancelada = false.<br>
      Se transfieren créditos al propietario.<br>
      No se aceptan nuevas inscripciones.
    end note

    note right of Cancelada
      Estado final no exitoso.<br>
      esAbierta = false, esCancelada = true.<br>
      Se devuelven los créditos a los usuarios inscritos.<br>
      No se aceptan nuevas inscripciones.
    end note


```
