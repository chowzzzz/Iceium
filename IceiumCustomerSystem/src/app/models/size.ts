import { SizeTypeEnum } from './enum/size-type-enum.enum';
import { Specification } from './specification';

export class Size {
    sizeId: number | undefined;
    size: number | undefined;
    sizeTypeEnum: SizeTypeEnum | undefined;
    code: string | undefined;
    specificationEntities: Specification[] | undefined;

    sizeString: string | undefined;
    sizeYString: string | undefined;
    sizeNString: string | undefined;

    constructor(
        sizeId?: number,
        size?: number,
        sizeTypeEnum?: SizeTypeEnum,
        code?: string
    ) {
        this.sizeId = sizeId;
        this.size = size;
        this.sizeTypeEnum = sizeTypeEnum;
        this.code = code;
    }
}
