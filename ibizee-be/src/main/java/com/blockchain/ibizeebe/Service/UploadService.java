package com.blockchain.ibizeebe.Service;

import com.blockchain.ibizeebe.Model.*;
import com.blockchain.ibizeebe.Repository.UploadRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
public class UploadService {

    // global variables
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleKey();
    private final UploadRepository uploadRepository;
    int uploadStatus = 0;
    Uploads uploads = new Uploads();
    private final UserService userService;
    private final EmailSenderService emailSenderService;

    public UploadService(UserService userService, EmailSenderService emailSenderService, UploadRepository uploadRepository) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.uploadRepository = uploadRepository;
    }

    /**
     * function to get Google key file location
     * @return
     */
    private static String getPathToGoogleKey() {
        System.out.println("***** getPathToGoogleKey function is starting *****");
        String directory = System.getProperty("user.dir");
        Path filePath = Paths.get(directory, "src/main/java/com/blockchain/ibizeebe/Shared/google_key.json");
        return filePath.toString();
    }

    public Uploads uploadFilesToDrive(File file) {
        System.out.println("***** uploadFilesToDrive function is starting *****");

        try {
            String uploadFolderId = "1yDLXLfdi2L2-QNR6FoHAnupYFZ8FDY3X";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(uploadFolderId));
//            FileContent fileContent = new FileContent("image/jpeg", file);
            FileContent fileContent = new FileContent("application/zip", file);

            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, fileContent)
                    .setFields("id").execute();

            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("imageUrl : " + imageUrl);
            file.delete();
            uploads.setStatus(200);
            uploads.setMessage("Upload successful");
            uploads.setUrl(imageUrl);
            uploadStatus = 200;

        } catch (Exception ex) {
            uploads.setStatus(400);
            uploads.setMessage("Upload failed: " + ex.getMessage());
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return uploads;
    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {
        System.out.println("***** createDriveService function is starting *****");
        GoogleCredential googleCredential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                googleCredential)
                .build();
    }

    /**
     * function to upload file details
     */
    public GeneralResponse uploadFileDetails(UploadRequest uploadRequest) {
        System.out.println("***** uploadFileDetails function is starting *****");
        GeneralResponse generalResponse = new GeneralResponse();
        List<Users> dbUsers = null;
        EmailRequest emailRequest = new EmailRequest();

        if (uploadStatus == 200) {
            generalResponse.setResponseCode(200);
            generalResponse.setResponseMessage("Upload successful");
            System.out.println("Details uploaded successfully");
            System.out.println("getFileDescription: " + uploadRequest.getFileDescription());
            System.out.println("getFilePassword: " + uploadRequest.getFilePassword());
            System.out.println("getDepartmentId: " + uploadRequest.getDepartmentId());

            System.out.println();

            // getting all the email address in the same department users
            dbUsers = userService.getDepartmentUsers(uploadRequest.getDepartmentId());

            if (dbUsers != null && dbUsers.size() > 0) {
                System.out.println("Users found for the department Id: " + uploadRequest.getDepartmentId());

               for (int i=0; i<dbUsers.size(); i++) {

                   // generate email body data
                   String subject = "New files Available (do-not-reply)";
                   String greeting = "Hello " + dbUsers.get(i).getFullName() + ",";
                   String userName = dbUsers.get(i).getFullName();
                   String middleText = "\n\nNow you can find the uploaded file using the below link\nPlease note that this is a auto generated email. Therefore, please do not reply";
                   String urlDetails = "\nURL: " + "The download link will be revealed once the security check done";
                   String password = "\nPassword: " + uploadRequest.getFilePassword();
                   String ending = "\n\nThank you!\niBIZEE File Transfer Service";

                   // binding email body data
                   emailRequest.setSubject(subject);
                   emailRequest.setBody(greeting + userName + middleText + urlDetails + password + ending);
                   emailRequest.setTo(dbUsers.get(i).getUserEmail());

                   // sending out the email
                   generalResponse = emailSenderService.sendEmail(emailRequest);

                   if (generalResponse.getResponseCode() == 200) {
                       System.out.println("Email sent to: " + dbUsers.get(i).getUserEmail());
                   }
                   else {
                       System.out.println("Email sending failed!");
                       generalResponse.setResponseCode(400);
                       generalResponse.setResponseMessage("Email sending failed!");
                   }
               }

               System.out.println("Uploading details into the database");

               // assign the current timestamp
               uploadRequest.setUploadedTimestamp(new Timestamp(System.currentTimeMillis()));

               // assign the uploaded URL
               uploadRequest.setUploadUrl(uploads.getUrl());

               UploadRequest dbUpload = uploadRepository.save(uploadRequest);

               if (dbUpload != null) {
                   System.out.println("Details uploaded successfully to the database");
               }
            }
            else {
                System.out.println("Users not found");
            }
        }
        else {
            generalResponse.setResponseCode(400);
            generalResponse.setResponseMessage("The file didn't upload yet");
            System.out.println("Details uploading failed!");
        }
        return generalResponse;
    }

    }
