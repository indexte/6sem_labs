import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Reservation} from '../model/reservation';
import {Feedback} from '../model/feedback';

@Injectable({
    providedIn: 'root'
})
export class FeedbackService {
    url = 'http://localhost:8080/feedback';
    urlCabinet = 'http://localhost:8080/cabinet';
    urlAdmin = 'http://localhost:8080/admin';

    constructor(private httpClient: HttpClient) {
    }


    getAllFeedbacksByUser(id: number): Observable<Feedback[]> {
        return this.httpClient.get<Feedback[]>(this.url + '?user=' + id);
    }

    createReservationByUser(reservation: Reservation): any {
        console.log(reservation);
        return this.httpClient.post<Reservation>(this.url, {reservation});
    }

    getAllReservations(): Observable<Reservation[]> {
        return this.httpClient.get<Reservation[]>(this.url);
    }

    updateReservation(roomId: number, id: number): Observable<any> {
        return this.httpClient.get<any>(this.urlAdmin + '?room_id=' + roomId + '&id=' + id);
    }
}
