import { House } from "./house";
import { Payment } from "./payment";

export class Aid{
aidId: number;
name : string;
startDate: string;
noOfServiceDays: number;
perDayCost: number;
payment : Payment;
paid: boolean;
complete: boolean;
house: House;
}