import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {

  constructor(private router: Router) {}

  // function to navigate to the file upload page
  clickingOnUpload() {
    this.router.navigate(['main-pane/file-transfer'])
  }

  clickingDownload() {
    this.router.navigate(['main-pane/file-download'])
  }
}
