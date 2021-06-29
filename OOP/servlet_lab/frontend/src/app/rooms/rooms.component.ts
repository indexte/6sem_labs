import {Component, OnInit} from '@angular/core';
import {Course} from '../model/course';
import {CourseService} from '../service/course.service';
import {User} from "../model/user";
import {AuthService} from "../service/auth-service";

@Component({
    selector: 'app-rooms',
    templateUrl: './rooms.component.html',
    styleUrls: ['./rooms.component.css']
})
export class RoomsComponent implements OnInit {
    courses: Course[];

    constructor(private courseService: CourseService,
                private authService: AuthService) {
    }

    ngOnInit(): void {
        this.getAllRooms();
    }

    getAllRooms(): void {
        this.courseService.getAllCourses().subscribe(rooms => {
            this.courses = rooms;
        });
    }

    subscribe(i: number): void {
        console.log('user:' + this.authService.currentUserValue.id);
        this.courseService.subscribeOnCourse(i, this.authService.currentUserValue.id).subscribe(course => {
            console.log(course);
        });
    }

}
