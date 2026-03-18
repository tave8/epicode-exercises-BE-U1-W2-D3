package entities;

import enums.OrderStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private long id;
    private OrderStatus status;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private List<Product> products = new ArrayList<>();
    private Customer customer;

    public Order(long id, Customer customer, OrderStatus status, LocalDate orderDate, LocalDate deliveryDate) {
        this.id = id;
        this.customer = customer;
        this.setStatus(status);
        this.setOrderDate(orderDate);
        this.setDeliveryDate(deliveryDate);
    }

    public Order(long id, Customer customer, OrderStatus status) {
        LocalDate defaultOrderDate = LocalDate.now();
        LocalDate defaultDeliveryDate = defaultOrderDate.plusYears(1);
        this(id, customer, status, defaultOrderDate, defaultDeliveryDate);
    }

    public long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }


    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.getProducts().add(product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", products=" + products +
                ", customer=" + customer +
                '}';
    }
}
