/// <reference types="vite/client" />

interface ImportMetaEnv {
  /**
   * Backend base URL
   */
  readonly VITE_BACKEND_BASE_URL: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
