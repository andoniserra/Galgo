package logica;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListaGenericaTest {
	
	ListaGenerica<Integer> lista = new ListaGenerica<Integer>();
	int obj1;
	int obj2;
	int obj3;
	int obj4;

	@Before
	public void setUp() throws Exception {
		obj1 = 1;
		obj2 = 2;
		obj3 = 3;
		obj4 = 1234;
	}

	@After
	public void tearDown() throws Exception {
		lista.vaciar();
		
		obj1 = 0;
		obj2 = 0;
		obj3 = 0;
		obj4 = 0;
				
	}

	@Test
	public void testListaGenerica() {
		//Comprobamos que la lista no es null
		assertNotNull(lista);
	}

	@Test
	public void testAnadir() {
		//A�adir un objeto en una lista vacia�
		lista.anadir(obj1);
		assertEquals(lista.getTamano(),1);
		//A�adir un objeto en una lista con un elemento
		lista.anadir(obj2);
		assertEquals(lista.getTamano(),2);
		//A�adir un objeto en una lista con mas de un elemento
		lista.anadir(obj3);
		assertEquals(lista.getTamano(),3);
		//A�adir un objeto existente en una lista
		lista.anadir(obj2);
		assertEquals(lista.getTamano(),3);
	}

	@Test
	public void testFiltrar(){
		Iterator<Integer> itr = null;
		int cont = 0;
		//Filtrar una lista vac�a
		itr = lista.filtrar("1");
		assertFalse(itr.hasNext());
		//Filtrar una lista con un elemento y que no filtre ninguno
		lista.anadir(obj1);
		
		itr = lista.filtrar("2");
		assertFalse(itr.hasNext());
		//Filtrar una lista con un elemento y que filtre un elemento
		itr = lista.filtrar("1");
		assertTrue(itr.hasNext());
		//Filtrar una lista con m�s de un elemento y que filtre un elemento
		lista.anadir(obj2);
		lista.anadir(obj3);
		
		itr = lista.filtrar("2");
		assertTrue(itr.hasNext());
		
		itr = lista.filtrar("3");
		assertTrue(itr.hasNext());
		//Filtrar una lista con m�s de un elemento y que filtre mas de un elemento
		lista.anadir(obj4);
		
		//Filtramos para que encuentre los elementos que contengan el numero 2
		itr = lista.filtrar("2");
		
		while (itr.hasNext()){
			itr.next();
			cont++;
		}
		assertEquals(cont,2);
		
		//Filtramos para que encuentre los elementos que contengan el numero 3
		cont = 0;
		itr = lista.filtrar("3");
		
		while (itr.hasNext()){
			itr.next();
			cont++;
		}
		assertEquals(cont,2);
		
		//Filtramos para que encuentre los elementos que contengan el numero 4
		cont = 0;
		itr = lista.filtrar("4");
		
		while (itr.hasNext()){
			itr.next();
			cont++;
		}
		assertEquals(cont,1);
		
	}

	@Test
	public void testEliminar() {
		//TODO
	}

	@Test
	public void testVaciar() {
		//Vaciar una lista vac�a
		assertEquals(lista.getTamano(),0);
		
		lista.vaciar();
		
		assertEquals(lista.getTamano(),0);
		
		//Vaciar una lista con un elemento
		lista.anadir(obj1);
		
		assertEquals(lista.getTamano(),1);
		
		lista.vaciar();
		
		assertEquals(lista.getTamano(),0);
		
		//Vaciar una lista con mas de un elemento
		lista.anadir(obj2);
		lista.anadir(obj3);
		
		assertEquals(lista.getTamano(),2);
		
		lista.vaciar();
		
		assertEquals(lista.getTamano(),0);
	}

	@Test
	public void testUltimoElemento() {
		//Coger el �ltimo elemento de una lista vac�a
		assertNull(lista.ultimoElemento());
		//Coger el �ltimo elemento de una lista con un elemento
		lista.anadir(obj1);
		
		assertTrue(lista.ultimoElemento() == 1);
		//Coger el �ltimo elemento de una lista con mas de un elemento
		lista.anadir(obj2);
		
		assertTrue(lista.ultimoElemento() == 2);
		
		lista.anadir(obj3);
		
		assertTrue(lista.ultimoElemento() == 3);
	}

}
