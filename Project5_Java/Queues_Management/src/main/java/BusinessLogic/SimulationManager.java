package BusinessLogic;

import Model.Client;
import Model.QueueServer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {
    public int numberOfClients;
    public int numberOfServers;
    public int timeLimit;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int minServiceTime;
    public int maxServiceTime;
    private SelectionPolicy strategy;
    private Scheduler scheduler;
    private List<Client> clients;
    private List<Client> processedClients = new ArrayList<>();
    private int peakHour = 0;
    private int maxClientsAtOnce = 0;
    private volatile boolean running = true;
    private int currentTime = 0;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getPeakHour() {
        return peakHour;
    }

    public SimulationManager(int numberOfClients, int numberOfServers, int timeLimit, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, SelectionPolicy strategy) {
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.strategy = strategy;
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        scheduler.changeStrategy(strategy);
        scheduler.setProcessedClientsList(processedClients);
        generateNRandomClients();
    }

    public void generateNRandomClients() {
        clients = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= numberOfClients; i++) {
            int arrivalTime = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            int serviceTime = random.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;

            clients.add(new Client(i, arrivalTime, serviceTime));
        }

        clients.sort(Comparator.comparingInt(Client::getArrivalTime));
    }

    public String getWaitingList() {
        StringBuilder waitingClients = new StringBuilder();
        int cnt = 0;
        for (Client c : clients) {
            cnt++;
            waitingClients.append("(").append(c.getID()).append(", ")
                    .append(c.getArrivalTime()).append(", ")
                    .append(c.getServiceTime()).append(") ");
            if(cnt == 9) {
                waitingClients.append("\n");
                cnt = 0;
            }
        }
        return waitingClients.toString();
    }

    public boolean isSimulationDone() {
        boolean allQueuesEmpty = scheduler.getSchedulerQueues().stream().allMatch(q -> q.getClients().isEmpty());
        boolean waitingListEmpty = clients.isEmpty();
        return allQueuesEmpty && waitingListEmpty;
    }

    public double getAverageWaitingTime() {
        int totalWaitingTime = 0;
        int clientsWhoStartedService = 0;
        for (Client c : processedClients) {
            if (c.getTimeStartedService() >= 0) {
                totalWaitingTime += (c.getTimeStartedService() - c.getTimeEnteredQueue());
                clientsWhoStartedService++;
            }
        }
        double avgWaitingTime = clientsWhoStartedService > 0 ? (double) totalWaitingTime / clientsWhoStartedService : 0;
        return avgWaitingTime;
    }

    public double getAverageServiceTime() {
        int totalServiceTime = 0;
        int clientsServed = 0;
        for (Client c : processedClients) {
            if (c.getTimeFinishedService() >= 0) {
                totalServiceTime += (c.getTimeFinishedService() - c.getTimeEnteredQueue());
                clientsServed++;
            }
        }
        double avgServiceTime = clientsServed > 0 ? (double) totalServiceTime / clientsServed : 0;
        return avgServiceTime;
    }

    @Override
    public void run() {
        setCurrentTime(0);
        while (getCurrentTime() < timeLimit && (!clients.isEmpty() || !scheduler.allQueuesEmpty()) && running) {
            for (Client client : new ArrayList<>(clients)) {
                if (client.getArrivalTime() == getCurrentTime()) {
                    scheduler.dispatchClient(client);
                    clients.remove(client);
                }
            }
            scheduler.updateCurrentTimeForServers(getCurrentTime());
            System.out.println("Time: " + getCurrentTime());
            System.out.println("Waiting Clients: " + getWaitingList());
            System.out.println(scheduler.getQueuesStatus());
            int totalClientsInQueues = 0;
            for (QueueServer qs : scheduler.getSchedulerQueues()) {
                totalClientsInQueues += qs.getClients().size();
            }
            if (totalClientsInQueues > maxClientsAtOnce) {
                maxClientsAtOnce = totalClientsInQueues;
                peakHour = getCurrentTime();
            }
            int currentTime = getCurrentTime();
            currentTime++;
            setCurrentTime(currentTime);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Time: " + getCurrentTime());
        System.out.println("Waiting Clients: " + getWaitingList());
        System.out.println(scheduler.getQueuesStatus());
        scheduler.stopAll();

        int totalWaitingTime = 0;
        int totalServiceTime = 0;
        int clientsWhoStartedService = 0;
        int clientsServed = 0;
        for (Client c : processedClients) {
            if (c.getTimeStartedService() >= 0) {
                totalWaitingTime += (c.getTimeStartedService() - c.getTimeEnteredQueue());
                clientsWhoStartedService++;
            }
            if (c.getTimeFinishedService() >= 0) {
                totalServiceTime += (c.getTimeFinishedService() - c.getTimeEnteredQueue());
                clientsServed++;
            }
        }
        double avgWaitingTime = clientsWhoStartedService > 0 ? (double) totalWaitingTime / clientsWhoStartedService : 0;
        double avgServiceTime = clientsServed > 0 ? (double) totalServiceTime / clientsServed : 0;
        System.out.println("Simulation finished!\n");
        System.out.println("Timp mediu de asteptare: " + avgWaitingTime);
        System.out.println("Timp mediu de servire: " + avgServiceTime);
        System.out.println("Timpul cu cei mai multi clienti in cozi: " + peakHour + " (Total: " + maxClientsAtOnce + ")");
        System.out.println("\n");
    }
}
