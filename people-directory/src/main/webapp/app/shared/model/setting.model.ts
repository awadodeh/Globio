export interface ISetting {
  id?: number;
  memberVisible?: boolean;
  selfAdd?: boolean;
  allowSubGroup?: boolean;
}

export class Setting implements ISetting {
  constructor(public id?: number, public memberVisible?: boolean, public selfAdd?: boolean, public allowSubGroup?: boolean) {
    this.memberVisible = this.memberVisible || false;
    this.selfAdd = this.selfAdd || false;
    this.allowSubGroup = this.allowSubGroup || false;
  }
}
