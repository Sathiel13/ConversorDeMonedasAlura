

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class main {

    // Clase estática para mapear el JSON de la API
    static class ApiResponse {
        Map<String, Double> conversion_rates; // Mapeo de tasas de conversión
    }

    public static void main(String[] args) {
        final String API_URL = "https://v6.exchangerate-api.com/v6/a9ad5dd62058a20a9c734542/latest/USD";
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        try {
            System.out.println("Conectándose a la API para obtener las tasas de cambio...");
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            //Aqui puedo poner el tipo de metodo a usar para la obtencion de informacion
            connection.setRequestMethod("GET");//Es opcional pero es lo correcto en este caso

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            // Uso el MAP para no tener que crear un array, e implementar algo nuevo de lo aprendido
            ApiResponse apiResponse = gson.fromJson(responseBuilder.toString(), ApiResponse.class);
            Map<String, Double> rates = apiResponse.conversion_rates;


            int opcion;
            do {
                System.out.println("\n----- CONVERSOR DE MONEDAS -----");
                System.out.println("Selecciona una opción de conversión:");
                System.out.println("1. USD a EUR (Euro)");
                System.out.println("2. USD a GBP (Libra esterlina)");
                System.out.println("3. USD a JPY (Yen japonés)");
                System.out.println("4. USD a CAD (Dólar canadiense)");
                System.out.println("5. USD a AUD (Dólar australiano)");
                System.out.println("6. USD a MXN (Peso mexicano)");
                System.out.println("7. USD a CNY (Yuan chino)");
                System.out.println("8. Salir");
                System.out.print("Ingresa tu opción: ");
                opcion = scanner.nextInt();

                if (opcion >= 1 && opcion <= 7) {
                    System.out.print("Ingresa la cantidad en USD que deseas convertir: ");
                    double monto = scanner.nextDouble();
                    double rate = 0;

                    switch (opcion) {
                        case 1 -> rate = rates.get("EUR");
                        case 2 -> rate = rates.get("GBP");
                        case 3 -> rate = rates.get("JPY");
                        case 4 -> rate = rates.get("CAD");
                        case 5 -> rate = rates.get("AUD");
                        case 6 -> rate = rates.get("MXN");
                        case 7 -> rate = rates.get("CNY");
                    }

                    double result = monto * rate;
                    System.out.printf("Resultado: "+ monto +"USD equivale a %.2f en la moneda seleccionada.\n", monto, result);
                } else if (opcion != 8) {
                    System.out.println("Opción no válida. Por favor, selecciona una opción del menú.");
                }
            } while (opcion != 8);

            System.out.println("Gracias por usar el conversor de monedas. ¡Hasta pronto!");

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al procesar la solicitud: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}