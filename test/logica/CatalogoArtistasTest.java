package logica;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CatalogoArtistasTest {
	
	Calendar cal1 = Calendar.getInstance();
	Grupo g1;
	
	@Before
	public void setUp() throws Exception {
		cal1.set(1980, 8, 10);
		g1 = new Grupo("RHCP");
		
	}
		
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuscarArtistasDeGrupo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFormacionesDeArtista() {
		fail("Not yet implemented");
	}

}
