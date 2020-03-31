import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IUserGroup, UserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from './user-group.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';
import { ISetting } from 'app/shared/model/setting.model';
import { SettingService } from 'app/entities/setting/setting.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person/person.service';

type SelectableEntity = IContact | ISetting | IUserGroup | IPerson;

@Component({
  selector: 'jhi-user-group-update',
  templateUrl: './user-group-update.component.html'
})
export class UserGroupUpdateComponent implements OnInit {
  isSaving = false;
  contacts: IContact[] = [];
  settings: ISetting[] = [];
  parents: IUserGroup[] = [];
  people: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    groupID: [],
    groupName: [],
    groupDescription: [],
    groupOwner: [],
    lastUpdated: [],
    contact: [],
    setting: [],
    parent: [],
    person: []
  });

  constructor(
    protected userGroupService: UserGroupService,
    protected contactService: ContactService,
    protected settingService: SettingService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userGroup }) => {
      this.updateForm(userGroup);

      this.contactService
        .query({ filter: 'usergroup-is-null' })
        .pipe(
          map((res: HttpResponse<IContact[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IContact[]) => {
          if (!userGroup.contact || !userGroup.contact.id) {
            this.contacts = resBody;
          } else {
            this.contactService
              .find(userGroup.contact.id)
              .pipe(
                map((subRes: HttpResponse<IContact>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IContact[]) => (this.contacts = concatRes));
          }
        });

      this.settingService
        .query({ filter: 'usergroup-is-null' })
        .pipe(
          map((res: HttpResponse<ISetting[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ISetting[]) => {
          if (!userGroup.setting || !userGroup.setting.id) {
            this.settings = resBody;
          } else {
            this.settingService
              .find(userGroup.setting.id)
              .pipe(
                map((subRes: HttpResponse<ISetting>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ISetting[]) => (this.settings = concatRes));
          }
        });

      this.userGroupService
        .query({ filter: 'usergroup-is-null' })
        .pipe(
          map((res: HttpResponse<IUserGroup[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IUserGroup[]) => {
          if (!userGroup.parent || !userGroup.parent.id) {
            this.parents = resBody;
          } else {
            this.userGroupService
              .find(userGroup.parent.id)
              .pipe(
                map((subRes: HttpResponse<IUserGroup>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IUserGroup[]) => (this.parents = concatRes));
          }
        });

      this.personService.query().subscribe((res: HttpResponse<IPerson[]>) => (this.people = res.body || []));
    });
  }

  updateForm(userGroup: IUserGroup): void {
    this.editForm.patchValue({
      id: userGroup.id,
      groupID: userGroup.groupID,
      groupName: userGroup.groupName,
      groupDescription: userGroup.groupDescription,
      groupOwner: userGroup.groupOwner,
      lastUpdated: userGroup.lastUpdated,
      contact: userGroup.contact,
      setting: userGroup.setting,
      parent: userGroup.parent,
      person: userGroup.person
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userGroup = this.createFromForm();
    if (userGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.userGroupService.update(userGroup));
    } else {
      this.subscribeToSaveResponse(this.userGroupService.create(userGroup));
    }
  }

  private createFromForm(): IUserGroup {
    return {
      ...new UserGroup(),
      id: this.editForm.get(['id'])!.value,
      groupID: this.editForm.get(['groupID'])!.value,
      groupName: this.editForm.get(['groupName'])!.value,
      groupDescription: this.editForm.get(['groupDescription'])!.value,
      groupOwner: this.editForm.get(['groupOwner'])!.value,
      lastUpdated: this.editForm.get(['lastUpdated'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      setting: this.editForm.get(['setting'])!.value,
      parent: this.editForm.get(['parent'])!.value,
      person: this.editForm.get(['person'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserGroup>>): void {
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
