package dbrusev;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dbrusev.CPrincipal.IdiomaEscogido;

public class Estadisticas {
	
	private final Date fecha = new Date();
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    
	private final String nombreArchivo = formatoFecha.format(fecha) + ".txt";
	private final File directorio = new File("SequenceHuntStats");
	private final File archivo = new File(directorio + File.separator + nombreArchivo);
	
	private List<String> secuenciaDesbloqueada;
	private Integer tiempoCompletado;
	
	private IdiomaEscogido idioma;
	
	public Estadisticas(List<String> secuencia, Integer tiempoCompletado) {
		this.secuenciaDesbloqueada = secuencia;
		this.tiempoCompletado = tiempoCompletado;
	}
	
	public IdiomaEscogido getIdioma() {
		return idioma;
	}
	
	public void setIdioma(IdiomaEscogido idioma) {
		this.idioma = idioma;
	}
	
	public String getLocalizacionArchivo() {
		return "./" + directorio + "/" + nombreArchivo;
	}
	
	public void guardarEstadisticas() throws IOException {
		if (!directorio.exists()) {directorio.mkdirs();} archivo.setWritable(true);
		BufferedWriter escrituraDatos = new BufferedWriter(new FileWriter(archivo, true));
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
		
		escrituraDatos.append(formatoHora.format(fecha) + " -> [");
		for (int i = 0; i < secuenciaDesbloqueada.size(); i++) {
			if (i + 1 == secuenciaDesbloqueada.size()) {escrituraDatos.append(secuenciaDesbloqueada.get(i));}
			else {escrituraDatos.append(secuenciaDesbloqueada.get(i) + "-");}
		} 
		
		if (getIdioma() == IdiomaEscogido.SPANISH) {escrituraDatos.append("] - Completada en " + tiempoCompletado + " segundos.\n");}
		else if (getIdioma() == IdiomaEscogido.ENGLISH) {escrituraDatos.append("] - Completed in " + tiempoCompletado + " seconds.\n");}
		
		escrituraDatos.close(); archivo.setWritable(false);
	}

}
