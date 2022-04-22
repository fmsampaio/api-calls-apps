import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PrincipalController implements Initializable {

    @FXML
    private TextField cepTextField;
    @FXML
    private TextArea dadosTextArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void trataConsultarButton(ActionEvent event) {
        if(!this.cepTextField.getText().equals("")) {
            try {
                AcessoAPICEP acesso = new AcessoAPICEP(this.cepTextField.getText());
                this.dadosTextArea.setText(acesso.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void trataLimparButton(ActionEvent event) {
        this.cepTextField.clear();
        this.dadosTextArea.clear();
    }

}
