package logica;

import util.LectorTags;

public class Cancion {
	
	private int ID;
	private String titulo;
	private String directorio;
	private int NRepro;
	private int puntuacion;
	private LectorTags lt = new LectorTags();
	
	public Cancion(String pDirectorio){
		this.directorio = pDirectorio;
		lt.setArchivo(directorio);

		this.titulo = lt.getTitulo();
		this.puntuacion = 0;
		this.NRepro = 0;
	}
	
	public int getID() {
		return this.ID;
	}
	public void setID(int pID){
		this.ID = pID;
	}
	public String getTitulo() {
		return this.titulo;
	}

	public String getDirectorio(){
		return this.directorio;
	}
	public int getNRepro() {
		return this.NRepro;
	}
	public int getPuntuacion() {
		return this.puntuacion;
	}
	
	public void setTitulo(String pTit){
		titulo = pTit;
	}
	
	public void puntuar(int pPuntos){
		this.puntuacion = pPuntos;
	}
	
	public String getGrupo(){
		Grupo miGrupo = CatalogoGrupos.getCatalogo().buscarGrupoDeCancion(ID);
		String ret="";
		if (miGrupo != null){
			ret = miGrupo.getNombre();
		}
		return ret;
	}
	
	public boolean tituloContiene(String pNombre) {
		return this.getTitulo().toLowerCase().contains(pNombre.toLowerCase());
	}

	public void aumentarNRepro(){
		NRepro++;
	}
	
	public String toString(){ 
		return this.titulo;
	}
}
