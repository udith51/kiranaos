export type UnitType = 'KG' | 'PIECE' | 'LITRE' | 'PACKET';

export interface CreateProductRequest {
  name: string;
  category: string;
  unit: UnitType;
  price: number;
  gstRate?: number;
  stockQuantity: number;
  reorderThreshold?: number;
}

export interface UpdateProductRequest {
  name?: string;
  category?: string;
  unit?: UnitType;
  price?: number;
  gstRate?: number;
  stockQuantity?: number;
  reorderThreshold?: number;
}

export interface ProductResponse {
  id: string;
  storeId: string;
  name: string;
  category: string;
  unit: UnitType;
  price: number;
  gstRate: number;
  stockQuantity: number;
  reorderThreshold: number;
}

export interface ProductImportResponse {
  successCount: number;
  failedCount: number;
  failedReasons: string[];
}
