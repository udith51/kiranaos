import { login, register } from '@/api/auth';
import { useAuthStore } from '@/store/AuthStore';
import { useMutation } from '@tanstack/react-query';
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
    onError: () => {
      toast.error('Failed to login');
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
    onError: () => {
      toast.error('Failed to register');
    },
  });
};
