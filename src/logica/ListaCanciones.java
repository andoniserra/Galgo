package logica;

import java.util.Iterator;

import net.sf.jga.algorithms.Sort;

public class ListaCanciones {
	private ListaGenerica<Cancion> miLista = new ListaGenerica<Cancion>();
	
	public Iterator<Cancion> getIterador() {
		return this.miLista.getIterador();
	}
	
	public Cancion anadirCancion(Cancion pCancion) {
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
	
	public void agregarCanciones(ListaCanciones pLista){
		Iterator<Cancion> itr = pLista.getIterador();
		while (itr.hasNext()){
			miLista.anadir(itr.next());
		}
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
	
	public Cancion buscarCancion(String pNombreCancion) {
		Iterator<Cancion> itr = miLista.getIterador();
		boolean enc = false;
		Cancion unaCancion = null;
		while(!enc && itr.hasNext()) {
			unaCancion = itr.next();
			if(unaCancion.getTitulo().equalsIgnoreCase(pNombreCancion)) {
				enc = true;
			}
		}
		if(!enc) {
			unaCancion = null;
		}
		return unaCancion;
	}
	
	public Iterator<Cancion> filtrar(String pNombre){
		return miLista.filtrar(pNombre);
	}
	
	public void eliminarCancion(Cancion pCancion) {
		miLista.eliminar(pCancion);
	}

	public Cancion get(int pIndice){
		return miLista.get(pIndice);
	}
	
	public int getIndex(Cancion pCancion){
		return miLista.getIndex(pCancion);
	}
	
	public ListaCanciones buscarCanciones(String pTitulo) {
		Iterator<Cancion> itr = miLista.filtrar(pTitulo);
		ListaCanciones lista = new ListaCanciones();
		while(itr.hasNext()) {
			lista.anadirCancion(itr.next());
		}
		return lista;
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

	public int getTamano(){
		return miLista.getTamano();
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
