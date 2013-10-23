package logica;

import java.util.Calendar;
import java.util.Date;

public class DatoBiografico {

	private int ID;
	private Date fechaInicio;
	private Date fechaFin;
	private Grupo grupo;
	
	public DatoBiografico(int pID, Date pFecIni, Date pFecFin, Grupo pGrupo){
		this.ID = pID;
		this.fechaInicio = pFecIni;
		this.fechaFin = pFecFin;
		this.grupo = pGrupo;
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
	
	public Grupo getGrupo() {
		return this.grupo;
	}
	
	public boolean esActual(){
		boolean ret = false;
		if (fechaInicio.before(Calendar.getInstance().getTime()) && 
			fechaFin.after(Calendar.getInstance().getTime())){
			ret = true;
		}
		
		return ret;
	}
	

}
