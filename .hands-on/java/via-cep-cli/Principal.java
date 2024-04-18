import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.Scanner;

public class Principal {
    private static final String BASE_CEP_URL = "https://viacep.com.br/ws/";

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("UF: ");
            String uf = scan.nextLine();

            System.out.print("Município: ");
            String municipio = scan.nextLine();

            System.out.print("Logradouro: ");
            String logradouro = scan.nextLine();

            acessaRotaLogradouro(uf, municipio, logradouro);

        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void acessaRotaLogradouro(String uf, String municipio, String logradouro) throws UnirestException, JSONException {
        String url = BASE_CEP_URL + uf + "/" + municipio + "/" + logradouro + "/json/";

        HttpResponse<String> response = Unirest.get(url)
                .header("Content-Type", "application/json")
                .asString();

        int codigoStatus = response.getStatus();
        String conteudo = response.getBody();

        if (codigoStatus == 400) {
            System.out.println("[400] Cidade ou logradouro devem possuir, ao menos, 3 caracteres.");
        } else { //codigoStatus == 200
            JSONArray jsonArray = new JSONArray(conteudo);
            System.out.println("\n\nResultados encontrados: " + jsonArray.length() + "\n\n");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);

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
                System.out.println();
                System.out.println();
            }

        }

    }
}
