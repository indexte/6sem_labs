import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Course} from '../model/course';
import {AdminDTO} from "../model/adminDTO";

@Injectable({
    providedIn: 'root'
})
export class CourseService {
    url = 'http://localhost:8080/courses';
    urlCab = 'http://localhost:8080/cabinet';
    urlAdm = 'http://localhost:8080/admin';

    constructor(private httpClient: HttpClient) {
    }

    getAllCourses(): Observable<Course[]> {
        return this.httpClient.get<Course[]>(this.url);
    }

    subscribeOnCourse(courseId: number, userId: number): Observable<Course> {
        console.log(userId);
        const body = {courseId, userId};
        console.log(body);
        return this.httpClient.post<Course>(this.url, body);
    }

    getAllCoursesByUserId(id: number): Observable<Course[]> {
        return this.httpClient.get<Course[]>(this.urlCab + '?user=' + id);
    }

    getAllCoursesWithCourseId(): Observable<AdminDTO[]>{
        return this.httpClient.get<AdminDTO[]>(this.urlAdm);
    }
}
