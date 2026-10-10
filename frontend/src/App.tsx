
import { Button } from "@/components/ui/button"

function App() {
  return (
    <main className="flex min-h-screen items-center justify-center bg-slate-50 p-6">
      <div className="w-full max-w-md rounded-2xl border bg-white p-8 shadow-sm">
        <h1 className="text-3xl font-bold tracking-tight">
          Social App
        </h1>

        <p className="mt-3 text-sm text-slate-600">
          Connect, share and discover.
        </p>

        <div className="mt-6 flex gap-3">
          <Button>Get started</Button>
          <Button variant="outline">Sign in</Button>
        </div>
      </div>
    </main>
  )
}

export default App
