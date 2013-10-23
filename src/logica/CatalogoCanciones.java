package logica;

import java.util.Comparator;
import java.util.Iterator;

import net.sf.jga.algorithms.Sort;

public class CatalogoCanciones {
	private ListaGenerica<Cancion> miLista;
	private static CatalogoCanciones miCatalogo = new CatalogoCanciones();

	private CatalogoCanciones(){
		this.miLista = new ListaGenerica<Cancion>();
	}

	public static CatalogoCanciones getCatalogo(){
		return miCatalogo;
	}

	public Iterator<Cancion> getIterador() {
		return this.miLista.getIterador();
	}

	public int getTamano() { 
		return CatalogoCanciones.getCatalogo().getTamano();
	}

	public Cancion anadirCancion(Cancion pCancion){
		Cancion nuevaCancion = nombreExacto(pCancion.getTitulo());
		if(nuevaCancion == null){
			Cancion ultimo = miLista.ultimoElemento();

			if (ultimo != null){
				pCancion.setID(ultimo.getID()+1);
			}else{
				pCancion.setID(1);
			}

			miLista.anadir(pCancion);
			nuevaCancion = pCancion;
		}
		
		return nuevaCancion;
	}

	public Cancion buscarCancion(int pID) {
		Iterator<Cancion> itr = miLista.getIterador();
		boolean enc = false;
		Cancion unaCancion = null;
		while(!enc && itr.hasNext()) {
			unaCancion = itr.next();
			if(unaCancion.getID() == pID) {
				enc = true;
			}
		}
		if(!enc) {
			unaCancion = null;
		}
		return unaCancion;
	}
	
	public Cancion buscarCancion(String pNombre) {
		Iterator<Cancion> itr = miLista.getIterador();
		boolean enc = false;
		Cancion unaCancion = null;
		while(!enc && itr.hasNext()) {
			unaCancion = itr.next();
			if(unaCancion.getTitulo().equalsIgnoreCase(pNombre)) {
				enc = true;
			}
		}
		if(!enc) {
			unaCancion = null;
		}
		return unaCancion;
	}

	public ListaCanciones filtrarCanciones(String pTitulo){
		ListaCanciones retLista = new ListaCanciones();
		Iterator<Cancion> itr = miLista.filtrar(pTitulo); 

		while (itr.hasNext()){
			retLista.anadirCancion(itr.next());
		}

		return retLista; 
	}

	public void eliminarCancion(Cancion pCancion) {
		miLista.eliminar(pCancion);
	}
	
	public void vaciar(){
		miLista.vaciar();
	}

	public ListaCanciones obtenerTodas(){
		Iterator<Cancion> itr = miLista.getIterador();
		ListaCanciones retLista = new ListaCanciones();
		while(itr.hasNext()){
			retLista.anadirCancion(itr.next());
		}
		return retLista;
	}
	
	public ListaCanciones obtenerMasValoradas(int pCuantas) {
		int cont = 0;
		ListaCanciones retLista = new ListaCanciones();
		Iterator<Cancion> itr = Sort.sort(miLista.getIterador(), new comparadorPuntuacion());
		
		while( cont < pCuantas && itr.hasNext() ){
			retLista.anadirCancion(itr.next());
		}
		return retLista;
	}

	public ListaCanciones obtenerMasEscuchadas(int pCuantas) {
		int cont = 0;
		ListaCanciones retLista = new ListaCanciones();
		Iterator<Cancion> itr = Sort.sort(miLista.getIterador(), new comparadorReproducciones());
		
		while( cont < pCuantas && itr.hasNext() ){
			retLista.anadirCancion(itr.next());
		}
		return retLista;
	}

	private Cancion nombreExacto(String pNombre){
		boolean enc = false;
		Cancion miCancion, retCancion = null;
		Iterator<Cancion> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			miCancion = itr.next();
			if (miCancion.getTitulo().equals(pNombre)){
				retCancion = miCancion;
				enc = true;
			}
		}
		return retCancion;
	}
}

class comparadorPuntuacion implements Comparator<Cancion>{

	@Override
	public int compare(Cancion C1, Cancion C2) {
		return C1.getPuntuacion() - C2.getPuntuacion();
	}
	
}

class comparadorReproducciones implements Comparator<Cancion>{

	@Override
	public int compare(Cancion C1, Cancion C2) {
		return C1.getNRepro() - C2.getNRepro();
	}
	
}
