import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AcessoAPICEP {
    private String cep, estado, cidade, bairro, endereco, mensagem;
    private String conteudoJson;
    private int status;

    private final String BASE_CEP_URL = "https://ws.apicep.com/cep/xxxx.json";

    public AcessoAPICEP(String cep) throws IOException, JSONException {
        acessaAPI(cep);
        trataJSON();
    }

    private void trataJSON() throws JSONException {
        JSONObject jsonObj = new JSONObject(this.conteudoJson);
        this.status = jsonObj.getInt("status");
        if(this.status == 200) {
            this.cep = jsonObj.getString("code");
            this.estado = jsonObj.getString("state");
            this.cidade = jsonObj.getString("city");
            this.bairro = jsonObj.getString("district");
            this.endereco = jsonObj.getString("address");
        }
        else {
            this.mensagem = jsonObj.getString("message");
        }
    }

    private void acessaAPI(String cep) throws IOException {
        URL url = new URL(BASE_CEP_URL.replace("xxxx", cep));

        HttpsURLConnection conexao = (HttpsURLConnection) url.openConnection();
        conexao.addRequestProperty("Content-Type", "application/json");
        conexao.addRequestProperty("User-Agent", "Mozilla/4.76");

        BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));

        String inputLine;
        conteudoJson = "";
        while( (inputLine = entrada.readLine()) != null) {
            conteudoJson += inputLine;
        }
    }

    public String getConteudoJson() {
        return conteudoJson;
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

    public String getMensagem() {
        return mensagem;
    }

    public int getStatus() {
        return status;
    }
}
