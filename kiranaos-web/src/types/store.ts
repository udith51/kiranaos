export interface CreateStoreRequest {
  name: string;
  address?: string;
  phone?: string;
  gstNumber?: string;
  logoUrl?: string;
}

export interface StoreResponse {
  id: string;
  name: string;
  address: string;
  phone: string;
  gstNumber: string;
  logoUrl: string;
}

export interface UpdateStoreRequest {
  name?: string;
  address?: string;
  phone?: string;
  gstNumber?: string;
  logoUrl?: string;
}
