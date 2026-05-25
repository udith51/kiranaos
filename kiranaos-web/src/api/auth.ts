import api from '@/lib/axios';
import type {
  AuthRequest,
  AuthResponse,
  RefreshTokenRequest,
} from '@/types/auth';
import type { MessageResponse } from '@/types/common';

export const login = (request: AuthRequest): Promise<AuthResponse> => {
  return api.post<AuthResponse>('/auth/login', request).then((res) => res.data);
};

export const register = (request: AuthRequest): Promise<AuthResponse> => {
  return api
    .post<AuthResponse>('/auth/register', request)
    .then((res) => res.data);
};

export const logout = (
  request: RefreshTokenRequest,
): Promise<MessageResponse> => {
  return api
    .post<MessageResponse>('/auth/logout', request)
    .then((res) => res.data);
};
