package logica;

import java.util.Iterator;

public class ListasReproduccion{
	
	private ListaGenerica<Lista> miLista = new ListaGenerica<Lista>();
	
	public void anadirLista(Lista pLista){
		miLista.anadir(pLista);
	}
	
	public Lista buscarLista(int pID){
		boolean enc = false;
		Lista retLista = null;
		Iterator<Lista> itr = miLista.getIterador();
		
		while (itr.hasNext() && !enc){
			Lista actLista = itr.next();
			if(actLista.getID() == pID){
				retLista = actLista;
				enc = true;
			}
		}
		
		return retLista;
	}

	public void eliminarLista(Lista pLista){
		miLista.eliminar(pLista);
	}

}
