import java.util.Random;

public class Producer extends Thread {
    private Workspace ws;

    Producer(Workspace ws) {
        this.ws = ws;
    }

    @Override
    public void run() {
        Random rand = new Random();

        for (int i = 0; i < 500; i++) {
            int name = i + 1;
            int runTime = rand.nextInt(191) + 10; // between 10 and 200 (inclusive)
            int priority = rand.nextInt(25) + 1; // between 1 and 25 (inclusive)
            synchronized (ws.out) {
                ws.out.printf("ЗАПРОС: %d; приоритет %d; время (мс) %d\n", name, priority, runTime);
            }
            ws.put(new Task(name, priority, runTime));

            int sleep = rand.nextInt(191) + 10;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
