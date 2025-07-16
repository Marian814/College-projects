package BusinessLogic;

import DataAccess.ProductDAO;
import Model.Product;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ProductBLL {
    private final ProductDAO productDAO;

    /**
     * Constructor for ProductBLL class.
     */
    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * Find Product by ProductID.
     * @param id
     * @return
     */
    public Product findProductById(int id) {
        Product product = productDAO.findProductByIdDAO(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return product;
    }

    /**
     * Find all Products.
     * @return
     */
    public ArrayList<Product> findAllProducts() {
        ArrayList<Product> products = productDAO.findAllProductsDAO();
        if (products == null) {
            throw new NoSuchElementException("The products were not found!");
        }
        return products;
    }

    /**
     * Insert Product.
     * @param product
     * @return
     */
    public Product insertProduct(Product product) {
        Product inserted = productDAO.insertProductDAO(product);
        if (inserted == null) {
            throw new IllegalArgumentException("The product could not be inserted!");
        }
        return inserted;
    }

    /**
     * Update Product.
     * @param product
     * @return
     */
    public Product updateProduct(Product product) {
        Product updated = productDAO.updateProductDAO(product);
        if (updated == null) {
            throw new IllegalArgumentException("The product could not be updated!");
        }
        return updated;
    }

    /**
     * Delete Product.
     * @param product
     * @return
     */
    public Product deleteProduct(Product product) {
        Product deleted = productDAO.deleteProductDAO(product);
        if (deleted == null) {
            throw new IllegalArgumentException("The product could not be deleted!");
        }
        return deleted;
    }
}
