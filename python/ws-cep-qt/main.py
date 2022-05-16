from PyQt5 import  uic
import PyQt5.QtWidgets as QtWidgets
import sys

class Ui(QtWidgets.QDialog):
    def __init__(self):
        super(Ui, self).__init__()
        uic.loadUi('/home/felipe/Projetos/api-calls-apps/python/ws-cep-qt/main_window.ui', self)
        self.show()

app = QtWidgets.QApplication(sys.argv)
window = Ui()
app.exec()