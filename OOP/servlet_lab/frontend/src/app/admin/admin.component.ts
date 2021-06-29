import {Component, OnInit} from '@angular/core';
import {AuthService} from '../service/auth-service';
import {ReservationService} from '../service/reservation.service';
import {CourseService} from '../service/course.service';
import {Course} from '../model/course';
import {Reservation} from '../model/reservation';
import {Router} from '@angular/router';
import {AdminDTO} from "../model/adminDTO";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  courses: AdminDTO[];
  reservations: Reservation[];
  selectedRoom: number;

  constructor(private authService: AuthService,
              private reservationService: ReservationService,
              private courseService: CourseService,
              private router: Router,
  ) {

  }

  ngOnInit(): void {
    this.getAllReservations();
    this.getAllRooms();
  }

  getAllReservations(): void {
    this.reservationService.getAllReservations().pipe().subscribe(data => {
      this.reservations = data;
      console.log(data);
    });
  }

  getAllRooms(): void {
    this.courseService.getAllCoursesWithCourseId().pipe().subscribe(data => {
      this.courses = data;
      console.log(data);
    });
  }

  updateReservation(id: number): void {
    console.log(id, this.selectedRoom);
    this.reservationService.updateReservation(this.selectedRoom, id).pipe().subscribe();
  }

  onSelect(value: any): void {
    this.selectedRoom = value;
    console.log(this.selectedRoom);

  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
