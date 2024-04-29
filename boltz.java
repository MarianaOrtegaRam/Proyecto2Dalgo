import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class boltz {

    int w1;
    int w2;
    List<elemento> elementos;
    boolean posible;
    List<Atomo> caminoMinimo;
    HashMap<Integer, List<elemento>> conexiones = new HashMap<>();
    List<Atomo> atomosPosibles = new ArrayList<>();
    Map<Integer, Map<Integer, Integer>> grafo = new HashMap<>();
    List<elemento> caminoSinIntermedios = new ArrayList<>();
    List<Integer> positivos = new ArrayList<>();
    Integer energiaTotal = 0;
    Map<Integer, List<Integer>> costosMinimos = new HashMap<>();
    Map<Integer, Integer> pesoCostoMinimo = new HashMap<>();
    String caminoFinal;

    public boltz(int w1, int w2, List<elemento> elementos) {
        this.elementos = elementos;
        this.w1 = w1;
        this.w2 = w2;
        boolean pos = caminoPosible(w1, w2, elementos);
        this.posible = pos;
        if (pos) {
            calcularCaminoMinimo(w1, w2, elementos);
            darCaminoFinal();
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

    private void darCaminoFinal() {
        for (int i = 0; i < caminoSinIntermedios.size() - 2; i++) {

            elemento e1 = caminoSinIntermedios.get(i);
            elemento e2 = caminoSinIntermedios.get(i + 1);

            Atomo atomoBusqueda = darComun(e1, e2);
            Integer masa = atomoBusqueda.getMasa();
            List<Integer> intermedios = costosMinimos.get(masa);
            energiaTotal += pesoCostoMinimo.get(masa);
            String primero;
            String mitad;
            String fin;
            if (e1.getAtomo1() != atomoBusqueda) {
                primero = "(" + String.valueOf(e1.getAtomo1().getCargaMasa())
                        + String.valueOf(atomoBusqueda.getCargaMasa()) + ")";
                mitad = "," + (intermedios.subList(1, intermedios.size() - 1)).toString() + ",";
                if (e2.getAtomo1() != atomoBusqueda) {
                    fin = "(" + String.valueOf(atomoBusqueda.getCargaMasa())
                            + String.valueOf(e2.getAtomo1().getCargaMasa()) + ")";
                } else {
                    fin = "(" + String.valueOf(atomoBusqueda.getCargaMasa())
                            + String.valueOf(e2.getAtomo2().getCargaMasa()) + ")";
                }
            }

            else

            {
                primero = "(" + String.valueOf(e1.getAtomo2().getCargaMasa())
                        + String.valueOf(atomoBusqueda.getCargaMasa()) + ")";
                mitad = "," + (intermedios.subList(1, intermedios.size() - 1)).toString() + ",";
                if (e2.getAtomo1() != atomoBusqueda) {
                    fin = "(" + String.valueOf(atomoBusqueda.getCargaMasa())
                            + String.valueOf(e2.getAtomo1().getCargaMasa()) + ")";
                } else {
                    fin = "(" + String.valueOf(atomoBusqueda.getCargaMasa())
                            + String.valueOf(e2.getAtomo2().getCargaMasa()) + ")";
                }

            }

            caminoFinal += primero + mitad + fin;

        }

        caminoFinal += "," + String.valueOf(energiaTotal);

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
            Atomo a12 = new Atomo(masa1, !(carga1), -1 * (cargaMasa1));
            Atomo a2 = e.getAtomo2();

            int masa2 = a2.getMasa();
            boolean carga2 = a2.getCarga();
            int cargaMasa2 = a2.getCargaMasa();
            Atomo a22 = new Atomo(masa2, !(carga2), -1 * (cargaMasa2));
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
                if (!positivos.contains(a2.getMasa())) {
                    positivos.add(a2.getMasa());
                }
            }

            if (!atomosPosibles.contains(a12)) {
                atomosPosibles.add(a12);
                if (!positivos.contains(a12.getMasa())) {
                    positivos.add(a12.getMasa());

                }
            }

            if (!atomosPosibles.contains(a1)) {
                atomosPosibles.add(a1);
                if (!positivos.contains(a1.getMasa())) {
                    positivos.add(a1.getMasa());

                }
            }

            if (!atomosPosibles.contains(a22)) {
                atomosPosibles.add(a22);
                if (!positivos.contains(a22.getMasa())) {
                    positivos.add(a22.getMasa());

                }
            }

        }

        /*
         * Cuando ya está armada el mapa, revisaremos la psibilidad de conexión de los
         * atomos tomandoe en cuenta:
         * 1. deben al menos un atomo tales que solo tengan un elemento asociado, ya que
         * uno es el frente y el otro el dinal de la cadena
         * 2. si hay más de 3 atomos que solo tienen un elemento asociado puede
         * significar:
         * a. todos los atomos son disyuntos, es decir no existen atomos en comun entre
         * los elementos.
         * b. exiten conjuntos de cadenas de elementos correctas, pero disyuntas con
         * otros conjuntos de elementos.
         */

        int contadorUnicos = 0;
        List<Integer> listaUnicos = new ArrayList<>();

        for (Map.Entry<Integer, List<elemento>> entry : conexiones.entrySet()) {
            int key = entry.getKey();
            List<elemento> value = entry.getValue();

            if (value.size() == 1) {
                contadorUnicos += 1;
                listaUnicos.add(key);
            }

        }

        if (contadorUnicos > 2) {
            return false;
        } else {
            caminoSinConexiones(listaUnicos, contadorUnicos);
        }

        return true;
    }

    public void caminoSinConexiones(List<Integer> unicos, int contador) {

        // Agarro uno de los unicos en mi lista de unicos
        int primerUnico = unicos.get(0); // me da la cragaMasa de ese unico que tomatemos como inicio de mi cadena

        elemento primerElemento = conexiones.get(primerUnico).get(0); // obtengo el elemento asociado a esa cargaMasa
        // elemento ultimoElemento = conexiones.get(primerUnico).get(1);
        caminoSinIntermedios.add(primerElemento); // lo agrego primero a mi lista final con el camino posible
        while (caminoSinIntermedios.size() < elementos.size()) { // voy a moverme por el orden que voy metiendo los
                                                                 // elementos
            int sizeLista = caminoSinIntermedios.size();
            elemento unElemento = caminoSinIntermedios.get(sizeLista - 1); // saco el elemento que este en la ulitma
                                                                           // posicion para conectarlo
            Atomo a1 = unElemento.getAtomo1(); // un atomo en el elemento
            Atomo a2 = unElemento.getAtomo2(); // otro atomo en el elemnto

            List<elemento> adj1 = conexiones.get(a1.getCargaMasa()); // saco los elementos que tienen este atomo
            List<elemento> adj2 = conexiones.get(a2.getCargaMasa());

            adj1.remove(unElemento);
            adj2.remove(unElemento);

            if ((adj1.size() == 0) && (adj2.size() > 0)) { // miro a ver si es un elemento de unicos
                // si lo es, se que me tengo que mover por el otro atomo
                elemento elElemento = adj2.get(0);
                caminoSinIntermedios.add(elElemento);
            }

            else if ((adj2.size() == 0) && (adj1.size() > 0)) { // miro a ver si es un elemento de unicos
                // si lo es, se que me tengo que mover por el otro atomo
                elemento elElemento = adj1.get(0);
                caminoSinIntermedios.add(elElemento);
            }

        }

    }

    public void calcularCaminoMinimo(int w1, int w2, List<elemento> elementos) {
        armarGrafo(this.atomosPosibles, w1, w2);
        for (Integer atomoPosiblePos : positivos) {

            List<Integer> path = dijkstra(grafo, atomoPosiblePos, -(atomoPosiblePos));
            int weight = 0;
            for (int i = 1; i < path.size(); i++) {
                weight += grafo.get(path.get(i - 1)).get(path.get(i));

            }

            costosMinimos.put(atomoPosiblePos, path);
            pesoCostoMinimo.put(atomoPosiblePos, weight);
        }

    }

    public static List<Integer> dijkstra(Map<Integer, Map<Integer, Integer>> graph, int start, int end) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        for (int node : graph.keySet()) {
            if (node == start) {
                distances.put(node, 0);
            } else {
                distances.put(node, Integer.MAX_VALUE);
            }
            previous.put(node, null);
        }

        while (!visited.containsAll(distances.keySet())) {
            int current = getLowestDistanceNode(distances, visited);
            visited.add(current);

            for (int neighbor : graph.get(current).keySet()) {
                if (!visited.contains(neighbor)) {
                    int newDistance = distances.get(current) + graph.get(current).get(neighbor);
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previous.put(neighbor, current);
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        Integer current = end;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    public static int getLowestDistanceNode(Map<Integer, Integer> distances, Set<Integer> visited) {
        int lowestDistance = Integer.MAX_VALUE;
        int lowestDistanceNode = -1;

        for (int node : distances.keySet()) {
            if (!visited.contains(node) && distances.get(node) < lowestDistance) {
                lowestDistance = distances.get(node);
                lowestDistanceNode = node;
            }
        }

        return lowestDistanceNode;
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

    public Atomo darComun(elemento e1, elemento e2) {

        Atomo a11 = e1.getAtomo1();
        Atomo a12 = e1.getAtomo2();
        Atomo a21 = e2.getAtomo1();
        Atomo a22 = e2.getAtomo2();
        Atomo a = null;

        if (a11 == a21 || a11 == a22) {
            a = a11;

        }

        else if (a12 == a21 || a12 == a22) {
            a = a12;
        }

        return a;
    }

    public boolean getPosible() {
        return this.posible;
    }

    public String getCaminoFinal() {
        return this.caminoFinal;
    }
}
