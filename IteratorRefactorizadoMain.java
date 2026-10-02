import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// Interfaz Iterator
interface Iterador<T> {
    boolean hasNext();
    T next();
}

// Estructura del árbol Composite para el recorrido refactorizado
interface ComponenteNodo {
    String getNombre();
    List<ComponenteNodo> getHijos();
}

class MateriaNodo implements ComponenteNodo {
    private String nombre;
    private int creditos;
    private boolean electiva;

    public MateriaNodo(String nombre, int creditos, boolean electiva) {
        this.nombre = nombre;
        this.creditos = creditos;
        this.electiva = electiva;
    }

    public int getCreditos() { return creditos; }
    public boolean esElectiva() { return electiva; }
    
    @Override
    public String getNombre() { return nombre; }

    @Override
    public List<ComponenteNodo> getHijos() {
        return new ArrayList<>(); // Hoja: lista vacía de hijos
    }
}

class SemestreNodo implements ComponenteNodo {
    private int numero;
    private List<ComponenteNodo> hijos = new ArrayList<>();

    public SemestreNodo(int numero) {
        this.numero = numero;
    }

    public void agregar(ComponenteNodo c) { hijos.add(c); }

    @Override
    public String getNombre() { return "Semestre " + numero; }

    @Override
    public List<ComponenteNodo> getHijos() { return hijos; }
}

class ProgramaNodo implements ComponenteNodo {
    private String nombre;
    private List<ComponenteNodo> hijos = new ArrayList<>();

    public ProgramaNodo(String nombre) {
        this.nombre = nombre;
    }

    public void agregar(ComponenteNodo c) { hijos.add(c); }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public List<ComponenteNodo> getHijos() { return hijos; }
}

// Iterador Concreto 1: Recorrido en Profundidad usando Pila (DFS)
class IteradorProfundidad implements Iterador<ComponenteNodo> {
    private Deque<ComponenteNodo> pila = new ArrayDeque<>();

    public IteradorProfundidad(ComponenteNodo raiz) {
        pila.push(raiz);
    }

    @Override
    public boolean hasNext() {
        return !pila.isEmpty();
    }

    @Override
    public ComponenteNodo next() {
        if (!hasNext()) return null;
        ComponenteNodo actual = pila.pop();

        List<ComponenteNodo> hijos = actual.getHijos();
        for (int i = hijos.size() - 1; i >= 0; i--) {
            pila.push(hijos.get(i));
        }
        return actual;
    }
}

//Utiliza una pila interna (Deque) para recorrer todo el árbol académico de arriba a abajo 
// de forma transparente, visitando la raíz, luego el Semestre 1 con sus materias, 
// y finalmente el Semestre 2 con sus materias.



// Iterador Concreto 2: Filtrado exclusivo de materias electivas
class IteradorElectivas implements Iterador<MateriaNodo> {
    private IteradorProfundidad iteradorBase;
    private MateriaNodo siguienteElectiva;

    public IteradorElectivas(ComponenteNodo raiz) {
        this.iteradorBase = new IteradorProfundidad(raiz);
        avanzar();
    }

    private void avanzar() {
        siguienteElectiva = null;
        while (iteradorBase.hasNext()) {
            ComponenteNodo nodo = iteradorBase.next();
            if (nodo instanceof MateriaNodo) {
                MateriaNodo m = (MateriaNodo) nodo;
                if (m.esElectiva()) {
                    siguienteElectiva = m;
                    break;
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return siguienteElectiva != null;
    }

    @Override
    public MateriaNodo next() {
        MateriaNodo actual = siguienteElectiva;
        avanzar();
        return actual;
    }
}

//Encapsula la lógica de búsqueda. El cliente solo llama a hasNext() y next(), permitiendo obtener 
// directamente la materia electiva sin enterarse de cómo 
// está construida la estructura interna del programa.



public class IteratorRefactorizadoMain {
    public static void main(String[] args) {
        ProgramaNodo programa = new ProgramaNodo("Ingeniería de Sistemas");

        SemestreNodo s1 = new SemestreNodo(1);
        s1.agregar(new MateriaNodo("Programación I", 4, false));
        s1.agregar(new MateriaNodo("Cálculo I", 4, false));
        s1.agregar(new MateriaNodo("Álgebra Lineal", 3, false));

        SemestreNodo s2 = new SemestreNodo(2);
        s2.agregar(new MateriaNodo("Programación II", 4, false));
        s2.agregar(new MateriaNodo("Cálculo II", 4, false));
        s2.agregar(new MateriaNodo("Electiva Diseño Web", 2, true));

        programa.agregar(s1);
        programa.agregar(s2);

        System.out.println("=== ITERATOR (VERSIÓN REFACTORIZADA CON PATRÓN) ===");
        
        System.out.println("\n1. Recorrido completo en Profundidad (DFS):");
        Iterador<ComponenteNodo> itProf = new IteradorProfundidad(programa);
        while (itProf.hasNext()) {
            ComponenteNodo nodo = itProf.next();
            System.out.println("  - Visitando: " + nodo.getNombre());
        }

        System.out.println("\n2. Recorrido filtrado (Solo Electivas):");
        Iterador<MateriaNodo> itElec = new IteradorElectivas(programa);
        while (itElec.hasNext()) {
            MateriaNodo m = itElec.next();
            System.out.println("  -> Electiva encontrada: " + m.getNombre());
        }
    }
}