import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, retry } from 'rxjs';
import { UploadRequest } from '../Models/UploadRequest';

const headeroption = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  BASE_URL = 'http://127.0.0.1:8080/';

  constructor(private http: HttpClient, private router: Router) { }

  newFileUpload(formData: FormData, fileName: string) {
    return this.http.post(this.BASE_URL + 'file/upload/' + fileName, formData).pipe(
      retry(1),
      catchError(this.handleError)
    )
  }

  // function to send upload details to the backend
  uploadFileDetails(uploadRequest: UploadRequest) {
    return this.http.post(this.BASE_URL + 'file/details', uploadRequest);
  }

  // Function to navigate to no-connection page if cannot communicate with the back-end
  handleError() {
    console.log("back-end not connected properly!")
    return "error"
  }

  
}
