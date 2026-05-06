import { login, register } from '@/api/auth';
import { useAuthStore } from '@/store/AuthStore';
import { useMutation } from '@tanstack/react-query';

export const useLogin = (onSuccess?: () => void) => {
  return useMutation({
    mutationFn: login,
    onSuccess: (data) => {
      useAuthStore.getState().setAuth(data.accessToken, data.refreshToken);
      onSuccess?.();
    },
  });
};

export const useRegister = (onSuccess?: () => void) => {
  return useMutation({
    mutationFn: register,
    onSuccess: (data) => {
      useAuthStore.getState().setAuth(data.accessToken, data.refreshToken);
      onSuccess?.();
    },
  });
};
