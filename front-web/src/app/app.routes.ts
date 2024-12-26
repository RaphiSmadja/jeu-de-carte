import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { BattleComponent } from './components/battle/battle.component';

export const routes: Routes = [
    { path: '', component: AppComponent },
    { path: 'battle', component: BattleComponent }]