package ru.baskakov;

public class TestHelper {
    public static Order createCorrectOrder() {
        return new Order(1, "Milk", 10, 65.55);
    }

    public static Order createIncorrectOrder() {
        return new Order(0, "Milk", 0, 65.55);
    }
}
