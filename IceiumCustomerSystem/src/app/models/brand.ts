import { Model } from "./model";

export class Brand {
    brandId: number | undefined;
    name: string | undefined;
    code: string | undefined;
    modelEntities: Model[] | undefined;

    constructor(brandId?: number, name?: string, code?: string) {
        this.brandId = brandId;
        this.name = name;
        this.code = code;
    }
}
