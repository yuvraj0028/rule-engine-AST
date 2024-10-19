import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CreateRuleService } from '../../service/create-rule/create-rule.service';
import { CreateRule } from '../../model/create-rule.model';
import { SnackBarService } from '../../service/snack-bar/snack-bar.service';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-create-rule',
  standalone: true,
  imports: [CommonModule, FormsModule, NgxSpinnerModule],
  templateUrl: './create-rule.component.html',
  styleUrl: './create-rule.component.scss',
})
export class CreateRuleComponent implements OnInit {
  username: string = '';
  expression: string = '';
  chips: string[] = [];
  createRule!: CreateRule;

  constructor(
    private createRuleService: CreateRuleService,
    private snackBarService: SnackBarService,
    private spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.createRule = {
      metaData: {
        uploadedBy: '',
      },
      ruleExpression: [],
    };
  }

  addExpression(): void {
    if (this.expression.trim() === '') return;
    this.chips.push(this.expression.trim());
    this.expression = '';
  }

  removeChip(index: number): void {
    if (index < 0 || index >= this.chips.length) return;
    this.chips.splice(index, 1);
  }

  clearForm(): void {
    this.username = '';
    this.expression = '';
    this.chips = [];
  }

  submitForm(): void {
    if (this.username.trim() === '') {
      this.snackBarService.openSnackBar('username is required');
      return;
    }

    if (this.chips.length === 0) {
      this.snackBarService.openSnackBar('expression is required');
      return;
    }

    // populating createRule
    this.createRule.metaData.uploadedBy = this.username;
    this.createRule.ruleExpression = this.chips;

    // loading spinner start
    this.spinner.show();

    // call service
    this.createRuleService.createRule(this.createRule);

    // clear form
    this.clearForm();

    // loading spinner end
    this.spinner.hide();
  }
}
