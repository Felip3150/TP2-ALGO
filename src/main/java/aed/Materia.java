package aed;
import java.util.ArrayList;

public class Materia {
    
    /* Cada instancia de la clase Materia cumple que:
     *      1. El valor de inscriptos es mayor o igual a 0 y es igual a la longitud del array estudiantes.
     *      2. El tamaño del Array profesores es siempre 4 y sus elementos son mayores o iguales a 0.
     *      3. En estudiantes no hay elementos repetidos y cada elemento es una libreta universitaria valida.
     *      4. No puede haber 2 elementos de ParCarreraMateria que tengan referenciada una misma instancia de la clase Carrera
     */
    
    
    int inscriptos;
    int[] profesores;
    ArrayList<ParCarreraMateria> nombres;
    ArrayList<String> estudiantes;
     
    public class ParCarreraMateria {
        Carrera carrera;
        String materia;

        ParCarreraMateria(Carrera carreraInput, String materiaInput) {
            carrera = carreraInput;
            materia = materiaInput;
        }
    }

    public Materia(){
        inscriptos = 0;
        profesores = new int[] {0,0,0,0}; //[profes, JTP, Ayu1, Ayu2]
        nombres = new ArrayList<>();
        estudiantes = new ArrayList<>();
    }

    public void agregarParMateriaCarrera(Carrera nuevaCarrera, String nombreMateria){ //O(1)
        nombres.add(new ParCarreraMateria(nuevaCarrera, nombreMateria));
    }
}