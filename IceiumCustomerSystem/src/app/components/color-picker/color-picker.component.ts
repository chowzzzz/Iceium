import { Component, Input, OnInit } from '@angular/core';
import { Color } from 'src/app/models/color';

@Component({
    selector: 'app-color-picker',
    templateUrl: './color-picker.component.html',
    styleUrls: ['./color-picker.component.css'],
})
export class ColorPickerComponent implements OnInit {
    @Input() color: Color | undefined;

    constructor() {}

    ngOnInit(): void {}
}
