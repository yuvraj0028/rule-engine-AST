import { TestBed } from '@angular/core/testing';

import { CreateRuleService } from './create-rule.service';

describe('CreateRuleService', () => {
  let service: CreateRuleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateRuleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
