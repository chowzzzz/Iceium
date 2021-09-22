import {
    Component,
    OnInit,
    Output,
    EventEmitter,
    Renderer2,
    ElementRef,
} from '@angular/core';
import { Color } from 'src/app/models/color';
import { ColorService } from 'src/app/services/color.service';
@Component({
    selector: 'app-colors',
    templateUrl: './colors.component.html',
    styleUrls: ['./colors.component.css'],
})
export class ColorsComponent implements OnInit {
    @Output() changeColorEvent = new EventEmitter<Color>();
    isActive: boolean = false;
    colors: Color[];
    selectedColor: Color | undefined;

    constructor(
        private el: ElementRef,
        private renderer: Renderer2,
        private colorService: ColorService
    ) {
        this.colors = new Array();
    }

    ngOnInit(): void {
        this.fetchColors();
    }

    fetchColors() {
        this.colorService.getColors().subscribe((res) => {
            this.colors = res;
        });
    }
    changeColor(event: any, newColor: Color) {
        this.changeColorEvent.emit(newColor);
        const actives = document.querySelectorAll('.active');
        actives.forEach((active) => {
            active.classList.remove('active');
        });
        this.renderer.addClass(event.target, 'active');
    }
}
