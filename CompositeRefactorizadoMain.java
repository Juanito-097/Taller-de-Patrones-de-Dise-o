import java.util.ArrayList;
import java.util.List;

// Componente (Interfaz común)
interface ComponenteAcademico {
    int getCreditos();
    void mostrar();
}

// Hoja (Leaf)
class Materia implements ComponenteAcademico {
    private String nombre;
    private int creditos;
    private boolean electiva;

    public Materia(String nombre, int creditos, boolean electiva) {
        this.nombre = nombre;
        this.creditos = creditos;
        this.electiva = electiva;
    }

    public boolean esElectiva() {
        return electiva;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public int getCreditos() {
        return creditos;
    }

    @Override
    public void mostrar() {
        System.out.println("    - " + nombre + " (" + creditos + " créditos)");
    }
}

// Compuesto (Composite) - Semestre
class Semestre implements ComponenteAcademico {
    private int numero;
    private List<ComponenteAcademico> hijos = new ArrayList<>();

    public Semestre(int numero) {
        this.numero = numero;
    }

    public void agregar(ComponenteAcademico c) {
        hijos.add(c);
    }

    public List<ComponenteAcademico> getHijos() {
        return hijos;
    }

    @Override
    public int getCreditos() {
        int total = 0;
        for (ComponenteAcademico h : hijos) {
            total += h.getCreditos(); // Delegación recursiva por polimorfismo
        }
        return total;
    }

    @Override
    public void mostrar() {
        System.out.println("  Semestre " + numero + " (" + getCreditos() + " créditos)");
        for (ComponenteAcademico h : hijos) {
            h.mostrar();
        }
    }
}

// Compuesto (Composite) - Programa
class Programa implements ComponenteAcademico {
    private String nombre;
    private List<ComponenteAcademico> hijos = new ArrayList<>();

    public Programa(String nombre) {
        this.nombre = nombre;
    }

    public void agregar(ComponenteAcademico c) {
        hijos.add(c);
    }

    public List<ComponenteAcademico> getHijos() {
        return hijos;
    }

    @Override
    public int getCreditos() {
        int total = 0;
        for (ComponenteAcademico h : hijos) {
            total += h.getCreditos(); // Delegación recursiva hacia Semestres/Materias
        }
        return total;
    }

    @Override
    public void mostrar() {
        System.out.println("Programa: " + nombre);
        for (ComponenteAcademico h : hijos) {
            h.mostrar();
        }
    }
}
    //al llamar al programa.mostrar() y .getCreditos(), el objeto programa
    // delega automaticamente el calcuilo de cada Semestre y este a cada Materia
    // Todo se resuelve polimorficamente sin condiciones ni instanceof
public class CompositeRefactorizadoMain {
    public static void main(String[] args) {
        Programa programa = new Programa("Ingeniería de Sistemas");

        // Semestre 1: Programación I, Cálculo I, Álgebra Lineal
        Semestre s1 = new Semestre(1);
        s1.agregar(new Materia("Programación I", 4, false));
        s1.agregar(new Materia("Cálculo I", 4, false));
        s1.agregar(new Materia("Álgebra Lineal", 3, false));

        // Semestre 2: Programación II, Cálculo II, Electiva Diseño Web
        Semestre s2 = new Semestre(2);
        s2.agregar(new Materia("Programación II", 4, false));
        s2.agregar(new Materia("Cálculo II", 4, false));
        s2.agregar(new Materia("Electiva Diseño Web", 2, true));

        programa.agregar(s1);
        programa.agregar(s2);

        System.out.println("=== COMPOSITE (VERSIÓN REFACTORIZADA CON PATRÓN) ===");
        programa.mostrar();
        System.out.println("\nTotal créditos del programa: " + programa.getCreditos());
    }
}