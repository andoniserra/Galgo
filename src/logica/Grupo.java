package logica;

import java.util.Date;
import java.util.Iterator;

public class Grupo {

	private int ID;
	private String nombre;
	private Date creacion;
	private ListaDiscos discografia = new ListaDiscos();
	private ListaHistorial historial = new ListaHistorial();
	
	public Grupo(String pNombre){
		this.nombre = pNombre;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int pID){
		this.ID = pID;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setDate(Date pFecha){
		creacion = pFecha;
	}

	public Date getCreacion() {
		return creacion;
	}
	
	public ListaDiscos getDiscografia() {
		return discografia;
	}

	public ListaHistorial getHistorial() {
		return historial;
	}
	
	public Disco anadirDisco(Disco pDisco){
		return this.discografia.anadirDisco(pDisco);
	}
	
	public void eliminarDisco(Disco pDisco){
		discografia.eliminarDisco(pDisco);
	}
	
	public void anadirHistorial(Historial pHistorial){
		this.historial.anadirHistorial(pHistorial);
	}
	
	public Disco buscarDisco(int pIDDisco){
		return discografia.buscarDisco(pIDDisco);
	}
	
	public Disco buscarDisco(String pNombreDisco){
		return discografia.buscarDisco(pNombreDisco);
	}

	public Cancion buscarCancion(int pIDCancion){
		return discografia.buscarCancion(pIDCancion);
	}
	
	public Disco buscarDiscoDeCancion(int pIDCancion){
		return discografia.buscarDiscoDeCancion(pIDCancion);
	}
	
	public ListaCanciones obtenerTodasLasCanciones(){
		return discografia.obtenerTodasLasCanciones();
	}
	
	public String toString(){ 
		return this.nombre;
	}
	
	public ListaCanciones obtenerMasValoradas(int pCuantas) {
		return discografia.obtenerMasValoradas(pCuantas);
	}
	
	public ListaCanciones obtenerMasEscuchadas(int pCuantas) {
		return discografia.obtenerMasEscuchadas(pCuantas);
	}
	
	public ListaEventos futurosConciertos(){
		return CatalogoEventos.getCatalogo().buscarEventosDeGrupo(ID);
	}
	
	public ListaArtistas obtenerComponentes(){
		return historial.getTodosComponentes();
	}
	
	public ListaCanciones filtrarCanciones(String pNombre){
		ListaCanciones retLista = new ListaCanciones();
		Iterator<Disco> itr = discografia.getIterador();
		while(itr.hasNext()){
			retLista.agregarCanciones(itr.next().getListaCanciones());
		}
		return retLista;
	}
	
	public ListaArtistas obtenerArtistasActuales(){
		return historial.getComponentesActuales();
	}
}
