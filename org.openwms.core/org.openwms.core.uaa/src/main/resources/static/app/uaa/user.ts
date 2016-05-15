export class User {
    id: number; // make private when binding to backend
    appid: number;
    username: string;
    fullname: string;
    email: string;
    locked: boolean;
    enabled: boolean;
    
    constructor(username:string){
        this.appid = Math.floor(Math.random() * 1000);
        this.username = username;
    }
}
