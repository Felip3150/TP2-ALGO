package aed;

public interface DiccionarioDigital<K, V> {
    public DiccionarioDigital<K, V> diccionarioVacio();
    public boolean esta(K clave);
    public void definir(K clave, V valor);
    public void definirRapido(K clave, V valor);
    public V obtener(K clave);
    public void borrar(K clave);
    public int tamanio();
}
