class Resource {
    public synchronized void acquire(Resource other) {
        System.out.println(Thread.currentThread().getName() + " acquired " + this);
        try {
            Thread.sleep(100); // Simulating some work
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " trying to acquire " + other);
        other.use();
    }

    public synchronized void use() {
        System.out.println(Thread.currentThread().getName() + " using " + this);
    }

    @Override
    public String toString() {
        return "Resource " + hashCode();
    }
}

public class DeadlockExample {
    public static void main(String[] args) {
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();

        // Thread 1 tries to lock resource1, then resource2
        Thread thread1 = new Thread(() -> {
            resource1.acquire(resource2);
        }, "Thread-1");

        // Thread 2 tries to lock resource2, then resource1
        Thread thread2 = new Thread(() -> {
            resource2.acquire(resource1);
        }, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
