import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EvaluateRule } from '../../model/evaluate-rule.model';
import { EvaluateRuleService } from '../../service/evaluate-rule/evaluate-rule.service';
import { SnackBarService } from '../../service/snack-bar/snack-bar.service';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-evaluate-rule',
  standalone: true,
  imports: [FormsModule, NgxSpinnerModule],
  templateUrl: './evaluate-rule.component.html',
  styleUrl: './evaluate-rule.component.scss',
})
export class EvaluateRuleComponent implements OnInit {
  age: number | undefined;
  department: string = '';
  salary: number | undefined;
  experience: number | undefined;
  evaluateRule!: EvaluateRule;

  constructor(
    private evaluateRuleService: EvaluateRuleService,
    private snackBarService: SnackBarService,
    private spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.evaluateRule = {
      age: -1,
      department: '',
      salary: -1,
      experience: -1,
    };
  }

  clearForm(): void {
    this.age = undefined;
    this.department = '';
    this.salary = undefined;
    this.experience = undefined;
  }

  evaluate(): void {
    // call service
    if (
      this.age === undefined ||
      this.salary === undefined ||
      this.experience === undefined ||
      this.department.trim() === ''
    ) {
      this.snackBarService.openSnackBar('All fields are required.');
      return;
    }

    if (this.age < 0 || this.salary < 0 || this.experience < 0) {
      this.snackBarService.openSnackBar(
        'Age or Salary or Experience cannot be negative.'
      );
      return;
    }

    // populating evaluateRule
    this.evaluateRule.age = this.age;
    this.evaluateRule.department = this.department.trim();
    this.evaluateRule.salary = this.salary;
    this.evaluateRule.experience = this.experience;

    // loading spinner start
    this.spinner.show();

    // call service
    this.evaluateRuleService.evaluateRule(this.evaluateRule);

    // clear form
    this.clearForm();

    // loading spinner end
    this.spinner.hide();
  }
}
