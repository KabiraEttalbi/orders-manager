package com.example.orders.service;

import com.example.orders.client.CustomerClient;
import com.example.orders.client.ProductClient;
import com.example.orders.dto.OrderRequest;
import com.example.orders.dto.ProductDto;
import com.example.orders.model.Order;
import com.example.orders.model.OrderItem;
import com.example.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        // Vérifier si le client existe
        var customer = customerClient.getCustomerById(orderRequest.getCustomerId());
        
        // Calculer le montant total et créer les items de commande
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(itemRequest -> {
                    ProductDto product = productClient.getProductById(itemRequest.getProductId());
                    BigDecimal itemPrice = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
                    
                    return OrderItem.builder()
                            .productId(itemRequest.getProductId())
                            .quantity(itemRequest.getQuantity())
                            .price(product.getPrice())
                            .build();
                })
                .collect(Collectors.toList());
        
        // Calculer le montant total
        for (OrderItem item : orderItems) {
            totalAmount = totalAmount.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        
        // Créer la commande
        Order order = Order.builder()
                .customerId(orderRequest.getCustomerId())
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .status("PENDING")
                .build();
        
        Order savedOrder = orderRepository.save(order);
        
        // Associer les items à la commande
        orderItems.forEach(item -> item.setOrder(savedOrder));
        savedOrder.setItems(orderItems);
        
        return orderRepository.save(savedOrder);
    }
}
