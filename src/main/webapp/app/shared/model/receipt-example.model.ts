export interface IReceiptExample {
  id?: number;
  state?: string;
}

export class ReceiptExample implements IReceiptExample {
  constructor(public id?: number, public state?: string) {}
}
