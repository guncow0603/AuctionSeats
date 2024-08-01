package me.kimgunwoo.auctionseats.domain.show.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsInfoCreateRequest;
import me.kimgunwoo.auctionseats.domain.admin.dto.response.ShowsGetResponse;
import me.kimgunwoo.auctionseats.domain.place.entity.Place;
import me.kimgunwoo.auctionseats.domain.show.dto.response.*;
import me.kimgunwoo.auctionseats.domain.show.entity.Shows;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShowsService {
    // 공연 생성
    Shows createShows(ShowsCreateRequest showsCreateRequest, Place place, ShowsInfo showsInfo);

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

    // 전체 공연 정보 조회
    List<ShowsInfoGetResponse> getAllShowsInfo();

    // 전체 공연 페이징 조회
    ShowsGetSliceResponse getSliceShows(Pageable pageable, String categoryName);

    // 공연 단건 조회
    ShowsGetResponse getShows(Long showsId);

    // 공연 카테고리 전체 조회
    List<ShowsCategoryGetResponse> getAllShowsCategory();

    // 공연 정보 조회
    ShowsInfo findByShowsInfoId(Long showsInfoId);

    // 공연 조회
    Shows findByShowsId(Long showsId);

    // 공연 좌석 정보 조회
    ShowsSeatInfoResponse findShowsSeatInfo(Long showsId);

    ShowsAuctionSeatInfoResponse findShowsAuctionSeatInfo(Long scheduleId, Long showsId);
}
