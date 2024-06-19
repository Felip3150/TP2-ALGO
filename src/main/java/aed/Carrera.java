package aed;

public class Carrera {
    Trie<String, Materia> materias;

    public Carrera() {
        materias = new Trie<String, Materia>();
    }
}