package mecaman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Registra en un archivo de texto el estado del programa
 * (tamaño de la cola, prioridad de los consumidores y prioridad de los productores). <br>
 *
 * El archivo tendrá la estructura de un csv. Por ejemplo:
 *
 * <pre>
 *     size, consumerPriority, producerPriority
 *     5, 0, 10
 *     18, 2, 8
 *     50, 5, 5
 * </pre>
 *
 * La primera línea es escrita con una llamada a writeHeader()<br>
 * Las siguientes líneas son escritas con llamadas a logState()
 */
public class MecamanStateLogger {

    /**
     * Ruta del archivo de texto
     */
    private final String FILE_PATH = "resources/log.csv";

    /**
     * Crea el archivo de texto y escribe la cabecera ("size, consumerPriority, producerPriority")<br>
     *
     * Si el archivo no existe, se crea.<br>
     * Si el archivo ya existe, se sobreescribe<br>
     *
     */
    public void writeHeader() {
        try{
            File fichero = new File(FILE_PATH);
            //Comprobamos si existe el fichero
            if (!Files.exists(fichero.toPath())){
                //Si no existe, lo crea
                Files.createFile(fichero.toPath());
            }
            FileWriter fw = new FileWriter(fichero);
            BufferedWriter bw = new BufferedWriter(fw);
            //Escribimos la cabecera
            bw.write("size, consumerPriority, producerPriority");
            //Añadimos una linea
            bw.newLine();
            //Cerramos el BufferedWriter
            bw.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Añade una nueva linea al fichero.
     * @param queueSize Variable que contiene el tamaño de la cola
     * @param consumerPriority Variable que contiene la prioridad del consumidor
     * @param producerPriority Variable que contiene la prioridad del productor
     */
    public void logState(int queueSize, int consumerPriority, int producerPriority) {
        try{
            File fichero = new File(FILE_PATH);
            //Escribimos en la ultima linea del fichero
            FileWriter fw = new FileWriter(fichero, true);
            BufferedWriter bw = new BufferedWriter(fw);
            //Escribimos el contenido de las variables
            bw.write(queueSize + ", " + consumerPriority + ", " + producerPriority);
            //Añadimos una nueva linea
            bw.newLine();
            //Cerramos el BufferedWriter
            bw.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
