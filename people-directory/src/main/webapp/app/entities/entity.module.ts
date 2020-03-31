import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-group',
        loadChildren: () => import('./user-group/user-group.module').then(m => m.PeopledirectoryUserGroupModule)
      },
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.PeopledirectoryPersonModule)
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.PeopledirectoryContactModule)
      },
      {
        path: 'setting',
        loadChildren: () => import('./setting/setting.module').then(m => m.PeopledirectorySettingModule)
      },
      {
        path: 'name',
        loadChildren: () => import('./name/name.module').then(m => m.PeopledirectoryNameModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.PeopledirectoryLocationModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.PeopledirectoryDepartmentModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PeopledirectoryEntityModule {}
