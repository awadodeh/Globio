import { IContact } from 'app/shared/model/contact.model';
import { ISetting } from 'app/shared/model/setting.model';
import { IPerson } from 'app/shared/model/person.model';

export interface IUserGroup {
  id?: number;
  groupID?: string;
  groupName?: string;
  groupDescription?: string;
  groupOwner?: string;
  lastUpdated?: string;
  contact?: IContact;
  setting?: ISetting;
  parent?: IUserGroup;
  members?: IPerson[];
  person?: IPerson;
}

export class UserGroup implements IUserGroup {
  constructor(
    public id?: number,
    public groupID?: string,
    public groupName?: string,
    public groupDescription?: string,
    public groupOwner?: string,
    public lastUpdated?: string,
    public contact?: IContact,
    public setting?: ISetting,
    public parent?: IUserGroup,
    public members?: IPerson[],
    public person?: IPerson
  ) {}
}
