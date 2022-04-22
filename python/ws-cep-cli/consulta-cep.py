import requests
import json

inputCep = input("CEP: ")

reqURL = f'https://ws.apicep.com/cep/{inputCep}.json'
print(reqURL)

response = requests.get(reqURL)

content = response.content
contentJson = json.loads(response.content)

print('\nResultado da consulta:')

status = contentJson["status"]

if status == 200:
    cep = contentJson["code"]
    estado = contentJson["state"]
    cidade = contentJson["city"]
    bairro = contentJson["district"]
    endereco = contentJson["address"]
    
    print(f'CEP: {cep}')
    print(f'Endereço: {endereco}')
    print(f'Bairro: {bairro}')
    print(f'Cidade: {cidade}')
    print(f'Estado: {estado}')

else:
    mensagem = contentJson["message"]
    print(mensagem)

