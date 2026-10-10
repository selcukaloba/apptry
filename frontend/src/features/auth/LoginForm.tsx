import { useState, type FormEvent } from "react";
import { Button } from "@/components/ui/button";
import { login, type AuthResponse } from "./authService";

type LoginFormProps = {
  onSuccess: (response: AuthResponse) => void;
};

export function LoginForm({ onSuccess }: LoginFormProps) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setMessage("");
    setLoading(true);

    try {
      const response = await login({ username, password });
      onSuccess(response);
    } catch (error) {
      setMessage(
        error instanceof Error
          ? error.message
          : "Giriş sırasında bir hata oluştu."
      );
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div>
        <h2 className="text-xl font-semibold">Tekrar hoş geldin</h2>
        <p className="mt-1 text-sm text-on-surface-variant">
          Hesabına giriş yap.
        </p>
      </div>

      <div className="space-y-2">
        <label htmlFor="login-username" className="text-sm font-medium">
          Kullanıcı adı
        </label>
        <input
          id="login-username"
          autoComplete="username"
          required
          minLength={3}
          maxLength={50}
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Kullanıcı adın"
          className="w-full rounded-lg border border-white/10 bg-surface-container-lowest px-4 py-3 text-sm outline-none transition placeholder:text-on-surface-variant/60 focus:border-pulse-primary focus:ring-2 focus:ring-pulse-primary/20"
        />
      </div>

      <div className="space-y-2">
        <label htmlFor="login-password" className="text-sm font-medium">
          Şifre
        </label>
        <div className="flex rounded-lg border border-white/10 bg-surface-container-lowest focus-within:border-pulse-primary focus-within:ring-2 focus-within:ring-pulse-primary/20">
          <input
            id="login-password"
            type={showPassword ? "text" : "password"}
            autoComplete="current-password"
            required
            minLength={6}
            maxLength={50}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Şifren"
            className="min-w-0 flex-1 bg-transparent px-4 py-3 text-sm outline-none placeholder:text-on-surface-variant/60"
          />
          <button
            type="button"
            onClick={() => setShowPassword((current) => !current)}
            className="px-3 text-xs text-on-surface-variant hover:text-on-surface"
            aria-label={showPassword ? "Şifreyi gizle" : "Şifreyi göster"}
          >
            {showPassword ? "Gizle" : "Göster"}
          </button>
        </div>
      </div>

      {message && (
        <p role="alert" className="text-sm text-red-400">
          {message}
        </p>
      )}

      <Button type="submit" className="w-full" disabled={loading}>
        {loading ? "Giriş yapılıyor..." : "Giriş yap"}
      </Button>
    </form>
  );
}