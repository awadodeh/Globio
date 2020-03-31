import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IName, Name } from 'app/shared/model/name.model';
import { NameService } from './name.service';
import { NameComponent } from './name.component';
import { NameDetailComponent } from './name-detail.component';
import { NameUpdateComponent } from './name-update.component';

@Injectable({ providedIn: 'root' })
export class NameResolve implements Resolve<IName> {
  constructor(private service: NameService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IName> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((name: HttpResponse<Name>) => {
          if (name.body) {
            return of(name.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Name());
  }
}

export const nameRoute: Routes = [
  {
    path: '',
    component: NameComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'peopledirectoryApp.name.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NameDetailComponent,
    resolve: {
      name: NameResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'peopledirectoryApp.name.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NameUpdateComponent,
    resolve: {
      name: NameResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'peopledirectoryApp.name.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NameUpdateComponent,
    resolve: {
      name: NameResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'peopledirectoryApp.name.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
