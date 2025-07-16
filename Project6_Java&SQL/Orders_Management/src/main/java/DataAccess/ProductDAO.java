package DataAccess;

import Model.Product;
import java.util.ArrayList;

public class ProductDAO {
    AbstractDAO<Product> productDAO;

    /**
     * Constructor for ProductDAO class.
     */
    public ProductDAO() {
        productDAO = new AbstractDAO<Product>() {};
    }

    /**
     * Find all Products from a database.
     * @return
     */
    public ArrayList<Product> findAllProductsDAO() {
        return (ArrayList<Product>) productDAO.findAll();
    }

    /**
     * Find Product by ProductID from a database.
     * @param id
     * @return
     */
    public Product findProductByIdDAO(int id) {
        return productDAO.findById(id, "ProductID");
    }

    /**
     * Insert Product into a database.
     * @param product
     * @return
     */
    public Product insertProductDAO(Product product) {
        return productDAO.insert(product);
    }

    /**
     * Update Product into a database.
     * @param product
     * @return
     */
    public Product updateProductDAO(Product product) {
        return productDAO.update(product);
    }

    /**
     * Delete Product from a database.
     * @param product
     * @return
     */
    public Product deleteProductDAO(Product product) {
        return productDAO.delete(product);
    }
}
