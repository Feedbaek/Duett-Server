package Dino.Duett.domain.tag.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum TagType {
    MUSIC("음악", Arrays.asList(
            "팝", "힙합", "댄스", "Kpop", "록", "R&B", "발라드",
            "일렉트로닉", "클래식", "재즈", "메탈", "인디", "Jpop",
            "포크", "컨트리", "소울", "블루스", "라틴", "레게톤",
            "EDM", "트랜스", "트로트", "Emo", "Funk", "디스코",
            "파티", "로파이", "딥하우스", "얼터너티브", "보사노바",
            "뉴에이지", "캐리비안", "트로피칼", "메탈코어", "누메탈",
            "아프로", "애니메이션", "앰비언트", "연주곡", "브릿팝")),
    HOBBY("취미", Arrays.asList(
            "음악", "뮤직페스티벌", "콘서트", "음반수집", "악기 연주",
            "유튜브 시청", "TV 시청", "영화", "게임하기", "드라이빙",
            "걷기", "조깅", "러닝", "자전거", "헬스", "요가", "축구", "농구",
            "배드민턴", "테니스", "골프", "스키", "등산", "낚시", "캠핑", "클럽",
            "공연", "경기관람", "박물관", "전시회", "공원가기", "요리", "독서",
            "글쓰기", "그림 그리기", "댄스", "바다가기", "뜨개질", "바느질", "자수",
            "탁구", "스케이트", "보드", "배구", "복싱", "야구", "봉사활동"));
    private final String title;
    private final List<String> names;
}
