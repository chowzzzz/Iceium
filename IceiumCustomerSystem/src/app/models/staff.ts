import { AccessRightEnum } from './enum/access-right-enum.enum';

export class Staff {
    staffId: number | undefined;
    firstName: string | undefined;
    lastName: string | undefined;
    accessRightEnum: AccessRightEnum | undefined;
    username: string | undefined;
    password: string | undefined;

    constructor(
        staffId?: number,
        firstName?: string,
        lastName?: string,
        accessRightEnum?: AccessRightEnum,
        username?: string,
        password?: string
    ) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.accessRightEnum = accessRightEnum;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}
