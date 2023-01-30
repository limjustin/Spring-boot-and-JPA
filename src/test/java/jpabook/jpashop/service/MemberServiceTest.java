package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
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
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    /**
     * INSERT 쿼리가 안 보였던 이유 : @Transactional 어노테이션의 존재
     * Commit 단계가 진행되어야 INSERT 쿼리가 나가는데, 트랜잭션 안에서 그냥 Rollback 해버리니까 INSERT 쿼리가 없었던 것임!
     * 스프링이 그냥 Rollback 해버리니까, JPA 는 INSERT 쿼리를 날리지 않는다! (영속성 컨텍스트를 flush 하지 않기 때문)
     * em.flush() : 중간에 넣어주면 영속성 컨텍스트에 있는 변경 내용을 데이터베이스에 반영하는 것! Commit 해준다는 것이지! INSERT 쿼리 날라가고!
     * 테스트는 반복적으로 진행되니까, 기본적으로 Transactional 어노테이션이 필요! DB 안에 데이터가 남아있으면 안 되니까!
     */
    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("LIM");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    /**
     * 예외가 catch 되서 나간 친구가 IllegalStateException 이라면! 정상적으로 테스트가 진행되었다는 뜻!
     */
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("KIM");

        Member member2 = new Member();
        member2.setName("KIM");

        // when
        memberService.join(member1);
        memberService.join(member2);  // 여기서 IllegalStateException 잡혀서 밖으로 던져야 해!

        // then
        fail("예외가 발생해야 한다.");  // 여기까지 오면 안 되는 거야..
    }
}