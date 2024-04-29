import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class boltz {

    int w1;
    int w2;
    List<elemento> elementos;
    boolean posible;
    List<Atomo> caminoMinimo;
    HashMap<Integer, List<elemento>> conexiones = new HashMap<>();

    public boltz(int w1, int w2, List<elemento> elementos) {
        this.elementos = elementos;
        this.w1 = w1;
        this.w2 = w2;
        boolean pos = caminoPosible(w1, w2, elementos);
        this.posible = pos;
        if (pos) {
            this.caminoMinimo = calcularCaminoMinimo(w1, w2, elementos);
        } else {
            this.caminoMinimo = null;
        }

    }

    public static void main(String[] args) {
        // Crear una lista de elementos
        List<elemento> elementos = new ArrayList<>();
        // Agregar elementos a la lista (suponiendo que elemento y Atomo están definidos
        // correctamente)
        elementos.add(new elemento(1, 2));
        elementos.add(new elemento(1, 4));
        // Crear una instancia de boltz
        boltz miBoltz = new boltz(1, 2, elementos);
        // Verificar si es posible el camino
        boolean esPosible = miBoltz.posible;

        if (esPosible) {
            System.out.println("Es posible encontrar un camino.");
            // Obtener el camino mínimo (aún no implementado)
            List<Atomo> caminoMinimo = miBoltz.caminoMinimo;
            if (caminoMinimo != null) {
                System.out.println("El camino mínimo es: " + caminoMinimo);
            } else {
                System.out.println("No se ha calculado el camino mínimo.");
            }
        } else {
            System.out.println("No es posible encontrar un camino.");
        }
    }

    public boolean caminoPosible(int w1, int w2, List<elemento> elementos) {

        /*
         * En esta parte creamos un hash map, donde las llaves van a ser una cargaMasa
         * de un atomo
         * y el valor asociado será una lista con los elementos que contiene a este
         * atomo. Este mapa nos permite identificar que conexiones posibles, sin tomar
         * en
         * cuenta los atomos libres intermedios, si sería posible armar una cadena de
         * elementos
         */

        for (elemento e : elementos) {
            Atomo a1 = e.getAtomo1();
            Atomo a2 = e.getAtomo2();
            if (!conexiones.containsKey(a1.getCargaMasa())) {

                List<elemento> nuevaLista = new ArrayList<>();
                nuevaLista.add(e);
                conexiones.put(a1.getCargaMasa(), nuevaLista);
            } else {

                List<elemento> presencias1 = conexiones.get(a1.getCargaMasa());
                presencias1.add(e);
                conexiones.put(a1.getCargaMasa(), presencias1);
            }

            if (!conexiones.containsKey(a2.getCargaMasa())) {

                List<elemento> nuevaLista = new ArrayList<>();
                nuevaLista.add(e);
                conexiones.put(a2.getCargaMasa(), nuevaLista);
            } else {

                List<elemento> presencias2 = conexiones.get(a2.getCargaMasa());
                presencias2.add(e);
                conexiones.put(a2.getCargaMasa(), presencias2);
            }

        }

        /*
         * Cuando ya está armada el mapa, revisaremos la psibilidad de conexión de los
         * atomos tomandoe en cuenta:
         * 1. deben haber 2 atomos tales que solo tengan un elemento asociado, ya que
         * uno es el frente y el otro el dinal de la cadena
         * 2. si hay más de 3 atomos que solo tienen un elemento asociado puede
         * significar:
         * a. todos los atomos son disyuntos, es decir no existen atomos en comun entre
         * los elementos.
         * b. exiten conjuntos de cadenas de elementos correctas, pero disyuntas con
         * otros conjuntos de elementos.
         */

        int contadorUnicos = 0;

        for (Map.Entry<Integer, List<elemento>> entry : conexiones.entrySet()) {
            int key = entry.getKey();
            List<elemento> value = entry.getValue();

            if (value.size() == 1) {
                contadorUnicos += 1;

            }

        }

        if (contadorUnicos > 2) {
            return false;
        }

        return true;
    }

    public List<Atomo> calcularCaminoMinimo(int w1, int w2, List<elemento> elementos) {
        return null;
    }

}
