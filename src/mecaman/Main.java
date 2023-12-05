package mecaman;

import mecaman.producerconsumer.WordConsumer;
import mecaman.producerconsumer.WordManager;
import mecaman.producerconsumer.WordProducer;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Hilo principal. Lanza los hilos y a continuación controla la prioridad
 * de productores y consumidores
 */
public class Main {

    private static final int CONSUMER_THREADS = 4;
    private static final int PRODUCER_THREADS = 5;
    private static final int QUEUE_SIZE = 100;
    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> wordsQueue = new ConcurrentLinkedQueue<>();
        WordManager wordManager = new WordManager(QUEUE_SIZE, wordsQueue);
        Thread[] producers = new Thread[PRODUCER_THREADS];
        Thread[] consumers = new Thread[CONSUMER_THREADS];

        // Lanza hilos productores
        for (int i = 0; i < PRODUCER_THREADS; i++) {
            producers[i] = new WordProducer(wordManager, "Producer " + i);
            producers[i].start();
        }

        // Lanza hilos consumidores
        for (int i = 0; i < CONSUMER_THREADS; i++) {
            consumers[i] = new WordConsumer(wordManager, "Consumer " + i);
            consumers[i].start();
        }

        // Controla prioridad (podemos utilizar un timer para esto)
        while (true) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int consumerPriority = (int) Math.round(wordManager.getOccupancyPercentage() * 10);
            if (consumerPriority == 0)
                consumerPriority = 1; // La prioridad mínima es 1

            int producerPriority = 11 - consumerPriority;
            System.out.printf("""
                            ------
                            Prioridad consumidor: %d
                            Prioridad productor: %d
                            Tamaño de la cola: %d
                            -------
                            """,
                    consumerPriority, producerPriority, wordsQueue.size());
            for (Thread producer : producers) {
                producer.setPriority(producerPriority);
            }
            for (Thread consumer : consumers) {
                consumer.setPriority(consumerPriority);
            }
        }
    }
}
