package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")  // 하나의 회원이 여러 개의 상품을 주문하기 때문 & mappedBy "나는 (order 클래스 내) member 필드에 의해 매핑된거야"
    private List<Order> orders = new ArrayList<>();
}