import base64

from Crypto.Cipher import AES
#from Crypto.Util.Padding import unpad

key = base64.b64decode('a7pv8GM/Sm18bIJIME+VNA==')
nonce = base64.b64decode("Y5ZHoGnLr1GNm/xf")
aad = base64.b64decode("V29ybGRXb3JsZFdvcmxkVw==")

ciphertext = base64.b64decode("UwKJ8uEoZgYnc8QesUUkXxAX5tUT")[:-16]
tag = base64.b64decode("UwKJ8uEoZgYnc8QesUUkXxAX5tUT")[-16:]

cipher = AES.new(key, AES.MODE_GCM, nonce=nonce)
cipher.update(aad)

plaintext = cipher.decrypt(ciphertext)
print(plaintext)