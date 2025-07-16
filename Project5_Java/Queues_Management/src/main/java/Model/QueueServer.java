package Model;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueServer implements Runnable {
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;
    private volatile boolean running = true;
    private int currentTime = 0;
    private List<Client> processedClients;

    public QueueServer() {
        this.clients = new LinkedBlockingQueue<Client>();
        this.waitingPeriod = new AtomicInteger(0);
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public void setClients(BlockingQueue<Client> clients) {
        this.clients = clients;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public void setCurrentTime(int time) {
        this.currentTime = time;
    }

    public void setProcessedClients(List<Client> processedClients) {
        this.processedClients = processedClients;
    }

    public void addClient(Client client) {
        try {
            client.setTimeEnteredQueue(currentTime);
            clients.put(client);
            waitingPeriod.addAndGet(client.getServiceTime());
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getTotalWaitingTime() {
        int total = 0;
        for (Client c : clients) {
            total += c.getServiceTime();
        }
        return total;
    }

    public void stop() {
        running = false;
    }

    public void run() {
        while (running) {
            try {
                Client client = clients.peek();
                if (client != null) {
                    if (client.getTimeStartedService() == 0)
                        client.setTimeStartedService(currentTime);
                    while (client.getServiceTime() > 0 && running) {
                        Thread.sleep(1000);
                        client.setServiceTime(client.getServiceTime() - 1);
                        waitingPeriod.decrementAndGet();
                        currentTime++;
                    }
                    client.setTimeFinishedService(currentTime);
                    clients.take();
                    if (processedClients != null) {
                        synchronized (processedClients) {
                            processedClients.add(client);
                        }
                    }
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
