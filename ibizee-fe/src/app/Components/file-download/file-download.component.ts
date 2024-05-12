import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { DownloadService } from '../../Services/download.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UploadRequest } from '../../Models/UploadRequest';
import { Upload } from '../../Models/Upload';

@Component({
  selector: 'app-file-download',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './file-download.component.html',
  styleUrl: './file-download.component.css'
})
export class FileDownloadComponent {

  // global variables
  link_visibility = false;
  user_password: string = ""
  alertStatus: boolean = false
  alertClass: string = "alert alert-danger";
  alertText: string = "Something went wrong"
  finalUploadRequest: UploadRequest = new UploadRequest;


  constructor(private http: HttpClient, private downloadService: DownloadService) {}

  ngOnInit() {
    this.link_visibility = false;
    this.downloadDatabaseDetails();
  }

  // this functions works when started to changing fields in the form
  registerOnChange() {
    this.alertStatus = false
  }

  download_table: string[][] = []
  result_array = []

  downloadDatabaseDetails() {
    try {
      this.downloadService.downloadDatabaseDetails(1).subscribe((result: any) => {
        if (result) {
          this.result_array = result;
          var input = ""
  
          for (var i=0 ; i<this.result_array.length ; i++) {
            this.download_table.push([this.result_array[i]['fileDescription'], this.result_array[i]['uploadUrl'], this.result_array[i]['transferId'], this.result_array[i]['filePassword'], input, this.result_array[i]['departmentId']])
          }
        }
      })
    } catch (error) {
      this.link_visibility = false;
      this.alertStatus = true
      this.alertClass = "alert alert-warning";
      this.alertText = "No any files available for download"
    }
  }

  // clicking on the reveal button
  clickOnReveal(data: any) {
      if (data[4] == "") {
        this.link_visibility = false;
        this.alertStatus = true
        this.alertClass = "alert alert-danger";
        this.alertText = "Please enter the password"
      }
      else {
        console.log(data)

        // matching with the user entered password with the actual file password
        if (data[4] == data[3]) {

          // display message
          this.link_visibility = true
          this.alertStatus = true
          this.alertClass = "alert alert-warning";
          this.alertText = "Password is correct, File security check is starting..."

          // sending details to the backend for final validation
          this.finalUploadRequest.transferId = data[2];
          this.finalUploadRequest.fileDescription = data[0];
          this.finalUploadRequest.departmentId = data[5];
          this.finalUploadRequest.filePassword = data[3];
          this.finalUploadRequest.uploadUrl = data[1];

          console.log(this.finalUploadRequest)
          this.downloadService.finalValidate(this.finalUploadRequest).subscribe((result: any) => {
            console.log(result);

            // final link reveals only if the encryption and the passwords are matching
            console.log(result)
            if (result['responseCode'] == 200) {
              this.link_visibility = true
              this.alertStatus = true
              this.alertClass = "alert alert-success";
              this.alertText = "Security check scuccessful, Now you can download the file"
              console.log("Passwords are matching")
            }
            else {
              this.link_visibility = false
              this.alertStatus = true
              this.alertClass = "alert alert-danger";
              this.alertText = "Security check failed! Your files has been compromised!. Please Don't download any files"
              console.log("Security check failed! Your files has been compromised!. Please Don't download any files")
            }
          })
        }
        else {
          this.link_visibility = false
          this.alertStatus = true
          this.alertClass = "alert alert-danger";
          this.alertText = "Password is incorrect"
          console.log("Password is incorrect")
        }
      }
  }
}
