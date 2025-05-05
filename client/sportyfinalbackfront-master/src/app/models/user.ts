export type Role = 'PLAYER' | 'COACH' | 'DOCTOR';


export class User {
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public email: string,
    public role: string 
  ) {}
}
