import api from '@/lib/axios';
import type { MessageResponse } from '@/types/common';
import type {
  CreateProductRequest,
  ProductImportResponse,
  ProductResponse,
  UpdateProductRequest,
} from '@/types/product';

export const createProduct = (
  request: CreateProductRequest,
): Promise<ProductResponse> => {
  return api.post<ProductResponse>('/product', request).then((res) => res.data);
};

export const getProduct = (productId: string): Promise<ProductResponse> => {
  return api
    .get<ProductResponse>(`/product/${productId}`)
    .then((res) => res.data);
};

export const getAllProducts = (): Promise<ProductResponse[]> => {
  return api.get<ProductResponse[]>('/product').then((res) => res.data);
};

export const updateProduct = (
  productId: string,
  request: UpdateProductRequest,
): Promise<ProductResponse> => {
  return api
    .put<ProductResponse>(`/product/${productId}`, request)
    .then((res) => res.data);
};

export const deleteProduct = (productId: string): Promise<MessageResponse> => {
  return api
    .delete<MessageResponse>(`/product/${productId}`)
    .then((res) => res.data);
};

export const getAllProductsByCategory = (
  category: string,
): Promise<ProductResponse[]> => {
  return api
    .get<ProductResponse[]>(`/product/category/${category}`)
    .then((res) => res.data);
};

export const getLowStockProducts = (): Promise<ProductResponse[]> => {
  return api
    .get<ProductResponse[]>('/product/low-stock')
    .then((res) => res.data);
};

export const bulkImport = (file: File): Promise<ProductImportResponse> => {
  const formData = new FormData();
  formData.append('file', file);
  return api
    .post<ProductImportResponse>('/product/bulk-import', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    })
    .then((res) => res.data);
};
