
import { useState, type FormEvent } from "react";
import { Button } from "@/components/ui/button";

export function RegisterForm() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [message, setMessage] = useState("");

  function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();

    if (password !== confirmPassword) {
      setMessage("Şifreler birbiriyle eşleşmiyor.");
      return;
    }

    setMessage("Arayüz hazır. API bağlantısını sonraki adımda ekleyeceğiz.");
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
        <p role="status" className="text-sm text-on-surface-variant">
          {message}
        </p>
      )}

      <Button type="submit" className="w-full">
        Hesap oluştur
      </Button>
    </form>
  );
}
