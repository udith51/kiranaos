import axios from 'axios';
import { env } from './env';
import { useAuthStore } from '@/store/AuthStore';

const api = axios.create({
  baseURL: env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const accessToken = useAuthStore.getState().accessToken;
  if (accessToken) config.headers.Authorization = `Bearer ${accessToken}`;
  return config;
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        const refreshToken = useAuthStore.getState().refreshToken;
        if (refreshToken) {
          const { data } = await axios.post(
            `${env.VITE_API_BASE_URL}/auth/refresh`,
            {
              refreshToken,
            },
          );
          useAuthStore
            .getState()
            .setAuth(data.user, data.accessToken, data.refreshToken);
          originalRequest.headers.Authorization = `Bearer ${data.accessToken}`;
          return api(originalRequest);
        }
        return Promise.reject(error);
      } catch (err) {
        useAuthStore.getState().clearAuth();
        window.location.href = '/login';
        return Promise.reject(err);
      }
    }

    return Promise.reject(error);
  },
);

export default api;
