import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url = 'http://localhost:8081/hotel_project_war/login';

  constructor(private httpClient: HttpClient) {
  }

  getUser(): Observable<User> {
    return this.httpClient.get<User>(this.url);
  }

  loginUser(user: string, pass: string): Observable<User> {
    const body = {username: user, password: pass};
    return this.httpClient.post<User>(this.url, body);
  }

}
