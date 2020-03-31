import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPerson, Person } from 'app/shared/model/person.model';
import { PersonService } from './person.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';
import { IName } from 'app/shared/model/name.model';
import { NameService } from 'app/entities/name/name.service';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group/user-group.service';

type SelectableEntity = ILocation | IDepartment | IContact | IName | IUserGroup;

@Component({
  selector: 'jhi-person-update',
  templateUrl: './person-update.component.html'
})
export class PersonUpdateComponent implements OnInit {
  isSaving = false;
  locations: ILocation[] = [];
  departments: IDepartment[] = [];
  contacts: IContact[] = [];
  names: IName[] = [];
  usergroups: IUserGroup[] = [];

  editForm = this.fb.group({
    id: [],
    email: [],
    phoneNumber: [],
    image: [],
    location: [],
    department: [],
    contact: [],
    name: [],
    userGroup: []
  });

  constructor(
    protected personService: PersonService,
    protected locationService: LocationService,
    protected departmentService: DepartmentService,
    protected contactService: ContactService,
    protected nameService: NameService,
    protected userGroupService: UserGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ person }) => {
      this.updateForm(person);

      this.locationService
        .query({ filter: 'person-is-null' })
        .pipe(
          map((res: HttpResponse<ILocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILocation[]) => {
          if (!person.location || !person.location.id) {
            this.locations = resBody;
          } else {
            this.locationService
              .find(person.location.id)
              .pipe(
                map((subRes: HttpResponse<ILocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocation[]) => (this.locations = concatRes));
          }
        });

      this.departmentService
        .query({ filter: 'person-is-null' })
        .pipe(
          map((res: HttpResponse<IDepartment[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDepartment[]) => {
          if (!person.department || !person.department.id) {
            this.departments = resBody;
          } else {
            this.departmentService
              .find(person.department.id)
              .pipe(
                map((subRes: HttpResponse<IDepartment>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDepartment[]) => (this.departments = concatRes));
          }
        });

      this.contactService
        .query({ filter: 'person-is-null' })
        .pipe(
          map((res: HttpResponse<IContact[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IContact[]) => {
          if (!person.contact || !person.contact.id) {
            this.contacts = resBody;
          } else {
            this.contactService
              .find(person.contact.id)
              .pipe(
                map((subRes: HttpResponse<IContact>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IContact[]) => (this.contacts = concatRes));
          }
        });

      this.nameService
        .query({ filter: 'person-is-null' })
        .pipe(
          map((res: HttpResponse<IName[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IName[]) => {
          if (!person.name || !person.name.id) {
            this.names = resBody;
          } else {
            this.nameService
              .find(person.name.id)
              .pipe(
                map((subRes: HttpResponse<IName>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IName[]) => (this.names = concatRes));
          }
        });

      this.userGroupService.query().subscribe((res: HttpResponse<IUserGroup[]>) => (this.usergroups = res.body || []));
    });
  }

  updateForm(person: IPerson): void {
    this.editForm.patchValue({
      id: person.id,
      email: person.email,
      phoneNumber: person.phoneNumber,
      image: person.image,
      location: person.location,
      department: person.department,
      contact: person.contact,
      name: person.name,
      userGroup: person.userGroup
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const person = this.createFromForm();
    if (person.id !== undefined) {
      this.subscribeToSaveResponse(this.personService.update(person));
    } else {
      this.subscribeToSaveResponse(this.personService.create(person));
    }
  }

  private createFromForm(): IPerson {
    return {
      ...new Person(),
      id: this.editForm.get(['id'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      image: this.editForm.get(['image'])!.value,
      location: this.editForm.get(['location'])!.value,
      department: this.editForm.get(['department'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      name: this.editForm.get(['name'])!.value,
      userGroup: this.editForm.get(['userGroup'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
