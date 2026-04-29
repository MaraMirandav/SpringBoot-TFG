-- Añadir columnas faltantes a medications
ALTER TABLE medications
    ADD COLUMN IF NOT EXISTS stock INT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS user_id INTEGER REFERENCES users(id);