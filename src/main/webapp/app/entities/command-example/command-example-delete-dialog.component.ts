import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandExample } from 'app/shared/model/command-example.model';
import { CommandExampleService } from './command-example.service';

@Component({
  selector: 'jhi-command-example-delete-dialog',
  templateUrl: './command-example-delete-dialog.component.html'
})
export class CommandExampleDeleteDialogComponent {
  commandExample: ICommandExample;

  constructor(
    protected commandExampleService: CommandExampleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.commandExampleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'commandExampleListModification',
        content: 'Deleted an commandExample'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-command-example-delete-popup',
  template: ''
})
export class CommandExampleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ commandExample }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CommandExampleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.commandExample = commandExample;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/command-example', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/command-example', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
