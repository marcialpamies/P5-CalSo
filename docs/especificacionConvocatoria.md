# Especificación de métodos — Clase `Convocatoria`

## Descripción general

La clase `Convocatoria` representa una convocatoria ordinaria de un curso en la plataforma *formandera.com*.  
Una convocatoria permite gestionar el proceso de inscripción, cierre y cancelación de una edición concreta de un curso.

---

## **1. Constructor**

### **Firma**
```java
public Convocatoria(Curso curso)
```

### **Propósito**
Crea una nueva convocatoria asociada a un curso.  
La convocatoria se inicializa en estado **abierta** y **no cancelada**.

### **Parámetros**
| Nombre | Tipo | Descripción |
|---------|------|-------------|
| `curso` | `Curso` | Curso para el que se abre la convocatoria. No puede ser `null`. |

### **Salida**
| Situación | Comportamiento |
|------------|----------------|
| `curso == null` | Lanza `NullPointerException`. |
| `curso válido` | Se crea la convocatoria con el autor del curso como propietario. |

### **Efectos**
- Inicializa la lista de inscripciones vacía.  

---

## **2. `getCurso()`**

### **Firma**
```java
public Curso getCurso()
```

### **Propósito**
Devuelve el curso asociado a la convocatoria.

### **Salida**
| Situación | Valor devuelto |
|------------|----------------|
| Siempre | Objeto `Curso` asignado en el constructor. |

---

## **3. `getPropietario()`**

### **Firma**
```java
public Usuario getPropietario()
```

### **Propósito**
Devuelve el usuario propietario de la convocatoria (autor del curso).

### **Salida**
| Situación | Valor devuelto |
|------------|----------------|
| Siempre | Objeto `Usuario` propietario del curso. |

---

## **4. `getInscripciones()`**

### **Firma**
```java
public List<Inscripcion> getInscripciones()
```

### **Propósito**
Devuelve una **copia** de la lista de inscripciones actuales.  
Permite consultar, pero no modificar, las inscripciones registradas.

### **Salida**
| Situación | Valor devuelto |
|------------|----------------|
| Sin inscripciones | Lista vacía |
| Con inscripciones | Lista con todas las inscripciones registradas |

---

## **5. `getEsAbierta()`**

### **Firma**
```java
public boolean getEsAbierta()
```

### **Propósito**
Indica si la convocatoria sigue abierta para inscripciones.

### **Salida**
| Estado interno | Valor devuelto |
|----------------|----------------|
| Convocatoria abierta | `true` |
| Cerrada o cancelada | `false` |

---

## **6. `getEsCancelada()`**

### **Firma**
```java
public boolean getEsCancelada()
```

### **Propósito**
Indica si la convocatoria ha sido cancelada.

### **Salida**
| Estado interno | Valor devuelto |
|----------------|----------------|
| Convocatoria cancelada | `true` |
| Abierta o cerrada | `false` |

---

## **7. `getInscripcionPorNombre(String nombreUsuario)`**

### **Firma**
```java
public Inscripcion getInscripcionPorNombre(String nombreUsuario)
```

### **Propósito**
Devuelve la inscripción asociada al usuario cuyo nombre coincide con el parámetro.

### **Parámetros**
| Nombre | Tipo | Descripción |
|---------|------|-------------|
| `nombreUsuario` | `String` | Nombre del usuario inscrito (no distingue mayúsculas/minúsculas). |

### **Salida**
| Situación | Valor devuelto |
|------------|----------------|
| `nombreUsuario` nulo o vacío | `null` |
| No existe coincidencia | `null` |
| Existe coincidencia | Objeto `Inscripcion` del usuario |

---

## **8. `inscribir(Usuario usuario)`**

### **Firma**
```java
public boolean inscribir(Usuario usuario)
```

### **Propósito**
Permite inscribir a un usuario en la convocatoria si cumple las condiciones.

### **Parámetros**
| Nombre | Tipo | Descripción |
|---------|------|-------------|
| `usuario` | `Usuario` | Usuario que desea inscribirse. |

### **Salida**
| Condición | Resultado (`boolean`) | Efecto |
|------------|----------------------|--------|
| Usuario null | `false` | No hace nada. |
| Convocatoria cerrada o cancelada | `false` | No hace nada. |
| Usuario igual al proppietario | `false` | El propietario no puede inscribirse. |
| Usuario con crédito insuficiente | `false` | No se inscribe. |
| Usuario ya inscrito | `false` | No se duplica inscripción. |
| Caso válido | `true` | Descuenta crédito, crea inscripción, añade a la lista. |

---

## **9. `admitirInscripciones()`**

### **Firma**
```java
protected void admitirInscripciones()
```

### **Propósito**
En la convocatoria ordinaria, marca todas las inscripciones registradas como **admitidas**.  
Diseñada como método `protected` para que las subclases redefinan la regla de admisión.

### **Salida**
| Situación | Efecto |
|------------|--------|
| Siempre | Todas las inscripciones se marcan como admitidas. |

---

## **10. `getAdmitidas()`**

### **Firma**
```java
public List<Inscripcion> getAdmitidas()
```

### **Propósito**
Devuelve una lista con las inscripciones admitidas en la convocatoria.

### **Salida**
| Situación | Valor devuelto |
|------------|----------------|
| Sin inscripciones admitidas | Lista vacía |
| Con inscripciones admitidas | Lista inmodificable con las admitidas |

---

## **11. `transferirDinero()`**

### **Firma**
```java
protected void transferirDinero()
```

### **Propósito**
Suma al propietario el crédito correspondiente a las inscripciones admitidas:  
**número de admitidas × precio del curso**.

### **Salida**
| Situación | Efecto |
|------------|--------|
| Si hay admitidas | Incrementa el crédito del propietario. |
| Si no hay admitidas | No cambia el crédito. |

---

## **12. `cerrar()`**

### **Firma**
```java
public boolean cerrar()
```

### **Propósito**
Intenta cerrar la convocatoria.  
Admite inscripciones, transfiere el dinero al propietario y cambia el estado si el cierre es válido.

### **Salida**
| Condición | Resultado (`boolean`) | Efecto |
|------------|----------------------|--------|
| Cerrada o cancelada | `false` | No hace nada. |
| Sin inscripciones admitidas | `false` | Permanece abierta. |
| Con inscripciones admitidas | `true` | - Admite inscripciones. <br> - Marca convocatoria como cerrada. <br> - Transfiere crédito con `transferirDinero()`. |

---

## **13. `cancelar()`**

### **Firma**
```java
public boolean cancelar()
```

### **Propósito**
Cancela la convocatoria.  
Devuelve el crédito a los usuarios inscritos y marca la convocatoria como cancelada.

### **Salida**
| Condición | Resultado (`boolean`) | Efecto |
|------------|----------------------|--------|
| Cerrada o ya cancelada | `false` | No hace nada. |
| Abierta | `true` | - Marca `esAbierta = false` y `esCancelada = true`. <br> - Devuelve el precio del curso a cada usuario inscrito. |
