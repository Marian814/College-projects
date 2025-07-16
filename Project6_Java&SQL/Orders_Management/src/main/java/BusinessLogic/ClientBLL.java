package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ClientBLL {
    private ClientDAO clientBLL;

    /**
     * Constructor for ClientBLL class.
     */
    public ClientBLL() {
        clientBLL = new ClientDAO();
    }

    /**
     * Find Client by ClientID.
     * @param id
     * @return
     */
    public Client findClientById(int id) {
        Client client = clientBLL.findClientByIdDAO(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return client;
    }

    /**
     * Find all Clients.
     * @return
     */
    public ArrayList<Client> findAllClients() {
        ArrayList<Client> clients = clientBLL.findAllClientsDAO();
        if (clients == null) {
            throw new NoSuchElementException("The clients were not found!");
        }
        return clients;
    }

    /**
     * Insert Client.
     * @param client
     * @return
     */
    public Client insertClient(Client client) {
        Client inserted = clientBLL.insertClientDAO(client);
        if (inserted == null) {
            throw new IllegalArgumentException("The client could not be inserted!");
        }
        return inserted;
    }

    /**
     * Update Client.
     * @param client
     * @return
     */
    public Client updateClient(Client client) {
        Client updated = clientBLL.updateClientDAO(client);
        if (updated == null) {
            throw new IllegalArgumentException("The client could not be updated!");
        }
        return updated;
    }

    /**
     * Delete Client.
     * @param client
     * @return
     */
    public Client deleteClient(Client client) {
        Client deleted = clientBLL.deleteClientDAO(client);
        if (deleted == null) {
            throw new IllegalArgumentException("The client could not be deleted!");
        }
        return deleted;
    }
}
