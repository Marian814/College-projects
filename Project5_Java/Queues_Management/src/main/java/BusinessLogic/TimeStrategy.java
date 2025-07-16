package BusinessLogic;

import Model.Client;
import Model.QueueServer;

import java.util.List;

public class TimeStrategy implements Strategy {
    @Override
    public void addClient(List<QueueServer> queueServers, Client client) {
        QueueServer bestQueue = queueServers.get(0);
        int minTotalWaitingTime = bestQueue.getTotalWaitingTime();

        for (QueueServer qs : queueServers) {
            int totalWaitingTime = qs.getTotalWaitingTime();
            if (totalWaitingTime < minTotalWaitingTime) {
                minTotalWaitingTime = totalWaitingTime;
                bestQueue = qs;
            }
        }

        bestQueue.addClient(client);
    }
}
