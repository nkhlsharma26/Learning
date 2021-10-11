import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AidDetailsComponent } from './aid-details.component';

describe('AidDetailsComponent', () => {
  let component: AidDetailsComponent;
  let fixture: ComponentFixture<AidDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AidDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AidDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
