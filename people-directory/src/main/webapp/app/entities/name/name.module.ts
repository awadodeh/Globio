import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeopledirectorySharedModule } from 'app/shared/shared.module';
import { NameComponent } from './name.component';
import { NameDetailComponent } from './name-detail.component';
import { NameUpdateComponent } from './name-update.component';
import { NameDeleteDialogComponent } from './name-delete-dialog.component';
import { nameRoute } from './name.route';

@NgModule({
  imports: [PeopledirectorySharedModule, RouterModule.forChild(nameRoute)],
  declarations: [NameComponent, NameDetailComponent, NameUpdateComponent, NameDeleteDialogComponent],
  entryComponents: [NameDeleteDialogComponent]
})
export class PeopledirectoryNameModule {}
