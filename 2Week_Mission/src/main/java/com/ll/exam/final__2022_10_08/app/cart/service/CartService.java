package com.ll.exam.final__2022_10_08.app.cart.service;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.repository.CartRepository;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;

    @Transactional
    public CartItem addItem(Member member, Product product) {
        CartItem oldCartItem = cartRepository.findByMemberIdAndProductId(member.getId(), product.getId()).orElse(null);

        if (oldCartItem != null) {
            return oldCartItem;
        }
        CartItem cartItem = CartItem.builder()
                .member(member)
                .product(product)
                .build();
        cartRepository.save(cartItem);
        return cartItem;
    }

    @Transactional
    public boolean removeItem(Member member, Product product) {
        CartItem oldCartItem = cartRepository.findByMemberIdAndProductId(member.getId(), product.getId()).orElse(null);
        if (oldCartItem != null) {
            cartRepository.delete(oldCartItem);
            return true;
        }
        return false;
    }

    public boolean hasItem(Member member, Product product) {
        return cartRepository.existsByMemberIdAndProductId(member.getId(), product.getId());
    }

    public List<CartItem> getItemsByBuyer(Member member) {
        return cartRepository.findAllByMemberId(member.getId());
    }

    @Transactional
    public void removeItem(CartItem cartItem) {
        cartRepository.delete(cartItem);
    }

    @Transactional
    public void removeItem(
            Member member,
            Long productId
    ) {
        Product product = new Product(productId);
        removeItem(member, product);
    }

    public Optional<CartItem> findItemById(long id) {
        return cartRepository.findById(id);
    }

    public boolean actorCanDelete(Member member, CartItem cartItem) {
        return member.getId().equals(cartItem.getMember().getId());
    }
}
