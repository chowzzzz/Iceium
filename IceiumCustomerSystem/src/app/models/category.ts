import { Product } from './product';

export class Category {
    categoryId: number | undefined;
    name: string | undefined;
    description: string | undefined;
    subCategoryEntities: Category[] | undefined;
    parentCategoryEntity: Category | undefined;
    productEntites: Product[] | undefined;

    constructor(categoryId?: number, name?: string, description?: string) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }
}
