package com.blockchain.ibizeebe.Service;

import com.blockchain.ibizeebe.Model.GeneralResponse;
import com.blockchain.ibizeebe.Model.UploadRequest;
import com.blockchain.ibizeebe.Model.Users;
import com.blockchain.ibizeebe.Repository.UploadRepository;
import com.blockchain.ibizeebe.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService {

    private final UserRepository userRepository;
    private final UploadRepository uploadRepository;
    private final EncryptionService encryptionService;

    public DownloadService(UserRepository userRepository, UploadRepository uploadRepository, EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.uploadRepository = uploadRepository;
        this.encryptionService = encryptionService;
    }

    /**
     * function to download file details from the database
     * this function retrieves all the files available for the user
     * @param userId
     * @return
     */
    public List<UploadRequest> downloadDatabaseDetails(int userId) {
        System.out.println("***** downloadDatabaseDetails function is starting *****");

        System.out.println("User ID : " + userId);
        List<UploadRequest> uploadRequests = new ArrayList<>();

        try {
            Users dbUser = userRepository.findByUserId(userId);

            if (!dbUser.getFullName().equals("")) {
                System.out.println("User found in the system");

                uploadRequests = uploadRepository.findByDepartmentId(dbUser.getDepartmentId());

                if (uploadRequests.size() > 0) {
                    System.out.println("Uploaded files found for the user in the Department: " + dbUser.getDepartmentId());
                }
                else {
                    System.out.println("No files found for the user in the Department");
                }
            }
            else {
                System.out.println("User NOT found in the system");
            }
        } catch (Exception ex) {
            System.out.println("User details & file details not found");
            ex.printStackTrace();
        }
        return uploadRequests;
    }

    /**
     * function to validate final data
     * @param uploadRequest
     * @return
     */
    public GeneralResponse finalValidate(UploadRequest uploadRequest) throws IOException {
        GeneralResponse generalResponse = new GeneralResponse();
        return encryptionService.validateFileData(uploadRequest);
    }
}
