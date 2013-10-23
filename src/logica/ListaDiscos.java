package logica;

import java.util.Iterator;

public class ListaDiscos {

	private ListaGenerica<Disco> miLista = new ListaGenerica<Disco>();

	public Iterator<Disco> getIterador() { 
		return this.miLista.getIterador();
	}

	public Disco anadirDisco(Disco pDisco) {
		Disco nuevoDisco = nombreExacto(pDisco.getTitulo());

		if (nuevoDisco == null){
			Disco ultimo = miLista.ultimoElemento();
			if (ultimo != null){
				pDisco.setID(ultimo.getID()+1);
			}else{
				pDisco.setID(1);
			}
			miLista.anadir(pDisco);
			nuevoDisco = pDisco;
		}

		return nuevoDisco;
	}

	public void eliminarDisco(Disco pDisco) {
		miLista.eliminar(pDisco);
	}

	public Disco buscarDisco(int pID) {
		Disco unDisco = null;
		Iterator<Disco> itr = this.getIterador();
		boolean enc = false;
		while(itr.hasNext() && !enc) {
			unDisco = itr.next();
			if(unDisco.getID() == pID) {
				enc = true;
			}
		}
		if(!enc) {
			unDisco = null;
		}
		return unDisco;
	}

	public Disco buscarDisco(String pNombre) {
		Disco unDisco = null;
		Iterator<Disco> itr = this.getIterador();
		boolean enc = false;
		while(itr.hasNext() && !enc) {
			unDisco = itr.next();
			if(unDisco.getTitulo().equalsIgnoreCase(pNombre)) {
				enc = true;
			}
		}
		if(!enc) {
			unDisco = null;
		}
		return unDisco;
	}

	public ListaDiscos filtrarDisco(String pNombre) {
		ListaDiscos retLista = new ListaDiscos();
		Iterator<Disco> itr = miLista.filtrar(pNombre); 

		while (itr.hasNext()){
			retLista.anadirDisco(itr.next());
		}

		return retLista; 
	}

	public Cancion buscarCancion(int pIDCancion){
		boolean enc = false;
		Iterator<Disco> itr = miLista.getIterador();
		Cancion retCancion = null;
		while (itr.hasNext() && !enc){
			Cancion miCancion = itr.next().buscarCancion(pIDCancion);
			if(miCancion != null){
				retCancion = miCancion;
				enc = true;
			}
		}

		return retCancion;
	}

	public ListaCanciones buscarCanciones(String pNombre) {
		ListaCanciones rdo = new ListaCanciones();
		Iterator<Disco> itr = this.getIterador();

		while(itr.hasNext()) {
			Iterator<Cancion> itrCan = itr.next().filtrarCanciones(pNombre).getIterador();

			while (itrCan.hasNext()){
				Cancion cancionActual = itrCan.next();
				if (rdo.buscarCancion(cancionActual.getID()) == null)
				{
					rdo.anadirCancion(cancionActual);
				}
			}
		}

		return rdo;
	}

	public Disco buscarDiscoDeCancion(int pIDCancion){
		boolean enc = false;
		Disco retDisco = null;
		Iterator<Disco> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			Disco miDisco = itr.next();
			if (miDisco.buscarCancion(pIDCancion) != null){
				retDisco = miDisco;
				enc = true;
			}

		}

		return retDisco;
	}

	public ListaCanciones obtenerTodasLasCanciones(){
		ListaCanciones retLista = new ListaCanciones();
		Iterator<Disco> itr = miLista.getIterador();
		
		while(itr.hasNext()){
			retLista.agregarCanciones(itr.next().getListaCanciones());
		}
		
		return retLista;
		
	}

	public ListaCanciones obtenerMasValoradas(int pCuantas) { //TODO
		ListaCanciones retLista = new ListaCanciones();
		Iterator<Disco> itr = miLista.getIterador();

		while(itr.hasNext()){
			retLista.agregarCanciones(itr.next().obtenerMasValoradas(pCuantas));
		}

		return retLista.obtenerMasValoradas(pCuantas);
	}

	public ListaCanciones obtenerMasEscuchadas(int pCuantas) { //TODO
		ListaCanciones retLista = new ListaCanciones();
		Iterator<Disco> itr = miLista.getIterador();

		while(itr.hasNext()){
			retLista.agregarCanciones(itr.next().obtenerMasEscuchadas(pCuantas));
		}

		return retLista.obtenerMasEscuchadas(pCuantas);
	}

	private Disco nombreExacto(String pNombre){
		boolean enc = false;
		Disco miDisco, retDisco = null;
		Iterator<Disco> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			miDisco = itr.next();
			if (miDisco.getTitulo().equalsIgnoreCase(pNombre)){
				retDisco = miDisco;
				enc = true;
			}
		}
		return retDisco;
	}

}
