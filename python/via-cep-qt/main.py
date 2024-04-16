from PyQt5 import  uic
import PyQt5.QtWidgets as QtWidgets

import sys
import requests
import json

class Ui(QtWidgets.QDialog):
    def __init__(self):
        super(Ui, self).__init__()
        uic.loadUi('/home/felipe/Projetos/api-calls-apps/python/ws-cep-qt/main_window.ui', self)
        self.show()

        self.consultaButton.clicked.connect(self.handleConsultaButton)
        self.limparButton.clicked.connect(self.handleLimparButton)

    def handleLimparButton(self):
        self.cepTextEdit.setText('')
        self.conteudoTextBrowser.setText('')

    def handleConsultaButton(self):
        inputCep = self.cepTextEdit.toPlainText()

        if inputCep == '':
            return
        
        reqURL = f'https://viacep.com.br/ws/{inputCep}/json/'
        print(reqURL)

        response = requests.get(reqURL)

        content = response.content
        statusCode = response.status_code

        if statusCode == 200:
            contentJson = json.loads(content)

            if b'erro' in content:
                outputText = "CEP Inexistente!"
            
            else:
                cep = contentJson["cep"]
                estado = contentJson["uf"]
                cidade = contentJson["localidade"]
                bairro = contentJson["bairro"]
                endereco = contentJson["logradouro"]
                
                outputText = f'CEP: {cep}\n'
                outputText += f'Endereço: {endereco}\n'
                outputText += f'Bairro: {bairro}\n'
                outputText += f'Cidade: {cidade}\n'
                outputText += f'Estado: {estado}\n'

        elif statusCode == 400:
            outputText = "CEP Inválido!" 
        
        self.conteudoTextBrowser.setText(outputText)

app = QtWidgets.QApplication(sys.argv)
window = Ui()
app.exec()