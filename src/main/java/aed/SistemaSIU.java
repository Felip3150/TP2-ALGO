package aed;
public class SistemaSIU {
    Trie<String, Carrera> carrerasSIU; //Trie<nombre-carrera, Trie<nombre-materia, objMateria>>
    Trie<String, Integer> estudiantes; //Trie<libreta, cant. materias que cursa>

    enum CargoDocente{
        PROF,
        JTP,
        AY1,
        AY2
    }

    public SistemaSIU(InfoMateria[] materiasEnCarreras, String[] libretasUniversitarias){
        this.carrerasSIU = new Trie<String, Carrera>();
        this.estudiantes = new Trie<String, Integer>();

        for(int i=0; i<materiasEnCarreras.length; i++) { //Se ejecuta cantidad de materias veces
            Materia materiaAAgregar = new Materia();
            for(int j=0; j<materiasEnCarreras[i].carreras.length; j++) {  //este ciclo se ejecuta
                //la cant. carreras en la que está esa materia = N_m (nombres distintos que tiene una materia)
                String nombreDeLaCarrera = materiasEnCarreras[i].carreras[j];
                if(!carrerasSIU.esta(nombreDeLaCarrera)) {
                    carrerasSIU.definir(nombreDeLaCarrera, new Carrera()); //O(|nombreDeLaCarrera|) = O(c)
                }
                //Agregamos la materia a la carrera
                Carrera carrera = carrerasSIU.obtener(nombreDeLaCarrera); //O(|nombreDeLaCarrera|) = O(c)
                String nombreMateriaEnCarrera = materiasEnCarreras[i].nombresEnCarreras[j];
                carrera.materias.definir(nombreMateriaEnCarrera, materiaAAgregar); //O(|nombreMateriaEnCarrera|) = O(n)
                materiaAAgregar.agregarParMateriaCarrera(carrera, nombreMateriaEnCarrera); //O(1)
            } 
            
            /* Complejidad del for interno
                ∑   |c| +|n|
                c, n en ParCarreraNombre
            */ 
            //∑ 
        }
        /* Complejidad del for completo
        ∑  (∑   |c| +|n|)
            c, n en ParCarreraNombre
        m en InfoMaterias
         */

        for(int i=0; i<libretasUniversitarias.length; i++) { //O(cant. estudiantes) = O(E)
            estudiantes.definir(libretasUniversitarias[i], 0);
        }
        //TODO justificar que esta complejidad es igual a al pedida
        /* complejidad final (E = cantidad libretas universitarias)
         ∑  (∑   |c| +|n|) + E
            c, n en ParCarreraNombre
        m en InfoMaterias
         */
    }

    public void inscribir(String estudiante, String carrera, String materia){
        Carrera carrera_ = carrerasSIU.obtener(carrera); //obtengo un trie carrera. O(|C|)
        Materia materia_ = carrera_.materias.obtener(materia); //obtengo objeto materia y voy a editar ahi. O(|M|)
        materia_.inscriptos++; //sumo un incripto a la materia. O(1)
        materia_.estudiantes.add(estudiante); // agrego el estudiante que se inscribio; O(1)
        Integer cantIncripciones = estudiantes.obtener(estudiante);//obtengo a cuantas materias esta incripto el estudiante antes de agregarse a la nueva. O(1) (pues las Libretas universitarias estan acotadas)
        estudiantes.definir(estudiante,cantIncripciones +1); // aumento en 1 la cantidad de materias a las que esta inscripto. O(1) (pues las Libretas universitarias estan acotadas)

        //COMPLEJIDAD TOTAL = O(|C| + |M|)

    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){ //O(|carrera| + |materia|)
        Carrera carreraAgregarDocente = carrerasSIU.obtener(carrera); //O(|carrera|)
        Materia materiaAgregarDocente = carreraAgregarDocente.materias.obtener(materia); //O(|materia|)
        int[] profes = materiaAgregarDocente.profesores;
        profes[cargo.ordinal()]++; //O(1)
    }

    public int[] plantelDocente(String materia, String carrera){ 
        Carrera carreraABuscar = carrerasSIU.obtener(carrera); //O(|carrera|)

        return carreraABuscar.materias.obtener(materia).profesores; //O(1)
    }

    public void cerrarMateria(String materia, String carrera){ //O(cant. inscriptos + cant. materias * |materia|)
        Carrera carreraCerrarMateria = carrerasSIU.obtener(carrera); //O(|carrera|)
        Materia materiaCerrar = carreraCerrarMateria.materias.obtener(materia); //O(|materia|)

        for (int i=0; i<materiaCerrar.estudiantes.size(); i++) { //O(cant. inscriptos)
            String estudianteInscripto = materiaCerrar.estudiantes.get(i); //O(1)
            int cantInscripcionesEstudiante = estudiantes.obtener(estudianteInscripto); //O(|estudiante|) = O(1) ACOTADO
            cantInscripcionesEstudiante--;
            estudiantes.definir(estudianteInscripto, cantInscripcionesEstudiante); //O(|estudiante|) = O(1) ACOTADO
        }

        for (int i=0; i<materiaCerrar.nombres.size(); i++) { //O(cant. materias) * O(|materia|) = O(cant. materias * |materia|)
            Carrera otraCarreraCerrarMateria = materiaCerrar.nombres.get(i).carrera; //O(1)
            otraCarreraCerrarMateria.materias.borrar(materiaCerrar.nombres.get(i).materia); //O(|materia|)
        }
    }

    public int inscriptos(String materia, String carrera){
        //encontrar la carrera es O(|carrera|). Obtener la materia es O(|materia|). 
        //Obtener el tamaño de cantidad estudiantes es O(1).
        //conclusión: O(|c|+|m|)
        return this.carrerasSIU.obtener(carrera).materias.obtener(materia).estudiantes.size();
    }

    public boolean excedeCupo(String materia, String carrera){
        Materia materiaDada  =  carrerasSIU.obtener(carrera).materias.obtener(materia); //O(|c|+|m|)
        float cantidadDeInscriptos = materiaDada.inscriptos;
        if ( cantidadDeInscriptos / materiaDada.profesores[0] > 250){
            return true;
        }
        if (cantidadDeInscriptos / materiaDada.profesores[1] > 100) {
            return true;
        }
        if (cantidadDeInscriptos / materiaDada.profesores[2] > 20) {
            return true;
        }
        if (cantidadDeInscriptos / materiaDada.profesores[3] > 30) {
            return true;
        }

         
       return false;  //O(1)
    }

    public String[] carreras(){
        return this.carrerasSIU.obtenerClaves(); //O(cantidad de nodos). 
        //como cada nodo representa una letra del nombre de una o más materias, 
        //esta complejidad es <= a O(sum_(carrera en Carreras)(|carrera|))
    }

    public String[] materias(String carrera){ //O(|carrera| + cant. materias)
        Carrera carreraVerMaterias = carrerasSIU.obtener(carrera); //O(|carrera|)
        return carreraVerMaterias.materias.obtenerClaves(); //O(cant. materias)
    }

    public int materiasInscriptas(String estudiante){
        return estudiantes.obtener(estudiante); //O(|LibretaUniversitaria|), que como tiene tamaño acotado es O(1)
    }
}
