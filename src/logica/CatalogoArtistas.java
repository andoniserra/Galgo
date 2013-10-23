package logica;

import java.util.Iterator;

public class CatalogoArtistas{

	private static CatalogoArtistas miCatalogo = new CatalogoArtistas();
	private ListaGenerica<Artista> miLista = new ListaGenerica<Artista>();

	private CatalogoArtistas(){}

	public static CatalogoArtistas getCatalogo() {
		return miCatalogo;
	}

	public Artista anadirArtista(Artista pArtista){
		Artista nuevoArtista = nombreExacto(pArtista.getNombre());
		
		if (nuevoArtista == null){
			Artista ultimo = miLista.ultimoElemento();
			if (ultimo != null){
				pArtista.setID(ultimo.getID()+1);
			}else{
				pArtista.setID(1);
			}
			miLista.anadir(pArtista);
			nuevoArtista = pArtista;
		}
		
		return nuevoArtista;
	}

	public void agregarArtistas(ListaArtistas pLista){
		Iterator<Artista> itr = pLista.getIterador();
		while (itr.hasNext()){
			anadirArtista(itr.next());
		}
	}
	
	public Artista buscarArtista(int pID){
		boolean enc = false;
		Artista miArtista, retArtista = null;
		Iterator<Artista> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			miArtista = itr.next();
			if (miArtista.getID() == pID){
				retArtista = miArtista;
				enc = true;
			}
		}
		return retArtista;
	}

	public Artista buscarArtista(String pNombre){
		boolean enc = false;
		Artista miArtista, retArtista = null;
		Iterator<Artista> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			miArtista = itr.next();
			if (miArtista.getNombre().equalsIgnoreCase(pNombre)){
				retArtista = miArtista;
				enc = true;
			}
		}
		return retArtista;
	}
	
	public ListaArtistas filtrarArtistas(String pNombre){
		ListaArtistas retLista = new ListaArtistas();
		Iterator<Artista> itr = miLista.filtrar(pNombre); 

		while (itr.hasNext()){
			retLista.anadirArtista(itr.next());
		}

		return retLista; 
	}

	public void eliminarArtista(Artista pArtista){
		miLista.eliminar(pArtista);
	}

	public ListaGrupos getFormacionesDeArtista(int pID){
		Artista artActual = buscarArtista(pID);
		ListaGrupos retLista = null;
		if (artActual != null){
			retLista =  artActual.obtenerListaGruposCompleta();
		}
		return retLista;
	}

	private Artista nombreExacto(String pNombre){
		boolean enc = false;
		Artista miArtista, retArtista = null;
		Iterator<Artista> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			miArtista = itr.next();
			if (miArtista.getNombre().equals( pNombre)){
				retArtista = miArtista;
				enc = true;
			}
		}
		return retArtista;
	}

}	