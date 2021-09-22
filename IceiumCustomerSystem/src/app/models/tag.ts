import { Product } from './product';

export class Tag {
    tagId: number | undefined;
    name: string | undefined;
    description: string | undefined;
    productEntites: Product[] | undefined;

    constructor(tagId?: number, name?: string, description?: string) {
        this.tagId = tagId;
        this.name = name;
        this.description = description;
    }
}
