package Dino.Duett.domain.search.service;

import Dino.Duett.config.EnvBean;
import Dino.Duett.domain.search.dto.ThumbnailResponse;
import Dino.Duett.domain.search.dto.VideoResponse;
import Dino.Duett.domain.search.exception.YoutubeException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static Dino.Duett.global.enums.LimitConstants.YOUTUBE_SEARCH_LIMIT;

@Slf4j(topic = "SearchService")
@Service
@RequiredArgsConstructor
public class SearchService {
    private final EnvBean envBean;
    private final YouTube youtube;
    private int youtubeKeyCurrentIndex = 0;

    /** @param index 유튜브 API 키 인덱스
     * @return 유튜브 API 키
     */
    private String getYoutubeApiKey(final int index) throws IllegalArgumentException {
        return switch (index) {
            case 0 -> envBean.getYoutubeApiKey1();
            case 1 -> envBean.getYoutubeApiKey2();
            case 2 -> envBean.getYoutubeApiKey3();
            case 3 -> envBean.getYoutubeApiKey4();
            case 4 -> envBean.getYoutubeApiKey5();
            case 5 -> envBean.getYoutubeApiKey6();
            case 6 -> envBean.getYoutubeApiKey7();
            case 7 -> envBean.getYoutubeApiKey8();
            case 8 -> envBean.getYoutubeApiKey9();
            case 9 -> envBean.getYoutubeApiKey10();
            case 10 -> envBean.getYoutubeApiKey11();
            case 11 -> envBean.getYoutubeApiKey12();
            case 12 -> envBean.getYoutubeApiKey13();
            case 13 -> envBean.getYoutubeApiKey14();
            default -> throw new IllegalArgumentException("Youtube API 허용 인덱스 초과");
        };
    }

    /** @param search 유튜브 검색 요청 객체
     * @return 유튜브 동영상 검색 결과 목록
     */
    private List<VideoResponse> requestYoutubeSearching(final YouTube.Search.List search) throws IOException {
        for (int i = 0; i < envBean.getYoutubeKeyMaxSize(); i++) {
            try {
                int idx = (youtubeKeyCurrentIndex + i) % envBean.getYoutubeKeyMaxSize();
                search.setKey(getYoutubeApiKey(idx));
                List<SearchResult> searchResult = search.execute().getItems();
                youtubeKeyCurrentIndex = idx;
                return searchResult.stream()
                        .map(this::convertToDto)
                        .toList();
            } catch (GoogleJsonResponseException e) {
                if (e.getStatusCode() == 403) {
                    log.error( "[GoogleJsonResponseException] " + (youtubeKeyCurrentIndex+1)+ "번째 YouTube API Key가 만료되었습니다.");
                } else {
                    log.error( "[IllegalArgumentException] " + e.getMessage());
                    throw new YoutubeException.YoutubeApiRequestFailed();
                }
            } catch (IllegalArgumentException e) {
                log.error( "[IllegalArgumentException] " + e.getMessage());
                throw new YoutubeException.YoutubeApiRequestFailed();
            }
        }
        throw new YoutubeException.YoutubeApiRequestLimitExceeded();
    }

    /** @param q 검색어
     * @param maxResults 최대 검색 결과 개수
     * @return 검색 결과
     */
    public List<VideoResponse> searchVideos(final String q, Long maxResults){
        try {
            YouTube.Search.List search = youtube.search().list(Collections.singletonList("id,snippet"))
                    .setQ(q)
                    .setType(Collections.singletonList("video"))
                    .setMaxResults(Math.min(maxResults, YOUTUBE_SEARCH_LIMIT.getLimit()));

            return requestYoutubeSearching(search);
        }
        catch (IOException e) {
            throw new YoutubeException.YoutubeApiRequestFailed();
        }
    }

    private VideoResponse convertToDto(SearchResult searchResult) {
        SearchResultSnippet searchResultSnippet= searchResult.getSnippet();

        return VideoResponse.builder()
                .videoId(searchResult.getId().getVideoId())
                .title(searchResultSnippet.getTitle())
                .thumbnail(ThumbnailResponse.of(searchResultSnippet.getThumbnails().getHigh()))
                .ChannelTitle(searchResultSnippet.getChannelTitle())
                .build();
    }
}
