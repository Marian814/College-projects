package BusinessLogic;

import Model.Client;
import Model.QueueServer;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addClient(List<QueueServer> queueServers, Client client) {
        QueueServer bestQueue = queueServers.get(0);

        for (QueueServer qs : queueServers) {
            if (qs.getClients().size() < bestQueue.getClients().size()) {
                bestQueue = qs;
            }
        }

        bestQueue.addClient(client);
    }
}
