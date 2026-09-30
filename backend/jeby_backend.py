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

# Dados de pagamento: mantidos em variáveis de ambiente, não no código público.
PIX_KEY = os.getenv("JEBY_PIX_KEY", "")
PIX_HOLDER = os.getenv("JEBY_PIX_HOLDER", "Jeferson Lopes Reverte")

SYSTEM_INSTRUCTION = f"""
Você é Jeby, uma assistente profissional, discreta e cordial da JEBY Gold.

INFORMAÇÕES SOBRE JEBY GOLD:
- Catálogo: barras de 10g, 100g, 1kg, moedas e joias em ouro 18K e 24K.
- Clubes: Acumulador (2g/mês), Safira (5g/mês), Imperial (20g/mês).
- Pagamento: PIX instantâneo, cartão até 12x e WhatsApp.
- Titular de recebimento: {PIX_HOLDER}.

Quando o cliente perguntar como pagar, informe o titular e o PIX somente se o PIX estiver configurado no servidor.
Nunca invente preços, cotações, descontos, dados bancários ou status de pagamento.
Não dê garantia de lucro nem aconselhamento financeiro personalizado.
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

@app.route("/api/payment-info", methods=["GET"])
def payment_info():
    if not PIX_KEY:
        return jsonify({
            "configured": False,
            "message": "PIX ainda não configurado no servidor."
        }), 503

    return jsonify({
        "configured": True,
        "pix": PIX_KEY,
        "holder": PIX_HOLDER
    }), 200

@app.route("/jeby/chat", methods=["POST"])
def jeby_chat():
    try:
        data = request.get_json(force=True, silent=True) or {}
        user_id = str(data.get("user_id", "default"))[:100]
        message = str(data.get("message", "")).strip()

        if not message:
            return jsonify({"success": False, "error": "Mensagem vazia"}), 400

        if user_id not in sessions:
            sessions[user_id] = model.start_chat()

        response = sessions[user_id].send_message(message)

        return jsonify({
            "success": True,
            "user_id": user_id,
            "message": response.text
        }), 200

    except Exception as error:
        app.logger.exception("Erro no chat da Jeby")
        return jsonify({
            "success": False,
            "error": "Não foi possível processar a mensagem agora."
        }), 500

@app.route("/jeby/clear", methods=["POST"])
def clear_chat():
    try:
        data = request.get_json(force=True, silent=True) or {}
        user_id = str(data.get("user_id", "default"))[:100]
        sessions.pop(user_id, None)
        return jsonify({"success": True}), 200
    except Exception:
        return jsonify({"success": False, "error": "Não foi possível limpar a conversa."}), 500

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=False)
