[VOLVER AL README](../README.md)

# Especificación de métodos — Clase `ConvocatoriaLimitada`

## Descripción general

La clase `ConvocatoriaLimitada` representa una convocatoria de curso con **plazas limitadas**, heredando el comportamiento base de la clase `Convocatoria`.  
En este tipo de convocatoria, al cerrarse, **solo un número máximo de inscripciones** (`maxAdmitidos`) pueden ser admitidas, seleccionadas según el número de **cursos completados en la temática** del curso.

El cierre sigue estas reglas:

1. Ordenar todas las inscripciones según sus cursos completados en la temática (descendente).  
2. Admitir únicamente las primeras `maxAdmitidos`.  
3. Las inscripciones no admitidas recuperan su crédito.  
4. Si **no hay ninguna inscripción admitida**, la convocatoria no puede cerrarse.

---

## 1. Constructor

### Firma
```java
public ConvocatoriaLimitada(Curso curso, int maxAdmitidos)
```

### Propósito
Crea una convocatoria limitada para el curso indicado.  
El propietario de la convocatoria es el **autor del curso**, establecido en el constructor de la superclase `Convocatoria`.

### Parámetros

| Nombre        | Tipo    | Descripción |
|---------------|---------|-------------|
| `curso`       | `Curso` | Curso para el que se abre la convocatoria. No puede ser `null`. |
| `maxAdmitidos` | `int`  | Máximo de inscripciones que pueden ser admitidas en el cierre. Debe ser mayor que 0. |

### Salida

| Situación                 | Comportamiento |
|--------------------------|----------------|
| `curso == null`          | Lanza `NullPointerException`. |
| `maxAdmitidos <= 0`      | Lanza `IllegalArgumentException`. |
| Parámetros válidos        | Se crea la convocatoria limitada, abierta y no cancelada, con propietario igual al autor del curso. |

---

## 2. `getMaxAdmitidos()`

### Firma
```java
public int getMaxAdmitidos()
```

### Propósito
Devuelve el límite máximo de inscripciones admitidas permitido en el cierre.

---

## 3. `setMaxAdmitidos(int maxAdmitidos)`

### Firma
```java
public void setMaxAdmitidos(int maxAdmitidos)
```

### Propósito
Modifica el valor máximo de inscripciones admitidas.

---

## 4. `admitirInscripciones()`

### Firma
```java
@Override
protected void admitirInscripciones()
```

### Propósito
Ejecuta la regla de admisión específica de una convocatoria limitada:

1. Marca todas las inscripciones como no admitidas.  
2. Ordena por número de cursos completados en la temática (descendente).  
3. Admite únicamente las primeras `maxAdmitidos`.  

---

## 5. `transferirDinero()`

### Firma
```java
@Override
protected void transferirDinero()
```

### Propósito
Gestiona la transferencia económica:

- Las inscripciones admitidas transfieren crédito al propietario.  
- Las inscripciones no admitidas recuperan su crédito.  


