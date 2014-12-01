public class Task implements Runnable {
    private final int name;
    private int priority;
    private final int time;


    public Task(int name, int priority, int time) {
        this.name = name;
        this.priority = priority;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int name() {
        return name;
    }

    public int priority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}