package DataAccess;

import Model.Client;
import java.util.ArrayList;

public class ClientDAO {
    AbstractDAO<Client> clientDAO;

    /**
     * Constructor for ClientDAO class.
     */
    public ClientDAO() {
        clientDAO = new AbstractDAO<Client>() {};
    }

    /**
     * Find all Clients from a database.
     * @return
     */
    public ArrayList<Client> findAllClientsDAO() {
        ArrayList<Client> clients = (ArrayList<Client>) clientDAO.findAll();
        return clients;
    }

    /**
     * Find Client by ClientID from a database.
     * @param id
     * @return
     */
    public Client findClientByIdDAO(int id) {
        Client client = clientDAO.findById(id, "ClientID");
        return client;
    }

    /**
     * Insert Client into a database.
     * @param client
     * @return
     */
    public Client insertClientDAO(Client client) {
        return clientDAO.insert(client);
    }

    /**
     * Update Client into a database.
     * @param client
     * @return
     */
    public Client updateClientDAO(Client client) {
        return clientDAO.update(client);
    }

    /**
     * Delete Client from a database.
     * @param client
     * @return
     */
    public Client deleteClientDAO(Client client) {
        return clientDAO.delete(client);
    }
}
