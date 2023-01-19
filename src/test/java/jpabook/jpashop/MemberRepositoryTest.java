package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("jaeyoung");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // then
        assertThat(savedId).isEqualTo(member.getId());
        assertThat(findMember).isEqualTo(member);

        // 같은 트랜잭션 안에서 저장하고 조회하면, 영속성 컨텍스트가 똑같으니까, 식별자가 같으면 똑같은 엔티티로 인식!
        System.out.println("findMember = " + findMember);
        System.out.println("member = " + member);
    }

}