package dbrusev;

import java.io.IOException;
import java.util.Scanner;

public class CPrincipal {

	public enum IdiomaEscogido {
		ENGLISH,
		SPANISH
	}
	
	private static final Funciones funciones = new Funciones();
	public static Scanner lector = new Scanner(System.in);
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		int idiomaEscogido = 0;
		while (idiomaEscogido < 1 || idiomaEscogido > 2) {
			funciones.limpiarConsola(false); funciones.muestraTitulo();
			System.out.println("AVAILABLE LANGUAGES:");
			System.out.println("1) English");
			System.out.println("2) Español\n");
			try {System.out.print("> ");; idiomaEscogido = lector.nextInt();}
			catch (Exception noEsNumero) {lector.next();}
		}
		
		if (idiomaEscogido == 1) {
			Minijuego_EN minijuegoEN = new Minijuego_EN();
			minijuegoEN.ejecutaMinijuego();
		} else {
			Minijuego_ES minijuegoES = new Minijuego_ES();
			minijuegoES.ejecutaMinijuego();
		} System.exit(0);
		
	}

}
