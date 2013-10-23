package logica;

import java.util.Iterator;

public class ListaHistorial {
		
	private ListaGenerica<Historial> miLista = new ListaGenerica<Historial>();
	
	public Iterator<Historial> getIterador() {
		return miLista.getIterador();
	}
	
	public void anadirHistorial(Historial pHistorial){
		miLista.anadir(pHistorial);
	}
	
	public Historial buscarHistorial(int pID){
		boolean enc = false;
		Historial retHist = null;
		Iterator<Historial> itr = miLista.getIterador();
		while (itr.hasNext() && !enc){
			Historial miHist = itr.next();
			if (miHist.getID() == pID){
				retHist = miHist;
			}
		}
		return retHist;
	}
	
	public void eliminarHistorial(Historial pHist){
		miLista.eliminar(pHist);
	}
	
	public ListaArtistas getComponentesActuales(){
		return null;
	}
	
	public ListaArtistas getTodosComponentes(){
		ListaArtistas retArtistas = new ListaArtistas();
		Iterator<Historial> itr = miLista.getIterador();
		
		while(itr.hasNext()){
			Historial histActual = itr.next();
			retArtistas.agregarArtistas(histActual.getComponentes());
		}
		
		
		return retArtistas;
	}
	
	
	public Historial buscarHistorialDeAnio(int pAnio){
		Historial miHist;
		Historial histRet = null;
		boolean enc = false;
		
		Iterator<Historial> itr = miLista.getIterador();
		while(itr.hasNext() && !enc){
			miHist = itr.next();
			if (miHist.historialEntre(pAnio)){
				enc = true;
				histRet = miHist;
			}
		}
		return histRet;
	}
	
	

}
