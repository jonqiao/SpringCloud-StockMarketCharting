import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyCompareComponent } from './company-compare.component';

describe('CompanyCompareComponent', () => {
  let component: CompanyCompareComponent;
  let fixture: ComponentFixture<CompanyCompareComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompanyCompareComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyCompareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
