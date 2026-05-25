import api from '@/lib/axios';
import type {
  StockAdjustmentRequest,
  StockAdjustmentResponse,
} from '@/types/stockAdjustment';

export const adjustStock = (
  productId: string,
  request: StockAdjustmentRequest,
): Promise<StockAdjustmentResponse> => {
  return api
    .post<StockAdjustmentResponse>(`/adjustments/product/${productId}`, request)
    .then((res) => res.data);
};

export const productTrails = (
  productId: string,
): Promise<StockAdjustmentResponse> => {
  return api
    .get<StockAdjustmentResponse>(`/adjustments/product/${productId}`)
    .then((res) => res.data);
};

export const storeTrails = (): Promise<StockAdjustmentRequest[]> => {
  return api
    .get<StockAdjustmentRequest[]>('/adjustments/store')
    .then((res) => res.data);
};
