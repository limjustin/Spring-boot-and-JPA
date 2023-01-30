package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ItemRepository itemRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = new Member();
        member.setName("LIM");
        member.setAddress(new Address("city", "street", "zipcode"));
        memberRepository.save(member);

        Book book = new Book();
        book.setName("JPA");
        book.setPrice(20000);
        book.setStockQuantity(10);
        itemRepository.save(book);

        // when
        Long getId = orderService.order(member.getId(), book.getId(), 3);

        // then
        Order getOrder = orderRepository.findOne(getId);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(member, getOrder.getMember());
        assertEquals(book.getStockQuantity(), 7);
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = new Member();
        member.setName("LIM");
        member.setAddress(new Address("city", "street", "zipcode"));
        memberRepository.save(member);

        Book book = new Book();
        book.setName("JPA");
        book.setPrice(20000);
        book.setStockQuantity(10);
        itemRepository.save(book);

        // when
        orderService.order(member.getId(), book.getId(), 15);

        // then
        fail("Test failed");
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = new Member();
        member.setName("LIM");
        member.setAddress(new Address("city", "street", "zipcode"));
        memberRepository.save(member);

        Book book = new Book();
        book.setName("JPA");
        book.setPrice(20000);
        book.setStockQuantity(10);
        itemRepository.save(book);

        Long getId = orderService.order(member.getId(), book.getId(), 3);

        // when
        orderService.cancelOrder(getId);

        // then
        Order getOrder = orderRepository.findOne(getId);
        assertEquals("상품 취소시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        System.out.println("book.getStockQuantity() = " + book.getStockQuantity());
        assertEquals(book.getStockQuantity(), 10);
    }
}