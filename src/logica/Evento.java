package logica;

import java.util.Date;

public class Evento {

	private int ID;
	private Date fechaHora;
	private String lugar;
	private ListaGrupos grupos = new ListaGrupos();
	
	public Evento(Date pFecha, String pLugar){
		this.fechaHora = pFecha;
		this.lugar = pLugar;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int pID) {
		this.ID = pID;
	}
	
	public Date getFechaHora() {
		return this.fechaHora;
	}
	
	public String getLugar() {
		return this.lugar;
	}
	
	public ListaGrupos getGrupos() {
		return this.grupos;
	}

	public void anadirGrupo(Grupo pGrupo){
		this.getGrupos().anadirGrupo(pGrupo);
	}
	
	public String toString(){
		return this.lugar;
	}
	
	public boolean seraEn(String pLugar){
		//TODO
		return false;
	}
	
	public boolean seraEntre(Date pInicio, Date pFin){
		//TODO
		return false;
	}
	
	public boolean contieneArtista(int pIDArtista){
		return false;
	}
	
	public boolean contieneGrupo(int pIDGrupo){
		return false;
	}
}
