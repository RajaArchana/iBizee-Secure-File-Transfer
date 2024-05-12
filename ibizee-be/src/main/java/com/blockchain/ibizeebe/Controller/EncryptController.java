package com.blockchain.ibizeebe.Controller;

import com.blockchain.ibizeebe.Model.GeneralResponse;
import com.blockchain.ibizeebe.Service.EncryptionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
public class EncryptController {

    private final EncryptionService encryptionService;

    // constructor
    public EncryptController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @PostMapping(value = "/data/key")
    public GeneralResponse generateKeys() {
        return encryptionService.generateKeys();
    }

    @PostMapping(value = "/data/enc")
    public GeneralResponse encryptData(@RequestBody String data) {
        return encryptionService.encryptData(data);
    }

    @PostMapping(value = "/data/dec")
    public GeneralResponse decryptData(@RequestBody String fileName) {
        return encryptionService.decryptData(fileName);
    }

    @PostMapping(value = "/data/file")
    public GeneralResponse encryptFile(@RequestParam("file") MultipartFile file) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        File temFile = File.createTempFile("ArchanaEncrypted", file.getName());
        file.transferTo(temFile);
        return encryptionService.testEncryptFile(temFile);
    }
}
