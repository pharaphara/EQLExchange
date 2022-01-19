import { TransferDto } from "./transferDto";

export interface ResultTransferDto {
    transferDto: TransferDto,
    transferOk: boolean,
    message: string
  }