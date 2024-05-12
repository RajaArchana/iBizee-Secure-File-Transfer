package com.blockchain.ibizeebe.Controller;

import com.blockchain.ibizeebe.Model.GeneralResponse;
import com.blockchain.ibizeebe.Model.UploadRequest;
import com.blockchain.ibizeebe.Model.Uploads;
import com.blockchain.ibizeebe.Service.UploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class UploadController {


    private UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/file/upload/{fileName}")
    public Uploads newFileUpload(@RequestParam ("file") MultipartFile file, @PathVariable String fileName) throws IOException, GeneralSecurityException {
        Uploads upload = new Uploads();
        System.out.println("file: " + file);
        if (file.isEmpty()) {
            System.out.println("File is empty");
        }
        else {
            File temFile = File.createTempFile(fileName, file.getOriginalFilename());
            file.transferTo(temFile);
            upload = uploadService.uploadFilesToDrive(temFile);
        }
        return upload;
    }

    @PostMapping("/file/details")
    public GeneralResponse uploadFileDetails(@RequestBody UploadRequest uploadRequest) {
        return this.uploadService.uploadFileDetails(uploadRequest);
    }
}
