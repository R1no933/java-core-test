package ru.baskakov;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        return orderRepository.saveOrder(order) != 0 ? "Order processed successfully" : "Order processing failed";
    }

    public double calculateTotal(int id) {
        return orderRepository.getOrderById(id).isPresent() ?
                orderRepository.getOrderById(id).get().getTotalPrice() : 0;
    }
}
