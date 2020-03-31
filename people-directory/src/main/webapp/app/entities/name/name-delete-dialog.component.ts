import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IName } from 'app/shared/model/name.model';
import { NameService } from './name.service';

@Component({
  templateUrl: './name-delete-dialog.component.html'
})
export class NameDeleteDialogComponent {
  name?: IName;

  constructor(protected nameService: NameService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nameService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nameListModification');
      this.activeModal.close();
    });
  }
}
