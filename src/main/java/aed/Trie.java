package aed;
import java.util.ArrayList;

 
public class Trie<K,V> implements Diccionario<K,V>{
    Nodo raiz;
    int tamanio;

    /*
     * Invariante de Representación del Trie:
     * 
     * No hay nodos inútiles: todos los nodos tienen un valor o tienen un descendiente con valor.
     * No hay ciclos: si un nodo es descendiente de otro, la relación es unidireccional.
     * No se puede llegar por dos claves al mismo nodo, cada nodo tiene un único padre.
     * El tamanio se corresponde a la cantidad de nodos con valor en el Trie.
     * 
     */

    private class Nodo {
        ArrayList<Nodo> siguientes;
        V valor;
        Nodo padre;
        char letra;

        Nodo(){
            valor = null;
            siguientes = null;
            this.padre = null;
            this.letra = Character.MIN_VALUE;
        }

        Nodo ( Nodo padre, char letra){
            this.valor = null;
            siguientes = null;
            this.padre = padre;
            this.letra = letra;
        }

        public void crearSiguientes(){
            siguientes = new ArrayList<>(256);
            for (int i = 0; i < 256;i++){
                siguientes.add(null);
            }
        }
        
    }

    public Trie(){
        raiz = null;
        tamanio = 0;
    }


    public Diccionario<K, V> diccionarioVacio(){
        return new Trie<K,V>();   
    }


    public String[] obtenerClaves(){ //TODO
        if(tamanio == 0) return new String[0];

        String[] palabras = null;
        palabras = agregarPalabras(raiz, "");

        return palabras;
    }

    private String concatenarLetra(String a, char b){
        String res;
        if(b == Character.MIN_VALUE){
            res = a;
        } else {
            res = a+b;
        }
        return res;
    }

    private String[] agregarPalabras(Nodo nodo, String prefijo){
        int cantPalabrasLocales = 0;
        if (nodo == null) return null;

        String palabraLocal = "";
        if (nodo.valor != null){ //este nodo es una palabra
            palabraLocal = concatenarLetra(prefijo, nodo.letra);
            cantPalabrasLocales = 1;
        }

        String[] hijosCompletos = new String[0];

        if (nodo.siguientes != null) { //este nodo tiene hijos
           for (Nodo hijo: nodo.siguientes){
                if (hijo != null){ //si este hijo es no nulo, busco
                    String busco = concatenarLetra(prefijo, nodo.letra);

                    String[] hijos = agregarPalabras(hijo, busco);

                    String[] nuevoHijosCompletos = new String[hijosCompletos.length + hijos.length];
                    int i = 0;
                    while (i < hijosCompletos.length){
                        nuevoHijosCompletos[i] = hijosCompletos[i];
                        i++;
                    }
                    for (int j = 0; j < hijos.length; j++){
                        nuevoHijosCompletos[i+j] = hijos[j];
                    }
                    hijosCompletos = nuevoHijosCompletos;
                }
            } 
        } 
        String[] resultado = new String[cantPalabrasLocales + hijosCompletos.length];
        if (cantPalabrasLocales > 0){
            resultado[0] = palabraLocal;
        }

        for (int i = 0; i < hijosCompletos.length; i++){
            resultado[cantPalabrasLocales+i] = hijosCompletos[i];
        }

        return resultado;
    }

    public boolean esta(K clave){
        String key = (String) clave;
        
        if (raiz == null) {
            return false;
        }
        Nodo actual = raiz;
        
        for (char c : key.toCharArray()) {
            int i = (int) c;
            if (actual.siguientes == null) return false;
            
            if (actual.siguientes.get(i) == null){
                return false;
            
            } else {
                actual = actual.siguientes.get(i);
            }

        }
        
        return actual.valor != null;
    }

    public void definir(K clave, V valor){
        if (!esta(clave)) tamanio+=1;
        
        String key = (String) clave;

        if (raiz == null) {
            raiz = new Nodo();
        }

        Nodo actual = raiz;

        for (int i = 0; i < key.length(); i++){
            char l = key.charAt(i);
            int ascii = (int) l;
            
            if (actual.siguientes == null){
                actual.crearSiguientes();
            }
            
            if (actual.siguientes.get(ascii) == null) {
                Nodo nuevo = new Nodo(actual, l);
                actual.siguientes.set(ascii, nuevo);
            }
            
            actual = actual.siguientes.get(ascii);
        }

        actual.valor = valor;
    }

    public V obtener(K clave){
        String key = (String) clave;
 
        Nodo actual = raiz;
        
        for (char c : key.toCharArray()) {
            int i = (int) c;
            actual = actual.siguientes.get(i);
        }
        return actual.valor;
    }



    public void borrar(K clave){
        tamanio--;
        if(clave == "") {
            raiz.valor = null;
            if(!tieneHijos(raiz)){
                raiz = null;
            }
            return;
        }
        String key = new String();
        key = (String) clave;
        Nodo nodoActual = raiz;
        Nodo ultimoNodoUtil = nodoActual;
        int j = (int) key.charAt(0);

        for (int i = 0; i<key.length();i++){
            int ascii = (int) key.charAt(i);
            nodoActual = nodoActual.siguientes.get(ascii);

            if (tieneMasDeUnHijo(nodoActual) || (nodoActual.valor != null && i != key.length()-1)){
                ultimoNodoUtil = nodoActual;
                if (i != key.length()-1) j = (int) key.charAt(i+1);
                else j=-1;
            }
        }
        
        if (tieneHijos (nodoActual)){ 
            nodoActual.valor = null;
        } else if (j != -1){
            ultimoNodoUtil.siguientes.set(j, null);
        }
    }
    private boolean tieneHijos(Nodo nodo) {
        ArrayList<Nodo> array = nodo.siguientes;

        if (array == null) return false;

        for (int i= 0;i<256; i++){
            if (array.get(i) != null){
                return true;
            }
        }
        return false;
    }
    
    private boolean tieneMasDeUnHijo(Nodo nodo) {
        ArrayList<Nodo> array = nodo.siguientes;
        int contador = 0;

        if (array == null) return false;

        for (int i= 0;i<256; i++){
            if (array.get(i) != null){
                contador ++;
                if (contador>1){
                    return true;
                }
            }
        }
        return false;
    }
    public int tamanio(){
        return tamanio;
    }

}
