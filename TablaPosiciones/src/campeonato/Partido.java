package campeonato;

public class Partido {
	private Integer fecha;
	private Equipo local;
	private Equipo visitante;
	private Integer golesLocal;
	private Integer golesVisitante;
	private Resultado resutado;
	
	public Partido(Equipo local, Equipo visitante, Integer fecha) {
		this.local = local;
		this.visitante = visitante;
		this.fecha = fecha;
	}

	public Integer getGolesLocal() {
		return golesLocal;
	}

	public void setGolesLocal(Integer golesLocal) {
		this.golesLocal = golesLocal;
	}

	public Equipo getLocal() {
		return local;
	}

	public Equipo getVisitante() {
		return visitante;
	}

	public Integer getFecha() {
		return fecha;
	}

	public void setFecha(Integer fecha) {
		this.fecha = fecha;
	}

	public Integer getGolesVisitante() {
		return golesVisitante;
	}

	public void setGolesVisitante(Integer golesVisitante) {
		this.golesVisitante = golesVisitante;
	}

	public Resultado getResutado() {
		return resutado;
	}

	public void setResutado(Resultado resutado) {
		this.resutado = resutado;
	}
}
