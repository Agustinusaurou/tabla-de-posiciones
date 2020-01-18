package campeonato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Stack;

public class BaseDeDatos {
	public Connection conexion() {
		String sURL = "jdbc:derby:memory:myDB;create=true";
		Connection con = null;
		try {
			con = DriverManager.getConnection(sURL);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return con;
	}

	public void crearTabla(Connection con) {
		PreparedStatement tablaEquipos = null;
		PreparedStatement tablaPartidos = null;
		try {
			tablaEquipos = con.prepareStatement(
					"CREATE TABLE Equipos ("
					+ "IDEquipo int NOT NULL, "
					+ "Nombre varchar(100) not null, "
					+ "Puntaje int, "
					+ " PRIMARY KEY (IDEquipo)"
					+ ")"
					);
			tablaEquipos.execute();
			tablaPartidos = con.prepareStatement(
					"CREATE TABLE Partidos ("
					+ "IDLocal int NOT NULL , "
					+ "IDVisitante int NOT NULL , "
					+ "Fecha int NOT NULL, " 
					+ "GolesLocal int, "
					+ "GolesVisitante int, "
					+ "CONSTRAINT Partidos_pk PRIMARY KEY (IDLocal, IDVisitante), "
					+ "CONSTRAINT EquiposLocal_fk FOREIGN KEY (IDLocal) "
					+ "REFERENCES Equipos (IDEquipo), "
					+ "CONSTRAINT EquiposVisitante_fk FOREIGN KEY (IDVisitante) "
					+ "REFERENCES Equipos (IDEquipo) "
					+ ")"
					);
			tablaPartidos.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ingresarEquipo(Connection con, Integer iD, String nombre) {
		Statement carga;
		try {
			carga = con.createStatement();
			carga.addBatch(String.format("INSERT INTO Equipos VALUES (%d,'%s', 0)",iD.intValue(),nombre));
			carga.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void ingresarPartidos(Connection con, Partido partido) {
		Statement carga;
		try {
			carga = con.createStatement();
				carga.addBatch(String.format("INSERT INTO Partidos VALUES (%d, %d, %d, 0, 0)",
						partido.getLocal().getNumInscripcion().intValue() ,
						partido.getVisitante().getNumInscripcion().intValue() , 
						partido.getFecha().intValue()
						));
			carga.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarPuntos(Connection con, Equipo equipo){
		PreparedStatement modificar;
		try {
			modificar = con.prepareStatement("UPDATE Equipos SET Puntaje=? WHERE iDEquipo=?");
		modificar.setString(1, equipo.getPuntos().toString());
		modificar.setString(2, equipo.getNumInscripcion().toString());
		int retorno = modificar.executeUpdate();
		System.out.println("Number of records updated are: "+retorno);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void actualizarResultado(Connection con, Partido partido){
		PreparedStatement modificar;
		try {
			modificar = con.prepareStatement("UPDATE Partidos SET golesLocal=?, golesVisitante=? "  
				+ "WHERE iDLocal=? AND iDVisitante=?"
				);
		modificar.setString(1, partido.getGolesLocal().toString());
		modificar.setString(2, partido.getGolesVisitante().toString());
		modificar.setString(3, partido.getLocal().getNumInscripcion().toString());
		modificar.setString(4, partido.getVisitante().getNumInscripcion().toString());
		int retorno = modificar.executeUpdate();
		System.out.println("Number of records updated are: "+retorno);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public LinkedList<Equipo> leerTablaEquipos(Connection con) {
		Statement leer = null;
		Equipo equipo = null;
		LinkedList<Equipo> equipos = new LinkedList<Equipo>();	

		try {	
			leer = con.createStatement();
			ResultSet results = leer.executeQuery("select * from " + "Equipos");
			while(results.next()) {
				equipo = new Equipo(results.getString(2), results.getInt(1));
				equipo.setPuntos(results.getInt(3));
				equipos.add(equipo);
			}
			results.close();
			leer.close();
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
		}
		return equipos;
	}

	public Stack<Partido> leerTablaPartidos(Connection con, LinkedList<Equipo> equipos) {
		Statement leer = null;
		Partido partido = null;
		Stack<Partido> partidos = new Stack<Partido>();		
		Equipo local = null;
		Equipo visitante = null;
		
		try {	
			leer = con.createStatement();
			ResultSet results = leer.executeQuery("select * from " + "Partidos");
			while(results.next()) {
				for(int i = 0; i < equipos.size(); i++) {
					local = (equipos.get(i).getNumInscripcion().equals((results.getInt(1))))?equipos.get(i):local;
					visitante = (equipos.get(i).getNumInscripcion().equals((results.getInt(2))))?equipos.get(i):visitante;				}
				partido = new Partido(local, visitante, results.getInt(3));
				partido.setGolesLocal(results.getInt(4));
				partido.setGolesVisitante(results.getInt(5));
				partidos.add(partido);
			}
			results.close();
			leer.close();
		}
		catch (SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
		}
		return partidos;
	}
}
