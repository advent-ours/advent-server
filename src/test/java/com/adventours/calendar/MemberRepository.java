package com.adventours.calendar;

import java.util.Optional;

interface MemberRepository {
    Optional<Member> findByProviderAndProviderId(OAuthProvider provider, String s);

    Member save(Member member);
}
