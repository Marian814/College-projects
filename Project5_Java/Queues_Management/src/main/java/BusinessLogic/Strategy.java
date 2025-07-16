package BusinessLogic;

import Model.Client;
import Model.QueueServer;

import java.util.List;

public interface Strategy {
    public void addClient(List<QueueServer> queueServers, Client client);
}
