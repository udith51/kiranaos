import { createStore, getStore, updateStore } from '@/api/store';
import { useMutation, useQuery } from '@tanstack/react-query';
import { toast } from 'sonner';

export const useGetStore = () => {
  return useQuery({
    queryKey: ['store'],
    queryFn: getStore,
    retry: false,
  });
};

export const useCreateStore = (onSuccess?: () => void) => {
  return useMutation({
    mutationFn: createStore,
    onSuccess: () => {
      toast.success('Store created successfully');
      onSuccess?.();
    },
    onError: () => {
      toast.error('Failed to create store');
    },
  });
};

export const useUpdateStore = (onSuccess?: () => void) => {
  return useMutation({
    mutationFn: updateStore,
    onSuccess: () => {
      toast.success('Store updated successfully');
      onSuccess?.();
    },
    onError: () => {
      toast.error('Failed to update store');
    },
  });
};
