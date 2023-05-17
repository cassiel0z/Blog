package com.example.blog_springboot.utils;

import com.example.blog_springboot.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageUtil {

    private static final long MAX_PHOTO_SIZE = 10 * 1024 * 1024;  // 大小限制 2MB

    private static final Map<String, String> ALLOWED_PHOTO_TYPES = new HashMap<>();

    static {
        ALLOWED_PHOTO_TYPES.put("FFD8FF", "image/jpeg");
        ALLOWED_PHOTO_TYPES.put("89504E", "image/png");
    }

    public static ResultVO<Object> checkImage(MultipartFile image) {
        try {
            if (image.getSize() > MAX_PHOTO_SIZE) {
                return new ResultVO<>().FAIL("图片过大");
            }

            if (!ALLOWED_PHOTO_TYPES.containsKey(MagicNumberUtil.getMagicNumber(image.getBytes(), 6))) {
                return new ResultVO<>().FAIL("类型不符");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO<>().FAIL("上传失败");
        }

        return null;
    }

    public static String getImageType(MultipartFile image) throws IOException {
        return ALLOWED_PHOTO_TYPES.get(MagicNumberUtil.getMagicNumber(image.getBytes(), 6));
    }

}
