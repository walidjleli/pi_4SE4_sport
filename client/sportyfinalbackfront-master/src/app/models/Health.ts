import { File } from "./File";

export class Health{
    id!:number;
    date!:Date;
    username!:string;
    userCondition !: String;
    notes!:String;

    file?: File;
  
}