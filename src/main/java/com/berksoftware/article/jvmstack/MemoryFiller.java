package com.berksoftware.article.jvmstack;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;


/**
 * Allocates stack and heap memory by given parameters for observing JVM behavior under different configurations
 */
public class MemoryFiller {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * Spawn threads, consume heap and stack memory per thread and wait
     *
     * @param threadCount   How many threads to create
     * @param heapPerThread Approx how much heap memory (in KB) to consume per thread
     * @param frameCount    How many stack frames to use per thread
     */
    public void spawnThreads(int threadCount, int heapPerThread, int frameCount) {
        System.out.println("\nSpawning " + threadCount + " threads, each consuming ~" + heapPerThread + " KB heap, "
                                   + frameCount + " stack frames\n");

        IntStream.rangeClosed(1, threadCount).forEachOrdered(threadNr -> new Thread(() -> {
            // Allocate heap memory
             byte[] heapArr = new byte[heapPerThread * 1024];
             // Allocate stack memory
             fillStack(frameCount);
             System.out.print("\r" + threadNr + " threads created");
             // Block thread for measuring
             try {
                 countDownLatch.await();
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
        }).start());

        System.out.println("\nPID: " + ManagementFactory.getRuntimeMXBean().getPid());
    }


    /**
     * Fills stack by recursively calling itself
     *
     * @param frameCount How many frames to add to stack memory
     */
    private void fillStack(int frameCount) {
        // Unused primitives defined to consume stack on each frame,
        // since "frames may be heap allocated" : https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-2.html#jvms-2.5.2
        var p1 = 1L;
        var p2 = 1L;
        if (frameCount > 0) {
            fillStack(frameCount - 1);
        }
    }


    public static void main(String[] args) {
        if(args.length != 3) {
            System.err.println("Usage: memoryfiller thread_count heap_per_thread stack_frame_per_thread");
            System.exit(1);
        }
        new MemoryFiller().spawnThreads(Integer.parseInt(args[0]),  // Thread count
                                        Integer.parseInt(args[1]),  // Heap per thread
                                        Integer.parseInt(args[2])); // Stack frames per thread
    }
}
