import org.json.JSONException;

import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);

            System.out.print("Digite um CEP: ");
            String cep = scan.nextLine();

            AcessoAPICEP apiCep = new AcessoAPICEP(cep);

            System.out.println("Resultado da consulta:");
            System.out.println(apiCep.toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
