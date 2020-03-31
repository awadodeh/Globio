import { ILocation } from 'app/shared/model/location.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IContact } from 'app/shared/model/contact.model';
import { IName } from 'app/shared/model/name.model';
import { IUserGroup } from 'app/shared/model/user-group.model';

export interface IPerson {
  id?: number;
  email?: string;
  phoneNumber?: string;
  image?: string;
  location?: ILocation;
  department?: IDepartment;
  contact?: IContact;
  name?: IName;
  contacts?: IUserGroup[];
  userGroup?: IUserGroup;
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public email?: string,
    public phoneNumber?: string,
    public image?: string,
    public location?: ILocation,
    public department?: IDepartment,
    public contact?: IContact,
    public name?: IName,
    public contacts?: IUserGroup[],
    public userGroup?: IUserGroup
  ) {}
}
