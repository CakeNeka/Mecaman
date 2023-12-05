package mecaman.producerconsumer;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class WordManager {
    private boolean active = true; // Cambiar a falso para finalizar el programa
    private int maxSize;
    private ConcurrentLinkedQueue<String> wordsQueue;
    private Semaphore producerSemaphore;
    private Semaphore consumerSemaphore;

    public WordManager(int maxSize, ConcurrentLinkedQueue<String> wordsQueue) {
        this.maxSize = maxSize;
        this.wordsQueue = wordsQueue;
        producerSemaphore = new Semaphore(maxSize);
        consumerSemaphore = new Semaphore(0);
    }

    /**
     * Devuelve el porcentaje de ocupación de la estructura de datos
     * Se utiliza para controlar las prioridades de los hilos
     */
    public double getOccupancyPercentage() {
        return ((double) consumerSemaphore.availablePermits() / maxSize);
    }

    public void addWord(WordProducer producer) {
        try {
            // Aquí el hilo productor espera hasta que haya sitio en la cola para añadir más palabras
            producerSemaphore.acquire();
            String word = generateWord(); // Placeholder, cambiar por clase de Leo
            System.out.printf("%s añade %s\n", producer.getName(), word);
            Thread.sleep((long) (Math.random() * 500));
            wordsQueue.add(word);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // Se ha añadido una palabra, ahora un consumidor puede procesarla
            consumerSemaphore.release();
        }
    }

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

            System.out.printf("%s escribe %s\n", consumer.getName(), new String(reversedWordArray));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // Se ha eliminado una palabra de la cola, ahora un productor puede añadir otra
            producerSemaphore.release();
        }
    }

    private String generateWord() {
        return "Macarrones";
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
