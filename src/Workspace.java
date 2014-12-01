import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.PriorityQueue;

public class Workspace {
    final PrintStream out;  // Переопределение стандартного вывода, задается в конструкторе
    private boolean empty; // чтобы избежать взаимной блокировки в get() -> wait()
    private final PriorityQueue<Task> pq;  // стандартная приоритетная очередь
    private int maxQueue;     // хранит максимальную длину очереди
    private int counter; // подразумевается, что задач будет меньше чем Integer.MAX_VALUE

    // Конструктор для программы без аргументов
    Workspace() {
        // задаем приоритетную очередь с помощью компаратора
        this.pq = new PriorityQueue<>(10, new TaskComparator());
        maxQueue = 0;
        counter = 0;
        out = System.out;
        // флаг пустой очереди
        empty = true;
    }

    // Аналогичный конструктор для вывода в файл
    Workspace(String path) throws FileNotFoundException {
        this.pq = new PriorityQueue<>(10, new TaskComparator());
        maxQueue = 0;
        counter = 0;
        out = new PrintStream(path);
        empty = true;
    }

    synchronized Task get() throws InterruptedException {
        Task tsk;
        if (empty) wait();  // избегаем взаимной блокировки
        synchronized (pq) {
            tsk = pq.poll(); // получаем элемент с максимальным приоритетом
            empty = pq.isEmpty();
        }
        tsk.setPriority(counter + tsk.priority()); // подводим эффективный приоритет к конечному виду

        return tsk;
    }

    synchronized void put(Task tsk) {
        tsk.setPriority(tsk.priority() - (++counter)); // для новых задач вычитаемое больше на единицу, чем для "старых"
        synchronized (pq) {
            pq.add(tsk);
            maxQueue = Math.max(maxQueue, pq.size());
            empty = pq.isEmpty();
        }
        notify();
    }

    synchronized int maxQueue() {
        return maxQueue;
    }
}
