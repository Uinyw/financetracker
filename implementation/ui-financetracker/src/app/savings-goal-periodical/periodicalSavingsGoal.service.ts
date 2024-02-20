import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PeriodicalSavingsGoal } from './periodicalSavingsGoal';

@Injectable({
  providedIn: 'root',
})
export class PeriodicalSavingsGoalService {

  BASE_URL = "http://localhost:8084"

  constructor(private httpClient: HttpClient) {
  }

  getAllPeriodicalSavingsGoals(): Observable<PeriodicalSavingsGoal[]> {
    return this.httpClient.get<PeriodicalSavingsGoal[]>(`${this.BASE_URL}/savings-goals/periodical`);
  }

  getPeriodicalSavingsGoalById(savingsGoalId: string): Observable<PeriodicalSavingsGoal> {
    return this.httpClient.get<PeriodicalSavingsGoal>(`${this.BASE_URL}/savings-goals/periodical/${savingsGoalId}`);
  }

  editPeriodicalSavingsGoal(savingsGoal: PeriodicalSavingsGoal): Observable<any> {
    return this.httpClient.patch<any>(`${this.BASE_URL}/savings-goals/periodical/${savingsGoal.id}`, savingsGoal);
  }

  createPeriodicalSavingsGoal(savingsGoal: PeriodicalSavingsGoal): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/savings-goals/periodical`, savingsGoal);
  }

  deletePeriodicalSavingsGoal(savingsGoalId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.BASE_URL}/savings-goals/periodical/${savingsGoalId}`);
  }

}