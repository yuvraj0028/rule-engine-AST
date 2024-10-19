import { TestBed } from '@angular/core/testing';

import { EvaluateRuleService } from './evaluate-rule.service';

describe('EvaluateRuleService', () => {
  let service: EvaluateRuleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EvaluateRuleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
