package ru.baskakov;

import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        return orderRepository.saveOrder(order) != 0 ? "Order processed successfully" : "Order processing failed";
    }

    public double calculateTotal(int id) {
        Optional<Order> optOrder = orderRepository.getOrderById(id);
        return optOrder.map(Order::getTotalPrice).orElse(0.0);
    }
}
