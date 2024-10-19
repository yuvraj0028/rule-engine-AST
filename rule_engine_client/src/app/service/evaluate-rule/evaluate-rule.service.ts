import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EvaluateRule } from '../../model/evaluate-rule.model';
import { RuleEndpoints } from '../../util/api-endpoints';
import { SnackBarService } from '../snack-bar/snack-bar.service';

@Injectable({
  providedIn: 'root',
})
export class EvaluateRuleService {
  data$!: Observable<any>;
  responseData!: any;
  constructor(
    private http: HttpClient,
    private snackBarService: SnackBarService
  ) {}

  evaluateRule(evaluateRule: EvaluateRule): void {
    try {
      this.data$ = this.http.post(
        RuleEndpoints.CONTEXT_PATH + RuleEndpoints.EVALUATE_RULE,
        evaluateRule
      );

      this.data$.subscribe(
        (data) => {
          this.responseData = data;
          this.snackBarService.openSnackBar(
            'Data evaluated successfully. OUTPUT : ' +
              this.responseData.responseData.eligible.toString().toUpperCase()
          );
        },
        (error) => {
          this.responseData = error;
          this.snackBarService.openSnackBar(
            'Data evaluation failed, ERROR : ' +
              this.responseData.error.errorMessage
          );
        }
      );
    } catch (error) {
      this.snackBarService.openSnackBar(
        'Data evaluation failed. ERROR : ' + error
      );
    }
  }
}
