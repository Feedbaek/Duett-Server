package Dino.Duett.domain.profile.dto.response;

public class LikedProfileResponse {
    private final Long profileId;
    private final String username;
    private final String nickname;
    private final String profileImageUrl;

    public LikedProfileResponse(Long profileId, String username, String nickname, String profileImageUrl) {
        this.profileId = profileId;
        this.username = username;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public Long getProfileId() {
        return profileId;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
