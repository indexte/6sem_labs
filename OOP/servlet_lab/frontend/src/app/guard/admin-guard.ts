import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {AuthService} from '../service/auth-service';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class AdminGuard implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const currentUser = this.authService.currentUserValue;
    console.log(currentUser.role);
    if (currentUser.role === 'ROLE_TEACHER') {
      return true;
    }
    this.router.navigate(['/logout'], {queryParams: {returnUrl: state.url}});
    return false;
  }

}
