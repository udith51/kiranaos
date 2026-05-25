export type AdjustmentType = 'SALE' | 'RESTOCK';

export interface StockAdjustmentRequest {
  quantityChange: number;
  adjustmentType: AdjustmentType;
  reason?: string;
}

export interface StockAdjustmentResponse {
  id: string;
  storeId: string;
  productId: string;
  productName: string;
  adjustmentType: AdjustmentType;
  quantityChange: number;
  reason: string;
  createdAt: string;
}
