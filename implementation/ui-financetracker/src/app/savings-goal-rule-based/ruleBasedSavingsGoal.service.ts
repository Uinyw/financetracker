import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RuleBasedSavingsGoal } from './ruleBasedSavingsGoal';

@Injectable({
  providedIn: 'root',
})
export class RuleBasedSavingsGoalService {

  BASE_URL = "http://localhost:8084"

  constructor(private httpClient: HttpClient) {
  }

  getAllRuleBasedSavingsGoals(): Observable<RuleBasedSavingsGoal[]> {
    return this.httpClient.get<RuleBasedSavingsGoal[]>(`${this.BASE_URL}/savings-goals/rule-based`);
  }

  getRuleBasedSavingsGoalById(savingsGoalId: string): Observable<RuleBasedSavingsGoal> {
    return this.httpClient.get<RuleBasedSavingsGoal>(`${this.BASE_URL}/savings-goals/rule-based/${savingsGoalId}`);
  }

  editRuleBasedSavingsGoal(savingsGoal: RuleBasedSavingsGoal): Observable<any> {
    return this.httpClient.patch<any>(`${this.BASE_URL}/savings-goals/rule-based/${savingsGoal.id}`, savingsGoal);
  }

  createRuleBasedSavingsGoal(savingsGoal: RuleBasedSavingsGoal): Observable<any> {
    return this.httpClient.post<any>(`${this.BASE_URL}/savings-goals/rule-based`, savingsGoal);
  }

  deleteRuleBasedSavingsGoal(savingsGoalId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.BASE_URL}/savings-goals/rule-based/${savingsGoalId}`);
  }

}