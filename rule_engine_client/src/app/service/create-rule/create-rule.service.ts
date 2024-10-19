import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateRule } from '../../model/create-rule.model';
import { Observable } from 'rxjs';
import { RuleEndpoints } from '../../util/api-endpoints';
import { SnackBarService } from '../snack-bar/snack-bar.service';

@Injectable({
  providedIn: 'root',
})
export class CreateRuleService {
  responseData!: any;
  constructor(
    private http: HttpClient,
    private snackBarService: SnackBarService
  ) {}

  createRule(createRule: CreateRule): void {
    try {
      this.http
        .post(
          RuleEndpoints.CONTEXT_PATH + RuleEndpoints.CREATE_RULE,
          createRule
        )
        .subscribe(
          (data) => {
            this.responseData = data;
            this.snackBarService.openSnackBar('Rule created successfully');
          },
          (error) => {
            this.responseData = error;
            this.snackBarService.openSnackBar(
              'Rule creation failed, ERROR : ' +
                this.responseData.error.errorMessage
            );
          }
        );
    } catch (error) {
      this.snackBarService.openSnackBar(
        'Rule creation failed, ERROR : ' + error
      );
    }
  }
}
