package DataAccess;

import Model.Orders;
import java.util.ArrayList;

public class OrdersDAO {
    private AbstractDAO<Orders> ordersDAO;

    /**
     * Constructor for OrdersDAO class.
     */
    public OrdersDAO() {
        ordersDAO = new AbstractDAO<Orders>() {};
    }

    /**
     * Find all Orders from a database.
     * @return
     */
    public ArrayList<Orders> findAllOrdersDAO() {
        return (ArrayList<Orders>) ordersDAO.findAll();
    }

    /**
     * Find Orders by OrderID from a database.
     * @param id
     * @return
     */
    public Orders findOrderByIdDAO(int id) {
        return ordersDAO.findById(id, "OrderID");
    }

    /**
     * Insert Orders into a database.
     * @param order
     * @return
     */
    public Orders insertOrderDAO(Orders order) {
        return ordersDAO.insert(order);
    }

    /**
     * Update Orders into a database.
     * @param order
     * @return
     */
    public Orders updateOrderDAO(Orders order) {
        return ordersDAO.update(order);
    }

    /**
     * Delete Orders from a database.
     * @param order
     * @return
     */
    public Orders deleteOrderDAO(Orders order) {
        return ordersDAO.delete(order);
    }
}
