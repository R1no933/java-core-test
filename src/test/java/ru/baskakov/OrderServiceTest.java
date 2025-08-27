package ru.baskakov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    // Создаем моки для сервиса и репозитория
    private OrderService orderService;
    private OrderRepository orderRepository;

    // Инициализируем моки перед каждым тестом, чтобы убрать дублирование кода
    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }


    // Тесты для метода public String processOrder(Order order)

    // Тест положительного сценария
    @Test
    void shouldReturnSuccessMessageWhenProcessSuccess() {
        //Arrange: Подготовка данных для теста
        Order order = TestHelper.createCorrectOrder();
        when(orderRepository.saveOrder(order)).thenReturn(1);

        //Act: вызываем метод processOrder для тестирования положительного сценария
        String message = orderService.processOrder(order);

        //Assert: проверяем что возвращает ожидаемое сообщение
        assertEquals("Order processed successfully", message);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    // Тест негативного сценария
    @Test
    void shouldReturnFailureMessageWhenProcessFailure() {
        //Arrange: Подготовка данных для теста
        Order order = TestHelper.createIncorrectOrder();
        when(orderRepository.saveOrder(order)).thenReturn(0);

        //Act: вызываем метод processOrder для тестирования негативного сценария
        String message = orderService.processOrder(order);

        //Assert: проверяем что возвращает ожидаемое сообщение
        assertEquals("Order processing failed", message);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    // Тест на выбрасывание исключения
    @Test
    void shouldReturnExceptionProcessFailure() {
        //Arrange: Подготовка данных для теста
        Order order = TestHelper.createCorrectOrder();
        when(orderRepository.saveOrder(order)).thenThrow(new RuntimeException("Failure with database"));

        //Act:
        Exception exception = assertThrows(RuntimeException.class, () ->
                orderService.processOrder(order));

        //Assert:
        assertEquals("Failure with database", exception.getMessage());
        verify(orderRepository, times(1)).saveOrder(order);
    }

    // Тесты для метода public double calculateTotal(int id)

    // Тест положительного сценария
    @Test
    void shouldReturnCorrectTotalPrice() {
        //Arrange: Подготовка данных для теста
        Order order = TestHelper.createCorrectOrder();
        when(orderRepository.getOrderById(order.getId())).thenReturn(Optional.of(order));

        //Act:
        double totalPrice = orderService.calculateTotal(1);

        //Assert:
        assertEquals(655.5, totalPrice);
        verify(orderRepository, times(1)).getOrderById(1);
    }

    // Тест негативного сценария
    @Test
    void shouldReturnZeroIncorrectOrderForTotalPrice() {
        //Arrange: Подготовка данных для теста
        Order order = TestHelper.createIncorrectOrder();
        when(orderRepository.getOrderById(order.getId())).thenReturn(Optional.empty());

        //Act:
        double totalPrice = orderService.calculateTotal(0);

        //Assert:
        assertEquals(0.0, totalPrice);
        verify(orderRepository, times(1)).getOrderById(order.getId());
    }

    // Тест на выбрасывание исключения
    @Test
    void shouldReturnExceptionForTotalPrice() {
        //Arrange:
        Order order = TestHelper.createIncorrectOrder();
        when(orderRepository.getOrderById(order.getId())).thenThrow(new RuntimeException("Failure with database"));

        //Act:
        Exception exception = assertThrows(RuntimeException.class, () ->
                orderService.calculateTotal(0));

        //Assert:
        assertEquals("Failure with database", exception.getMessage());
        verify(orderRepository, times(1)).getOrderById(order.getId());
    }
}