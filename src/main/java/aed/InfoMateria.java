package aed;

public class InfoMateria{
    String[] carreras;
    String[] nombresEnCarreras;

    /* Cada instancia de la clase InfoMateria debe cumplir que:
     *      1. La longitud de carreras es igual a la longitud de nombresEnCarreras.
     *      2. No puede haber elementos repetidos en carreras.
     *        
     */
    public InfoMateria(String[] carreras, String[] nombresEnCarreras){
        this.carreras = carreras;
        this.nombresEnCarreras = nombresEnCarreras;
    }
}