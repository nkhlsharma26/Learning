import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AidDetailsComponent } from './aid-details/aid-details.component';
import { CreateAidComponent } from './create-aid/create-aid.component';
import { CreateHouseComponent } from './create-house/create-house.component';
import { ListAidComponent } from './list-aid/list-aid.component';
import { ListHouseComponent } from './list-house/list-house.component';
import { UpdateAidComponent } from './update-aid/update-aid.component';

const routes: Routes = [
  { path: '', component: ListHouseComponent },
  { path: 'addHouse', component: CreateHouseComponent },
  { path: 'addAid', component: CreateAidComponent },
  { path: 'ListAids', component: ListAidComponent },
  { path: 'showAid', component: AidDetailsComponent },
  { path: 'updateAid', component: UpdateAidComponent },
  { path: ':houseId/getAllAidsForHouse', component: ListAidComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
