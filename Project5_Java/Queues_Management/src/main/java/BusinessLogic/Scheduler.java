package BusinessLogic;

import Model.Client;
import Model.QueueServer;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<QueueServer> schedulerQueues;
    private int maxNoServers;
    private int maxClientsPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxClientsPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxClientsPerServer = maxClientsPerServer;
        this.schedulerQueues = new ArrayList<>();

        for (int i = 0; i < maxNoServers; i++) {
            QueueServer server = new QueueServer();
            schedulerQueues.add(server);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers(int maxNoServers) {
        this.maxNoServers = maxNoServers;
    }

    public int getMaxClientsPerServer() {
        return maxClientsPerServer;
    }

    public void setMaxClientsPerServer(int maxClientsPerServer) {
        this.maxClientsPerServer = maxClientsPerServer;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<QueueServer> getSchedulerQueues() {
        return schedulerQueues;
    }

    public void changeStrategy(SelectionPolicy policy) {
        if(policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new TimeStrategy();
        }
    }

    public void dispatchClient(Client client) {
        strategy.addClient(schedulerQueues, client);
    }

    public String getQueuesStatus() {
        StringBuilder status = new StringBuilder();
        for (int i = 0; i < getSchedulerQueues().size(); i++) {
            QueueServer server = getSchedulerQueues().get(i);
            status.append("Queue ").append(i + 1).append(": ");
            if (server.getClients().isEmpty()) {
                status.append("closed");
            }
            else {
                boolean first = true;
                for (Client c : server.getClients()) {
                    if (first) {
                        status.append("[PROCESSING]").append("(")
                                .append(c.getID()).append(", ")
                                .append(c.getArrivalTime()).append(", ")
                                .append(c.getServiceTime()).append(") ");
                        first = false;
                    }
                    else {
                        status.append("(").append(c.getID()).append(", ")
                                .append(c.getArrivalTime()).append(", ")
                                .append(c.getServiceTime()).append(") ");
                    }
                }
            }
            status.append("\n");
        }
        return status.toString();
    }


    public void stopAll() {
        for (QueueServer server : schedulerQueues) {
            server.stop();
        }
    }

    public void updateCurrentTimeForServers(int time) {
        for (QueueServer qs : schedulerQueues) {
            qs.setCurrentTime(time);
        }
    }

    public void setProcessedClientsList(List<Client> processedClients) {
        for (QueueServer server : schedulerQueues) {
            server.setProcessedClients(processedClients);
        }
    }

    public boolean allQueuesEmpty() {
        for (QueueServer server : schedulerQueues) {
            if (!server.getClients().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
