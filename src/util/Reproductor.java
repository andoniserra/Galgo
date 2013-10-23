package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Observable;

import javax.swing.Timer;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import logica.Cancion;
import logica.ListaCanciones;

public class Reproductor extends Observable {

	private static Reproductor miRepro = new Reproductor(); //Inicializacion de la clase
	private static ListaCanciones miBiblioteca = new ListaCanciones(); //Biblioteca actual, Strings con la ruta

	private static LectorTags lt = new LectorTags();

	private int posActual = 0; //Para indicar la posición de la lista
	private double volActual = 0.5; //Guarda el volumen actual

	private boolean estaReproduciendo = false;

	private Media media = null; //Archivo a reproducir
	private MediaPlayer player = null; //Reproductor

	private Timer t1 = null;

	private Reproductor(){
		new JFXPanel();

		t1 = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				notifyObservers();
			}
		});

		t1.start();

	}

	public static Reproductor get(){return miRepro;}

	public ListaCanciones getBiblioteca(){
		return miBiblioteca;
	}

	public void setBiblioteca(ListaCanciones pBiblio){

		miBiblioteca = pBiblio;

		//reiniciamos el contador
		//a la primera canción
		posActual = 0;

		if (player == null){
			if (miBiblioteca.get(0) != null){
				crearPlayer(miBiblioteca.get(0).getDirectorio());
			}
		}

	}

	public double getTiempoActual(){return player.getCurrentTime().toSeconds();}

	public double getDuracion(){return player.getTotalDuration().toSeconds();}

	public String getAlbumActual(){
		miBiblioteca.get(posActual);
		lt.setArchivo(miBiblioteca.get(posActual).getDirectorio());
		return lt.getAlbum();
	}

	public String getAutorActual(){
		miBiblioteca.get(posActual);
		lt.setArchivo(miBiblioteca.get(posActual).getDirectorio());
		return lt.getAutor();
	}

	public Cancion getCancionActual(){
		return miBiblioteca.get(posActual);
	}

	public double getVolumen(){return volActual;}

	public void setVolumen(double pVol){
		volActual = pVol/100; 
		if (miBiblioteca.getTamano() > 0){
			player.setVolume(volActual);
		}
	}

	public void setTiempoActual(double pTiempo){
		if (miBiblioteca.getTamano() > 0){
			Duration dur = new Duration(pTiempo);
			player.seek(dur);
		}
	}

	public void reproducir(){
		if (player == null && miBiblioteca.getTamano() > 0){
			crearPlayer(miBiblioteca.get(0).getDirectorio());
		}
		play();
	}

	public void reproducir(Cancion pCancion){

		if (estaReproduciendo){
			stop();
		}

		Cancion miCancion = miBiblioteca.buscarCancion(pCancion.getID());

		if(miCancion != null){
			posActual = miBiblioteca.getIndex(miCancion);
			crearPlayer(miBiblioteca.get(posActual).getDirectorio());
			play();
		}

	}

	public boolean reproduciendo(){return estaReproduciendo;}

	private void play(){
		if (miBiblioteca.getTamano() > 0){
			player.play();
			player.setVolume(volActual);
			estaReproduciendo = true;	
		}
	}

	public void pause(){
		player.pause();
		estaReproduciendo = false;
	}

	public void stop(){
		if (player!=null){
			player.stop();
		}
		estaReproduciendo = false;
	}

	public void next(){

		if (miBiblioteca.getTamano() > 0){

			boolean estabaReproduciendo = estaReproduciendo;

			stop();

			if (posActual < miBiblioteca.getTamano() - 1)
				posActual +=1;
			else
				posActual = 0;

			crearPlayer(miBiblioteca.get(posActual).getDirectorio());

			if (estabaReproduciendo)
				reproducir();
		}
	}

	public void prev(){

		if (miBiblioteca.getTamano() > 0){

			boolean estabaReproduciendo = estaReproduciendo;

			stop();

			if (posActual > 0)
				posActual -=1;
			else
				posActual = miBiblioteca.getTamano() - 1;

			crearPlayer(miBiblioteca.get(posActual).getDirectorio());

			if (estabaReproduciendo)
				reproducir();
		}

	}

	public MediaPlayer.Status getEstado(){return player.getStatus();}

	private void crearPlayer(String pRuta){
		try{
			File mp3 = new File(pRuta);
			media = new Media(mp3.toURI().toURL().toExternalForm());
			player = new MediaPlayer(media);
			player.setOnError(new Runnable() {

				@Override
				public void run() {
					System.out.println("Error cargando la canción");
					System.out.println(player.getError().getMessage());
					next();
				}
			});

			player.setOnEndOfMedia(new Runnable() {

				@Override
				public void run() {
					miBiblioteca.get(posActual).aumentarNRepro();
					next();
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
