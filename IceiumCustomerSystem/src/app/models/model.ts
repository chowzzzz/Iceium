import { Brand } from "./brand";
import { Product } from "./product";

export class Model {
    modelId: number | undefined;
    name: string | undefined;
    code: string | undefined;
    brandEntity: Brand | undefined;
    productEntities: Product[] | undefined

    constructor(modelId?: number, name?: string, code?: string) {
        this.modelId = modelId;
        this.name = name;
        this.code = code;
    }
}
