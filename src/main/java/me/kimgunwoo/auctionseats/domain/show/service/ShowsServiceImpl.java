package me.kimgunwoo.auctionseats.domain.show.service;

import static me.kimgunwoo.auctionseats.domain.admin.service.AdminServiceImpl.*;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsInfoCreateRequest;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsCategoryRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsImageRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsInfoRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsRepository;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsAuctionSeatInfoResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsCategoryGetResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsGetCursorResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsGetQueryResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsGetResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsInfoGetResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsSeatInfoResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.util.S3Uploader;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowsServiceImpl implements ShowsService {

    private final ShowsInfoRepository showsInfoRepository;

    private final ShowsImageRepository showsImageRepository;

    private final S3Uploader s3Uploader;

    private final ShowsCategoryRepository showsCategoryRepository;

    public final ShowsRepository showsRepository;

    private final StringRedisTemplate stringRedisTemplate;

    // 공연 생성
    @Override
    public Shows createShows(ShowsCreateRequest showsCreateRequest, Place place, ShowsInfo showsInfo) {
        Shows shows = showsCreateRequest.toEntity(place, showsInfo);

        return showsRepository.save(shows);
    }

    // 공연 정보 생성
    @Override
    public ShowsInfo createShowsInfo(ShowsInfoCreateRequest showsInfoCreateRequest) {
        ShowsInfo showsInfo = showsInfoCreateRequest.toEntity();

        return showsInfoRepository.save(showsInfo);
    }

    // 공연 이미지 생성
    @Override
    public List<ShowsImage> createShowsImage(List<MultipartFile> multipartFiles, ShowsInfo showsInfo) {
        List<String> fileUrl = s3Upload(multipartFiles, showsInfo.getId());

        List<ShowsImage> showsImageList = divideTypeShowsImageList(fileUrl, showsInfo);

        return showsImageRepository.saveAll(showsImageList);
    }

    // 공연 이미지 s3 업로드
    @Override
    public List<String> s3Upload(List<MultipartFile> multipartFiles, Long showsInfoId) {
        List<String> fileUrl = new ArrayList<>();

        String thumbnailFilePath = FILE_PATH + THUMBNAIL + showsInfoId;
        String generalFilePath = FILE_PATH + GENERAL + showsInfoId;

        MultipartFile thumbnailMultipartFile = multipartFiles.get(0);
        multipartFiles.remove(0);

        String thumbnailUrl = s3Uploader.uploadSingleFileToS3(thumbnailMultipartFile, thumbnailFilePath);
        fileUrl.add(thumbnailUrl);

        List<String> generalUrl = s3Uploader.uploadFileToS3(multipartFiles, generalFilePath);
        fileUrl.addAll(generalUrl);

        return fileUrl;
    }

    // 공연 이미지 타입 분리
    @Override
    public List<ShowsImage> divideTypeShowsImageList(List<String> fileKeyList, ShowsInfo showsInfo) {
        List<ShowsImage> returnShowsIamgeList = new ArrayList<>();
        for (String fileKey : fileKeyList) {
            ShowsImage showsImage =
                    ShowsImage
                            .builder()
                            .s3Key(fileKey)
                            .type(this.checkShowsType(fileKey))
                            .showsInfo(showsInfo)
                            .build();
            returnShowsIamgeList.add(showsImage);
        }
        return returnShowsIamgeList;
    }

    // 공연 이미지 타입 체크
    @Override
    public String checkShowsType(String type) {
        if (type.contains(THUMBNAIL)) {
            return "대표";
        }
        return "일반";
    }

    // 카테고리 생성 기타 입력시
    @Override
    public ShowsCategory createShowsCategory(String name) {
        ShowsCategory showsCategory = showsCategoryRepository.findByName(name)
                .orElse(
                        ShowsCategory
                                .builder()
                                .name(name)
                                .build());

        return showsCategoryRepository.save(showsCategory);
    }

    // 공연 전체 정보 조회
    @Override
    @Transactional(readOnly = true)
    public List<ShowsInfoGetResponse> getAllShowsInfo() {
        List<ShowsInfo> showsInfos = showsInfoRepository.findAll();
        return showsInfos.stream().map(ShowsInfoGetResponse::new).toList();
    }

    // 공연 단건 조회
    @Override
    @Transactional(readOnly = true)
    public ShowsGetResponse getShows(Long showsId) {
        Shows shows = findByShowsId(showsId);
        return new ShowsGetResponse(shows);
    }

    // 공연 정보 카테고리별 페이징 페이징 조회
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "showsGlobalCache", key = "{#cursorId ?: 'default', #categoryName}", cacheManager = "redisCacheManager")
    public ShowsGetCursorResponse getShowsWithCursor(Long cursorId, int size, String categoryName) {
        List<ShowsGetQueryResponse> showsGetQueryResponses =
                showsRepository.findAllByShowsAndCategoryName(
                        cursorId,
                        size,
                        categoryName
                );
        Long nextCursorId = -1L;
        if (!showsGetQueryResponses.isEmpty()) {
            int lastSize = showsGetQueryResponses.size() - 1;
            nextCursorId = showsGetQueryResponses.get(lastSize).getShowsId();
        }

        return new ShowsGetCursorResponse(showsGetQueryResponses, nextCursorId);
    }

    // 공연 카테고리 전체 조회
    @Override
    @Cacheable(value = "showsCategoryGlobalCache", cacheManager = "redisCacheManager")
    public List<ShowsCategoryGetResponse> getAllShowsCategory() {
        List<ShowsCategory> showsCategorieList = showsCategoryRepository.findAll();
        return showsCategorieList
                .stream()
                .map(category -> new ShowsCategoryGetResponse(category.getName()))
                .collect(
                        Collectors.toList()
                );
    }

    // 공연 정보 조회
    @Override
    public ShowsInfo findByShowsInfoId(Long showsInfoId) {
        return showsInfoRepository.findById(showsInfoId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_SHOWS_INFO));
    }

    // 공연 조회
    public Shows findByShowsId(Long showsId) {
        return showsRepository.findById(showsId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_SHOWS));
    }

    @Override
    public ShowsSeatInfoResponse findShowsSeatInfo(Long showsId) {
        return ShowsSeatInfoResponse.builder()
                .seatInfos(showsRepository.findShowsSeatInfo(showsId))
                .build();
    }

    @Override
    public ShowsAuctionSeatInfoResponse findShowsAuctionSeatInfo(Long scheduleId, Long showsId) {
        return ShowsAuctionSeatInfoResponse.builder()
                .seatInfos(showsRepository.findShowsAuctionSeatInfo(scheduleId, showsId))
                .build();
    }

    // Redis에 저장되어 있는 공연 캐쉬 정보 삭제
    @CacheEvict(value = "showsGlobalCache", allEntries = true)
    public void clearShowsCache() {

    }

    // Redis에 저장되어 있는 공연카테고리 캐쉬 정보 삭제
    @CacheEvict(value = "showsCategoryGlobalCache", allEntries = true)
    public void clearShowsCategoryCache() {

    }

    /**
     * 특정 카테고리에 해당하는 캐시를 삭제합니다.
     *
     * @param categoryName 삭제할 캐시의 카테고리 이름
     */
    public void evictCacheForCategory(String categoryName) {
        String pattern = "showsGlobalCache::*," + categoryName;
        Set<String> keys = stringRedisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            stringRedisTemplate.delete(keys);
        }
    }
}
