export class File {
    id?: number;      // Optional because it's generated by the backend
    name!: string;
    type!: string;
    size!: number;
    data!: Blob; 
}