package logica;

import java.util.Iterator;

public class ListaGrupos {

	private ListaGenerica<Grupo> miLista = new ListaGenerica<Grupo>();
	
	public Iterator<Grupo> getIterador(){
		return miLista.getIterador();
	}
	
	public Grupo anadirGrupo(Grupo pGrupo){
		Grupo nuevoGrupo = nombreExacto(pGrupo.getNombre());
		if(nuevoGrupo == null){
			Grupo ultimo = miLista.ultimoElemento();

			if (ultimo != null){
				pGrupo.setID(ultimo.getID()+1);
			}else{
				pGrupo.setID(1);
			}
			miLista.anadir(pGrupo);
			nuevoGrupo = pGrupo;
		}
		
		return nuevoGrupo;
	}

	public Grupo buscarGrupo(int pID){
		boolean enc = false;
		Grupo miGrupo, retGrupo = null;
		Iterator<Grupo> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			miGrupo = itr.next();
			if (miGrupo.getID() == pID){
				retGrupo = miGrupo;
				enc = true;
			}
		}
		return retGrupo;
	}
	
	public Grupo buscarGrupo(String pNombre){
		boolean enc = false;
		Grupo miGrupo, retGrupo = null;
		Iterator<Grupo> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			miGrupo = itr.next();
			if (miGrupo.getNombre().equalsIgnoreCase(pNombre)){
				retGrupo = miGrupo;
				enc = true;
			}
		}
		return retGrupo;
	}

	public void eliminarGrupo(Grupo pGrupo){
		miLista.eliminar(pGrupo);
	}

	public Grupo buscarGrupoDeCancion(int pIDCancion){
		boolean enc = false;
		Grupo retGrupo = null;
		Iterator<Grupo> itr = miLista.getIterador();
		
		while (itr.hasNext() && !enc){
			Grupo miGrupo = itr.next();
			if (miGrupo.buscarCancion(pIDCancion) != null){
				retGrupo = miGrupo;
				enc = true;
			}
		}
		
		return retGrupo;
	}
	
	public Artista obtenerArtistaDeGrupo(int pIDArtista, int pIDGrupo){
		Artista retArt = null;
		Grupo miGrupo = buscarGrupo(pIDGrupo);
		if (miGrupo != null){
			retArt = miGrupo.obtenerComponentes().buscarArtista(pIDArtista);
		}
		return retArt;
	}

	public ListaArtistas obtenerArtistasActualesDeGrupo(int pIDGrupo){
		ListaArtistas retLista = new ListaArtistas();
		Grupo miGrupo = buscarGrupo(pIDGrupo);
		if (miGrupo != null){
			retLista = miGrupo.obtenerArtistasActuales();
		}
		return retLista;		
	}

	public ListaDiscos obtenerTodosDiscosDeGrupo(int pIDGrupo){
		ListaDiscos retLista = null;
		Grupo miGrupo = buscarGrupo(pIDGrupo);
		if (miGrupo != null){
			retLista = miGrupo.getDiscografia();
		}
		return retLista;
	}

	public ListaCanciones filtrarCanciones(String pCancion){
		ListaCanciones retLista = new ListaCanciones();
		Iterator<Grupo> itr = miLista.getIterador();
		while(itr.hasNext()){
			Grupo miGrupo = itr.next();
			retLista.agregarCanciones(miGrupo.filtrarCanciones(pCancion));
		}
		return retLista;
	}

	public Cancion buscarCancion(int pID){
		boolean enc = false;
		Iterator<Grupo> itr = miLista.getIterador();
		Cancion retCancion = null;
		while(itr.hasNext() && !enc){
			Grupo miGrupo = itr.next();
			Cancion miCancion = miGrupo.buscarCancion(pID);
			if(miCancion != null){
				retCancion = miCancion;
				enc = true;
			}
		}
		return retCancion;
	}

	private Grupo nombreExacto(String pNombre){
		boolean enc = false;
		Grupo miGrupo, retGrupo = null;
		Iterator<Grupo> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			miGrupo = itr.next();
			if (miGrupo.getNombre().equals(pNombre)){
				retGrupo = miGrupo;
				enc = true;
			}
		}
		return retGrupo;
	}
}


