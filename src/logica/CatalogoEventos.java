package logica;

import java.util.Date;
import java.util.Iterator;

public class CatalogoEventos{

	private static CatalogoEventos miCatalogo = new CatalogoEventos();
	private ListaGenerica<Evento> miLista = new ListaGenerica<Evento>();

	private CatalogoEventos(){}

	public static CatalogoEventos getCatalogo(){
		return miCatalogo;
	}

	public void anadirEvento(Evento pEvento){
		pEvento.setID(CatalogoEventos.getCatalogo().getTamano() + 1);
		miLista.anadir(pEvento);
	}

	public Evento buscarEvento(int pID){
		boolean enc = false;
		Evento miEvento, retGrupo = null;
		Iterator<Evento> itr = miLista.getIterador();

		while (itr.hasNext() && !enc){
			miEvento = itr.next();
			if (miEvento.getID() == pID){
				retGrupo = miEvento;
				enc = true;
			}
		}
		return retGrupo;

	}

	public int getTamano() { 
		return CatalogoEventos.getCatalogo().getTamano();
	}

	public ListaEventos buscarEventosDeArtista(int pIDArtista){
		Iterator<Evento> itr = miLista.getIterador();
		ListaEventos retEventos = new ListaEventos();		
		while(itr.hasNext()){
			Evento miEvento = itr.next();
			if (miEvento.contieneArtista(pIDArtista)){
				retEventos.anadirEvento(miEvento);
			}
		}
		return retEventos;
	}

	public ListaEventos buscarEventosDeGrupo(int pIDGrupo){
		Iterator<Evento> itr = miLista.getIterador();
		ListaEventos retEventos = new ListaEventos();		
		while(itr.hasNext()){
			Evento miEvento = itr.next();
			if (miEvento.contieneGrupo(pIDGrupo)){
				retEventos.anadirEvento(miEvento);
			}
		}
		return retEventos;
	}

	public ListaEventos buscarEventoPorLugar(String pLugar){
		ListaEventos retLista = new ListaEventos(); 
		Iterator<Evento> itr = miLista.filtrar(pLugar);
		while(itr.hasNext()){
			Evento miEvento = itr.next();
			if(miEvento.getLugar().equalsIgnoreCase(pLugar)){
				retLista.anadirEvento(miEvento);
			}
		}
		return retLista;
	}

	public ListaEventos eventosEntre(Date pInicio, Date pFin){
		ListaEventos retLista = new ListaEventos(); 
		Iterator<Evento> itr = miLista.getIterador();
		while (itr.hasNext()){
			Evento miEvento = itr.next();
			if(miEvento.seraEntre(pInicio, pFin)){
				retLista.anadirEvento(miEvento);
			}
		}
		return retLista;
	}
}
