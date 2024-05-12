import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from 'express';
import { UploadRequest } from '../Models/UploadRequest';

const headeroption = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DownloadService {

  BASE_URL = 'http://127.0.0.1:8080/';

  constructor(private http: HttpClient) { }

  // function to get file download details from the database
  downloadDatabaseDetails(userId: number) {
    return this.http.get(this.BASE_URL + 'file/download/' + userId)
  }

  // function to get file download details from the database
  finalValidate(uploadRequest: UploadRequest) {
    return this.http.post(this.BASE_URL + 'file/validate/final', uploadRequest)
  }
}
