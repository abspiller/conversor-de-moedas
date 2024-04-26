package conversordemoedas;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import com.google.gson.Gson;

public class Principal {

	public static void main(String[] args) throws IOException, InterruptedException {

		Scanner leitura = new Scanner(System.in);

		String moedaEntrada = "";
		String moedaSaida = "";
		double amount = 0;
		int opcao = 0;

		try {

			while (opcao != 7) {

				System.out.println("Selecione uma opção:");
				System.out.println("1. Real para Dólar");
				System.out.println("2. Dólar para Real");
				System.out.println("3. Real para Euro");
				System.out.println("4. Euro para Real");
				System.out.println("5. Real para Peso Argentino");
				System.out.println("6. Peso Argentino para Real");
				System.out.println("7. Sair");

				opcao = leitura.nextInt();

				switch (opcao) {
				case 1:
					System.out.println("Digite o valor em Reais: ");
					amount = leitura.nextDouble();
					moedaEntrada = "BRL";
					moedaSaida = "USD";
					break;

				case 2:
					System.out.println("Digite o valor em Dólares: ");
					amount = leitura.nextDouble();
					moedaEntrada = "USD";
					moedaSaida = "BRL";
					break;

				case 3:
					System.out.println("Digite o valor em Reais: ");
					amount = leitura.nextDouble();
					moedaEntrada = "BRL";
					moedaSaida = "EUR";
					break;

				case 4:
					System.out.println("Digite o valor em Euros: ");
					amount = leitura.nextDouble();
					moedaEntrada = "EUR";
					moedaSaida = "BRL";
					break;

				case 5:
					System.out.println("Digite o valor em Reais: ");
					amount = leitura.nextDouble();
					moedaEntrada = "BRL";
					moedaSaida = "ARS";
					break;

				case 6:
					System.out.println("Digite o valor em Pesos Argentinos: ");
					amount = leitura.nextDouble();
					moedaEntrada = "ARS";
					moedaSaida = "BRL";
					break;

				case 7:
					System.out.println("Fim do programa");
					break;

				default:

					break;

				}

				String endereco = "https://v6.exchangerate-api.com/v6/224a07912b9defae46ac592f/pair/" + moedaEntrada
						+ "/" + moedaSaida + "/" + amount;

				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();

				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

				String json = response.body();

				Gson gson = new Gson();
				Informacoes minhaConversao = gson.fromJson(json, Informacoes.class);

				System.out.println("\n* * * CONVERSÃO * * *");
				System.out.println("Moeda escolhida: " + minhaConversao.base_code());
				System.out.println("Converter para: " + minhaConversao.target_code());
				System.out.printf("1 %s equivale a %.2f %s\n", minhaConversao.base_code(),
						minhaConversao.conversion_rate(), minhaConversao.target_code());
				System.out.printf("Valor convertido:  %.2f %s", minhaConversao.conversion_result(),
						minhaConversao.target_code(), "\n");
				System.out.println("\n");

			}

		} catch (com.google.gson.JsonSyntaxException e) {
			System.out.println("Opção inválida");
		}

	}

}
