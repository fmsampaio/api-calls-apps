import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.Scanner;

public class Principal {
    private static final String BASE_CEP_URL = "https://viacep.com.br/ws/";

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("Digite um CEP: ");
            String cep = scan.nextLine();

            acessaRotaCEP(cep);

        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void  acessaRotaCEP(String inputCep) throws UnirestException, JSONException {
        String url = BASE_CEP_URL + inputCep + "/json/";

        HttpResponse<String> response = Unirest.get(url)
                .header("Content-Type", "application/json")
                .asString();

        int codigoStatus = response.getStatus();
        String conteudo = response.getBody();

        if (codigoStatus == 400) {
            System.out.println("CEP Inválido!");
        } else { //codigoStatus == 200
            JSONObject jsonObj = new JSONObject(conteudo);
            if (jsonObj.has("erro")) {
                System.out.println("CEP inexistente!");
            } else {
                String cep = jsonObj.getString("cep");
                String estado = jsonObj.getString("uf");
                String cidade = jsonObj.getString("localidade");
                String bairro = jsonObj.getString("bairro");
                String endereco = jsonObj.getString("logradouro");
                String complemento = jsonObj.getString("complemento");

                System.out.println("CEP: " + cep);
                System.out.println("Endereço: " + endereco);
                System.out.println("Complemento: " + complemento);
                System.out.println("Bairro: " + bairro);
                System.out.println("Município: " + cidade);
                System.out.println("Estado: " + estado);
            }
        }

    }
}
