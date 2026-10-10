
import { useState } from "react";
import { LoginForm } from "./LoginForm";
import { RegisterForm } from "./RegisterForm";

export function AuthPage() {
  const [mode, setMode] = useState<"login" | "register">("login");

  return (
    <main className="auth-theme flex min-h-screen items-center justify-center px-4 py-10">
      <section className="w-full max-w-md">
        <header className="mb-8 text-center">
          <div className="mx-auto mb-4 flex size-14 items-center justify-center rounded-2xl bg-pulse-primary text-2xl font-bold text-[#21183b]">
            P
          </div>

          <h1 className="font-headline-md text-3xl font-bold tracking-tight">
            Pulse
          </h1>

          <p className="mt-2 text-sm text-on-surface-variant">
            Bağlan, paylaş ve keşfet.
          </p>
        </header>

        <div className="rounded-2xl border border-white/10 bg-surface-container-low p-6 shadow-2xl sm:p-8">
          <div className="mb-6 grid grid-cols-2 rounded-xl bg-surface-container-lowest p-1">
            <button
              type="button"
              onClick={() => setMode("login")}
              className={`rounded-lg px-4 py-2.5 text-sm font-medium transition ${
                mode === "login"
                  ? "bg-pulse-primary text-[#21183b]"
                  : "text-on-surface-variant hover:text-on-surface"
              }`}
            >
              Giriş yap
            </button>

            <button
              type="button"
              onClick={() => setMode("register")}
              className={`rounded-lg px-4 py-2.5 text-sm font-medium transition ${
                mode === "register"
                  ? "bg-pulse-primary text-[#21183b]"
                  : "text-on-surface-variant hover:text-on-surface"
              }`}
            >
              Kayıt ol
            </button>
          </div>

          {mode === "login" ? <LoginForm /> : <RegisterForm />}
        </div>

        <p className="mt-6 text-center text-xs text-on-surface-variant">
          Devam ederek kullanım koşullarını kabul etmiş olursun.
        </p>
      </section>
    </main>
  );
}
