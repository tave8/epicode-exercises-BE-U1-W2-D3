package exercises;

import entities.Customer;
import entities.Order;
import entities.Product;
import enums.CustomerTier;
import enums.OrderStatus;
import enums.ProductCategory;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {
    static void main(String[] args) {
        // SAMPLE DATA
        Map<String, List> sampleData = getSampleData();

        // TODO: fix "unchecked assignment"
        List<Product> products = sampleData.get("products");
        List<Order> orders = sampleData.get("orders");
        List<Customer> customers = sampleData.get("customers");

        // exercise 1
        List<Product> expensiveBooks = getExpensiveBooks(products);
        // exercise 2
        List<Order> ordersWithBabyProducts = getOrdersWithBabyProducts(orders);
        // exercise 3
        List<Product> discountedBoyProducts = getDiscountedBoyProducts(products);

        System.out.println("---- EXPENSIVE BOOKS -----");
        System.out.println(expensiveBooks);

        System.out.println("---- ORDERS WITH BABY PRODUCTS");
        System.out.println(ordersWithBabyProducts);

        System.out.println("---- DISCOUNTED BOY PRODUCTS ");
        System.out.println(discountedBoyProducts);
    }

    static List<Product> getDiscountedBoyProducts(List<Product> products) {
        Stream<Product> productsStream = products.stream();
        Stream<Product> boysProducts = productsStream.filter(product -> product.getCategory().equals(ProductCategory.BOY));
        Stream<Product> discountedBoysProducts = boysProducts.map(currProduct -> {
            double discountedPrice = currProduct.getPrice() - (currProduct.getPrice() * 0.1);
            // return a new product with same data 
            // but discounted price 
            return new Product(
                    currProduct.getId(),
                    currProduct.getName(),
                    discountedPrice,
                    currProduct.getCategory()
            );
        });
        return discountedBoysProducts.toList();
    }

    static List<Order> getOrdersWithBabyProducts(List<Order> orders) {
        Stream<Order> ordersStream = orders.stream();
        Predicate<Product> productIsBabyProduct = product -> product.getCategory().equals(ProductCategory.BABY);
        Predicate<Order> orderHasAtLeastOneBabyProduct = order -> order.getProducts().stream().anyMatch(productIsBabyProduct);
        Stream<Order> ordersWithAtLeastOneBabyProduct = ordersStream.filter(orderHasAtLeastOneBabyProduct);
        // TODO: 
        //      A) for every order that has at least one baby product: all products 
        //      B) for every order that has at least one baby product: only baby products 

        // Stream<Order> ordersWithOnlyBabyProducts = ordersWithAtLeastOneBabyProduct.map(order -> {

        // });
        return ordersWithAtLeastOneBabyProduct.toList();
    }

    static List<Product> getExpensiveBooks(List<Product> products) {
        Stream<Product> productsStream = products.stream();
        Stream<Product> books = productsStream.filter(product -> product.getCategory().equals(ProductCategory.BOOK));
        Stream<Product> expensiveBooks = books.filter(book -> book.getPrice() > 100);
        return expensiveBooks.toList();
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
        // ***** PRODUCTS
        Product product1 = new Product(1, "book1", 12.34, ProductCategory.BOOK);
        Product product2 = new Product(2, "book2", 12.34, ProductCategory.BOOK);
        Product product3 = new Product(3, "book3", 102.34, ProductCategory.BOOK);
        Product product4 = new Product(4, "baby1", 23, ProductCategory.BABY);
        Product product5 = new Product(5, "boy1", 100, ProductCategory.BOY);

        // ***** CUSTOMERS
        Customer customer1 = new Customer(1, "Giuseppe", CustomerTier.ONE);
        Customer customer2 = new Customer(2, "Maria", CustomerTier.ONE);

        // ***** ORDERS
        Order order1 = new Order(1, customer1, OrderStatus.DELIVERED);
        Order order2 = new Order(2, customer1, OrderStatus.SHIPPED);

        // ***** EDIT ENTITY RELATIONSHIPS
        order1.addProduct(product4);

        // products
        List<Product> products = List.of(
                product1,
                product2,
                product3,
                product4,
                product5
        );

        // customers
        List<Customer> customers = List.of(
                customer1,
                customer2
        );

        // orders
        List<Order> orders = List.of(
                order1,
                order2
        );

        return Map.of(
                "products", products,
                "customers", customers,
                "orders", orders
        );
    }

}
