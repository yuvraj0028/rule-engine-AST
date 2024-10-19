import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluateRuleComponent } from './evaluate-rule.component';

describe('EvaluateRuleComponent', () => {
  let component: EvaluateRuleComponent;
  let fixture: ComponentFixture<EvaluateRuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EvaluateRuleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EvaluateRuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
