import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AcessoAPICEP {
    private String cep, estado, cidade, bairro, endereco;
    private String conteudo;
    private int codigoStatus;
    private boolean isCepInvalido, isCepInexistente;

    private final String BASE_CEP_URL = "https://viacep.com.br/ws/xxxx/json/";

    public AcessoAPICEP() {
        this.conteudo = "";
        this.codigoStatus = -1;
        this.isCepInexistente = false;
        this.isCepInvalido = false;
    }

    public void trataJSON() throws JSONException {
        if (codigoStatus == 400) {
            //System.out.println("CEP Inválido!");
            this.isCepInvalido = true;
        } else { //codigoStatus == 200

            JSONObject jsonObj = new JSONObject(this.conteudo);
            if (jsonObj.has("erro")) {
                //System.out.println("CEP inexistente!");
                this.isCepInexistente = true;
            } else {
                this.cep = jsonObj.getString("cep");
                this.estado = jsonObj.getString("uf");
                this.cidade = jsonObj.getString("localidade");
                this.bairro = jsonObj.getString("bairro");
                this.endereco = jsonObj.getString("logradouro");
            }
        }
    }

    public void acessaAPI(String cep) throws UnirestException {
        String url = BASE_CEP_URL.replace("xxxx", cep);

        HttpResponse<String> response = Unirest.get(url)
                .header("Content-Type", "application/json")
                .asString();

        this.codigoStatus = response.getStatus();
        this.conteudo = response.getBody();

        // Descomentar as próximas linhas para debug
        //System.out.println(codigoStatus);
        //System.out.println(conteudo);
    }

    public String getCep() {
        return cep;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getCodigoStatus() {
        return codigoStatus;
    }

    @Override
    public String toString() {
        String retorno = "";

        if(isCepInvalido) {
            retorno = "CEP Inválido!";
        }
        else if(isCepInexistente) {
            retorno = "CEP Inexistente!";
        }
        else { //if(this.getCodigoStatus() == 200) {
            retorno += "CEP: " + this.getCep() + "\n";
            retorno += "Endereço: " + this.getEndereco() + "\n";
            retorno += "Bairro: " + this.getBairro() + "\n";
            retorno += "Município: " + this.getCidade() + "\n";
            retorno += "Estado: " + this.getEstado() + "\n";
        }

        return retorno;
    }

}