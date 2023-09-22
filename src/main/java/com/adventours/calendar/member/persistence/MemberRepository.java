package com.adventours.calendar.member.persistence;

import com.adventours.calendar.member.domain.Member;
import com.adventours.calendar.member.domain.OAuthProvider;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByProviderAndProviderId(OAuthProvider provider, String s);

    Member save(Member member);
}
