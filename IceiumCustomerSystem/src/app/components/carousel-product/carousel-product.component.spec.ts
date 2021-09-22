import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarouselProductComponent } from './carousel-product.component';

describe('CarouselProductComponent', () => {
  let component: CarouselProductComponent;
  let fixture: ComponentFixture<CarouselProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarouselProductComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CarouselProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
