package com.mall.sys.entity.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;



@Data
public class PathVo {

    private MultipartFile httpFile;
    private String path;
}
