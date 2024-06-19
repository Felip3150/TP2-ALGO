package aed;
import java.util.ArrayList;

public class Materia {
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

    public void agregarParMateriaCarrera(Carrera nuevaCarrera, String nombreMateria){
        nombres.add(new ParCarreraMateria(nuevaCarrera, nombreMateria));
    }
}