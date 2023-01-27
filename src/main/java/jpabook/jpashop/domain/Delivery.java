package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.EAGER)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)  // STRING 타입으로 꼭 써야 enum에 기록한대로 저장된다!
    private DeliveryStatus status;  // 배송 상태 [READY, COMP]
}
