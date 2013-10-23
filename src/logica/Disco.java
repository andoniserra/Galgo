package logica;

import java.util.Iterator;

public class Disco {
	private int ID;
	private String titulo;
	private String estilo;
	private ListaCanciones lista = new ListaCanciones();
	private int publicacion;
	
	public Disco(String pTitulo, String pEstilo, int pFecPub){
		this.titulo = pTitulo;
		this.estilo = pEstilo;
		this.publicacion = pFecPub;
	}
	
	public int getID() {
		return this.ID;
	}
	public void setID(int pID){
		this.ID = pID;
	}
	public String getTitulo() {
		return this.titulo;
	}
	public String getEstilo() {
		return this.estilo;
	}
	public ListaCanciones getListaCanciones() {
		return this.lista;
	}
	public int getPublicacion() {
		return this.publicacion;
	}
	
	public void anadirCancionADisco(Cancion pCancion){
		lista.anadirCancion(pCancion);
	}
	
	public Cancion buscarCancion(int pID){
		return lista.buscarCancion(pID);
	}
	
	public Cancion buscarCancion(String pNombre){
		return lista.buscarCancion(pNombre);
	}
	
	public ListaCanciones filtrarCanciones(String pNombreCancion){
		return lista.buscarCanciones(pNombreCancion);
	}
	
	public String toString(){
		return this.titulo;
	}
	
	public int obtenerAnioPublicacion() {
	    return publicacion;
	}
	
	public ListaCanciones obtenerCanciones(String pNombre) {
		ListaCanciones rdo = new ListaCanciones();
		Iterator<Cancion> itr = lista.filtrar(pNombre);
		while(itr.hasNext()) {
			rdo.anadirCancion(itr.next());
		}
		return rdo;
	}
	
	public ListaCanciones obtenerMasValoradas(int pCuantas) {
		return lista.obtenerMasValoradas(pCuantas);
	}
	
	public ListaCanciones obtenerMasEscuchadas(int pCuantas) {
		return lista.obtenerMasEscuchadas(pCuantas);
	}
}
