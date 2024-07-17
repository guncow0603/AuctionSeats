package me.kimgunwoo.auctionseats.domain.show.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsInfoCreateRequest;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsInfoGetResponse;
import me.kimgunwoo.auctionseats.domain.show.dto.response.ShowsInfoGetSliceResponse;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShowsInfoService {

    // 공연 카테고리 생성
    ShowsCategory createShowsCategory(String name);

    // 공연 이미지 생성
    List<ShowsImage> createShowsImage(List<MultipartFile> multipartFiles, ShowsInfo showsInfo);

    // 공연 정보 생성
    ShowsInfo createShowsInfo(ShowsInfoCreateRequest showsInfoCreateRequest);

    // 공연 이미지 s3 업로드
    List<String> s3Upload(List<MultipartFile> multipartFiles, Long showsInfo);

    // 공연 이미지 타입 분리
    List<ShowsImage> divideTypeShowsImageList(List<String> fileKeyList, ShowsInfo showsInfo);

    // 공연 이미지 타입 체크
    String checkShowsType(String type);

    // 공연 정보 단건 조회
    ShowsInfoGetResponse getShowsInfo(Long showsInfoId);

    // 공연 정보 카테고리별 페이징 페이징 조회
    ShowsInfoGetSliceResponse getSliceShowsInfo(Pageable pageable, String categoryName);


    // 공연 체크 이미 끝난 공연은 제외
    List<Shows> checkShows(ShowsInfo showsInfo);

    ShowsInfo findByShowsInfoId(Long showsInfoId);
}
