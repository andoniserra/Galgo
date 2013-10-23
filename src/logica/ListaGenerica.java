package logica;

import java.util.ArrayList;
import java.util.Iterator;

import net.sf.jga.algorithms.Filter;
import net.sf.jga.fn.UnaryFunctor;

public class ListaGenerica<T> {

	private ArrayList<T> miLista;

	//Constructor
	public ListaGenerica(){miLista = new ArrayList<T>();}

	//Devolvemos el tamaño
	public int getTamano(){return miLista.size();}

	//Obtener el indice
	public T get(int pIndex){return miLista.get(pIndex);}
	
	//Obtener la posicion del objeto
	public int getIndex(T pObj){return miLista.indexOf(pObj);}
	
	//Añadimos un objeto
	public void anadir(T pObj){miLista.add(pObj);}

	//Obtenemos Iterador
	public Iterator<T> getIterador() {return miLista.iterator();}

	//Filtramos por nombre
	public Iterator<T> filtrar(String pStr){return Filter.filter(miLista, new encontrar<T>(pStr)).iterator();}

	//Eliminamos un elemento
	public void eliminar(T pObj){miLista.remove(pObj);}

	//Vaciamos la lista
	public void vaciar(){miLista.clear();}
	
	//Obtenemos el ultimo elemento de la lista
	public T ultimoElemento(){
		T ret = null;
		if (miLista.size() > 0) {
			ret = miLista.get(miLista.size() - 1 );
		}
		return ret;
	}
	
}

class encontrar<T> extends UnaryFunctor<T, Boolean>{
	private static final long serialVersionUID = 1L;
	String str;
	public encontrar(String pStr) {
		this.str = pStr;
	}

	@Override
	public Boolean fn(T pObj) {
		String strObj = pObj.toString().toUpperCase();
		return strObj.contains(str.toUpperCase());
	}
}