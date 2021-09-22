import { Specification } from "./specification";

export class Color {
    colorId: number | undefined;
    name: string | undefined;
    code: string | undefined;
    hex: string | undefined;
    specificationEntities: Specification[] | undefined;

    constructor(colorId?: number, name?: string, code?: string, hex?: string) {
        this.colorId = colorId;
        this.name = name;
        this.code = code;
        this.hex = hex;
    }
}
