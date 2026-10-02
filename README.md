# Taller de Patrones de Diseño: Composite e Iterator

**Asignatura:** Programación III

**Programa:** Ingeniería de Sistemas - Universidad de Córdoba

**Docente:** Mag. Alberto Paternina León

## Integrantes del Grupo

* Valentina Miranda Prasca
* Diego Fernando Mendoza Hoyos
* Juan Manuel Galvis López

---

## Descripción del Proyecto

Este repositorio contiene la solución e implementación en Java de dos patrones de diseño de software emparentados: **Composite** (Estructural) e **Iterator** (Comportamental).

El modelo de dominio propio implementado representa la **Estructura de un Pensum Académico Universitario**, donde un *Programa* se compone de *Semestres*, y estos a su vez de *Materias*.

---

## Estructura del Repositorio

Cada patrón de diseño cuenta con dos versiones independientes para evidenciar el defecto que soluciona:

* **`1_composite_inicial`**: Versión sin patrón. Muestra la fragilidad de calcular créditos haciendo uso de condicionales e inspección de tipos con `instanceof`.
* **`2_composite_refactorizado`**: Versión con patrón **Composite**. Aplica polimorfismo y delegación recursiva mediante la interfaz `ComponenteAcademico`.
* **`3_iterator_inicial`**: Versión sin patrón. Expone la representación interna de las colecciones al cliente mediante bucles `for` anidados para filtrar electivas.
* **`4_iterator_refactorizado`**: Versión con patrón **Iterator**. Encapsula el recorrido del árbol jerárquico (`IteradorProfundidad`) y el filtrado de dominio (`IteradorElectivas`) utilizando la interfaz `Iterador`.

---

## Instrucciones de Ejecución

### Requisitos Previos

* Java JDK 11 o superior instalado.
* IDE de preferencia (VS Code, IntelliJ IDEA, Eclipse) o consola de comandos.

### Pasos para Ejecutar desde Consola

1. **Clonar el repositorio:**
```bash
git clone https://github.com/Juanito-097/Taller-de-Patrones-de-Dise-o.git
cd Taller-de-Patrones-de-Dise-o

```


2. **Compilar y ejecutar cualquier ejemplo (por ejemplo, Iterator Refactorizado):**
```bash
javac 4_iterator_refactorizado/*.java
java 4_iterator_refactorizado.IteratorRefactorizadoMain

```