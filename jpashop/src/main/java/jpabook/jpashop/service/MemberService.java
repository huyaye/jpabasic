package jpabook.jpashop.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	//    @Autowired //테스트 케이스에 용의하다.
	//    public MemberService(MemberRepository memberRepository) {
	//        this.memberRepository = memberRepository;
	//    }

	/**
	 * 회원 가입
	 */
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	/**
	 * 회원 전체 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}

	/**
	* 회원 수정
	* JPA 변경 감지 사용
	*/
	@Transactional
	public void update(Long id, String name) {
		Member member = memberRepository.findOne(id);
		member.setName(name);
	}
}

