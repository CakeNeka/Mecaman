package mecaman.producerconsumer;

import mecaman.wordgeneration.RandomWordGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class WordManager {
    private boolean active = true; // Cambiar a falso para finalizar el programa
    private int maxSize;    // Tamaño máximo de la cola
    private ConcurrentLinkedQueue<String> wordsQueue;
    private Semaphore producerSemaphore;
    private Semaphore consumerSemaphore;
    private Semaphore mutex; // Utilizado para generar la palabra aleatoria
    private RandomWordGenerator wordGenerator;
    private String nomFichero; //fichero en el que se guardaran las palabras que se consuman en la ejecucion
    public WordManager(int maxSize, ConcurrentLinkedQueue<String> wordsQueue, String nomFichero) {
        this.maxSize = maxSize;
        this.wordsQueue = wordsQueue;
        producerSemaphore = new Semaphore(maxSize);
        consumerSemaphore = new Semaphore(0);
        mutex = new Semaphore(1);
        wordGenerator = RandomWordGenerator.getInstance();
        this.nomFichero = nomFichero;

    }

    /**
     * Devuelve el porcentaje de ocupación de la estructura de datos
     * Se utiliza para controlar las prioridades de los hilos
     */
    public double getOccupancyPercentage() {
        return ((double) consumerSemaphore.availablePermits() / maxSize);
    }

    // Método llamado por el PRODUCTOR (añade palabras a la cola y espera entre 0 y 0.5 segundos)
    public void addWord(WordProducer producer) {
        try {
            // Aquí el hilo productor espera si no hay sitio en la cola para añadir más palabras
            producerSemaphore.acquire();
            String word = generateWord();
            System.out.printf("%s añade %s\n", producer.getName(), word);
            Thread.sleep((long) (Math.random() * 500)); // El hilo es suspendido entre 0 y 0.5 segundos
            wordsQueue.add(word);
        } catch (InterruptedException e) {
            System.err.println("Se ha producido un error durante la ejecución del productor");
        } finally {
            // Se ha añadido una palabra, ahora un consumidor puede procesarla
            consumerSemaphore.release();
        }
    }

    // Método llamado por el CONSUMIDOR
    public void typeWord(WordConsumer consumer) {
        try {
            // Aquí el hilo consumidor espera en caso de que la cola esté vacía
            consumerSemaphore.acquire();
            String word = wordsQueue.remove();

            // Consumidor procesa palabra (le da la vuelta)
            char[] reversedWordArray = new char[word.length()];
            int j = word.length() - 1;
            int i = 0;
            while (j >= 0) {
                reversedWordArray[j--] = word.charAt(i++);
                Thread.sleep(20);
            }
            //una vez procesada la palabra procede a escribirla en el fichero
            try{
                File fichero = new File(this.nomFichero);
                //Comprobamos si existe el fichero
                if (!Files.exists(fichero.toPath())){
                    //Si no existe, lo crea
                    Files.createFile(fichero.toPath());
                }
                FileWriter fw = new FileWriter(fichero, true);
                BufferedWriter bw = new BufferedWriter(fw);
                //Escribimos la palabra
                bw.write(new String(reversedWordArray));
                //Añadimos un salto de linea
                bw.newLine();
                //Cerramos el BufferedWriter
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.printf("%s escribe %s\n", consumer.getName(), new String(reversedWordArray));
        } catch (InterruptedException e) {
            System.err.println("Se ha producido un error durante la ejecución del consumidor");
        } finally {
            // Se ha eliminado una palabra de la cola, ahora un productor puede añadir otra
            producerSemaphore.release();
        }
    }

    /**
     * Genera una palabra aleatoria
     * @return Devuelve un String que contiene la palabra generada
     */

    private String generateWord() {
        String word = null;
        try {
            //Activamos el semaforo Mutex
            mutex.acquire();
            //Generamos la palabra llamando a wordGeneratore
            word = wordGenerator.getRandomWord();
            //Liberamos el semaforo
            mutex.release();
        } catch (InterruptedException e) {
            System.err.println("Error en la exclusión mutua");
        }
        return word;
    }

    /**
     * Comprueba si el WordManager esta activo
     * @return Devuelve true si esta activo
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Establece el estado de active
     * @param active Le pasamos una variable booleana para cambiar el estado
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
