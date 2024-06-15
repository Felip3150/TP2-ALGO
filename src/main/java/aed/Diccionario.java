package aed;

public interface Diccionario<K, V> {
    public Diccionario<K, V> diccionarioVacio();

    public boolean esta(K clave);

    public void definir(K clave, V valor);
    
    public V obtener(K clave);
    
    public void borrar(K clave);
    
    public int tamanio();

}