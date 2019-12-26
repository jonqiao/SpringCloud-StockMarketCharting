import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectorCompareComponent } from './sector-compare.component';

describe('SectorCompareComponent', () => {
  let component: SectorCompareComponent;
  let fixture: ComponentFixture<SectorCompareComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectorCompareComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectorCompareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
