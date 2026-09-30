from flask import Flask, request, jsonify
from flask_cors import CORS
import os
import google.generativeai as genai

app = Flask(__name__)
CORS(app)

API_KEY = os.getenv("GEMINI_API_KEY")
if not API_KEY:
    raise RuntimeError("Defina a variável de ambiente GEMINI_API_KEY")

genai.configure(api_key=API_KEY)

SYSTEM_INSTRUCTION = """
Você é Jeby, uma assistente sutil e profissional especializada em transações e informações sobre a compra e venda de ouro, sempre mantendo um tom discreto e cortês.

INFORMAÇÕES SOBRE JEBY GOLD:
- Catálogo: Barras de 10g, 100g, 1kg, moedas, joias em ouro 18K e 24K
- Faturamento: R$ 148.520,00 (meta: R$ 250.000,00)
- Clubes: Acumulador (2g/mês), Safira (5g/mês), Imperial (20g/mês)
- PIX: pix@gebouro.com.br com 3% de desconto
- Pagamento: PIX instantâneo, cartão até 12x, WhatsApp
- Conta Brasileira: Inter - Agência 0001 - Conta 55707954-3
- Conta Global: Community Federal Savings Bank - Account 8893262719
- Nome do projeto: JEBY Gold New — Gemini

Sempre seja prestativo, educado e discreto nas respostas sobre investimento em ouro.
"""

model = genai.GenerativeModel(
    model_name="gemini-1.5-flash",
    generation_config={
        "temperature": 0.7,
        "top_p": 0.95,
        "top_k": 40,
        "max_output_tokens": 1024,
    },
    system_instruction=SYSTEM_INSTRUCTION,
)

sessions = {}

@app.route("/health", methods=["GET"])
def health():
    return jsonify({"status": "ok"}), 200

@app.route("/jeby/chat", methods=["POST"])
def jeby_chat():
    try:
        data = request.get_json(force=True, silent=True) or {}
        user_id = data.get("user_id", "default")
        message = data.get("message", "").strip()

        if not message:
            return jsonify({"success": False, "error": "Mensagem vazia"}), 400

        if user_id not in sessions:
            sessions[user_id] = model.start_chat()

        chat = sessions[user_id]
        response = chat.send_message(message)

        return jsonify({
            "success": True,
            "user_id": user_id,
            "message": response.text
        }), 200

    except Exception as e:
        return jsonify({
            "success": False,
            "error": str(e)
        }), 500

@app.route("/jeby/clear", methods=["POST"])
def clear_chat():
    try:
        data = request.get_json(force=True, silent=True) or {}
        user_id = data.get("user_id", "default")
        sessions.pop(user_id, None)
        return jsonify({"success": True}), 200
    except Exception as e:
        return jsonify({"success": False, "error": str(e)}), 500

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=False)
