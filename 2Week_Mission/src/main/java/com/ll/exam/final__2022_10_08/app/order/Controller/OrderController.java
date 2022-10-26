package com.ll.exam.final__2022_10_08.app.order.Controller;

import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.order.entiry.Order;
import com.ll.exam.final__2022_10_08.app.order.exception.ActorCanNotSeeOrderException;
import com.ll.exam.final__2022_10_08.app.order.service.OrderService;
import com.ll.exam.final__2022_10_08.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, Model model) {
        Order order = orderService.findForPrintById(id).get();

        Member actor = memberContext.getMember();

        if (orderService.actorCanSee(actor, order) == false) {
            throw new ActorCanNotSeeOrderException();
        }

        model.addAttribute("order", order);

        return "order/detail";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String makeOrder(@AuthenticationPrincipal MemberContext memberContext) {
        Member member = memberContext.getMember();
        Order order = orderService.createFromCart(member);

        return "redirect:/order/%d".formatted(order.getId());
    }

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showOrderList(@AuthenticationPrincipal MemberContext memberContext, Model model){
        Member member = memberContext.getMember();
        List<Order> orders = orderService.findAllByMemberId(member.getId());
        model.addAttribute("orders", orders);

        return "order/list";
    }
}
