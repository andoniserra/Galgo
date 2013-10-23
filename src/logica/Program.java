package logica;

import gui.Principal;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import util.LectorTags;
import util.Reproductor;
import util.Util;

public class Program {
	private static ListaCanciones listaInterna = new ListaCanciones();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				comprobarConfiguracion(); //comprobar si existe el config.xml
				iniciarGUI();
			}
		});
	}
	
	public static void recargar(){
		CatalogoCanciones.getCatalogo().vaciar();
		CatalogoGrupos.getCatalogo().vaciar();
		iniciarGUI();
	}
	
	private static void iniciarGUI(){
		cargarLibreria(); //cargar la biblioteca indicada en el config.xml
		Principal prin = new Principal();
		prin.CargarArbol(listaInterna);
		listaInterna = null;
		prin.show();
	}

	
	private static void comprobarConfiguracion(){

		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			
			//miramos si existe fichero de configuracion
			File fileConfig = new File("config.xml");

			//Si no existe se creará el archivo y mostraremos una ventana para seleccionar la biblioteca
			if(!fileConfig.exists()){
				
				String strBiblioteca = Util.abrirArchivo(); 

				if(strBiblioteca.equals("")){
					//Si el usuario cancela cerramos el programa
					System.exit(0);
				}

				fileConfig.createNewFile();

				//Sino creamos el archivo
				doc = dBuilder.newDocument();
				Element rootElement = doc.createElement("Library");

				doc.appendChild(rootElement);

				Element carpeta = doc.createElement("Folder");
				rootElement.appendChild(carpeta);

				Attr atributo = doc.createAttribute("Path");
				carpeta.setAttributeNode(atributo);
				atributo.setValue(strBiblioteca);

				carpeta.setAttributeNode(atributo);

				TransformerFactory transFactory = TransformerFactory.newInstance();
				Transformer transformer = transFactory.newTransformer();
				DOMSource dom = new DOMSource(doc);
				StreamResult streamRes = new StreamResult(fileConfig);

				transformer.transform(dom, streamRes);

			}

		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void modificarConfig(String pValor){

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("config.xml");

			Node carpeta = doc.getElementsByTagName("Folder").item(0);

			NamedNodeMap attr = carpeta.getAttributes();
			Node nodeAttr = attr.getNamedItem("Path");
			nodeAttr.setTextContent(pValor);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config.xml"));
			transformer.transform(source, result);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void cargarLibreria(){

		ArrayList<String> rutaCanciones = new ArrayList<String>();
		listaInterna = new ListaCanciones();
		LectorTags lt = new LectorTags();

		try {
			
			File fileConfig = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(fileConfig);

			doc.getDocumentElement().normalize();

			NodeList carpetas = doc.getElementsByTagName("Folder");

			for (int i = 0; i < carpetas.getLength(); i ++){
				Node nodo = carpetas.item(i);

				if (nodo.getNodeType() == Node.ELEMENT_NODE){

					Element ruta = (Element) nodo;

					rutaCanciones.addAll(buscarMP3(ruta.getAttribute("Path")));
				}
			}

			//Aqui guardamos los datos en las clases
			Iterator<String> itr = rutaCanciones.iterator();
			
			while(itr.hasNext()){
				
				String pRuta = itr.next();
				String strGrupo, strDisco, strCancion;
				lt = new LectorTags();
				lt.setArchivo(pRuta);
				
				strGrupo = lt.getAutor();
				strDisco = lt.getAlbum();
				strCancion = lt.getTitulo();
				
				if (Principal.DEBUG){System.out.print("File: " + strGrupo + " - " + strDisco + " - " + strCancion);}
				Grupo miGrupo = CatalogoGrupos.getCatalogo().buscarGrupo(strGrupo);
				
				if(miGrupo != null){
					//existe grupo
					Disco miDisco = miGrupo.buscarDisco(strDisco);

					if (miDisco != null){
						//existe grupo y disco
						Cancion miCancion = miDisco.buscarCancion(strCancion);

						if(miCancion == null){
							//no existe cancion en grupo y disco
							miCancion = new Cancion(pRuta);
							miCancion = CatalogoCanciones.getCatalogo().anadirCancion(miCancion);
							miDisco.anadirCancionADisco(miCancion);
							
							listaInterna.anadirCancion(miCancion);
							if (Principal.DEBUG){System.out.println(" CARGADO");}
						}
						
					}else{
						//existe grupo, no existe disco
						
						//Añadimos el disco al grupo
						miDisco = new Disco(strDisco, lt.getEstilo(), Integer.parseInt(lt.getAnio()));
						miDisco = miGrupo.anadirDisco(miDisco);
						
						//Añadimos la cancion al disco
						Cancion miCancion = new Cancion(pRuta);
						miCancion = CatalogoCanciones.getCatalogo().anadirCancion(miCancion);
						miDisco.anadirCancionADisco(miCancion);
						
						listaInterna.anadirCancion(miCancion);
						if (Principal.DEBUG){System.out.println(" CARGADO");}
					}

				}else{
					//no existe grupo
					
					//Añadimos el grupo al catalogo
					miGrupo = new Grupo(strGrupo);
					miGrupo = CatalogoGrupos.getCatalogo().anadirGrupo(miGrupo);
					
					//Añadimos el disco al grupo
					Disco miDisco = new Disco(strDisco, lt.getEstilo(), Integer.parseInt(lt.getAnio()));
					miDisco = miGrupo.anadirDisco(miDisco);
					
					//Añadimos la cancion al catalogo y al disco
					Cancion miCancion = new Cancion(pRuta);
					miCancion = CatalogoCanciones.getCatalogo().anadirCancion(miCancion);
					miDisco.anadirCancionADisco(miCancion);
					
					//añadimos la cancion a la lista que se le pasara al reproductor
					listaInterna.anadirCancion(miCancion);
					if (Principal.DEBUG){System.out.println(" CARGADO");}
				}
				lt = null;
			}
			
			Reproductor.get().setBiblioteca(listaInterna);
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception ex){
			System.out.println(ex.getMessage());
		}

	}

	/**
	 * Funcion recursiva que devuelve un ArrayList con todos los MP3 encontrados
	 * @param pRuta
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static ArrayList<String> buscarMP3(String pRuta){
		ArrayList<String> ret = new ArrayList<String>();
		File ruta = new File(pRuta);

		if (ruta.isDirectory()){
			String [] listaArchivos = ruta.list();

			//recursividad
            for (String listaArchivo : listaArchivos) ret.addAll(buscarMP3(pRuta + "/" + listaArchivo));

		}else{

			if (pRuta.toLowerCase().endsWith("mp3")){
				ret.add(pRuta);
				if (Principal.DEBUG){System.out.println("ruta: " + pRuta);}
			}
		}
		ruta = null;
		return ret;
	}

}