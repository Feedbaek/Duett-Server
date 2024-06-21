package Dino.Duett.utils;

import Dino.Duett.config.login.jwt.JwtTokenProvider;
import Dino.Duett.config.login.jwt.JwtTokenType;
import Dino.Duett.domain.member.entity.Member;
import Dino.Duett.domain.member.entity.Role;
import Dino.Duett.domain.member.enums.MemberState;
import Dino.Duett.domain.member.enums.RoleName;
import Dino.Duett.domain.profile.entity.Profile;
import Dino.Duett.domain.profile.enums.GenderType;
import Dino.Duett.domain.signup.dto.SignUpReq;
import Dino.Duett.domain.tag.entity.Tag;
import Dino.Duett.domain.tag.enums.TagType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * make-: 테스트용 객체 생성
 * create-: 테스트용 엔티티 생성
 * */
@Component
public class TestUtil {
    public static final String MEMBER_PHONE_NUMBER = "01012345678";
    public static final String MEMBER_KAKAO_ID = "kakaoId";
    public static final String MEMBER_NICKNAME = "nickname";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * 테스트용 회원가입 요청 생성
     * @return SignUpReq
     * */
    public static SignUpReq makeSignUpReq() {
        SignUpReq signUpReq = new SignUpReq();
        MockMultipartFile multipartFile = new MockMultipartFile("profileImage", "profileImage.jpg", "image/jpeg", "profileImage".getBytes());

        signUpReq.setPhoneNumber(MEMBER_PHONE_NUMBER);
        signUpReq.setVerificationCode("code");
        signUpReq.setNickname(MEMBER_NICKNAME);
        signUpReq.setKakaoId(MEMBER_KAKAO_ID);
        signUpReq.setSex("male");
        signUpReq.setBirth("1997-10-31");
        signUpReq.setLocation(new double[]{1.1, 2.2});
        signUpReq.setProfileImage(multipartFile);
        signUpReq.setComment("comment");

        return signUpReq;
    }

    public static InputStream makeImageFile() throws IOException {
        // 1x1 pixel image 생성
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, 0xFFFFFF); // Set the color to white

        // byte array 생성
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        // byte array -> InputStream 변환
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return new ByteArrayInputStream(imageBytes);
    }

    /**
     * 테스트용 멤버 생성. id 1의 role 사용해서 만듦. 멤버 id는 생성되지 않음.
     * @return Member
    * */
    public static Member createMember() {
        // 역할 생성
        Role role = Role.builder()
                .id(1L)
                .name(RoleName.USER.name())
                .build();

        return Member.builder()
                .phoneNumber(MEMBER_PHONE_NUMBER)
                .kakaoId(MEMBER_KAKAO_ID)
                .coin(0)
                .state(MemberState.ACTIVE)
                .role(role)
                .build();
    }

    /**
     * 테스트용 엑세스 토큰 생성
     * @param memberId 멤버 id
     * @return String
     * */
    public String createAccessToken(Long memberId) {
        return jwtTokenProvider.createToken(memberId, JwtTokenType.ACCESS_TOKEN);
    }

    /**
     * 테스트용 태그 생성
     * @return List<Tag>
     */

    public static List<Tag> createTags() {
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("음악1", TagType.MUSIC));
        tags.add(Tag.of("음악2", TagType.MUSIC));
        tags.add(Tag.of("음악3", TagType.MUSIC));
        tags.add(Tag.of("음악4", TagType.MUSIC));
        tags.add(Tag.of("음악5", TagType.MUSIC));
        tags.add(Tag.of("음악6", TagType.MUSIC));

        tags.add(Tag.of("취미1", TagType.HOBBY));
        tags.add(Tag.of("취미2", TagType.HOBBY));
        tags.add(Tag.of("취미3", TagType.HOBBY));
        tags.add(Tag.of("취미4", TagType.HOBBY));
        tags.add(Tag.of("취미5", TagType.HOBBY));
        tags.add(Tag.of("취미6", TagType.HOBBY));
        return tags;
    }

    /**
     * 테스트용 프로필 가진 멤버 생성
     * @return Member
     * */

    public static Member createMemberWithProfile() {
        Role role = Role.builder()
                .id(1L)
                .name(RoleName.USER.name())
                .build();
        Member member = Member.builder()
                .phoneNumber("010-1234-5678")
                .kakaoId("kakaoId")
                .coin(0)
                .state(MemberState.ACTIVE)
                .role(role)
                .profile(Profile.builder()
                        .gender(GenderType.MAN)
                        .birthDate("1999.01.01")
                        .build())
                .build();
        return member;
    }
}
