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
        char letra;

        Nodo(){
            valor = null;
            siguientes = null;
            this.letra = Character.MIN_VALUE;
        }

        Nodo ( Nodo padre, char letra){
            this.valor = null;
            siguientes = null;
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


    public String[] obtenerClaves(){
        if(tamanio == 0) return new String[0];

        String[] palabras = new String[tamanio];
        ArrayList<String> palabrasArr = obtenerPalabras(raiz, ""); 

        //la función obtenerPalabras es O(1) cuando se la llama una vez, 
        //pero en la recursión se llama una vez por nodo: O(cantNodos)

        //convierto el arraylist a array - O(tamanio). tamanio < cantNodos
        for (int i = 0; i < palabrasArr.size(); i++){
            palabras[i] = palabrasArr.get(i);
        }
        return palabras;
        //la complejidad es O(cantNodos)
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

    private ArrayList<String> obtenerPalabras(Nodo nodo, String prefijo){
        //Estos checkeos son O(1)
        int cantPalabrasLocales = 0;
        if (nodo == null) return null;

        String palabraLocal = "";
        if (nodo.valor != null){ //este nodo es una palabra
            palabraLocal = concatenarLetra(prefijo, nodo.letra);
            cantPalabrasLocales = 1;
        }

        ArrayList<String> hijosCompletos = new ArrayList<String>();

        if (nodo.siguientes != null) { //este nodo tiene hijos
           for (Nodo hijo: nodo.siguientes){ //acá me fijo cada hijo: O(256) = O(1)
                if (hijo != null){ //si este hijo es no nulo, busco
                    String busco = concatenarLetra(prefijo, nodo.letra);

                    ArrayList<String> hijos = obtenerPalabras(hijo, busco); //obtengo los hijos. O(1) (justificación al fondo)

                    //acá copio el array de los nuevos hijos encontrados y el array de los hijos 
                    //ya encontrados en el mismo array.
                    //esto tiene complejidad máxima de o(this.tamanio)
                    hijosCompletos.addAll(hijos);
                }
            } 
        } 

        if(cantPalabrasLocales > 0) hijosCompletos.add(0, palabraLocal);

        return hijosCompletos;
        
        //la función, ejecutada una vez, es O(1)
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
