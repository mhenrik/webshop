package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.ShoppingCart;
import com.codecool.shop.model.Supplier;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.*;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        List<ProductCategory> categories = productCategoryDataStore.getAll();

        Map params = new HashMap<>();

        params.put("categories", categories);
        return new ModelAndView(params, "product/index");
    }

    public static String renderShoppingCart(Request req, Response res){
        ShoppingCart shoppingCart = ShoppingCart.getInstance();
        shoppingCart.addToCart(req.body());

        List<Product> products = new ArrayList<>();

        List<String> productList = shoppingCart.getProductsInCart();
        for (String prod: productList
             ) {
            String productId = prod.substring(8, prod.length() - 1);
            Integer productIdInt = Integer.parseInt(productId);
            ProductDaoMem productDaoMem = ProductDaoMem.getInstance();
            Product addedProduct = productDaoMem.find(productIdInt);
            products.add(addedProduct);

        }



        Integer quantity = shoppingCart.getCartSize();
        System.out.println(products);




        //System.out.println(quantityList.get(0));

        Map params = new HashMap<>();

        params.put("products", products);
        params.put("quantity", quantity);
        //params.put("price", addedProduct.getPrice());


        Map<String, List> quantityAndPrice = new HashMap<>();


      /*  quantityAndPrice.put("price", addedProduct.getPrice());
        quantityAndPrice.put("quantity", quantity.toString());
        quantityAndPrice.put("id", String.valueOf(addedProduct.getId()));*/

        Gson gson = new Gson();
        return gson.toJson(quantity);
        //return new ModelAndView(params, "product/index" );
    }

}
