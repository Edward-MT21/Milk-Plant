import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MilkCollectionComponent } from './milk-collection/milk-collection.component';
import { MenuComponent } from './menu/menu.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MilkCollectionComponent, MenuComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'milk-plant-app';
}
