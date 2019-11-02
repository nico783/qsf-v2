export interface ICommandExample {
  id?: number;
  description?: string;
  receiptExampleId?: number;
}

export class CommandExample implements ICommandExample {
  constructor(public id?: number, public description?: string, public receiptExampleId?: number) {}
}
