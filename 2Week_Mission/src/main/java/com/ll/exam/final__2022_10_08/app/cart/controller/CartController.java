package com.ll.exam.final__2022_10_08.app.cart.controller;

import com.ll.exam.final__2022_10_08.app.cart.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cart.service.CartService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import com.ll.exam.final__2022_10_08.app.product.service.ProductService;
import com.ll.exam.final__2022_10_08.app.security.dto.MemberContext;
import com.ll.exam.final__2022_10_08.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showItems(@AuthenticationPrincipal MemberContext memberContext, Model model) {
        Member member = memberContext.getMember();

        List<CartItem> items = cartService.getItemsByBuyer(member);

        model.addAttribute("items", items);

        return "cart/items";
    }

    // Post 요청으로 수정 필요
    @GetMapping("/add/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String addItems(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long productId) {
        Member member = memberContext.getMember();
        Product product = productService.findById(productId).get();
        cartService.addItem(member, product);

        return "redirect:/product/list?msg=" + Ut.url.encode("장바구니에 추가되었습니다.");
    }

    @PostMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    public String removeItems(@AuthenticationPrincipal MemberContext memberContext, String ids) {
        Member member = memberContext.getMember();

        String[] idsArr = ids.split(",");

        Arrays.stream(idsArr)
                .mapToLong(Long::parseLong)
                .forEach(id -> {
                    CartItem cartItem = cartService.findItemById(id).orElse(null);

                    if (cartService.actorCanDelete(member, cartItem)) {
                        cartService.removeItem(cartItem);
                    }
                });

        return "redirect:/cart/list?msg=" + Ut.url.encode("%d건의 품목을 삭제하였습니다.".formatted(idsArr.length));
    }
}
