/// <reference types="vite/client" />

interface ImportMetaEnv {
  /**
   * Vite backend base url
   */
  readonly VITE_SERVER_BASE_URL: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
