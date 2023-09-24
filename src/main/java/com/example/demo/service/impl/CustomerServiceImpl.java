package com.example.demo.service.impl;

import com.example.demo.convert.OrderConverter;
import com.example.demo.entities.*;
import com.example.demo.entities.MenuItem;
import com.example.demo.entities.Queue;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.OrderDto;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.RequestOrderItemDto;
import com.example.demo.repository.*;
import com.example.demo.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final ShopRepository shopRepository;
    private final ShopOrderRepository shopOrderRepository;
    private final CustomerQueueRepository customerQueueRepository;
    private final QueueRepository queueRepository;
    private final CustomerRepository customerRepository;
    private final OrderConverter orderConverter;

    @Transactional
    public OrderDto createOrder(String username, Integer shopId, List<RequestOrderItemDto> requestOrderItems) {
        Customer customer = customerRepository.findCustomerByShopUser(username);
        if (customer == null) {
            throw new BusinessException("Customer not found with username " + username);
        }
        Optional<Shop> shop = shopRepository.findById(shopId);
        if (!shop.isPresent()) {
            throw new BusinessException("Shop not found with shop id " + shopId);
        }
        if (CollectionUtils.isEmpty(requestOrderItems)) {
            throw new BusinessException("Request order items are empty");
        }
        List<Integer> menuItemIds = shop.get().getMenuItems().stream().map(MenuItem::getId).collect(Collectors.toList());
        List<Integer> menuItemsNotFounds = requestOrderItems.stream().map(RequestOrderItemDto::getMenuItemId).filter(number -> !menuItemIds.contains(number)).collect(Collectors.toList());
        if (menuItemsNotFounds.size() > 0) {
            throw new BusinessException(String.format("Shop id %d can not found menu item ids %s", shopId, menuItemsNotFounds.toString()));
        }
        Map<Integer, Integer> menuItemPrices = shop.get().getMenuItems().stream().collect(
                Collectors.toMap(MenuItem::getId, MenuItem::getPrice));
        CustomerOrder customerOrder = createCustomerOrder(customer.getId(), shopId, requestOrderItems, menuItemPrices);

        createCustomerQueue(customer.getId(), shop, customerOrder);

        OrderDto orderDto = orderConverter.toOrderDto(customerOrder);
        orderDto.setWaitingTimes(shop.get().getShopQueues().stream().mapToInt(Queue::getCurrentCustomer).sum()
                * shop.get().getAverageWaitTime());
        return orderDto;
    }

    private void createCustomerQueue(Integer customerId, Optional<Shop> shop, CustomerOrder customerOrder) {
        Queue shopQueue = shop.get().getShopQueues().stream().min(Comparator.comparing(Queue::getCurrentCustomer)).get();
        shopQueue.setCurrentCustomer(shopQueue.getCurrentCustomer() + 1);
        queueRepository.save(shopQueue);

        customerQueueRepository.save(CustomerQueue.builder().customerId(customerId).queueId(shopQueue.getId()).orderId(customerOrder.getId()).orderNumber(shopQueue.getCurrentCustomer()).build());
    }

    private CustomerOrder createCustomerOrder(Integer customerId, Integer shopId,
                                              List<RequestOrderItemDto> requestOrderItems,
                                              Map<Integer, Integer> menuItemPrices) {

        CustomerOrder customerOrder = CustomerOrder.builder().customerId(customerId).shopId(shopId)
                .totalAmount(0L).orderItems(new ArrayList<>()).status(OrderStatus.IN_QUEUE).build();

        requestOrderItems.stream().forEach(orderItem -> {
            customerOrder.addOrderItem(OrderItem.builder()
                    .menuItemId(orderItem.getMenuItemId())
                    .price(menuItemPrices.get(orderItem.getMenuItemId())
                            * orderItem.getQuantity())
                    .build());
        });
        shopOrderRepository.save(customerOrder);
        return customerOrder;
    }
}
