package campeonato;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Stack;

public class Campeonato {

	public static void crearNuevoCampeonato() {
		Stack<Partido> partidos = new Stack<Partido>();
		LinkedList<Equipo> equipos = new LinkedList<Equipo>();
		Integer cantEquipo = null;
		String nombreEquipo = null;
		Connection con = null;
		BaseDeDatos baseDatos = new BaseDeDatos();

		con = baseDatos.conexion();
		baseDatos.crearTabla(con);
		cantEquipo = ManejoDeEntrada.pedirNumero();
		for (int i = 0; i < cantEquipo; i++) {
			nombreEquipo = ManejoDeEntrada.pedirPalabra();
			equipos.add(new Equipo(nombreEquipo, i));
			baseDatos.ingresarEquipo(con, equipos.get(i).getNumInscripcion(), equipos.get(i).getNombre());
		}
		Integer contadorFecha = 0;
		while (!equipos.isEmpty()) {
			partidos = nuevoPartido(equipos, contadorFecha, partidos, cantEquipo);
			contadorFecha++;
		}
		for (int i = 0; i < partidos.size(); i++) {
			baseDatos.ingresarPartidos(con, partidos.get(i));
		}
	}

	public static Stack<Partido> nuevoPartido(LinkedList<Equipo> equipos, Integer contFecha, Stack<Partido> partidos,
			Integer cant) {
		Integer fecha = null;
		Equipo equipo = equipos.peek();
		equipos.poll();
		if (!equipos.isEmpty()) {
			for (int i = 0; i < equipos.size(); i++) {
				fecha = buscarFecha(i, partidos, cant, equipos, contFecha);
				partidos.add(new Partido(equipo, equipos.get(i), fecha));
			}
		}
		return partidos;
	}

	public static Integer buscarFecha(int i, Stack<Partido> partidos, Integer cant, LinkedList<Equipo> equipos,
			Integer contFecha) {
		Integer fecha = null;

		if (partidos.isEmpty()) {
			return 1;
		}
		if (i == 0) {
			fecha = partidos.peek().getFecha() + 3;
		} else {
			fecha = partidos.peek().getFecha() + 1;
		}
		if (i == equipos.size() - 1) {
			fecha = ((fecha + contFecha) % (cant - 1) == 0) ? 3 : (fecha + contFecha) % (cant - 1);
			return fecha;
		} else {
			fecha = (fecha % (cant - 1) == 0) ? 3 : fecha % (cant - 1);
			return fecha;
		}
	}

	public static Stack<Partido> cargarCampeonato(Connection con, BaseDeDatos bd) {
		LinkedList<Equipo> equipos = bd.leerTablaEquipos(con);
		Stack<Partido> partidos = bd.leerTablaPartidos(con, equipos);
		return partidos;
	}

	public static void cambiarResultado(Connection con, BaseDeDatos bd, Partido partido) {
		partido.setGolesLocal(ManejoDeEntrada.pedirNumero());
		partido.setGolesVisitante(ManejoDeEntrada.pedirNumero());
		if(partido.getGolesLocal() == partido.getGolesVisitante()) {
			partido.getLocal().setPuntos(partido.getLocal().getPuntos() + 1);
			partido.getVisitante().setPuntos(partido.getVisitante().getPuntos() + 1);
			bd.actualizarPuntos(con, partido.getLocal());
			bd.actualizarPuntos(con, partido.getVisitante());	
		}else if(partido.getGolesLocal() > partido.getGolesVisitante()) {
			partido.getLocal().setPuntos(partido.getLocal().getPuntos() + 3);
			bd.actualizarPuntos(con, partido.getLocal());
		}else {
			partido.getVisitante().setPuntos(partido.getVisitante().getPuntos() + 3);			
			bd.actualizarPuntos(con, partido.getVisitante());
		}
		bd.actualizarResultado(con, partido);
	}
	
	public void reiniciarCampeonato(Connection con, BaseDeDatos bd, Stack<Partido> partidos) {
		for(int i = 0; i < partidos.size(); i++) {
			if(partidos.get(i).getFecha() == 1) {
				partidos.get(i).setGolesLocal(0);
				partidos.get(i).setGolesVisitante(0);
				partidos.get(i).getLocal().setPuntos(0);
				partidos.get(i).getVisitante().setPuntos(0);
				bd.actualizarPuntos(con, partidos.get(i).getLocal());
				bd.actualizarPuntos(con, partidos.get(i).getVisitante());
				bd.actualizarResultado(con, partidos.get(i));			
			}else {
				partidos.get(i).setGolesLocal(0);
				partidos.get(i).setGolesVisitante(0);
				bd.actualizarResultado(con, partidos.get(i));						
			}
		}
	}
}