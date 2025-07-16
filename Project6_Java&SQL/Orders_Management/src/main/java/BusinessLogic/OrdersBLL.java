package BusinessLogic;

import DataAccess.OrdersDAO;
import Model.Orders;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class OrdersBLL {
    private OrdersDAO ordersDAO;

    /**
     * Constructor for OrdersBLL class.
     */
    public OrdersBLL() {
        ordersDAO = new OrdersDAO();
    }

    /**
     * Find Orders by OrderID.
     * @param id
     * @return
     */
    public Orders findOrderById(int id) {
        Orders order = ordersDAO.findOrderByIdDAO(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        }
        return order;
    }

    /**
     * Find all Orders.
     * @return
     */
    public ArrayList<Orders> findAllOrders() {
        ArrayList<Orders> orders = ordersDAO.findAllOrdersDAO();
        if (orders == null) {
            throw new NoSuchElementException("The orders were not found!");
        }
        return orders;
    }

    /**
     * Insert Orders.
     * @param order
     * @return
     */
    public Orders insertOrder(Orders order) {
        Orders inserted = ordersDAO.insertOrderDAO(order);
        if (inserted == null) {
            throw new IllegalArgumentException("The order could not be inserted!");
        }
        return inserted;
    }

    /**
     * Update Orders.
     * @param order
     * @return
     */
    public Orders updateOrder(Orders order) {
        Orders updated = ordersDAO.updateOrderDAO(order);
        if (updated == null) {
            throw new IllegalArgumentException("The order could not be updated!");
        }
        return updated;
    }

    /**
     * Delete Orders.
     * @param order
     * @return
     */
    public Orders deleteOrder(Orders order) {
        Orders deleted = ordersDAO.deleteOrderDAO(order);
        if (deleted == null) {
            throw new IllegalArgumentException("The order could not be deleted!");
        }
        return deleted;
    }
}
