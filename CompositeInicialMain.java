import java.util.ArrayList;
import java.util.List;

// Clases concretas de la versión inicial
class MateriaSinPatron {
    String nombre;
    int creditos;

    public MateriaSinPatron(String nombre, int creditos) {
        this.nombre = nombre;
        this.creditos = creditos;
    }
}

class SemestreSinPatron {
    int numero;
    List<MateriaSinPatron> materias = new ArrayList<>();

    public SemestreSinPatron(int numero) {
        this.numero = numero;
    }
}

class ProgramaSinPatron {
    String nombre;
    List<SemestreSinPatron> semestres = new ArrayList<>();

    public ProgramaSinPatron(String nombre) {
        this.nombre = nombre;
    }
}

public class CompositeInicialMain {

    // DEFECTO VISIBLE: El método cliente requiere instanceof para determinar el tipo
    // y duplicar condicionales/recorridos según cada nivel
    public static int calcularCreditos(Object nodo) {
        if (nodo instanceof MateriaSinPatron) {
            MateriaSinPatron m = (MateriaSinPatron) nodo;
            return m.creditos;
        }
        if (nodo instanceof SemestreSinPatron) {
            SemestreSinPatron s = (SemestreSinPatron) nodo;
            int total = 0;
            for (MateriaSinPatron m : s.materias) {
                total += m.creditos;
            }
            return total;
        }
        if (nodo instanceof ProgramaSinPatron) {
            ProgramaSinPatron p = (ProgramaSinPatron) nodo;
            int total = 0;
            for (SemestreSinPatron s : p.semestres) {
                total += calcularCreditos(s);
            }
            return total;
        }
        throw new IllegalArgumentException("Tipo de nodo no soportado");
    } // el metodo evalia el objeto ProgramaSinPatron,
    // itera sobre sus dos semestres y suma las materias de cada unp

    public static void main(String[] args) {
        ProgramaSinPatron programa = new ProgramaSinPatron("Ingeniería de Sistemas");

        // Semestre 1: Programación I, Cálculo I, Álgebra Lineal
        SemestreSinPatron s1 = new SemestreSinPatron(1);
        s1.materias.add(new MateriaSinPatron("Programación I", 4));
        s1.materias.add(new MateriaSinPatron("Cálculo I", 4));
        s1.materias.add(new MateriaSinPatron("Álgebra Lineal", 3));

        // Semestre 2: Programación II, Cálculo II, Electiva Diseño Web
        SemestreSinPatron s2 = new SemestreSinPatron(2);
        s2.materias.add(new MateriaSinPatron("Programación II", 4));
        s2.materias.add(new MateriaSinPatron("Cálculo II", 4));
        s2.materias.add(new MateriaSinPatron("Electiva Diseño Web", 2));

        programa.semestres.add(s1);
        programa.semestres.add(s2);

        System.out.println("=== COMPOSITE (VERSIÓN INICIAL CON DEFECTO) ===");
        System.out.println("Total créditos del programa: " + calcularCreditos(programa));
    }
}