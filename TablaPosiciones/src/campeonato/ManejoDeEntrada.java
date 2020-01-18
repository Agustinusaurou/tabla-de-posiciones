package campeonato;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ManejoDeEntrada {
	public static Integer pedirNumero() {
		Integer numIngresado = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // Ya tenemos el "lector"
			System.out.println("Por favor ingrese un numero");// Se pide un dato al usuario

			numIngresado = Integer.valueOf(br.readLine()); // Se lee el nombre con readLine() que retorna un String con
		} catch (NumberFormatException e) {
			System.out.println("Por favor poner un numero");
		} catch (IOException e) {
			System.out.println("Error ingresando caracter");
		}
		return numIngresado;
	}

	public static String pedirPalabra() {
		String palabraIngresada = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // Ya tenemos el "lector"
			palabraIngresada = br.readLine(); // Se lee el nombre con readLine() que retorna un String con
		}
		catch (IOException e) {
			System.out.println("Error ingresando caracter");
		}
		return palabraIngresada;
	}
}
