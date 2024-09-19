package dbrusev;

import org.fusesource.jansi.AnsiConsole;

import dbrusev.CPrincipal.IdiomaEscogido;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class Temporizador extends Thread {
	
	private int tiempoLimite;
	private IdiomaEscogido idioma;
	
	// ----------------------------------------------------------
	
	public IdiomaEscogido getIdioma() {
		return idioma;
	}
	
	public void setIdioma(IdiomaEscogido idioma) {
		this.idioma = idioma;
	}
	
	public int getTiempoRestante() {
		return tiempoLimite;
	}
	
	public void setTiempoLimite(int tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}
	
	@Override
	public void run() {
		AnsiConsole.systemInstall();
		while (tiempoLimite != 0) {
			try {Thread.sleep(1000);}
			catch (InterruptedException e) {System.exit(1);}
			tiempoLimite--;
		}
		
		if (getIdioma() == IdiomaEscogido.SPANISH) {System.out.println("- BÚSQUEDA DE SECUENCIA " + ansi().fg(RED).a("FALLIDA").reset() + "- El tiempo se ha acabado.");}
		else {System.out.println("- SEQUENCE SEARCH " + ansi().fg(RED).a("FAILED").reset() + "- Time has run out.");}
		System.exit(0);
	}

}
