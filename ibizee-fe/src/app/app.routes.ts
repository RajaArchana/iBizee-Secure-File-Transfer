import { Routes } from '@angular/router';
import { RegisterPageComponent } from './Components/register-page/register-page.component';
import { LoginPageComponent } from './Components/login-page/login-page.component';
import { FileTransferComponent } from './Components/file-transfer/file-transfer.component';
import { MainPaneComponent } from './Components/main-pane/main-pane.component';
import { FileDownloadComponent } from './Components/file-download/file-download.component';
import { HomePageComponent } from './Components/home-page/home-page.component';

export const routes: Routes = [
    
    { path: '', redirectTo: "main-pane", pathMatch: 'full' },
    { path: 'main-pane', component: MainPaneComponent, children: [
        { path: '', redirectTo: "login", pathMatch: 'full' },
        { path: 'register', component: RegisterPageComponent },
        { path: 'login', component: LoginPageComponent },
        { path: 'file-transfer', component: FileTransferComponent },
        { path: 'file-download', component: FileDownloadComponent },
        { path: 'home', component: HomePageComponent }
    ] }
];
