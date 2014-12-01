import java.util.Comparator;

// Используется для сортировки по убыванию, т.к. в "голове" priorityQueue находится наименьший элемент,
// а нужен наибольший
public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        if (o1.priority() > o2.priority())
            return -1;
        if (o1.priority() < o2.priority())
            return 1;
        return 0;
    }
}