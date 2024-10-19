import { Component } from '@angular/core';
import { CreateRuleComponent } from '../create-rule/create-rule.component';
import { InfoComponent } from '../info/info.component';
import { EvaluateRuleComponent } from '../evaluate-rule/evaluate-rule.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CreateRuleComponent,
    InfoComponent,
    EvaluateRuleComponent,
    CommonModule,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  currBtn: number = 0;

  createClick(): void {
    this.currBtn = 0;
  }

  evaluateClick(): void {
    this.currBtn = 1;
  }
}
