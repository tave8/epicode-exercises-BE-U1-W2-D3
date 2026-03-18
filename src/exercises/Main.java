package exercises;

import entities.Customer;
import entities.Order;
import entities.Product;
import enums.CustomerTier;
import enums.OrderStatus;
import enums.ProductCategory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {
    static void main(String[] args) {
        // SAMPLE DATA
        // TODO 3: cannot use raw List?
        Map<String, List> sampleData = getSampleData();

        // TODO 2: fix "unchecked assignment"
        List<Product> products = sampleData.get("products");
        List<Order> orders = sampleData.get("orders");
        List<Customer> customers = sampleData.get("customers");

        // exercise 1
        List<Product> expensiveBooks = getExpensiveBooks(products);
        // exercise 2
        List<Order> ordersWithBabyProducts = getOrdersWithBabyProducts(orders);
        // exercise 3
        List<Product> discountedBoyProducts = getDiscountedBoyProducts(products);
        // exercise 4
        List<Product> productsBetweenDateRangeOfTier2Customers = getProductsBetweenDateRangeOfTier2Customers(orders);

        System.out.println("---- EXPENSIVE BOOKS -----");
        for (Product book : expensiveBooks) {
            System.out.println(book);
        }

        System.out.println();
        System.out.println("---- ORDERS WITH BABY PRODUCTS");
        for (Order order : ordersWithBabyProducts) {
            System.out.println(order);
        }

        System.out.println();
        System.out.println("---- DISCOUNTED BOY PRODUCTS ");
        for (Product product : discountedBoyProducts) {
            System.out.println(product);
        }

        System.out.println();
        System.out.println("---- PRODUCTS ORDERED BETWEEN DATE RANGE, ORDERED BY TIER 2 CUSTOMERS");
        System.out.println(productsBetweenDateRangeOfTier2Customers);
    }


    /**
     * Exercise 4
     */
    static List<Product> getProductsBetweenDateRangeOfTier2Customers(List<Order> orders) {
        LocalDate startDate = LocalDate.of(2026, 3, 18);
        LocalDate endDate = LocalDate.of(2026, 5, 18);

        // predicates
        Predicate<Order> isOrderAfterStartDate = order -> order.getOrderDate().isAfter(startDate);
        Predicate<Order> isOrderBeforeEndDate = order -> order.getOrderDate().isBefore(endDate);
        Predicate<Order> isOrderInDateRange = isOrderAfterStartDate.and(isOrderBeforeEndDate);

        // streams
        Stream<Order> filteredOrders = orders.stream()
                .filter(order -> order.getCustomer().getTier().equals(CustomerTier.TWO))
                .filter(isOrderInDateRange);

        List<Order> orderList = filteredOrders.toList();

        // METHOD 1: for enhanced
        List<Product> allProducts = new ArrayList<>();
        for (Order order : orderList) {
            allProducts.addAll(order.getProducts());
        }

        return allProducts;

        //     METHOD 2:
        //     1 order -> N products
        // Stream<Product> productsOfOrders = ordersBetweenDateRange.flatMap(order -> order.getProducts().stream());
        //
        // return productsOfOrders.toList();
    }

    /**
     * Exercise 3
     */
    static List<Product> getDiscountedBoyProducts(List<Product> products) {
        return products.stream()
                .filter(product -> product.getCategory().equals(ProductCategory.BOY))
                .map(currProduct -> {
                    double discountedPrice = currProduct.getPrice() - (currProduct.getPrice() * 0.1);
                    // return a new product with same data 
                    // but discounted price 
                    return new Product(
                            currProduct.getId(),
                            currProduct.getName(),
                            discountedPrice,
                            currProduct.getCategory()
                    );
                }).toList();
    }

    /**
     * Exercise 2
     */
    static List<Order> getOrdersWithBabyProducts(List<Order> orders) {
        // predicates
        Predicate<Product> productIsBabyProduct = product -> product.getCategory().equals(ProductCategory.BABY);
        Predicate<Order> orderHasAtLeastOneBabyProduct = order -> order.getProducts().stream().anyMatch(productIsBabyProduct);

        return orders.stream()
                .filter(orderHasAtLeastOneBabyProduct)
                .map(currOrder -> {
                    Order newOrder = new Order(
                            currOrder.getId(),
                            currOrder.getCustomer(),
                            currOrder.getStatus(),
                            currOrder.getOrderDate(),
                            currOrder.getDeliveryDate()
                    );
                    // add products of current order, to products of new order
                    // add the product only if it's a baby product
                    currOrder.getProducts().forEach(product -> {
                        if (product.getCategory().equals(ProductCategory.BABY)) {
                            newOrder.addProduct(product);
                        }
                    });
                    return newOrder;
                }).toList();
    }

    /**
     * Exercise 1
     */
    static List<Product> getExpensiveBooks(List<Product> products) {
        Predicate<Product> isBook = product -> product.getCategory().equals(ProductCategory.BOOK);
        Predicate<Product> isExpensive = product -> product.getPrice() > 100;
        Predicate<Product> isExpensiveBook = isBook.and(isExpensive);
        return products.stream()
                .filter(isExpensiveBook)
                .toList();
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
        Customer customer3 = new Customer(3, "Giovanna", CustomerTier.TWO);
        Customer customer4 = new Customer(4, "Mariello", CustomerTier.TWO);

        // ***** ORDERS
        Order order1 = new Order(1, customer1, OrderStatus.DELIVERED);
        Order order2 = new Order(2, customer1, OrderStatus.SHIPPED);
        Order order3 = new Order(3, customer3, OrderStatus.SHIPPED);
        Order order4 = new Order(4, customer4, OrderStatus.SHIPPED);

        // ***** EDIT ENTITY RELATIONSHIPS
        order1.addProduct(product4);
        order1.addProduct(product3);
        order2.addProduct(product3);
        order2.addProduct(product4);

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
                customer2,
                customer3,
                customer4
        );

        // orders
        List<Order> orders = List.of(
                order1,
                order2,
                order3,
                order4
        );

        return Map.of(
                "products", products,
                "customers", customers,
                "orders", orders
        );
    }

}
