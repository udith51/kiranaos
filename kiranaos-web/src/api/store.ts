import api from '@/lib/axios';
import type {
  CreateStoreRequest,
  StoreResponse,
  UpdateStoreRequest,
} from '@/types/store';

export const createStore = (
  request: CreateStoreRequest,
): Promise<StoreResponse> => {
  return api
    .post<StoreResponse>('/store/profile', request)
    .then((res) => res.data);
};

export const getStore = (): Promise<StoreResponse> => {
  return api.get<StoreResponse>('/store/profile').then((res) => res.data);
};

export const updateStore = (
  request: UpdateStoreRequest,
): Promise<StoreResponse> => {
  return api
    .put<StoreResponse>('/store/profile', request)
    .then((res) => res.data);
};
