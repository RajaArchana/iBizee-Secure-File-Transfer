package com.blockchain.ibizeebe.Model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "file_transfer")
public class UploadRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    int transferId;

    @Column(name = "file_description")
    String fileDescription = "";

    @Column(name = "department_id")
    int departmentId;

    @Column(name = "file_password")
    String filePassword;

    @Column(name = "upload_url")
    String uploadUrl;

    @Column(name = "uploaded_timestamp")
    Date uploadedTimestamp;

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getFilePassword() {
        return filePassword;
    }

    public void setFilePassword(String filePassword) {
        this.filePassword = filePassword;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public Date getUploadedTimestamp() {
        return uploadedTimestamp;
    }

    public void setUploadedTimestamp(Date uploadedTimestamp) {
        this.uploadedTimestamp = uploadedTimestamp;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }
}
