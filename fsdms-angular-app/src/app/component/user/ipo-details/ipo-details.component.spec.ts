import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IpoDetailsComponent } from './ipo-details.component';

describe('IpoDetailsComponent', () => {
  let component: IpoDetailsComponent;
  let fixture: ComponentFixture<IpoDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IpoDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IpoDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
