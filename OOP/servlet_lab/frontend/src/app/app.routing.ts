import {RouterModule, Routes} from '@angular/router';
import {CabinetComponent} from './cabinet/cabinet/cabinet.component';
import {AuthGuard} from './guard/auth-guard';
import {LoginComponent} from './login/login.component';
import {RoomsComponent} from './rooms/rooms.component';
import {OrderComponent} from './order/order.component';
import {AdminComponent} from './admin/admin.component';
import {AdminGuard} from './guard/admin-guard';

const routes: Routes = [
  {path: 'cabinet', component: CabinetComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'rooms', component: RoomsComponent, canActivate: [AuthGuard]},
  {path: 'order', component: OrderComponent, canActivate: [AuthGuard]},
  {path: 'admin', component: AdminComponent, canActivate: [AdminGuard]},
  {path: '**', redirectTo: 'login'}
];

export const appRoutingModule = RouterModule.forRoot(routes);

