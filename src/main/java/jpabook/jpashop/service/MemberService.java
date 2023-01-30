package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor  // final 붙은 애들 생성자 주입
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * Transactional default value : false (true 옵션이면 값의 변경이 안 되니까 따로 달아주기)
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);  // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();  // 이렇게 꺼내면 항상 값이 있다는 것을 보장!
        // 영속성 컨텍스트, id 값이 항상 생성이 되어있는 것을 보장
        // 아직 DB에 들어간 시점이 아니어도, id 값을 채워준다는 뜻
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {  // Exception Logic
            System.out.println("MemberService.validateDuplicateMember");
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }  // 리팩토링 : 굳이 이름으로 찾지 않아도 중복된 회원 이름을 가진 개수로 찾을 수 있음!
    }

    /**
     * 회원 조회
     * readonly=true 옵션을 포함하면 JPA 성능에서 최적화 가능 (읽기만 하면 되니까!)
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
