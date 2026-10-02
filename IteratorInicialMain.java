import java.util.ArrayList;
import java.util.List;

// Clases de la versión inicial de Iterator (sin patrón de recorrido)
class MateriaSinIterador {
    private String nombre;
    private int creditos;
    private boolean electiva;

    public MateriaSinIterador(String nombre, int creditos, boolean electiva) {
        this.nombre = nombre;
        this.creditos = creditos;
        this.electiva = electiva;
    }

    public String getNombre() { return nombre; }
    public boolean esElectiva() { return electiva; }
    public int creditos() { return creditos; }
}

class SemestreSinIterador {
    private int numero;
    private List<MateriaSinIterador> materias = new ArrayList<>();

    public SemestreSinIterador(int numero) {
        this.numero = numero;
    }

    public int getNumero() { return numero; }

    public void agregarMateria(MateriaSinIterador m) {
        materias.add(m);
    }

    // DEFECTO: Expone directamente la colección interna
    public List<MateriaSinIterador> getMaterias() {
        return materias;
    }
}

class ProgramaSinIterador {
    private String nombre;
    private List<SemestreSinIterador> semestres = new ArrayList<>();

    public ProgramaSinIterador(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    public void agregarSemestre(SemestreSinIterador s) {
        semestres.add(s);
    }

    // DEFECTO: Expone directamente la colección interna
    public List<SemestreSinIterador> getSemestres() {
        return semestres;
    }
}

// El cliente accede directamente a las listas con programa.getSemestres() y .getMaterias()
//Realiza un bucle for anidado para inspeccionar cada materia 1 por 1 y filtra unicamente la que tiene
//electiva = true

public class IteratorInicialMain {
    public static void main(String[] args) {
        ProgramaSinIterador programa = new ProgramaSinIterador("Ingeniería de Sistemas");

        SemestreSinIterador s1 = new SemestreSinIterador(1);
        s1.agregarMateria(new MateriaSinIterador("Programación I", 4, false));
        s1.agregarMateria(new MateriaSinIterador("Cálculo I", 4, false));
        s1.agregarMateria(new MateriaSinIterador("Álgebra Lineal", 3, false));

        SemestreSinIterador s2 = new SemestreSinIterador(2);
        s2.agregarMateria(new MateriaSinIterador("Programación II", 4, false));
        s2.agregarMateria(new MateriaSinIterador("Cálculo II", 4, false));
        s2.agregarMateria(new MateriaSinIterador("Electiva Diseño Web", 2, true));

        programa.agregarSemestre(s1);
        programa.agregarSemestre(s2);

        System.out.println("=== ITERATOR (VERSIÓN INICIAL CON DEFECTO) ===");
        System.out.println("Programa: " + programa.getNombre());
        System.out.println("Buscando materias electivas exponiendo colecciones internas:\n");

        // DEFECTO VISIBLE: El cliente realiza bucles anidados acoplados a la estructura interna.
        // Si la representación cambia (ej. de List a Tree o Grafo), esta lógica se rompe.
        for (SemestreSinIterador semestre : programa.getSemestres()) {
            System.out.println("Revisando Semestre " + semestre.getNumero() + "...");
            for (MateriaSinIterador materia : semestre.getMaterias()) {
                if (materia.esElectiva()) {
                    System.out.println("  -> Electiva encontrada: " + materia.getNombre());
                }
            }
        }
    }
}