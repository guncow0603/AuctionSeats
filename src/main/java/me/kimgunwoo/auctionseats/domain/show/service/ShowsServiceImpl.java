package me.kimgunwoo.auctionseats.domain.show.service;

import java.util.ArrayList;
import java.util.List;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsInfoCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ShowsGetResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Places;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsGetSliceResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsInfoGetResponse;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsCategoryRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsImageRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsInfoRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsRepository;
import me.kimgunwoo.auctionseats.global.exception.ApiException;
import me.kimgunwoo.auctionseats.global.util.S3Uploader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import static me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl.*;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_SHOWS;
import static me.kimgunwoo.auctionseats.global.exception.ErrorCode.NOT_FOUND_SHOWS_INFO;

@Service
@RequiredArgsConstructor
public class ShowsServiceImpl implements ShowsService {

    private final ShowsInfoRepository showsInfoRepository;

    private final ShowsImageRepository showsImageRepository;

    private final S3Uploader s3Uploader;

    private final ShowsCategoryRepository showsCategoryRepository;

    public final ShowsRepository showsRepository;

    @Override
    public Shows createShows(ShowsCreateRequest showsCreateRequest, Places places, ShowsInfo showsInfo) {
        Shows shows = showsCreateRequest.toEntity(places, showsInfo);
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
    public ShowsGetSliceResponse getSliceShows(Pageable pageable, String categoryName) {
        Sort sort = pageable.getSort();
        Sort additionalSort = Sort.by(Sort.Direction.ASC, "startDate");
        Sort finalSort = sort.and(additionalSort);
        Pageable addFinalPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), finalSort);

        Slice<Shows> showsSlice = showsRepository.findAllByShowsAndCategoryName(addFinalPageable, categoryName);
        return new ShowsGetSliceResponse(showsSlice);

    }

    // 공연 정보 조회
    @Override
    public ShowsInfo findByShowsInfoId(Long showsInfoId) {
        return showsInfoRepository.findById(showsInfoId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_SHOWS_INFO));
    }

    // 공연 정보 조회
    public Shows findByShowsId(Long showsId) {
        return showsRepository.findById(showsId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_SHOWS));
    }
}