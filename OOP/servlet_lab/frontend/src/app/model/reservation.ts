import {Course} from './course';

export interface Reservation {
  id?: number;
  places: number;
  checkIn: Date;
  checkOut: Date;
  type: string;
  userByUserId: number;
  roomId?: Course;
  status: string;

}
