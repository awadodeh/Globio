import { ILocation } from 'app/shared/model/location.model';

export interface IDepartment {
  id?: number;
  departmentName?: string;
  departmentID?: string;
  location?: ILocation;
}

export class Department implements IDepartment {
  constructor(public id?: number, public departmentName?: string, public departmentID?: string, public location?: ILocation) {}
}
