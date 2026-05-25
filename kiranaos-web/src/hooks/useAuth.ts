import { login, logout, register } from '@/api/auth';
import { useAuthStore } from '@/store/AuthStore';
import { useMutation } from '@tanstack/react-query';
import axios from 'axios';
import { toast } from 'sonner';

export const useLogin = (onSuccess?: () => void) => {
  return useMutation({
    mutationFn: login,
    onSuccess: (data) => {
      useAuthStore
        .getState()
        .setAuth(data.userId, data.accessToken, data.refreshToken);
      toast.success('Login successfully');
      onSuccess?.();
    },
    onError: (error) => {
      if (axios.isAxiosError(error)) {
        toast.error(error.response?.data.message || 'Failed to login');
      } else toast.error('Failed to login');
    },
  });
};

export const useRegister = (onSuccess?: () => void) => {
  return useMutation({
    mutationFn: register,
    onSuccess: (data) => {
      useAuthStore
        .getState()
        .setAuth(data.userId, data.accessToken, data.refreshToken);
      toast.success('Register successfully');
      onSuccess?.();
    },
    onError: (error) => {
      if (axios.isAxiosError(error)) {
        toast.error(error.response?.data.message || 'Failed to register');
      } else toast.error('Failed to register');
    },
  });
};

export const useLogout = (onSuccess?: () => void) => {
  return useMutation({
    mutationFn: logout,
    onSuccess: () => {
      useAuthStore.getState().clearAuth();
      toast.success('Logout successfully');
      onSuccess?.();
    },
    onError: (error) => {
      if (axios.isAxiosError(error)) {
        toast.error(error.response?.data.message || 'Failed to logout');
      } else toast.error('Failed to logout');
    },
  });
};
