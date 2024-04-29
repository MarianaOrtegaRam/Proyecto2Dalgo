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
    List<Atomo> atomosPosibles = new ArrayList<>();
    Map<Integer, Map<Integer, Integer>> grafo = new HashMap<>();

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
         * 
         * 
         * Adicionalmente saco los atomos posibles para hacer usarse como atomos libres
         * para los enlances boltz
         */

        for (elemento e : elementos) {
            Atomo a1 = e.getAtomo1();
            int masa1 = a1.getMasa();
            boolean carga1 = a1.getCarga();
            int cargaMasa1 = a1.getCargaMasa();
            Atomo a12 = new Atomo(masa1, carga1, cargaMasa1);
            Atomo a2 = e.getAtomo2();

            int masa2 = a2.getMasa();
            boolean carga2 = a2.getCarga();
            int cargaMasa2 = a2.getCargaMasa();
            Atomo a22 = new Atomo(masa2, carga2, cargaMasa2);
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

            if (!atomosPosibles.contains(a2)) {
                atomosPosibles.add(a2);
            }

            if (!atomosPosibles.contains(a12)) {
                atomosPosibles.add(a12);
            }

            if (!atomosPosibles.contains(a1)) {
                atomosPosibles.add(a1);
            }

            if (!atomosPosibles.contains(a2)) {
                atomosPosibles.add(a2);
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
        armarGrafo(this.atomosPosibles, w1, w2);

        return null;
    }

    public void armarGrafo(List<Atomo> atomosPosibles, int w1, int w2) {
        for (Atomo atomo1 : atomosPosibles) {
            Map<Integer, Integer> mapaListaAdj = new HashMap<>(); // para cada atomo
            for (Atomo atomo2 : atomosPosibles) {
                if (atomo1.getMasa() != atomo2.getMasa()) { // solo hago el caso cuando no sea con el misma masa,
                                                            // diferente carga
                    int LTP = calcularLTP(atomo1, atomo2, w1, w2); // este es el peso del arco
                    mapaListaAdj.put(atomo2.getCargaMasa(), LTP); // las llaves son cargaMasa de Atomo2, y valor es el
                                                                  // LTP atomo 1 con atomo2
                }
            }
            this.grafo.put(atomo1.getCargaMasa(), mapaListaAdj);
        }
    }

    public int calcularLTP(Atomo a1, Atomo a2, int w1, int w2) {
        int LTP;
        boolean c1 = a1.getCarga();
        boolean c2 = a2.getCarga();
        int masa1 = a1.getMasa();
        int masa2 = a2.getMasa();
        int diferenciaABS = Math.abs(masa1 - masa2);

        if (c1 == c2) {
            LTP = 1 + (diferenciaABS % w1);
        } else {
            LTP = w2 - (diferenciaABS % w2);
        }

        return LTP;

    }
}
