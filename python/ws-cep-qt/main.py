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
        
        reqURL = f'https://ws.apicep.com/cep/{inputCep}.json'
        print(reqURL)

        response = requests.get(reqURL)

        contentJson = json.loads(response.content)

        status = contentJson["status"]

        if status == 200:
            cep = contentJson["code"]
            estado = contentJson["state"]
            cidade = contentJson["city"]
            bairro = contentJson["district"]
            endereco = contentJson["address"]
            
            
            outputText = f'CEP: {cep}\n'
            outputText += f'Endereço: {endereco}\n'
            outputText += f'Bairro: {bairro}\n'
            outputText += f'Cidade: {cidade}\n'
            outputText += f'Estado: {estado}\n'

        else:
            mensagem = contentJson["message"]
            outputText = mensagem 
        
        self.conteudoTextBrowser.setText(outputText)

app = QtWidgets.QApplication(sys.argv)
window = Ui()
app.exec()