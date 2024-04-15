import requests
import json

inputCep = input("CEP: ")
reqURL = f'https://viacep.com.br/ws/{inputCep}/json/'

print(reqURL)

response = requests.get(reqURL)

content = response.content
statusCode = response.status_code

if statusCode == 200:
    contentJson = json.loads(response.content)

    if b'erro' in content:
        print("CEP Inexistente!")
    
    else:
        print('\nResultado da consulta:')
        cep = contentJson["cep"]
        estado = contentJson["uf"]
        cidade = contentJson["localidade"]
        bairro = contentJson["bairro"]
        endereco = contentJson["logradouro"]
        
        print(f'CEP: {cep}')
        print(f'Endereço: {endereco}')
        print(f'Bairro: {bairro}')
        print(f'Cidade: {cidade}')
        print(f'Estado: {estado}')

elif statusCode == 400:
    print("CEP Inválido!")

