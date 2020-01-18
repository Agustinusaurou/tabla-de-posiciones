package campeonato;

public class Equipo {
	private Integer numInscripcion;
	private String nombre;
	private Integer puntos;

	public Equipo(String nombre, Integer numInscripcion) {
		this.nombre = nombre;
		this.numInscripcion = numInscripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getNumInscripcion() {
		return numInscripcion;
	}
	
	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
}
