package aed;

public class Carrera {

    /* Cada instancia de la clase Carrera cumple que:
     *      1. Para toda clave del Trie materias, su valor contiene una Materia con un ParCarreraMateria
     *         que referencia a esta Carrera (objeto) y la clave (el nombre de la materia) utilizado para llegar al mismo.
     *     
     *      2. Una Carrera no tiene la misma Materia bajo dos nombres distintos (no hay valores repetidos).
     */

    Trie<String, Materia> materias;

    public Carrera() {
        materias = new Trie<String, Materia>();
    }
}