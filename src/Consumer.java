public class Consumer extends Thread {
    private Workspace ws;

    Consumer(Workspace ws) {
        this.ws = ws;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 500; i++) {
            try {
                long currentTime;
                Task tsk = ws.get();
                int name = tsk.name();
                int priority = tsk.priority();

                synchronized (ws.out) {
                    currentTime = System.currentTimeMillis();
                    if (tsk.name() == 1) { // на первых запусках теряет милисекунды на проверку данного условия
                        startTime = currentTime;
                    }
                    ws.out.printf(
                            "СТАРТ: %d; старт в %d; стартовый приоритет %d\n",
                            name,
                            currentTime - startTime,
                            priority
                    );
                }

                tsk.run();

                synchronized (ws.out) {
                    currentTime = System.currentTimeMillis();
                    ws.out.printf(
                            "ФИНИШ: %d; финиш в %d\n",
                            name,
                            currentTime - startTime
                    );
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long finishTime = System.currentTimeMillis();
        synchronized (ws.out) {
            ws.out.printf(
                    "ВЫПОЛНЕНО ЗА: %d; МАКС. ОЧЕРЕДЬ: %d\n",
                    finishTime - startTime,
                    ws.maxQueue()
            );
        }
    }
}
