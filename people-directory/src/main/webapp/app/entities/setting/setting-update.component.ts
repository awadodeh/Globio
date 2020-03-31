import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISetting, Setting } from 'app/shared/model/setting.model';
import { SettingService } from './setting.service';

@Component({
  selector: 'jhi-setting-update',
  templateUrl: './setting-update.component.html'
})
export class SettingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    memberVisible: [],
    selfAdd: [],
    allowSubGroup: []
  });

  constructor(protected settingService: SettingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ setting }) => {
      this.updateForm(setting);
    });
  }

  updateForm(setting: ISetting): void {
    this.editForm.patchValue({
      id: setting.id,
      memberVisible: setting.memberVisible,
      selfAdd: setting.selfAdd,
      allowSubGroup: setting.allowSubGroup
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const setting = this.createFromForm();
    if (setting.id !== undefined) {
      this.subscribeToSaveResponse(this.settingService.update(setting));
    } else {
      this.subscribeToSaveResponse(this.settingService.create(setting));
    }
  }

  private createFromForm(): ISetting {
    return {
      ...new Setting(),
      id: this.editForm.get(['id'])!.value,
      memberVisible: this.editForm.get(['memberVisible'])!.value,
      selfAdd: this.editForm.get(['selfAdd'])!.value,
      allowSubGroup: this.editForm.get(['allowSubGroup'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISetting>>): void {
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
