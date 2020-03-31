export interface IName {
  id?: number;
  firstName?: string;
  lastName?: string;
  middleName?: string;
  preferedName?: string;
}

export class Name implements IName {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public middleName?: string,
    public preferedName?: string
  ) {}
}
