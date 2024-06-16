package aed;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Random;


public class TrieTests {
    
    
    @Test
    public void definirAlgo(){
        Trie<String,Integer> newTrie = new Trie<String,Integer>();
        
        
        newTrie.definir("Arbol", 5);
        assertEquals(5, newTrie.obtener("Arbol"));
        newTrie.definir("Bósqué", 280);
        assertEquals(280, newTrie.obtener("Bósqué"));
        newTrie.definir("Arb8o_-leda!", 125);
        assertEquals(125, newTrie.obtener("Arb8o_-leda!"));
        assertEquals(true, newTrie.esta("Arbol"));
        assertEquals(false, newTrie.esta("Arbol "));
        assertEquals(false, newTrie.esta("arbol"));
        assertEquals(false, newTrie.esta("A"));

        System.out.println((char) 65);

        newTrie.definir("", 55);
        assertEquals(true, newTrie.esta(""));
        assertEquals(55, newTrie.obtener(""));
    }

    @Test
    public void borrar(){
        Trie<String,Integer> newTrie = new Trie<String,Integer>();

        String[] palabras = {"Arbol", "casa", "Arboleda", "Anteojos!!!!", "1264398-//()###", "Análisis $$", "", "Arbolito"};
        
        for(int i = 0; i < palabras.length; i++){
            assertEquals(false, newTrie.esta(palabras[i]));
            newTrie.definir(palabras[i], i);
            assertEquals(true, newTrie.esta(palabras[i]));
        }
        
        String[] bla = newTrie.obtenerClaves();
        for (int i = 0; i < bla.length; i++){
            System.out.println(bla[i]);
        }
                
        for(int i = 0; i < palabras.length; i++){
            assertEquals(i, newTrie.obtener(palabras[i]));
            assertEquals(true, newTrie.esta(palabras[i]));
            newTrie.borrar(palabras[i]);
            assertEquals(false, newTrie.esta(palabras[i]));
        }

    }

    @Test
    public void stress(){
        int cantPalabras = 1000;
        Random random = new Random(579235649);
        String[] palabras = new String[cantPalabras];

        for (int i = 0; i < cantPalabras; i++){
            String palabra = new String();
            int largo = random.nextInt(50);
            //TODO si tenemos ganas
        }
    }

    


}
