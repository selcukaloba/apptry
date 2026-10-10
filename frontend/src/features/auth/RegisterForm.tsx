import { useState, type FormEvent } from "react";
import { Button } from "@/components/ui/button";
import { register, type AuthResponse } from "./authService";

type RegisterFormProps = {
  onSuccess: (response: AuthResponse) => void;
};

export function RegisterForm({ onSuccess }: RegisterFormProps) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setMessage("");

    if (password !== confirmPassword) {
      setMessage("Şifreler birbiriyle eşleşmiyor.");
      return;
    }

    setLoading(true);

    try {
      const response = await register({ username, password });
      onSuccess(response);
    } catch (error) {
      setMessage(
        error instanceof Error
          ? error.message
          : "Kayıt sırasında bir hata oluştu."
      );
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-5">
      <div>
        <h2 className="text-xl font-semibold">Hesabını oluştur</h2>
        <p className="mt-1 text-sm text-on-surface-variant">
          Pulse'a katılmak için bilgilerini gir.
        </p>
      </div>

      <div className="space-y-2">
        <label htmlFor="register-username" className="text-sm font-medium">
          Kullanıcı adı
        </label>
        <input
          id="register-username"
          autoComplete="username"
          required
          minLength={3}
          maxLength={50}
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          placeholder="Bir kullanıcı adı belirle"
          className="w-full rounded-lg border border-white/10 bg-surface-container-lowest px-4 py-3 text-sm outline-none placeholder:text-on-surface-variant/60 focus:border-pulse-primary focus:ring-2 focus:ring-pulse-primary/20"
        />
      </div>

      <div className="space-y-2">
        <label htmlFor="register-password" className="text-sm font-medium">
          Şifre
        </label>
        <input
          id="register-password"
          type={showPassword ? "text" : "password"}
          autoComplete="new-password"
          required
          minLength={6}
          maxLength={50}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="En az 6 karakter"
          className="w-full rounded-lg border border-white/10 bg-surface-container-lowest px-4 py-3 text-sm outline-none placeholder:text-on-surface-variant/60 focus:border-pulse-primary focus:ring-2 focus:ring-pulse-primary/20"
        />
      </div>

      <div className="space-y-2">
        <label htmlFor="register-confirm-password" className="text-sm font-medium">
          Şifreyi doğrula
        </label>
        <input
          id="register-confirm-password"
          type={showPassword ? "text" : "password"}
          autoComplete="new-password"
          required
          minLength={6}
          maxLength={50}
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
          placeholder="Şifreni tekrar gir"
          className="w-full rounded-lg border border-white/10 bg-surface-container-lowest px-4 py-3 text-sm outline-none placeholder:text-on-surface-variant/60 focus:border-pulse-primary focus:ring-2 focus:ring-pulse-primary/20"
        />
      </div>

      <label className="flex items-center gap-2 text-sm text-on-surface-variant">
        <input
          type="checkbox"
          checked={showPassword}
          onChange={(e) => setShowPassword(e.target.checked)}
          className="accent-[#b7a1ff]"
        />
        Şifreleri göster
      </label>

      {message && (
        <p role="alert" className="text-sm text-red-400">
          {message}
        </p>
      )}

      <Button type="submit" className="w-full" disabled={loading}>
        {loading ? "Hesap oluşturuluyor..." : "Hesap oluştur"}
      </Button>
    </form>
  );
}