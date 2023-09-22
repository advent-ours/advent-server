package com.adventours.calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberControllerTest {

    private MemberService memberService;

    @Test
    @DisplayName("카카오 로그인 성공")
    void login_kakao() {
        final OAuthProvider provider = OAuthProvider.KAKAO;
        final String token = "loginToken";
        final LoginRequest request = new LoginRequest(token);
        final LoginResponse response = memberService.login(provider, request);
        assertThat(response.isNewMember).isTrue();
    }

    private class MemberService {
        private final Map<OAuthProvider, OAuthRequestPort> oAuthRequestPortMap;
        private final MemberRepository memberRepository;

        public MemberService(final Map<OAuthProvider, OAuthRequestPort> oAuthRequestPortMap, final MemberRepository memberRepository) {
            this.oAuthRequestPortMap = oAuthRequestPortMap;
            this.memberRepository = memberRepository;
        }

        public LoginResponse login(final OAuthProvider provider, final LoginRequest request) {
            OAuthRequestPort oAuthRequestPort = getProperProviderPort(provider);
            OAuthUserInformation userInformation = oAuthRequestPort.requestUserInformation(request.token());
            Optional<Member> optionalMember = memberRepository.findByProviderAndProviderId(provider, userInformation.providerId());
            if (optionalMember.isPresent()) {
                return new LoginResponse(optionalMember.get().getId(), false);
            } else {
                final Member member = memberRepository.save(new Member());
                return new LoginResponse(member.getId(), true);
            }

        }

        private OAuthRequestPort getProperProviderPort(final OAuthProvider provider) {
            return oAuthRequestPortMap.get(provider);
        }
    }

    private enum OAuthProvider {
        KAKAO
    }

    private record LoginRequest(String token) {
    }

    private interface OAuthRequestPort {
        OAuthUserInformation requestUserInformation(String token);
    }

    public class KakaoOAuthRequestAdapter implements OAuthRequestPort {
        @Override
        public OAuthUserInformation requestUserInformation(final String token) {
            throw new UnsupportedOperationException("KakaoOAuthRequestAdapter#requestUserInformation not implemented yet.")
        }
    }

    private interface MemberRepository {
        Optional<Member> findByProviderAndProviderId(OAuthProvider provider, String s);

        Member save(Member member);
    }

    private record OAuthUserInformation(String providerId, String initialProfileImage) {
    }

    private class Member {
        private Long id;
        public Long getId() {
            return id;
        }
    }

    private record LoginResponse(long memberId, boolean isNewMember) {
    }
}
