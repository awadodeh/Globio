export interface IContact {
  id?: number;
  email?: string;
  ownerGroup?: string;
}

export class Contact implements IContact {
  constructor(public id?: number, public email?: string, public ownerGroup?: string) {}
}
