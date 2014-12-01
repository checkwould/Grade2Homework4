import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Workspace ws;
        if (args.length == 0) {
            ws = new Workspace();
        } else {
            ws = new Workspace(args[0]);
        }

        Producer pr = new Producer(ws);
        pr.setName("ПОСТАВЩИК");

        Consumer cs = new Consumer(ws);
        cs.setName("ПОТРЕБИТЕЛЬ");

        pr.start();
        cs.start();
    }
}

