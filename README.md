# JEBY Gold New

Projeto organizado em **uma pasta única** (`jeby-gold-new`) com o app Android e o backend.

```text
jeby-gold-new/
├── android/   # aplicativo Android Kotlin/Compose
├── backend/   # API Flask + Gemini
└── README.md
```

## Configurar o backend

```bash
cd backend
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
export GEMINI_API_KEY="SUA_CHAVE_GEMINI"
export JEBY_PIX_HOLDER="Jeferson Lopes Reverte"
export JEBY_PIX_KEY="38061093898"
python jeby_backend.py
```

No Windows PowerShell:

```powershell
$env:GEMINI_API_KEY="SUA_CHAVE_GEMINI"
$env:JEBY_PIX_HOLDER="Jeferson Lopes Reverte"
$env:JEBY_PIX_KEY="38061093898"
python jeby_backend.py
```

A chave Gemini e o PIX ficam em variáveis de ambiente e **não devem ser commitados no GitHub**.

Teste:

```bash
curl http://127.0.0.1:5000/health
curl http://127.0.0.1:5000/api/payment-info
```

## Android

Abra no Android Studio a pasta:

```text
jeby-gold-new/android
```

Para o emulador Android, o app usa:

```text
http://10.0.2.2:5000/
```

Para um celular físico, substitua `10.0.2.2` pelo IP local do computador, por exemplo `192.168.1.100`.

## Gerar o APK

No Android Studio:

**Build → Build Bundle(s) / APK(s) → Build APK(s)**

O APK de debug será criado em:

```text
android/app/build/outputs/apk/debug/app-debug.apk
```

## Segurança

- Nunca coloque `GEMINI_API_KEY` no Android.
- Nunca publique chaves de API ou dados bancários em arquivos versionados.
- Antes de divulgar um PIX, confirme o titular e o valor na tela do banco.
