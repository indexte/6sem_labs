import {Component, OnInit} from '@angular/core';
import {ReservationService} from '../service/reservation.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../service/auth-service';
import {Reservation} from '../model/reservation';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {
  reservationForm: FormGroup;

  constructor(private reservationService: ReservationService,
              private formBuilder: FormBuilder,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.reservationForm = this.formBuilder.group(
      {
        places: ['', Validators.required],
        checkIn: ['', Validators.required],
        checkOut: ['', Validators.required],
        type: ['', Validators.required]
      }
    );
  }

  buildReservation(): any {
    const reserv = {
      places: this.inputs.places.value,
      checkIn: this.inputs.checkIn.value,
      checkOut: this.inputs.checkOut.value,
      type: this.inputs.type.value,
      userByUserId: this.authService.currentUserValue,
      status: 'PENDING'
    };
    console.log(reserv);
    return reserv ;
  }

  get inputs(): any {
    return this.reservationForm.controls;
  }

  createNewReservationByUser(): any {
    return this.reservationService.createReservationByUser(this.buildReservation()).subscribe();

  }

}
