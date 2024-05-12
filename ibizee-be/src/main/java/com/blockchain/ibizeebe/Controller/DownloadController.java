package com.blockchain.ibizeebe.Controller;

import com.blockchain.ibizeebe.Model.GeneralResponse;
import com.blockchain.ibizeebe.Model.UploadRequest;
import com.blockchain.ibizeebe.Service.DownloadService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class DownloadController {

    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping(value = "/file/download/{userId}")
    private List<UploadRequest> downloadDatabaseDetails(@PathVariable int userId) {
        return downloadService.downloadDatabaseDetails(userId);
    }

    @PostMapping(value = "/file/validate/final")
    private GeneralResponse finalValidate(@RequestBody UploadRequest uploadRequest) throws IOException {
        return downloadService.finalValidate(uploadRequest);
    }
}
