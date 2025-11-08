# P5-CalSo

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
          + getCurso(): Curso
          + getPropietario(): Usuario
          + getInscripciones(): List~Inscripcion~
          + getEsAbierta(): boolean
          + getEsCancelada(): boolean
          + cerrar(): boolean
          + cancelar(): void
          + inscribir(usuario: Usuario): boolean
          + admitirInscripciones(): void
          + getAdmitidas(): List~Inscripcion~
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

