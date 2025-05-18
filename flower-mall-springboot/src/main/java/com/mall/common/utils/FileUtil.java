package com.mall.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.mall.common.result.ConstantsFields;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


public class FileUtil {

    public static String createCatalog(String coMpath, String path, String typeId, String goodsId) {
        String s1 = path + "/" + typeId + "/" + goodsId;
        String s = coMpath + s1;
        cn.hutool.core.io.FileUtil.mkdir(s);
        System.out.println(s1);
        return s1;
    }

    public static String uploadAffix(MultipartFile httpFile, String typeId, String goodsId) throws Exception {
        String realPath = createCatalog(ConstantsFields.BASEPIC, ConstantsFields.SURROUNDING_BUSINESS_IMG_PATH, typeId, goodsId);
        //获取文件后缀
        String extension = FilenameUtils.getExtension(httpFile.getOriginalFilename());
        long l = System.currentTimeMillis();
        String format = DateUtil.format(new Date(), "yyyyMMdd");
        String newFileName = IdUtil.fastSimpleUUID() + format + "." + extension;
        byte[] bytes = httpFile.getBytes();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(ConstantsFields.BASEPIC + realPath, newFileName));
        IoUtil.write(fileOutputStream, true, bytes);
        return realPath + "/" + newFileName;
    }
}
