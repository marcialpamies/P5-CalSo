[VOLVER AL README](../README.md)

# Especificación de métodos — Clase `ConvocatoriaMinima`

## Descripción general

La clase `ConvocatoriaMinima` representa una convocatoria de curso que **exige un mínimo de inscripciones** y un **mínimo de cursos completados en la temática** para poder participar y para que la convocatoria pueda cerrarse.  
Hereda el comportamiento base de la clase `Convocatoria` y redefine parte de la lógica de inscripción y cierre.

En este tipo de convocatoria:

1. Solo pueden inscribirse usuarios que hayan completado al menos `minimoCursosCompletados` cursos en la temática del curso.  
2. La convocatoria solo puede cerrarse si el número total de inscripciones es al menos `minimoInscripciones`.  
3. Una vez cumplidas estas condiciones, se aplica la lógica ordinaria de admisión y transferencia de dinero definida en `Convocatoria`.

---

## 1. Constructor

### Firma
```java
public ConvocatoriaMinima(Curso curso, int minimoInscripciones, int minimoCursosCompletados)
```

### Propósito
Crea una convocatoria mínima asociada a un curso, estableciendo:

- Un número mínimo de inscripciones requeridas para poder cerrar la convocatoria.
- Un número mínimo de cursos completados en la temática para que un usuario pueda inscribirse.

El propietario de la convocatoria es el **autor del curso**, determinado en la superclase `Convocatoria`.

### Parámetros

| Nombre                    | Tipo    | Descripción |
|---------------------------|---------|-------------|
| `curso`                   | `Curso` | Curso para el que se abre la convocatoria. No puede ser `null`. |
| `minimoInscripciones`     | `int`   | Número mínimo de inscripciones requerido para poder cerrar la convocatoria. Debe ser mayor que 0. |
| `minimoCursosCompletados` | `int`   | Número mínimo de cursos completados en la temática que debe tener un usuario para poder inscribirse. No puede ser negativo. |

### Salida

| Situación                          | Comportamiento |
|------------------------------------|----------------|
| `curso == null`                    | Lanza `NullPointerException` (en el constructor de `Convocatoria`). |
| `minimoInscripciones <= 0`         | Lanza `IllegalArgumentException` con mensaje explicativo. |
| `minimoCursosCompletados < 0`      | Lanza `IllegalArgumentException` con mensaje explicativo. |
| Parámetros válidos                 | Se crea la convocatoria mínima, abierta y no cancelada, con los mínimos configurados. |

---

## 2. `getMinimoInscripciones()`

### Firma
```java
public int getMinimoInscripciones()
```

### Propósito
Devuelve el número mínimo de inscripciones necesarias para poder cerrar la convocatoria.

### Salida

| Situación | Valor devuelto          |
|-----------|-------------------------|
| Siempre   | Valor del atributo `minimoInscripciones`. |

---

## 3. `getMinimoCursosCompletados()`

### Firma
```java
public int getMinimoCursosCompletados()
```

### Propósito
Devuelve el número mínimo de cursos completados en la temática exigidos para que un usuario pueda inscribirse.

### Salida

| Situación | Valor devuelto                |
|-----------|-------------------------------|
| Siempre   | Valor del atributo `minimoCursosCompletados`. |

---

## 4. `setMinimoInscripciones(int minimoInscripciones)`

### Firma
```java
public void setMinimoInscripciones(int minimoInscripciones)
```

### Propósito
Modifica el mínimo de inscripciones requerido para poder cerrar la convocatoria.

### Parámetros

| Nombre                | Tipo  | Descripción |
|-----------------------|-------|-------------|
| `minimoInscripciones` | `int` | Nuevo mínimo de inscripciones. Debe ser mayor que 0. |

### Salida

| Situación                    | Comportamiento |
|------------------------------|----------------|
| `minimoInscripciones <= 0`   | Lanza `IllegalArgumentException`. |
| `minimoInscripciones > 0`    | Actualiza el atributo `minimoInscripciones`. |

---

## 5. `setMinimoCursosCompletados(int minimoCursosCompletados)`

### Firma
```java
public void setMinimoCursosCompletados(int minimoCursosCompletados)
```

### Propósito
Modifica el número mínimo de cursos completados en la temática requerido para que un usuario pueda inscribirse.

### Parámetros

| Nombre                    | Tipo | Descripción |
|---------------------------|------|-------------|
| `minimoCursosCompletados` | `int`| Nuevo mínimo de cursos completados. No puede ser negativo. |

### Salida

| Situación                          | Comportamiento |
|------------------------------------|----------------|
| `minimoCursosCompletados < 0`      | Lanza `IllegalArgumentException`. |
| `minimoCursosCompletados >= 0`     | Actualiza el atributo `minimoCursosCompletados`. |

---

## 6. `inscribir(Usuario usuario)`

### Firma
```java
@Override
public boolean inscribir(Usuario usuario)
```

### Propósito
Controla el proceso de inscripción respetando el mínimo de cursos completados en la temática del curso.

### Parámetros

| Nombre   | Tipo     | Descripción |
|----------|----------|-------------|
| `usuario`| `Usuario`| Usuario que desea inscribirse en la convocatoria. |

### Lógica

1. Si `usuario` es `null`, la inscripción se rechaza.  
2. Se calcula cuántos cursos ha completado el usuario en la temática del curso:
   ```java
   int completados = usuario.getCursosCompletadosPorTematica(curso.getTematica());
   ```
3. Si `completados < minimoCursosCompletados`, la inscripción se rechaza.  
4. Si cumple el mínimo, se delega en la lógica ordinaria de `Convocatoria` mediante `super.inscribir(usuario)`.

### Salida

| Condición                                       | Resultado (`boolean`) | Efecto |
|-------------------------------------------------|-----------------------|--------|
| `usuario == null`                               | `false`               | No se realiza la inscripción. |
| `completados < minimoCursosCompletados`         | `false`               | No se realiza la inscripción. |
| `completados >= minimoCursosCompletados`        | Depende de `super.inscribir(usuario)` | Se aplica la lógica habitual de inscripción (crédito, duplicados, etc.). |

---

## 7. `cerrar()`

### Firma
```java
@Override
public boolean cerrar()
```

### Propósito
Intenta cerrar la convocatoria mínima, asegurando que se ha alcanzado el número mínimo de inscripciones.

### Lógica

1. Si la convocatoria no está abierta o ya está cancelada, no se puede cerrar.  
2. Si el número total de inscripciones es menor que `minimoInscripciones`, no se puede cerrar.  
3. Si se cumplen las condiciones anteriores:
   - Se llama a `super.admitirInscripciones()` para admitir las inscripciones según la lógica ordinaria.  
   - Se llama a `super.transferirDinero()` para gestionar la transferencia de crédito al propietario y la carga correspondiente a los usuarios inscritos.
   - Se marca la convocatoria como cerrada (`esAbierta = false`) y no cancelada (`esCancelada = false`).

### Salida

| Condición                                        | Resultado (`boolean`) | Efecto |
|--------------------------------------------------|-----------------------|--------|
| `!esAbierta` o `esCancelada`                     | `false`               | No se modifica el estado. |
| `inscripciones.size() < minimoInscripciones`     | `false`               | La convocatoria permanece abierta. |
| Condiciones de cierre cumplidas                  | `true`                | Se admiten inscripciones, se transfiere dinero y la convocatoria pasa a cerrada. |
