import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PeopledirectorySharedModule } from 'app/shared/shared.module';

import { DocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [PeopledirectorySharedModule, RouterModule.forChild([docsRoute])],
  declarations: [DocsComponent]
})
export class DocsModule {}
