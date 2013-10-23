package logica;

import java.util.Iterator;

public class ListaDatosBiograficos{

	private ListaGenerica<DatoBiografico> miLista = new ListaGenerica<DatoBiografico>();

	public void anadirDato(DatoBiografico pDato){
		if (buscarDato(pDato.getID()) == null){
			miLista.anadir(pDato);
		}
	}

	public DatoBiografico buscarDato(int pID){
		boolean enc = false;
		DatoBiografico retDato = null;
		Iterator<DatoBiografico> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			DatoBiografico datoActual = itr.next();
			if (datoActual.getID() == pID){
				retDato = datoActual;
				enc = true;
			}
		}

		return retDato;
	}

	public void eliminarDato(DatoBiografico pDato){
		miLista.eliminar(pDato);
	}

	public ListaGenerica<DatoBiografico> getLista(){ 
		return this.miLista;
	}

	public ListaGrupos obtenerListaGruposCompleta(){
		ListaGrupos retGrupos = new ListaGrupos();
		Iterator<DatoBiografico> itr = miLista.getIterador();

		while(itr.hasNext()){
			retGrupos.anadirGrupo(itr.next().getGrupo());
		}
		return retGrupos;
	}

	public ListaGrupos obtenerListaGruposActuales(){
		ListaGrupos retGrupos = new ListaGrupos();
		Iterator<DatoBiografico> itr = miLista.getIterador();

		while(itr.hasNext()){
			DatoBiografico miDato = itr.next();
			if(miDato.esActual()){
				if(retGrupos.buscarGrupo(miDato.getGrupo().getID()) == null){
					retGrupos.anadirGrupo(miDato.getGrupo());
				}
			}
		}
		return retGrupos;
	}
}
