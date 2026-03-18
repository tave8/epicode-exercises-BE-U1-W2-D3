package exercises;

import entities.Customer;
import entities.Order;
import entities.Product;
import enums.CustomerTier;
import enums.OrderStatus;
import enums.ProductCategory;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
    static void main(String[] args) {
        // SAMPLE DATA
        Map<String, List> sampleData = getSampleData();

        // TODO: fix "unchecked assignment"
        List<Product> products = sampleData.get("products");
        List<Order> orders = sampleData.get("orders");
        List<Customer> customers = sampleData.get("customers");

        // ****** EXERCISE 1:
        //      PRODUCT LIST: BOOK AND PRODUCT PRICE > 100
        List<Product> expensiveBooks = getExpensiveBooks(products);
        System.out.println(expensiveBooks);
    }


    /**
     *
     * return {
     * products: List
     * customers: List
     * orders: List
     * }
     */
    static Map<String, List> getSampleData() {
        // products
        List<Product> products = List.of(
                new Product(1, "book1", 12.34, ProductCategory.BOOK),
                new Product(2, "book2", 12.34, ProductCategory.BOOK),
                new Product(3, "book3", 102.34, ProductCategory.BOOK)
        );

        // customers
        List<Customer> customers = List.of(
                new Customer(1, "Giuseppe", CustomerTier.ONE)
        );

        // orders
        List<Order> orders = List.of(
                new Order(1, customers.get(0), OrderStatus.DELIVERED)
        );

        return Map.of(
                "products", products,
                "customers", customers,
                "orders", orders
        );
    }

    static List<Product> getExpensiveBooks(List<Product> products) {
        Stream<Product> productsStream = products.stream();
        Stream<Product> books = productsStream.filter(product -> product.getCategory().equals(ProductCategory.BOOK));
        Stream<Product> expensiveBooks = books.filter(book -> book.getPrice() > 100);
        return expensiveBooks.toList();
    }
}
