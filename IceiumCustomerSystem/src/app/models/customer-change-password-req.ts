export class CustomerChangePasswordReq {
    username: string | undefined;
    oldPwd: string | undefined;
    newPwd: string | undefined;

    constructor(username?: string, oldPwd?: string, newPwd?: string) {
        this.username = username;
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }
}
