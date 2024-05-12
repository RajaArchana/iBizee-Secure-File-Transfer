import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-main-pane',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './main-pane.component.html',
  styleUrl: './main-pane.component.css'
})
export class MainPaneComponent {

  constructor(private router: Router) {}

  // function to navigate to the file upload page
  clickingOnUpload() {
    this.router.navigate(['file-transfer'])
  }

  clickingDownload() {
    this.router.navigate(['file-download'])
  }

}
