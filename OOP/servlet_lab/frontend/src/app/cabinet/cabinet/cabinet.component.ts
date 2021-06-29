import {Component, OnInit} from '@angular/core';
import {UserService} from '../../service/user.service';
import {User} from '../../model/user';
import {AuthService} from '../../service/auth-service';
import {CourseService} from '../../service/course.service';
import {Course} from '../../model/course';
import {FeedbackService} from '../../service/feedback.service';
import {Feedback} from '../../model/feedback';


@Component({
    selector: 'app-cabinet',
    templateUrl: './cabinet.component.html',
    styleUrls: ['./cabinet.component.css']
})
export class CabinetComponent implements OnInit {

    user: User;
    courses: Course[];
    feedbacks: Feedback[];

    constructor(private userService: UserService,
                private authService: AuthService,
                private courseService: CourseService,
                private feedBackService: FeedbackService) {
    }

    ngOnInit(): void {

        this.getAllCoursesByUser();
        this.getAllFeedbackByUser();
    }

    getAllCoursesByUser(): void {
        this.courseService.getAllCoursesByUserId(this.authService.currentUserValue.id).pipe().subscribe(
            data => {
                this.courses = data;
            }
        );
    }

    getAllFeedbackByUser(): void {
        this.feedBackService.getAllFeedbacksByUser(this.authService.currentUserValue.id).pipe().subscribe(
            data => {

                this.feedbacks = data;
            }
        );
    }

    logout(): void {
        this.authService.logout();
    }

}
