package logica;

public class Artista {

	private int ID;
	private String nombre;
	private ListaDatosBiograficos biografia = new ListaDatosBiograficos();
	
	public Artista(String pNombre){
		this.nombre = pNombre;
	}
	
	public int getID(){
		return this.ID;
	}
	public void setID(int pID) {
		this.ID = pID;
	}
	public String getNombre() {
		return this.nombre;
	}

	public void anadirDato(DatoBiografico pDato){
		this.getBiografia().anadirDato(pDato);
	}

	public DatoBiografico buscarDato(int pIDDato){
		return this.getBiografia().buscarDato(pIDDato);
	}
	
	public void eliminarDato(DatoBiografico pDato){
		this.getBiografia().eliminarDato(pDato);
	}
	
	public ListaDatosBiograficos getBiografia() {
		return this.biografia;
	}
	
	public ListaGrupos obtenerListaGruposCompleta(){
		return biografia.obtenerListaGruposCompleta();
	}
	
	public ListaEventos participaEnEventos(){
		return CatalogoEventos.getCatalogo().buscarEventosDeArtista(getID());
	}
	
	public String toString(){ 
		return this.nombre;
	}
	
}
