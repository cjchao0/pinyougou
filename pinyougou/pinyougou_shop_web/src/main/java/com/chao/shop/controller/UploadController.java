package com.chao.shop.controller;

import com.chao.common.util.FastDFSClient;
import com.chao.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @PostMapping
    public Result upload(MultipartFile file){
        try {
            //获取文件拓展名
            String file_ext_name = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastdfs/tracker.conf");

            String url = fastDFSClient.uploadFile(file.getBytes(), file_ext_name);
            return Result.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.fail("上传图片失败");
    }

}
