package com.yami.shop.api.controller;

import com.yami.shop.common.response.ServerResponseEntity;
import com.yami.shop.service.AttachFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/p/file")
@Tag(name = "文件接口")
@AllArgsConstructor
public class FileController {

    private final AttachFileService attachFileService;

    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public ServerResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ServerResponseEntity.success();
        }
        String fileName = attachFileService.uploadFile(file);
        return ServerResponseEntity.success(fileName);
    }
}
