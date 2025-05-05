export class Subgroup {
  constructor(
    public subgroupId: number,
    public subgroupName: string,
    public teamName: string,
    public users: any[] = [],
    public trainingSessions: any[] = [],
    public label?: string
  ) {}
}
