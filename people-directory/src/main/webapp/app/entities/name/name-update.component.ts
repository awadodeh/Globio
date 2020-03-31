import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IName, Name } from 'app/shared/model/name.model';
import { NameService } from './name.service';

@Component({
  selector: 'jhi-name-update',
  templateUrl: './name-update.component.html'
})
export class NameUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    middleName: [],
    preferedName: []
  });

  constructor(protected nameService: NameService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ name }) => {
      this.updateForm(name);
    });
  }

  updateForm(name: IName): void {
    this.editForm.patchValue({
      id: name.id,
      firstName: name.firstName,
      lastName: name.lastName,
      middleName: name.middleName,
      preferedName: name.preferedName
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const name = this.createFromForm();
    if (name.id !== undefined) {
      this.subscribeToSaveResponse(this.nameService.update(name));
    } else {
      this.subscribeToSaveResponse(this.nameService.create(name));
    }
  }

  private createFromForm(): IName {
    return {
      ...new Name(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      preferedName: this.editForm.get(['preferedName'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IName>>): void {
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
}
