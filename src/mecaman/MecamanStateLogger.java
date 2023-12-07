package mecaman;

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

    }


    /**
     * Añade una nueva línea al archivo.
     *
     */
    public void logState(int queueSize, int consumerPriority, int producerPriority) {

    }
}
