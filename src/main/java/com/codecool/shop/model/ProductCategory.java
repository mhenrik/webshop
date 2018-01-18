package com.codecool.shop.model;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.JDBC.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.JDBC.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.JDBC.SupplierDaoJDBC;
import com.codecool.shop.exception.ConnectToStorageFailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.sql.SQLException;
import java.util.*;


public class ProductCategory extends BaseModel {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategory.class);
    private ArrayList<Product> products;

    /**
     * Constructor
     * @param   name    (required) name of the product category.
     * @param   description (required) description of the product category
     */
    public ProductCategory(String name, String description) {

        super(name);
        this.products = new ArrayList<>();
        this.description = description;
    }

    /**
     * Setter for products.
     * @param products
     */
    public void setProducts(ArrayList<Product> products) {

        this.products = products;
    }

    /**
     * Adds products.
     * @param product
     */
    public void addProduct(Product product) {
        this.products.add(product);
        logger.info("{} is added", product.getName());
    }

    /**
     * Gets all suppliers.
     * @return Set of suppliers
     * @throws SQLException
     */
    public Set<Supplier> getSuppliers() throws ConnectToStorageFailed{
        Set<Supplier> suppliers = new HashSet<>();
        List<Product> products = this.getProducts();
        List<Integer> supplierIds = new ArrayList<>();
        SupplierDaoJDBC supplierDaoJDBC = SupplierDaoJDBC.getInstance();
        for (Product product: products) {
            supplierIds.add(product.getSupplier().getId());
            if(Collections.frequency(supplierIds, product.getSupplier().getId()) == 1){
                suppliers.add(supplierDaoJDBC.find(product.getSupplier().getId()));
            }
        }
        for (Supplier supplier: suppliers) {
            System.out.println(supplier);
        }
        logger.info("Suppliers from getSuppliers method returns: {}", suppliers);
        return suppliers;
    }

    /**
     * Gets all products
     * @return List of products
     * @throws SQLException
     */
    public List<Product> getProducts() throws ConnectToStorageFailed{
        ProductDaoJDBC productDaoJDBC = ProductDaoJDBC.getInstance();
        logger.info("Products from getProducts returns: {}", productDaoJDBC.getBy(this));   
        return productDaoJDBC.getBy(this);
    }

    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description);
    }

}