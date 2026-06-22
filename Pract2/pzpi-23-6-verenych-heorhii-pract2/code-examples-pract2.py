"""
Приклад програмної взаємодії з Telegram Bot API мовою Python.
Демонструє клієнт-серверну архітектуру: клієнт формує HTTPS-запит у форматі
JSON до Bot API, який працює поверх протоколу MTProto на серверах Telegram.
"""

import requests

TOKEN = "<TOKEN>"  # токен бота, отриманий від @BotFather
URL = f"https://api.telegram.org/bot{TOKEN}/sendMessage"


def send_message(chat_id, text):
    payload = {
        "chat_id": chat_id,
        "text": text,
        "parse_mode": "HTML",
    }
    response = requests.post(URL, json=payload)
    return response.json()


if __name__ == "__main__":
    result = send_message(123456789, "Повідомлення через Bot API")
    print(result)
