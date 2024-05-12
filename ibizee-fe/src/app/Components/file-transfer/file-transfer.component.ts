import { Component } from '@angular/core';
import { UploadService } from '../../Services/upload.service';
import { UploadRequest } from '../../Models/UploadRequest';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Upload } from '../../Models/Upload';
import { GeneralResponse } from '../../Models/GeneralResponse';

@Component({
  selector: 'app-file-transfer',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './file-transfer.component.html',
  styleUrl: './file-transfer.component.css'
})
export class FileTransferComponent {

  // global variables
  uploadRequest: UploadRequest = new UploadRequest;
  upload: Upload = new Upload;
  generalResponse: GeneralResponse = new GeneralResponse;
  selectedFile: File | null = null
  fileType: string = "image"
  alertStatus: boolean = false
  alertClass: string = "alert alert-danger";
  alertText: string = "Something went wrong"

  constructor(private uploadService: UploadService) {
    this.makeRandomPassword()
  }

  // this functions works when started to changing fields in the form
  OnChange() {
    this.alertStatus = false
  }

  onFileChange(event: any): void {
    console.log("Line 20")
    this.selectedFile = event.target.files[0]
    console.log(this.selectedFile)
  }
  
  uploadFile() {
    console.log("Line 25")
    const formData = new FormData()
    formData.append('file', this.selectedFile as Blob);

    this.uploadService.newFileUpload(formData, this.uploadRequest.fileDescription).subscribe((result: any) => {
      console.log("File uploaded")
    });
  }

  // function to make a 8 charactor long random password
  makeRandomPassword() {
    let possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890/[]\(*&#@!";
    const lengthOfCode = 8;
    let text = "";
    for (let i = 0; i < lengthOfCode; i++) {
      text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    this.uploadRequest.filePassword = text;
  }

  // function add file description, department id and the password to the backend
  uploadFileDetails() {
    if (this.uploadRequest.fileDescription == "") {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Please add a description"
    }
    else if (this.uploadRequest.departmentId == undefined) {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Please select a daprtment"
    }
    else if (this.uploadRequest.filePassword == "") {
      this.alertStatus = true
      this.alertClass = "alert alert-danger"
      this.alertText = "Please add a password"
    }
    else {
      console.log("Line 25")
      const formData = new FormData()
      formData.append('file', this.selectedFile as Blob);

      this.uploadService.newFileUpload(formData, this.uploadRequest.fileDescription).subscribe((result: any) => {
        console.log("File uploaded")
        this.upload = result;

        if (this.upload.status == 200) {
          this.uploadService.uploadFileDetails(this.uploadRequest).subscribe((detailsResults: any) => {
            this.generalResponse = detailsResults;

            if (this.generalResponse.responseCode == 200) {
              this.alertStatus = true
              this.alertClass = "alert alert-success"
              this.alertText = "File uploaded successfully"
            }
            else {
              this.alertStatus = true
              this.alertClass = "alert alert-danger"
              this.alertText = "File uploading failed"
            }
          })
        }
      });
    }
  }

}
