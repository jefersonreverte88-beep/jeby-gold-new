# JEBY Gold New

Aplicativo Android + backend Python para a assistente Jeby, usando Gemini API.

## Estrutura

- `backend/` - API Python com Flask e Gemini
- `android/` - projeto Android Compose (Kotlin)

## Backend

```bash
cd backend
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
export GEMINI_API_KEY="SUA_CHAVE_AQUI"
python jeby_backend.py
```

## Android

Abra a pasta `android` no Android Studio.

A URL do backend local para emulador Android é:

```kotlin
http://10.0.2.2:5000/
```

## Observação

A chave da API deve ficar apenas no backend. O app Android não deve conter a chave da API.
