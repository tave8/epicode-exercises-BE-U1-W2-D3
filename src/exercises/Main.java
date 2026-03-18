package exercises;

import entities.Product;
import enums.ProductCategory;

import java.util.List;

public class Main {
    static void main(String[] args) {
        // SAMPLE DATA
        List<Product> products = List.of(
                new Product(1, "book1", 12.34, ProductCategory.BOOK)
        );
        System.out.println(products);

        // ****** EXERCISE 1:
        //      PRODUCT LIST: BOOK AND PRODUCT PRICE > 100
        // List<Product> expensiveBooks = getExpensiveBooks();
    }

    // static List<Product> getExpensiveBooks() {
    //
    // }
}
