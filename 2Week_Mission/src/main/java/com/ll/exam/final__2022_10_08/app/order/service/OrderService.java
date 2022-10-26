package com.ll.exam.final__2022_10_08.app.order.service;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.member.service.MemberService;
import com.ll.exam.final__2022_10_08.app.order.entiry.Order;
import com.ll.exam.final__2022_10_08.app.order.entiry.OrderItem;
import com.ll.exam.final__2022_10_08.app.order.repository.OrderRepository;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final MemberService memberService;
    private final CartService cartService;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createFromCart(Member buyer) {
        List<CartItem> cartItems = cartService.getItemsByBuyer(buyer);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (product.isOrderable()) {
                orderItems.add(new OrderItem(product));
            }

            cartService.removeItem(cartItem);
        }

        return create(buyer, orderItems);
    }

    @Transactional
    public Order create(Member member, List<OrderItem> orderItems) {
        Order order = Order
                .builder()
                .member(member)
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return order;
    }

    @Transactional
    public void payByRestCashOnly(Order order) {
        Member buyer = order.getMember();

        long restCash = buyer.getRestCash();

        int payPrice = order.calculatePayPrice();

        if (payPrice > restCash) {
            throw new RuntimeException("예치금이 부족합니다.");
        }

        memberService.addCash(buyer, payPrice * -1, "주문결제__예치금결제");

        order.setPaymentDone();
        orderRepository.save(order);
    }

    @Transactional
    public void refund(Order order) {
        int payPrice = order.getPayPrice();
        memberService.addCash(order.getMember(), payPrice, "주문환불__예치금환불");

        order.setRefundDone();
        orderRepository.save(order);
    }

    public Optional<Order> findForPrintById(long id) {
        return findById(id);
    }

    private Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    public boolean actorCanSee(Member actor, Order order) {
        return actor.getId().equals(order.getMember().getId());
    }
}