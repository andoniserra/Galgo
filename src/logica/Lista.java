package logica;

import java.util.Date;

public class Lista {

	private int ID;
	private Date fechaCreacion;
	private ListaCanciones canciones = new ListaCanciones();
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int pID){
		this.ID = pID;
	}
	
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}
	public ListaCanciones getCanciones() {
		return this.canciones;
	}
	
	public void agregarCancion(Cancion pCancion){
		this.canciones.anadirCancion(pCancion);
	}
	
	public void eliminarCancion(Cancion pCancion){
		this.canciones.eliminarCancion(pCancion);
	}
}
