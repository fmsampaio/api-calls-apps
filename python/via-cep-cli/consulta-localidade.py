import requests
import json


uf = input('UF: ')
cidade = input('Cidade: ')
endereco = input('Endereço: ')

reqUrl = f'https://viacep.com.br/ws/{uf}/{cidade}/{endereco}/json/'

response = requests.get(reqUrl)

content = response.content
statusCode = response.status_code

if statusCode == 200:
    #print(content)
    contentJson = json.loads(content)

    print(f'\n\nResultados encontrados: {len(contentJson)}\n\n')
    for resultado in contentJson:
        cep = resultado["cep"]
        estado = resultado["uf"]
        cidade = resultado["localidade"]
        bairro = resultado["bairro"]
        endereco = resultado["logradouro"]
        
        print(f'CEP: {cep}')
        print(f'Endereço: {endereco}')
        print(f'Bairro: {bairro}')
        print(f'Cidade: {cidade}')
        print(f'Estado: {estado}')
        print('\n')


elif statusCode == 400:
    print('[400] Cidade ou logradouro devem possuir, ao menos, 3 caracteres.')


