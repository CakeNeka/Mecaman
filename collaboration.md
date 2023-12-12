# Trabajo en equipo

## 🎀 Presentación 🎀

Vamos a presentar utilizando el [readme.md](readme.md), esto es para organizar
que parte va a decir cada uno.

#### 1. Introducción
#### 2. Estructura utilizada
- Explicación de la estructura (_Ángel Robles_)
- Casos de uso (_Leo_)
- Características de un problema que se resuelve con esta estructura. (_Leo_)
- Tabla de métodos de `ConcurrentLinkedQueue` (_Ángel Robles_)
  - Diferencia entre `element()` y `peek()`
  - Diferencia entre `remove()` y `poll()`
  - Diferencia entre `add()` y `offer()`
  - (Diferencia: los primeros pueden lanzar una excepción)
          
#### 3. Explicación de **MECAMAN**

Explicar lo que hay al principio del readme.

1. Estructura general con el Diagrama de clases (_Mar_)
2. Clase `RandomWordGenerator` (_Leo_)
3. Clase `Main`, `WordConsumer` y `WordProducer` (_Mar_)
   1. Instanciar hilos y cola
   2. Control de prioridad
   3. Control de tiempo de ejecución
5. Clase `WordManager`
   1. Método del Productor
   2. Método del consumidor

#### 4. Conclusión

#### 5. Preguntas o dudas

## ⛏ Tareas ⚒

### Repasar readme

**Descripción de la cola:**

Deja claro que en una cola la inserción (`push`) se
realiza por un extremo y la extracción (`pull`) por el otro. Y por eso el primero
en entrar es el último en salir (FIFO, First in, First out).

---

### 1. Ficha sobre la estructura utilizada (`ConcurrentLinkedQueue`)

Hay que hacer una ficha con información de la estructura que hemos elegido.

- [x] Introducción (Ángel Robles)
    - ¿Qué es una cola?
    - ¿Para que sirve una cola?
- [x] **Casos de uso** (Leo)
- [x] Tabla con los **métodos** más relevantes (Ángel Contreras) (Ángel Robles)
    - [Documentación](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html)

### 2. Proyecto Mecaman

- [x] Clase `mecaman.wordgeneration.RandomWordGenerator` (Leo)
    - Escoge **palabras aleatorias** a partir de un fichero **csv** (`words.csv`)
    - `words.csv` contiene una lista de palabras comunes
    - Es más eficiente si carga todas las palabras en memoria al iniciarse el programa
- [x] Clase `WordProducer`
- [x] Clase `WordConsumer`
- [x] Clase `StateLogger` (Ángel Robles)
  - La clase está creada, hay que implementar la funcionalidad.
  - Detalles en el propio programa


---

#### Log

> *Viernes, 12 de noviembre de 2023*:
> - Definir estructura del proyecto
> - Asignar tareas
> - Ficha de la estructura de datos

