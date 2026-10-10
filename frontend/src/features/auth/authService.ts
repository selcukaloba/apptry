export type AuthResponse = {
  accessToken: string;
  refreshToken: string;
  username: string;
};

type AuthRequest = {
  username: string;
  password: string;
};

const API_URL = "/api/auth";

async function request<T>(path: string, body: unknown): Promise<T> {
  const response = await fetch(`${API_URL}${path}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });

  if (!response.ok) {
    const errorBody = await response.json().catch(() => null);

    const message =
      errorBody?.detail ??
      errorBody?.message ??
      errorBody?.title ??
      "İşlem başarısız oldu. Bilgilerini kontrol edip tekrar dene.";

    throw new Error(message);
  }

  // Yanıtı önce düz metin olarak okuyoruz
  const text = await response.text();
  
  // Eğer yanıt gövdesi boşsa (örn. logout isteğindeki 200 OK), ayrıştırmaya çalışma
  if (!text) {
    return undefined as T;
  }

  // Doluysa JSON'a çevir (login ve register için)
  return JSON.parse(text) as T;
}

export function login(data: AuthRequest): Promise<AuthResponse> {
  return request<AuthResponse>("/login", data);
}

export function register(data: AuthRequest): Promise<AuthResponse> {
  return request<AuthResponse>("/register", data);
}

export function logout(refreshToken: string): Promise<void> {
  return request<void>("/logout", { refreshToken });
}