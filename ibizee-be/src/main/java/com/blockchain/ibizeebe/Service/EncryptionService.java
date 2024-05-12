package com.blockchain.ibizeebe.Service;

import com.blockchain.ibizeebe.Model.GeneralResponse;
import com.blockchain.ibizeebe.Model.UploadRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class EncryptionService {
    public static Map<String, Object> map = new HashMap<>();
    private SecretKey secretKey;

    /**
     * function to generate public keys and private keys
     * to encrypt or decrypt file names
     */
    public GeneralResponse generateKeys() {
        System.out.println("***** generateKeys function is starting *****");
        GeneralResponse generalResponse = new GeneralResponse();

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            map.put("publicKey", publicKey);
            map.put("privateKey", privateKey);

            System.out.println("Key generation successful");
            generalResponse.setResponseCode(200);
            generalResponse.setResponseMessage("Key generation successful");

            System.out.println("");
            System.out.println("Starting to store keys in the blockchain service");
            transactionToBlockchain(1, map);


        } catch (Exception ex) {
            System.out.println("Key generation failed!");
            generalResponse.setResponseCode(400);
            generalResponse.setResponseMessage("Key generation failed!");
            ex.printStackTrace();
        }
        return generalResponse;
    }

//    public GeneralResponse generateAESKeys() {
//        System.out.println("***** generateAESKeys function is starting *****");
//        GeneralResponse generalResponse = new GeneralResponse();
//        SecureRandom srandom = new SecureRandom();
//
//        try {
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            kgen.init(128);
//            secretKey = kgen.generateKey();
//
//            byte[] iv = new byte[128/8];
//            srandom.nextBytes(iv);
//            IvParameterSpec ivspec = new IvParameterSpec(iv);
//
//        } catch (Exception ex) {
//            System.out.println("Key generation failed!");
//        }
//        return generalResponse;
//    }

    /**
     * function to encrypt the file name
     */
    public GeneralResponse encryptData(String data) {
        System.out.println("***** encryptData function is starting *****");
        GeneralResponse generalResponse = new GeneralResponse();

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            PublicKey publicKey = (PublicKey) map.get("publicKey");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encrypted = cipher.doFinal(data.getBytes());

            System.out.println("Data encryption successful");
            generalResponse.setResponseCode(200);
            generalResponse.setResponseMessage(Base64.getEncoder().encodeToString(encrypted));

            System.out.println("");
            System.out.println("Starting to get keys from the blockchain service");
            transactionToBlockchain(2, map);

        } catch (Exception ex) {
            System.out.println("Data encryption failed!");
            generalResponse.setResponseCode(400);
            generalResponse.setResponseMessage("Data encryption failed!");
            ex.printStackTrace();
        }
        return generalResponse;
    }

    /**
     * ==============================
     * test
     *
     * @return
     */
    public GeneralResponse testEncryptFile(File file) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        PublicKey publicKey = (PublicKey) map.get("publicKey");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        try (FileInputStream in = new FileInputStream(file);
             FileOutputStream out = new FileOutputStream("src/main/java/com/blockchain/ibizeebe/Shared/EncryptedFiles/abc.enc")) {

            processFile(cipher, in, out);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    static private void processFile(Cipher ci,InputStream in,OutputStream out)
            throws javax.crypto.IllegalBlockSizeException,
            javax.crypto.BadPaddingException,
            java.io.IOException
    {
        byte[] ibuf = new byte[1024];
        int len;
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = ci.update(ibuf, 0, len);
            if ( obuf != null ) out.write(obuf);
        }
        byte[] obuf = ci.doFinal();
        if ( obuf != null ) out.write(obuf);
    }


        /**
     * function to decrypt the file name
     */
    public GeneralResponse decryptData(String text) {
        System.out.println("***** decryptData function is starting *****");
        GeneralResponse generalResponse = new GeneralResponse();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            PrivateKey privateKey = (PrivateKey) map.get("privateKey");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(text));
            String str = new String(decrypted, StandardCharsets.UTF_8);
            System.out.println("File decryption successful");
            generalResponse.setResponseCode(200);
            generalResponse.setResponseMessage(str);
//            return new String(decrypted);
        } catch (Exception ex) {
            System.out.println("File decryption failed!");
            generalResponse.setResponseCode(400);
            generalResponse.setResponseMessage("File decryption failed!");
            ex.printStackTrace();
        }
        return generalResponse;
    }

    /**
     * function validate file data including name and the timestamp
     */
    public GeneralResponse validateFileData(UploadRequest uploadRequest) throws IOException {
        System.out.println("***** validateFileData function is starting *****");
        GeneralResponse generalResponse = new GeneralResponse();

        // Google Drive file URL
        URL url = new URL(uploadRequest.getUploadUrl());

        // Open connection to get the content disposition which contains the file name
        URLConnection connection = url.openConnection();
        String contentDisposition = connection.getHeaderField("Content-Disposition");

        // Extracting file name from content disposition
        String fileName = null;

        if (contentDisposition != null) {
            int index = contentDisposition.indexOf("filename=");
            if (index != -1) {
                fileName = contentDisposition.substring(index + 9);
                fileName = fileName.replaceAll("\"", ""); // Remove quotes if present
            }
        }

        System.out.println("fileName: " + fileName);

        // validating the file name
        if (fileName.contains(uploadRequest.getFileDescription())) {
            System.out.println("File names matches");
            generalResponse.setResponseCode(200);
            generalResponse.setResponseMessage("File names matches");
        }
        else {
            System.out.println("File name doesn't match");
            generalResponse.setResponseCode(400);
            generalResponse.setResponseMessage("File name doesn't match");
        }

        File destination = new File("src/main/java/com/blockchain/ibizeebe/Shared/EncryptedFiles/" + fileName);
        FileUtils.copyURLToFile(url, destination);

        // If file name not found in content disposition, extract from URL
        if (fileName == null || fileName.isEmpty()) {
            String[] segments = url.getPath().split("/");
            fileName = segments[segments.length - 1];
        }

        System.out.println("File downloaded successfully");
        return generalResponse;
    }

    /**
     * function to check the connection to the blockchain service
     * insert keys into the blockchain service
     * download keys from the blockchain service
     * @return
     */
    public GeneralResponse transactionToBlockchain(int status, Map<String, Object> keys) {
        GeneralResponse generalResponse = new GeneralResponse();

        String addKeys_connection = "http://127.0.0.1:3000/addNewHash";
        String getKeys_connection = "http://127.0.0.1:3000/getHash";

        // add keys to the blockchain
        if (status == 1) {
            System.out.println("Attempting to store keys in the blockchain");
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject(addKeys_connection, "keys", String.class);

            if (result != null) {
                System.out.println("Keys stored successfully");
                generalResponse.setResponseCode(200);
                generalResponse.setResponseMessage(result);
            }
            else {
                System.out.println("Keys storing failed");
            }
        }
        // get keys from the blockchain
        else {
            System.out.println("Attempting to get keys from the blockchain");
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(getKeys_connection, String.class);

            if (result != null) {
                System.out.println("Keys retrieved successfully");
            }
            else {
                System.out.println("Keys retrieving failed");
            }
        }
        return generalResponse;
    }
}
