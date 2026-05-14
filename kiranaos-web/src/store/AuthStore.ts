import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface AuthState {
  userId: string | null;
  accessToken: string | null;
  refreshToken: string | null;
  isAuthenticated: boolean;
}

interface AuthActions {
  setAuth: (userId: string, accessToken: string, refreshToken: string) => void;
  clearAuth: () => void;
}

export const useAuthStore = create<AuthState & AuthActions>()(
  persist(
    (set) => ({
      userId: null,
      accessToken: null,
      refreshToken: null,
      isAuthenticated: false,
      setAuth: (userId, accessToken, refreshToken) =>
        set({ userId, accessToken, refreshToken, isAuthenticated: true }),
      clearAuth: () =>
        set({
          userId: null,
          accessToken: null,
          refreshToken: null,
          isAuthenticated: false,
        }),
    }),
    {
      name: 'kiranaos-auth',
      partialize: (state) => ({
        userId: state.userId,
        accessToken: state.accessToken,
        refreshToken: state.refreshToken,
        isAuthenticated: state.isAuthenticated,
      }),
    },
  ),
);
