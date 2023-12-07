package mecaman;

import mecaman.producerconsumer.WordConsumer;
import mecaman.producerconsumer.WordManager;
import mecaman.producerconsumer.WordProducer;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hilo principal. Lanza los hilos y a continuación controla la prioridad
 * de productores y consumidores según la ocupación de la cola.
 *
 * Finaliza la ejecución del programa después del tiempo indicado
 */
public class Main {

    private static final int CONSUMER_THREADS = 4; // Número de hilos consumidores
    private static final int PRODUCER_THREADS = 9; // Número de hilos productores
    private static final int QUEUE_SIZE = 100;  // Tamaño máximo de la cola
    private static final int DURATION = 30;     // El programa finaliza cuando pasen esta cantidad de segundos
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

        MecamanStateLogger logger = new MecamanStateLogger();
        logger.writeHeader();

        // Controla prioridad. Con Runnable y ExecutorService evitamos el uso de while(true)
        Runnable priorityHandler = () -> {
            // 1. Calcular prioridad
            int consumerPriority = (int) Math.round(wordManager.getOccupancyPercentage() * 10);
            if (consumerPriority == 0)
                consumerPriority = 1; // La prioridad mínima es 1
            int producerPriority = 11 - consumerPriority;

            // 2. Asignar prioridad
            for (Thread producer : producers) {
                producer.setPriority(producerPriority);
            }
            for (Thread consumer : consumers) {
                consumer.setPriority(consumerPriority);
            }

            // 3. Mostrar prioridad y tamaño de la cola
            System.out.printf("""
                            ------
                            Prioridad consumidor: %d
                            Prioridad productor: %d
                            Tamaño de la cola: %d
                            -------
                            """,
                    consumerPriority, producerPriority, wordsQueue.size());

            // 4. Registrar prioridad y tamaño de la cola en el archivo log
            logger.logState(wordsQueue.size(), consumerPriority, producerPriority);
        };

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // El código de priorityHandler se ejecuta cada 2 segundos hasta que hacemos executor.shutdown().
        executor.scheduleAtFixedRate(priorityHandler,2, 2, TimeUnit.SECONDS);

        // Finaliza la ejecución después de DURATION segundos
        try {
            Thread.sleep(Duration.ofSeconds(DURATION));
        } catch (InterruptedException e) {
            e.getMessage();
        }
        executor.shutdown();
        wordManager.setActive(false);
    }
}
