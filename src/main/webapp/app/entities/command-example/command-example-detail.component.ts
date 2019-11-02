import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandExample } from 'app/shared/model/command-example.model';

@Component({
  selector: 'jhi-command-example-detail',
  templateUrl: './command-example-detail.component.html'
})
export class CommandExampleDetailComponent implements OnInit {
  commandExample: ICommandExample;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ commandExample }) => {
      this.commandExample = commandExample;
    });
  }

  previousState() {
    window.history.back();
  }
}
