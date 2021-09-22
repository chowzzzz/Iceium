import { Color } from './color';
import { Product } from './product';
import { Size } from './size';
import { Specification } from './specification';

export class CreateCustomizeProductReq {
    newProductEntity: Product | undefined;
    modelId: number | undefined;
    specificationEntity: Specification | undefined;
    colorEntity: Color | undefined;
    sizeEntity: Size | undefined;

    constructor(
        newProductEntity?: Product,
        modelId?: number,
        specificationEntity?: Specification,
        colorEntity?: Color,
        sizeEntity?: Size
    ) {
        this.newProductEntity = newProductEntity;
        this.modelId = modelId;
        this.specificationEntity = specificationEntity;
        this.colorEntity = colorEntity;
        this.sizeEntity = sizeEntity;
    }
}
