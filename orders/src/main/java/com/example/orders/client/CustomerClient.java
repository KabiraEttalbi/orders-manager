package com.example.orders.client;

import com.example.orders.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer", url = "http://localhost:8081")
public interface CustomerClient {
    @GetMapping("/api/customers/{id}")
    CustomerDto getCustomerById(@PathVariable Long id);
}
