package me.kimgunwoo.auctionseats.domain.show.service;

import lombok.RequiredArgsConstructor;
import me.kimgunwoo.auctionseats.domain.admin.dto.request.ShowsRequest;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsCategory;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsImage;
import me.kimgunwoo.auctionseats.domain.show.entity.ShowsInfo;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsCategoryRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsImageRepository;
import me.kimgunwoo.auctionseats.domain.show.repository.ShowsInfoRepository;
import me.kimgunwoo.auctionseats.global.util.S3Uploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static me.kimgunwoo.auctionseats.domain.admin.adminService.AdminServiceImpl.*;

@Service
@RequiredArgsConstructor
public class ShowsInfoServiceImpl implements ShowsInfoService {
    private final ShowsInfoRepository showsInfoRepository;

    private final ShowsImageRepository showsImageRepository;

    private final S3Uploader s3Uploader;

    private final ShowsCategoryRepository showsCategoryRepository;

    // 공연 정보 생성
    @Override
    public ShowsInfo createShowsInfo(ShowsRequest showsRequest) {
        ShowsInfo showsInfo = showsRequest.toEntity();

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
        ShowsCategory showsCategory = showsCategoryRepository.findByName(name).orElse(null);
        if (showsCategory == null) {
            showsCategory =
                    ShowsCategory
                            .builder()
                            .name(name)
                            .build();
        }
        return showsCategoryRepository.save(showsCategory);
    }

}