package dbrusev;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import org.fusesource.jansi.AnsiConsole;

import dbrusev.CPrincipal.IdiomaEscogido;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class Funciones {

	public enum DificultadEscogida {
		FACIL,
		INTERMEDIA,
		DIFICIL,
		EXTREMA,
		PERSONALIZADA
	}
	
	private int filas, columnas;
	private List<Integer> secuenciaFilas = new ArrayList<Integer>();
	private List<Integer> secuenciaNumeros = new ArrayList<Integer>();
	
	private Scanner lector = new Scanner(System.in);
	
	private DificultadEscogida dificultad;
	private IdiomaEscogido idioma;
	
	// ----------------------------------------------------------
	
	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}
	
	public void setFilasYColumnas(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
	}

	public DificultadEscogida getDificultad() {
		return dificultad;
	}
	
	public List<Integer> getSecuenciaFilas() {
		return secuenciaFilas;
	}
	
	public List<Integer> getSecuenciaNumeros() {
		return secuenciaNumeros;
	}
	
	public IdiomaEscogido getIdioma() {
		return idioma;
	}
	
	public void setIdioma(IdiomaEscogido idioma) {
		this.idioma = idioma;
	}

	public void setDificultad(int eleccion) {
		switch (eleccion) {
		case 1: this.dificultad = DificultadEscogida.FACIL; this.filas = this.columnas = 6; break;
		case 2: this.dificultad = DificultadEscogida.INTERMEDIA; this.filas = this.columnas = 10; break;
		case 3: this.dificultad = DificultadEscogida.DIFICIL; this.filas = this.columnas = 14; break;
		case 4: this.dificultad = DificultadEscogida.EXTREMA; this.filas = this.columnas = 18; break;
		case 5: this.dificultad = DificultadEscogida.PERSONALIZADA; break;
		}
	}
	
	public String[][] preparaTablero() {
		String[][] tablero = new String[filas][columnas];
		
		for (int i = 0; i < filas; i++) { 
			HashSet<Integer> listaNumerosRandom = new HashSet<Integer>(); int j = 0;
			while (listaNumerosRandom.size() != columnas) {listaNumerosRandom.add((int) (Math.random() * (99 - 10)) + 10);}
			Iterator<Integer> iterador = listaNumerosRandom.iterator();
			while (iterador.hasNext()) {tablero[i][j] = Integer.toString(iterador.next()); j++;}
		} 
		
		for (int itr = 0; itr < columnas - 1; itr++) {
			secuenciaFilas.add((int) (Math.random() * filas));
			while (itr >= 1 && secuenciaFilas.get(itr) == secuenciaFilas.get(itr-1)) {
				secuenciaFilas.set(itr, (int) (Math.random() * filas));
			}
		}
		
		for (int clm = 0; clm < columnas; clm++) {
			if (clm + 1 != columnas) {
				int filaEscogida = secuenciaFilas.get(clm);
				tablero[filaEscogida][clm + 1] = tablero[filaEscogida][clm];
				secuenciaNumeros.add(Integer.parseInt(tablero[filaEscogida][clm]));
			}
		} return tablero;
	}
	
	public int solicitaTiempoLimite() {
		int tiempoLimite = 0;
		AnsiConsole.systemInstall();
		while (tiempoLimite < 10 || tiempoLimite > 180) {
			if (getIdioma() == IdiomaEscogido.SPANISH) {
				try {
					System.out.print("Introduzca el tiempo límite " + ansi().fg(CYAN).a("(10s a 180s)").reset() + ": "); tiempoLimite = lector.nextInt();
					if (tiempoLimite < 10 || tiempoLimite > 180) {System.out.println(ansi().fg(RED).a("Número fuera de rango..").reset() + "\n");}
				} catch (Exception noEsNumero) {System.out.println(ansi().fg(RED).a("El valor introducido no es un número.").reset() + "\n"); lector.next();}
			} else if (getIdioma() == IdiomaEscogido.ENGLISH) {
				try {
					System.out.print("Choose the time limit " + ansi().fg(CYAN).a("(10s to 180s)").reset() + ": "); tiempoLimite = lector.nextInt();
					if (tiempoLimite < 10 || tiempoLimite > 180) {System.out.println(ansi().fg(RED).a("Number out of range..").reset() + "\n");}
				} catch (Exception noEsNumero) {System.out.println(ansi().fg(RED).a("Input value is not a number.").reset() + "\n"); lector.next();}
			}
		} return tiempoLimite;
	}
	
	public void muestraTitulo() {
		AnsiConsole.systemInstall();
		System.out.println(ansi().fg(GREEN).a("  ____                   ____                      _           __  __ ____  _     ").reset());
		System.out.println(ansi().fg(GREEN).a(" / ___|  __ _ _ __      / ___|  ___  __ _ _ __ ___| |__       |  \\/  |  _ \\| |    ").reset());
		System.out.println(ansi().fg(GREEN).a(" \\___ \\ / _` | '_ \\ ____\\___ \\ / _ \\/ _` | '__/ __| '_ \\ _____| |\\/| | | | | |    ").reset());
		System.out.println(ansi().fg(GREEN).a("  ___) | (_| | | | |_____|__) |  __/ (_| | | | (__| | | |_____| |  | | |_| | |___ ").reset());
		System.out.println(ansi().fg(GREEN).a(" |____/ \\__, |_| |_|    |____/ \\___|\\__,_|_|  \\___|_| |_|     |_|  |_|____/|_____|").reset());
		System.out.println(ansi().fg(GREEN).a("           |_|                                                                    ").reset());
		System.out.println(ansi().fg(BLACK).a("-----------------------------------------------------------------------------").reset());
	}
	
	public void muestraTablero(String[][] tablero, int contador) {
		muestraTitulo(); contador--;
		for (int i = 0; i < filas; i++) {
			System.out.print("| ");
			for (int j = 0; j < columnas; j++) {
				if (tablero[i][j] == "xx") {
					if (j + 1 == columnas) {System.out.print(ansi().fg(GREEN).a(tablero[i][j]).reset());}
					else {System.out.print(ansi().fg(GREEN).a(tablero[i][j]).reset() + " - ");}
				} else {
					if (j <= contador) {
						if (j + 1 == columnas) {System.out.print(ansi().fg(RED).a(tablero[i][j]).reset());}
						else {System.out.print(ansi().fg(RED).a(tablero[i][j]).reset() + " - ");}
					} else {
						if (j + 1 == columnas) {System.out.print(tablero[i][j]);}
						else {System.out.print(tablero[i][j] + " - ");}
					}
				}
			} System.out.print(" |\n");
		}
	}
	
	public void muestraSecuenciaDesbloqueada(List<String> secuencia, int numsPorDesbloquear) {
		AnsiConsole.systemInstall();
		
		if (getIdioma() == IdiomaEscogido.SPANISH) {
			if (numsPorDesbloquear == 0) {System.out.print("\n" + ansi().fg(GREEN).a("SECUENCIA DESBLOQUEADA: ").reset() + "\n[");}
			else {System.out.print("\n" + ansi().fg(CYAN).a("SECUENCIA BLOQUEADA: ").reset() + "\n[");}
		} else if (getIdioma() == IdiomaEscogido.ENGLISH) {
			if (numsPorDesbloquear == 0) {System.out.print("\n" + ansi().fg(GREEN).a("UNLOCKED SEQUENCE: ").reset() + "\n[");}
			else {System.out.print("\n" + ansi().fg(CYAN).a("LOCKED SEQUENCE: ").reset() + "\n[");}
		}
		
		for (int i = 0; i < secuencia.size(); i++) {
			if (i + 1 != secuencia.size()) {System.out.print(secuencia.get(i) + "-");}
			else {System.out.print(secuencia.get(i));}
		} System.out.print("]\n\n");
	}
	
	// -----------------------
	// UTILIDADES DEL PROGRAMA
	// -----------------------
	public void limpiarConsola(boolean temporizador) throws InterruptedException, IOException {
		if (temporizador) {Thread.sleep(2000);}
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}
	
}
