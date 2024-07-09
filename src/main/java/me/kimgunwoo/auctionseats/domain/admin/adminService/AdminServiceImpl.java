package me.kimgunwoo.auctionseats.domain.admin.adminService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    public static final String S3_PATH = "https://auction-ticket.s3.ap-northeast-2.amazonaws.com/";

    public static final String FILE_PATH = "goods/";
    public static final String THUMBNAIL = "thumbnail/";

    public static final String GENERAL = "general/";

}
