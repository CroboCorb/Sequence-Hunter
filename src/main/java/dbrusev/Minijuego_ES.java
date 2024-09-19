package dbrusev;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dbrusev.CPrincipal.IdiomaEscogido;
import dbrusev.Funciones.DificultadEscogida;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class Minijuego_ES {

	private static final Funciones funciones = new Funciones();
	private static final Scanner lector = new Scanner(System.in);
	
	private static String[][] tableroJuego;
	private static List<Integer> secuenciaFilas;
	private static List<Integer> secuenciaNumeros;
	private static List<String> secuenciaDesbloqueada = new ArrayList<String>();
	
	private static boolean modoDebug = false;

	private final IdiomaEscogido idioma = IdiomaEscogido.SPANISH;
	
	public void ejecutaMinijuego() throws InterruptedException, IOException {
		
		funciones.setIdioma(idioma);
		
		int menuDificultad = -1;
		while (menuDificultad < 1 || menuDificultad > 5) {
			funciones.limpiarConsola(false); funciones.muestraTitulo();
			System.out.println(ansi().fg(CYAN).a("1) Fácil [6x6] (FxC)").reset());
			System.out.println(ansi().fg(GREEN).a("2) Intermedio [10x10] (FxC)").reset());
			System.out.println(ansi().fg(YELLOW).a("3) Difícil [14x14] (FxC)").reset());
			System.out.println(ansi().fg(RED).a("4) Extremo [18x18] (FxC)").reset());
			System.out.println(ansi().fg(MAGENTA).a("5) Personalizado [-x-] (FxC)").reset());
			System.out.print("\n- MODO DEBUG: ");
			if (!modoDebug) {System.out.print(ansi().fg(RED).a("DESACTIVADO").reset() + " -\n\n");}
			else {System.out.print(ansi().fg(GREEN).a("ACTIVADO").reset() + " -\n\n");}
			try {
				System.out.print("Introduzca la dificultad a escoger: "); menuDificultad = lector.nextInt();
				if (!modoDebug && menuDificultad == 2003) {modoDebug = true;}
				else if (modoDebug && menuDificultad == 2003) {modoDebug = false;}
			} catch (Exception noEsNumero) {lector.next();}
		} lector.nextLine();
		
		funciones.setDificultad(menuDificultad);
		System.out.print("\nDificultad escogida: ");
		if (funciones.getDificultad() == DificultadEscogida.FACIL) {System.out.print(ansi().fg(CYAN).a("FÁCIL").reset() + "\n");}
		else if (funciones.getDificultad() == DificultadEscogida.INTERMEDIA) {System.out.print(ansi().fg(GREEN).a("INTERMEDIA").reset() + "\n");}
		else if (funciones.getDificultad() == DificultadEscogida.DIFICIL) {System.out.print(ansi().fg(YELLOW).a("DIFÍCIL").reset() + "\n");}
		else if (funciones.getDificultad() == DificultadEscogida.EXTREMA) {System.out.print(ansi().fg(RED).a("EXTREMA").reset() + "\n");}
		else {System.out.print(ansi().fg(MAGENTA).a("PERSONALIZADA").reset() + "\n\n");}
		
		if (menuDificultad == 5) {
			int filas = 0, columnas = 0;
			while (filas < 4 || filas > 24) {
				try {
					System.out.print("Introduzca la cantidad de filas " + ansi().fg(CYAN).a("(4 a 24)").reset() + ": "); filas = lector.nextInt();
					if (filas < 4 || filas > 24) {System.out.println(ansi().fg(RED).a("Número fuera de rango..").reset() + "\n");}
				} catch (Exception noEsNumero) {System.out.println(ansi().fg(RED).a("El valor introducido no es un número.").reset() + "\n"); lector.next();}
			}
			while (columnas < 4 || columnas > 24 || columnas % 2 != 0) {
				try {
					System.out.print("Introduzca la cantidad de columnas " + ansi().fg(CYAN).a("(4 a 24)").reset() + ": "); columnas = lector.nextInt();
					if ((columnas >= 4 && columnas <= 24) && columnas % 2 != 0) {
						System.out.println(ansi().fg(RED).a("Las columnas deben de ser múltiplos de dos..").reset() + "\n");
					} else if (columnas < 4 || columnas > 24) {System.out.println(ansi().fg(RED).a("Número fuera de rango..").reset() + "\n");}
				} catch (Exception noEsNumero) {System.out.println(ansi().fg(RED).a("El valor introducido no es un número.").reset() + "\n"); lector.next();}
			} funciones.setFilasYColumnas(filas, columnas);
		}
		
		int tiempoLimiteMinijuego = 0;
		Temporizador temporizador = new Temporizador();
		if (!modoDebug) {
			temporizador.setIdioma(idioma);
			tiempoLimiteMinijuego = funciones.solicitaTiempoLimite();
			temporizador.setTiempoLimite(tiempoLimiteMinijuego); 
		} System.out.println("\n" + ansi().fg(RED).a("Espere...").reset());
		
		tableroJuego = funciones.preparaTablero(); 
		secuenciaFilas = funciones.getSecuenciaFilas();
		secuenciaNumeros = funciones.getSecuenciaNumeros();
		
		for (int i = 0; i < secuenciaNumeros.size(); i++) {
			secuenciaDesbloqueada.add("xx");
		} funciones.limpiarConsola(true);
		
		if (modoDebug) {System.out.println("MODO DEBUG " + ansi().fg(RED).a("ACTIVADO").reset() + ". No habrá tiempo límite.");}
		System.out.println("Pulse " + ansi().fg(RED).a("ENTER").reset() + " para comenzar con el minijuego.");
		lector.nextLine();
		
		for (int i = 3; i > 0; i--) {
			switch (i) {
			case 3: System.out.print(ansi().fg(GREEN).a(i + ".. ").reset()); Thread.sleep(1000); break;
			case 2: System.out.print(ansi().fg(YELLOW).a(i + ".. ").reset()); Thread.sleep(1000); break;
			case 1: System.out.print(ansi().fg(RED).a(i + "..").reset()); Thread.sleep(1000); break;
			}
		} funciones.limpiarConsola(false);
		
		if (!modoDebug) {temporizador.start();}
		
		int contadorColumna = 0;
		while (!secuenciaNumeros.isEmpty() && !secuenciaFilas.isEmpty()) {
			int entradaUsuario = 0;
			while (entradaUsuario < 10 || entradaUsuario > 99) {
				funciones.muestraTablero(tableroJuego, contadorColumna);
				funciones.muestraSecuenciaDesbloqueada(secuenciaDesbloqueada, secuenciaNumeros.size());
				try {System.out.print("> "); entradaUsuario = lector.nextInt();}
				catch (InputMismatchException noEsNumero) {entradaUsuario = 0; lector.next(); funciones.limpiarConsola(false);}
			}
			
			for (int i = 0; i < funciones.getFilas(); i++) {
				if (entradaUsuario == secuenciaNumeros.get(0) && i == secuenciaFilas.get(0)) {
					secuenciaDesbloqueada.set(contadorColumna, Integer.toString(entradaUsuario));
					tableroJuego[i][contadorColumna] = tableroJuego[i][contadorColumna+1] = "xx";
					
					secuenciaNumeros.remove(0); secuenciaFilas.remove(0);
					contadorColumna++; break;
				}
			} funciones.limpiarConsola(false);
		} contadorColumna++;
		
		funciones.muestraTablero(tableroJuego, contadorColumna);
		funciones.muestraSecuenciaDesbloqueada(secuenciaDesbloqueada, secuenciaNumeros.size());
		System.out.print("- SECUENCIA " + ansi().fg(GREEN).a("COMPLETADA").reset());
		
		if (!modoDebug) {
			int tiempoCompletado = tiempoLimiteMinijuego - temporizador.getTiempoRestante();
			Estadisticas guardaStats = new Estadisticas(secuenciaDesbloqueada, tiempoCompletado);
			guardaStats.setIdioma(idioma); guardaStats.guardarEstadisticas();
			System.out.print(" en " + tiempoCompletado + " segundos -\n");
			
			System.out.println("Estadísticas guardadas en " + ansi().fg(YELLOW).a(guardaStats.getLocalizacionArchivo()).reset());
		} else {System.out.println("Modo debug " + ansi().fg(RED).a("activado").reset() + ": no se han guardado las estadísticas.");}
	}
	
}
