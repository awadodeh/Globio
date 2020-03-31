import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IName } from 'app/shared/model/name.model';
import { NameService } from './name.service';
import { NameDeleteDialogComponent } from './name-delete-dialog.component';

@Component({
  selector: 'jhi-name',
  templateUrl: './name.component.html'
})
export class NameComponent implements OnInit, OnDestroy {
  names?: IName[];
  eventSubscriber?: Subscription;

  constructor(protected nameService: NameService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.nameService.query().subscribe((res: HttpResponse<IName[]>) => (this.names = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNames();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IName): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNames(): void {
    this.eventSubscriber = this.eventManager.subscribe('nameListModification', () => this.loadAll());
  }

  delete(name: IName): void {
    const modalRef = this.modalService.open(NameDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.name = name;
  }
}
