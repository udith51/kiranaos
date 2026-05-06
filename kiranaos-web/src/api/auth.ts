import api from '@/lib/axios';
import type { AuthRequest, AuthResponse } from '@/types/auth';

export const login = (request: AuthRequest): Promise<AuthResponse> => {
  return api.post<AuthResponse>('/auth/login', request).then((res) => res.data);
};

export const register = (request: AuthRequest): Promise<AuthResponse> => {
  return api
    .post<AuthResponse>('/auth/register', request)
    .then((res) => res.data);
};
