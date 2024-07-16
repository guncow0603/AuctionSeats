package me.kimgunwoo.auctionseats.domain.show.service;

import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShowsInfoService {

    // 공연 카테고리 생성
    ShowsCategory createShowsCategory(String name);

    // 공연 이미지 생성
    List<ShowsImage> createShowsImage(List<MultipartFile> multipartFiles, ShowsInfo showsInfo);

    // 공연 정보 생성
    ShowsInfo createShowsInfo(ShowsRequest showsRequest);

    // 공연 이미지 s3 업로드
    List<String> s3Upload(List<MultipartFile> multipartFiles, Long showsInfo);

    // 공연 이미지 타입 분리
    List<ShowsImage> divideTypeShowsImageList(List<String> fileKeyList, ShowsInfo showsInfo);

    // 공연 이미지 타입 체크
    String checkShowsType(String type);

    // 공연 정보 단건 조회
    ShowsInfoGetResponse getShowsInfo(Long showsInfoId);

}
