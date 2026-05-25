export interface AuthRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  userId: string;
  accessToken: string;
  refreshToken: string;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}
