import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PeopledirectorySharedModule } from 'app/shared/shared.module';
import { SettingComponent } from './setting.component';
import { SettingDetailComponent } from './setting-detail.component';
import { SettingUpdateComponent } from './setting-update.component';
import { SettingDeleteDialogComponent } from './setting-delete-dialog.component';
import { settingRoute } from './setting.route';

@NgModule({
  imports: [PeopledirectorySharedModule, RouterModule.forChild(settingRoute)],
  declarations: [SettingComponent, SettingDetailComponent, SettingUpdateComponent, SettingDeleteDialogComponent],
  entryComponents: [SettingDeleteDialogComponent]
})
export class PeopledirectorySettingModule {}
