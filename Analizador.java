//Mariana Ortega - 2022112313 

//Paulina Arrázola - 202020631 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Analizador {

    public static void main(String[] args) throws Exception {
        Analizador instance = new Analizador();
        instance.solveProblems();
    }

    public void solveProblems() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del archivo: ");
        String fileName = scanner.nextLine();
        scanner.close();

        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            // Lee el número de casos de prueba
            int cantcasos = fileScanner.nextInt();
            fileScanner.nextLine(); // Consumir el salto de línea

            // Lista para almacenar los casos de prueba
            List<ProblemaP2> casos = new ArrayList<>();

            // Leer cada caso de prueba
            for (int i = 0; i < cantcasos; i++) {
                int n = fileScanner.nextInt();
                int w1 = fileScanner.nextInt();
                int w2 = fileScanner.nextInt();
                fileScanner.nextLine(); // Consumir el salto de línea

                // Lista para almacenar los elementos de cada caso
                List<elemento> elementos = new ArrayList<>();

                // Leer los elementos de este caso
                for (int j = 0; j < n; j++) {
                    int a1 = fileScanner.nextInt();
                    int a2 = fileScanner.nextInt();
                    elemento unElemento = new elemento(a1, a2);
                    elementos.add(unElemento);
                    fileScanner.nextLine(); // Consumir el salto de línea
                }
                ProblemaP2 caso = new ProblemaP2(w1, w2, elementos);
                boolean posible = caso.getPosible();
                if (posible == false) {
                    System.out.println("NO SE PUEDE EJECUTAR");
                } else
                    System.out.println(caso.caminoFinal);

                // Agregar el caso de prueba a la lista
                casos.add(caso);
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        }
    }

}