package logica;

import java.util.Calendar;
import java.util.Date;

public class Historial {

	private int ID;
	private Date fechaInicio;
	private Date fechaFin;
	private ListaArtistas componentes = new ListaArtistas();
	
	public Historial(int pID, Date pFecIni, Date pFecFin){
		this.ID = pID;
		this.fechaInicio = pFecIni;
		this.fechaFin = pFecFin;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public void setID(int pID){
		this.ID = pID;
	}
	
	public Date getFechaInicio() {
		return this.fechaInicio;
	}
	
	public Date getFechaFin() {
		return this.fechaFin;
	}
	
	public ListaArtistas getComponentes() {
		return this.componentes;
	}
	
	public boolean historialEntre(int pAnio){
		
		boolean ret = false;
		
		Calendar calIni = Calendar.getInstance();
		Calendar calFin = Calendar.getInstance();
		Calendar calComp = Calendar.getInstance();
		
		calComp.set(pAnio, 1,1 );
		
		calIni.setTime(fechaInicio);
		calFin.setTime(fechaFin);
		
		if (calComp.after(calIni) && calComp.before(calFin)){
			ret = true;
		}
		
		return ret;
	}
	
	public void anadirArtista(Artista pArtista){
		this.componentes.anadirArtista(pArtista);
	}
	
	public ListaArtistas filtrarArtistas(String pNombre){
		return componentes.filtrarArtistas(pNombre);
	}
	
	public Artista buscarArtista(int pID){
		return componentes.buscarArtista(pID);
	}
	
	public void eliminarArtista(Artista pArtista){
		this.componentes.eliminarArtista(pArtista);
	}
	
}
