package com.adventours.calendar.member.persistence;

import com.adventours.calendar.member.domain.Member;
import com.adventours.calendar.member.domain.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndProviderId(OAuthProvider provider, String s);
}
