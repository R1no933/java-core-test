package ru.baskakov;

public class Main {
    public static void main(String[] args) {
        Order order1 = new Order(1, "Grocess", 10, 310.50);
        Order order2 = new Order(0, "Griss", 0, 310.50);

        System.out.println(order1.getProductName());
        System.out.println(order1.getId());
        System.out.println(order1.getTotalPrice());

        System.out.println(order2.getProductName());
        System.out.println(order2.getId());
        System.out.println(order2.getTotalPrice());

    }
}