import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        var sc = new Scanner(System.in);
        int respuesta;

        do {
            System.out.println("Menú");
            System.out.println("1. Combinar ficheros.");
            System.out.println("2. Combinar ordenados.");
            System.out.println("3. Salir.");

            System.out.print("Seleccione opción: ");
            respuesta = sc.nextInt();

            switch (respuesta) {
                default -> System.out.println("Opción inválida, vuelva a intentarlo.");
                case 1 -> combinarFicheros();
                case 2 -> combinarOrdenados();
                case 3 -> System.out.println("Terminando programa");
            }
        } while (respuesta != 3);
    }

    private static List<String> leerArchivo(String ruta) {
        var lista = new ArrayList<String>();

        try {
            var file = new File(ruta);
            var sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String str = sc.nextLine();
                if (str.length() == 0) continue;
                lista.add(str);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
        }

        return lista;
    }

    private static void escribirArchivo(String ruta, String... datos) {
        try {
            var path = Paths.get(ruta);
            if (path.toFile().delete()) System.gc();
            for (String dato : datos) {
                Files.write(path, dato.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
                Files.write(path, System.lineSeparator().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            }
        } catch (InvalidPathException e) {
            System.out.println("Ruta inválida.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }

    private static void combinarFicheros() {
        var frutas = leerArchivo("frutas.txt");
        var planetas = leerArchivo("planetas.txt");
        var combinados = new ArrayList<String>();

        for (int i = 0; i < Math.max(frutas.size(), planetas.size()); i++) {
            if (i < frutas.size()) {
                combinados.add(frutas.get(i));
            }
            if (i < planetas.size()) {
                combinados.add(planetas.get(i));
            }
        }

        escribirArchivo("resultado.txt", combinados.toArray(String[]::new));
    }

    private static void combinarOrdenados() {
        var frutas = leerArchivo("frutas.txt");
        var planetas = leerArchivo("planetas.txt");

        var combinados = new TreeSet<>(Stream.concat(frutas.stream(), planetas.stream()).toList());

        escribirArchivo("salida-ordenada.txt", combinados.toArray(String[]::new));
    }
}