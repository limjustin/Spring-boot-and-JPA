package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }  // 저장을 하는 것은 커멘드 이팩트, 리턴 값을 거의 필요로하지 않기 때문에, id 정도는 나중에 필요하니까 return id 값 하는 것임!

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
