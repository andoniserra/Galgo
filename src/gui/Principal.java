package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;

import net.miginfocom.swing.MigLayout;

import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import util.Reproductor;
import util.Util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import logica.Cancion;
import logica.CatalogoCanciones;
import logica.CatalogoGrupos;
import logica.Disco;
import logica.Grupo;
import logica.ListaCanciones;
import logica.ListaDiscos;
import logica.ListaGrupos;
import logica.Program;

import java.awt.FlowLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.GridBagConstraints;


/**
 * 
 * @author andoniserrano
 *
 */

public class Principal implements Observer{

	public static boolean DEBUG = false;

	private JFrame frame = new JFrame();

	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnArchivo = new JMenu("Archivo");
	private JMenu mnNueva = new JMenu("Nueva");
	private JMenuItem mntmLista = new JMenuItem("Lista");
	private JMenuItem mntmListaInteligente = new JMenuItem("Lista Inteligente");
	private JMenuItem mntmEditarLista = new JMenuItem("Editar Lista");
	private JMenuItem mntmAadirCancion = new JMenuItem("A\u00F1adir Cancion...");
	private JMenu mnControles = new JMenu("Controles");
	private JMenuItem mntmPlayPause = new JMenuItem("Reproducir");
	private JMenuItem mntmDetener = new JMenuItem("Detener");
	private JMenuItem mntmSiguiente = new JMenuItem("Siguiente");
	private JMenuItem mntmAnterior = new JMenuItem("Anterior");
	private JMenu mnRepetir = new JMenu("Repetir");
	private JCheckBoxMenuItem chckbxmntmNinguna = new JCheckBoxMenuItem("Ninguna");
	private JCheckBoxMenuItem chckbxmntmUna = new JCheckBoxMenuItem("Una");
	private JCheckBoxMenuItem chckbxmntmTodas = new JCheckBoxMenuItem("Todas");
	private JCheckBoxMenuItem chckbxmntmAleatorio = new JCheckBoxMenuItem("Aleatorio");

	private JPanel pnlPrincipal = new JPanel();
	private JPanel panelSuperior = new JPanel();
	private JPanel pnlControles = new JPanel();
	private JPanel pnlVolume = new JPanel();
	private JPanel panelCancion = new JPanel();
	private JPanel panelCancionSlider = new JPanel();
	private JPanel pnlBuscar = new JPanel();
	private JPanel pnlArbol = new JPanel();		

	private JButton btnPrev = new JButton();
	private JButton btnPlay = new JButton();
	private JButton btnNext = new JButton();

	private JSlider sldTrack = new JSlider();
	private JSlider sldVolumen = new JSlider();

	private JTextField textBuscar = new JTextField();

	private static JTree treeMusica = new JTree();
	private JScrollPane scrollArbol = new JScrollPane();
	private JScrollPane scrollLista = new JScrollPane();

	private static Icon play;
	private static Icon playpress;
	private static Icon pause;
	private static Icon pausepress;


	private static JLabel lblDurAct = new JLabel("0:00");
	private static JLabel lblDurMax = new JLabel("0:00");
	private static JLabel lblCancion = new JLabel("MUPY");
	private static DefaultMutableTreeNode arbolMusica = null;
	private static JTable tableLista = new JTable();

	private final JMenuItem mntmAadirCarpeta = new JMenuItem("Abrir Carpeta...");
	private final JLabel lblVolumen = new JLabel("");

	public Principal() {
		try {
			this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			initialize();
			this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void initialize() throws IOException {

		Reproductor.get().addObserver(this);
		frame.setMinimumSize(new Dimension(1024,600));
		frame.setBounds(100, 100, 1024, 600);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.setJMenuBar(menuBar);
		menuBar.add(mnArchivo);
		mnNueva.setVisible(false);
		mnArchivo.add(mnNueva);
		mnNueva.add(mntmLista);
		mnNueva.add(mntmListaInteligente);
		mntmEditarLista.setVisible(false);
		mnArchivo.add(mntmEditarLista);
		mntmAadirCarpeta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String ruta = Util.abrirArchivo();
				if (!ruta.equals("")){
					Reproductor.get().stop();
					hide();
					Program.modificarConfig(ruta);
					Program.recargar();
				}
			}
		});

		mnArchivo.add(mntmAadirCarpeta);
		mntmAadirCancion.setVisible(false);
		mntmAadirCancion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO Agregar Cancion
			}
		});
		mnArchivo.add(mntmAadirCancion);
		mnControles.setVisible(false);
		menuBar.add(mnControles);
		mnControles.add(mntmPlayPause);
		mnControles.add(mntmDetener);
		mnControles.add(mntmSiguiente);
		mnControles.add(mntmAnterior);
		mnControles.addSeparator();
		mnControles.add(mnRepetir);
		mnRepetir.add(chckbxmntmNinguna);
		mnRepetir.add(chckbxmntmUna);
		mnRepetir.add(chckbxmntmTodas);
		mnControles.add(chckbxmntmAleatorio);

		frame.getContentPane().add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setSize(50, 50);
		panelSuperior.setLayout(new MigLayout("", "[245px][190px][424px,grow][150px]", "[68px,grow,fill]"));

		panelSuperior.add(pnlControles, "cell 0 0,alignx left,aligny center");

		final Icon prev = new ImageIcon(ImageIO.read(Principal.class.getResource("/Imagenes/prev.png")));
		final Icon prevpress = new ImageIcon(ImageIO.read(Principal.class.getResource("/Imagenes/prevpressed.png")));

		btnPrev.setIcon(prev);
		btnPrev.setPressedIcon(prevpress);

		btnPrev.setBorderPainted(false);
		btnPrev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Reproductor.get().prev();
			}
		});
		pnlControles.add(btnPrev);

		btnPlay.setBorderPainted(false);

		play = new ImageIcon(ImageIO.read(Principal.class.getResource("/Imagenes/play.png")));
		playpress = new ImageIcon(ImageIO.read(Principal.class.getResource("/Imagenes/playpressed.png")));
		pause = new ImageIcon(ImageIO.read(Principal.class.getResource("/Imagenes/pause.png")));
		pausepress = new ImageIcon(ImageIO.read(Principal.class.getResource("/Imagenes/pausepressed.png")));

		btnPlay.setIcon(play);
		btnPlay.setPressedIcon(playpress);

		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Reproductor.get().reproduciendo()){
					Reproductor.get().pause();

					btnPlay.setIcon(play);
					btnPlay.setPressedIcon(playpress);
				}else{
					Reproductor.get().reproducir();

					btnPlay.setIcon(pause);
					btnPlay.setPressedIcon(pausepress);

				}
			}
		});
		pnlControles.add(btnPlay);

		final Icon next = new ImageIcon(ImageIO.read(Principal.class.getResource("/Imagenes/next.png")));
		final Icon nexpress = new ImageIcon(ImageIO.read(Principal.class.getResource("/Imagenes/nextpressed.png")));
		btnNext.setIcon(next);
		btnNext.setPressedIcon(nexpress);

		btnNext.setBorderPainted(false);
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Reproductor.get().next(); 
			}
		});
		pnlControles.add(btnNext);

		panelSuperior.add(pnlVolume, "cell 1 0,alignx left,aligny center");

		//Todo esto para que sea mas pequeño el boton de volumen
		ImageIcon iconoVolumen = new ImageIcon(Principal.class.getResource("/Imagenes/volumeMedium.png"));
		Image img = iconoVolumen.getImage();
		Image imgFinal = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		ImageIcon iconoFinal = new ImageIcon(imgFinal);
		//FIN

		lblVolumen.setIcon(iconoFinal);

		pnlVolume.add(lblVolumen);


		GridBagConstraints gbc_sldVolumen = new GridBagConstraints();
		gbc_sldVolumen.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc_sldVolumen.gridx = 0;
		gbc_sldVolumen.gridy = 1;
		pnlVolume.add(sldVolumen, gbc_sldVolumen);
		//sldVolumen.addChangeListener(new VolumenListener());

		sldVolumen.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				Reproductor.get().setVolumen(source.getValue());
				//Para el volumen

				double volActual = Reproductor.get().getVolumen();
				ImageIcon iconoVolumen = null;		

				if (volActual >= 0.1 && volActual < 0.33 ){
					iconoVolumen = new ImageIcon(Principal.class.getResource("/Imagenes/volumeLow.png"));			
				}else if(volActual >= 0.33 && volActual < 0.66){
					iconoVolumen = new ImageIcon(Principal.class.getResource("/Imagenes/volumeMedium.png"));
				}else if(volActual >= 0.66){
					iconoVolumen = new ImageIcon(Principal.class.getResource("/Imagenes/volumeHigh.png"));
				}else if(volActual < 0.1){
					iconoVolumen = new ImageIcon(Principal.class.getResource("/Imagenes/volumeZero.png"));
				}

				//Todo esto para que sea mas pequeño el boton de volumen
				Image img = iconoVolumen.getImage();
				Image imgFinal = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
				ImageIcon iconoFinal = new ImageIcon(imgFinal);
				//FIN

				lblVolumen.setIcon(iconoFinal);
			}
		});

		panelSuperior.add(panelCancion, "cell 2 0,growx,aligny center");
		panelCancion.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("415px:grow"),},
				new RowSpec[] {
				RowSpec.decode("21px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("43px"),}));

		panelCancion.add(lblCancion, "1, 1, center, default");

		panelCancion.add(panelCancionSlider, "1, 3, fill, top");
		panelCancionSlider.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.MIN_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.MIN_COLSPEC,},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("38px"),}));
		panelCancionSlider.add(lblDurAct, "1, 2, left, center");

		panelCancionSlider.add(sldTrack, "2, 2, fill, center");
		sldTrack.setValue(0);
		sldTrack.setPaintTicks(true);
		panelCancionSlider.add(lblDurMax, "3, 2, left, center");
		sldTrack.addChangeListener(new TiempoActualListener());

		panelSuperior.add(pnlBuscar, "cell 3 0,alignx right,aligny center");
		pnlBuscar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		pnlBuscar.add(textBuscar);
		textBuscar.setColumns(10);

		pnlPrincipal.setBackground(Color.WHITE);
		frame.getContentPane().add(pnlPrincipal, BorderLayout.CENTER);
		pnlPrincipal.setLayout(new BorderLayout(0, 0));

		pnlPrincipal.add(pnlArbol, BorderLayout.WEST);
		pnlArbol.setLayout(new MigLayout("insets 0", "[200px:200px,grow,fill]", "[19px,grow]"));

		scrollArbol.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollArbol.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		treeMusica.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		treeMusica.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if(!treeMusica.isSelectionEmpty()){

					ListaCanciones miLista = new ListaCanciones();

					//Obtenemos el Path del arbol
					TreePath tp = treeMusica.getSelectionPath();
					//Obtenemos el Nodo
					DefaultMutableTreeNode sel = (DefaultMutableTreeNode) tp.getLastPathComponent();

					Object obj = sel.getUserObject();

					if(obj instanceof Disco){
						Disco miDisco = (Disco) obj;
						miLista = CatalogoGrupos.getCatalogo().obtenerCancionesDeDisco(miDisco.getTitulo());
					}

					if(obj instanceof Grupo){
						Grupo miGrupo = (Grupo) obj;
						miLista = CatalogoGrupos.getCatalogo().obtenerTodosDiscosDeGrupo(miGrupo.getID()).obtenerTodasLasCanciones();
					}

					if (obj instanceof String){
						if (obj.toString().contains("Todos los grupos") || obj.toString().contains("Todos los albumes")){
							miLista = CatalogoCanciones.getCatalogo().obtenerTodas();
						}
					}
					
					if (miLista.getTamano() > 0){
						Reproductor.get().setBiblioteca(miLista);
						cargarGrid(Reproductor.get().getBiblioteca());
					}
					if (DEBUG){System.out.println(obj.getClass().toString());}
				}

			}
		});

		scrollArbol.setViewportView(treeMusica);
		pnlArbol.add(scrollArbol, "cell 0 0,grow");

		scrollLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollLista.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		tableLista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && !e.isConsumed()){
					e.consume();

					int row = tableLista.getSelectedRow();
					int idCancion = Integer.parseInt((String)tableLista.getModel().getValueAt(row, 3));

					Cancion nuevaCan = CatalogoCanciones.getCatalogo().buscarCancion(idCancion);

					btnPlay.setIcon(pause);
					btnPlay.setPressedIcon(pausepress);

					Reproductor.get().reproducir(nuevaCan);
				}
			}
		});

		scrollLista.setViewportView(tableLista);

		pnlPrincipal.add(scrollLista, BorderLayout.CENTER);
	}

	public void CargarArbol(ListaCanciones pLista){

		ListaGrupos nuevaListaG = new ListaGrupos();
		ListaDiscos nuevaListaD = new ListaDiscos();

		Iterator<Cancion> itr = pLista.getIterador();

		while (itr.hasNext()){
			Cancion canActual = itr.next();
			Grupo miGrupo = CatalogoGrupos.getCatalogo().buscarGrupoDeCancion(canActual.getID());
			Disco miDisco = miGrupo.buscarDiscoDeCancion(canActual.getID());

			//Creamos una lista de grupos
			nuevaListaG.anadirGrupo(miGrupo);

			//Creamos una lista de discos
			nuevaListaD.anadirDisco(miDisco);

		}
		//Con esto cargaremos el arbol de la izquierda
		DefaultMutableTreeNode grupos = new DefaultMutableTreeNode("Grupos");
		DefaultMutableTreeNode albumes = new DefaultMutableTreeNode("Albumes");

		Iterator<Disco> itrD = nuevaListaD.getIterador();
		Iterator<Grupo> itrG = nuevaListaG.getIterador();

		grupos.add(new DefaultMutableTreeNode("Todos los grupos"));
		albumes.add(new DefaultMutableTreeNode("Todos los albumes"));

		while (itrG.hasNext()){
			grupos.add(new DefaultMutableTreeNode(itrG.next()));
		}

		while (itrD.hasNext()){
			albumes.add(new DefaultMutableTreeNode(itrD.next()));
		}

		arbolMusica = new DefaultMutableTreeNode("Musica");
		//cargamos el arbol
		arbolMusica.add(grupos);
		arbolMusica.add(albumes);

		treeMusica.setModel(new DefaultTreeModel(arbolMusica));

		cargarGrid(pLista);
	}

	public void cargarGrid(ListaCanciones pLista){
		//Cargamos la lista
		String data[][] = new String[pLista.getTamano()][4];
		String col[] = {"Titulo","Artista","Nº Reproducciones"};


		for (int i=0;i<pLista.getTamano(); i++){
			data[i][0] = pLista.get(i).getTitulo();
			data[i][1] = pLista.get(i).getGrupo();
			data[i][2] = String.valueOf(pLista.get(i).getNRepro());
			data[i][3] = String.valueOf(pLista.get(i).getID());
		}

		miModelo modelo = new miModelo(data, col);

		tableLista.setModel(modelo);
	}

	public void show(){
		frame.setVisible(true);
	}

	public void hide(){
		frame.setVisible(false);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		if (Reproductor.get().reproduciendo()){
			Double minutos = (Reproductor.get().getDuracion() / 60);
			Double segundos = (Reproductor.get().getDuracion() % 60);
			int min = minutos.intValue();
			int seg = segundos.intValue();
			lblDurMax.setText(min + ":" + seg);
			sldTrack.setMaximum((min*60) + seg );

			minutos = (Reproductor.get().getTiempoActual() / 60);
			segundos = (Reproductor.get().getTiempoActual() % 60);
			min = minutos.intValue();
			seg = segundos.intValue();
			lblDurAct.setText( min + ":" + String.format("%02d", seg));
			sldTrack.setValue( (min*60) + seg );

			lblCancion.setText(Reproductor.get().getCancionActual() + " - " + Reproductor.get().getAutorActual() + " - " + Reproductor.get().getAlbumActual());
		}
	}

}


/**
 * 
 * @author andoniserrano
 *
 */
class VolumenListener implements ChangeListener{

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		Reproductor.get().setVolumen(source.getValue());
	}

}


/**
 * 
 * @author andoniserrano
 *
 */
class TiempoActualListener implements ChangeListener{

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (source.getValueIsAdjusting()){
			Double millis = (double) source.getValue();
			Reproductor.get().setTiempoActual(millis * 1000);
		}
	}
}


/**
 * 
 * @author andoniserrano
 *
 */
class miModelo extends AbstractTableModel {


	private static final long serialVersionUID = 1L;
	String[][] data;
	String[] cols;

	public miModelo(String[][] datos, String[] pCols){
		data = new String[datos.length][pCols.length];
		cols = new String[pCols.length];
		data = datos;
		cols = pCols;
	}

	public boolean isCellEditable(int row, int column){
		return false;  
	}

	@Override
	public String getColumnName(int col) { return cols[col]; }

	@Override
	public int getColumnCount() { return 3; }

	@Override
	public int getRowCount() {return data.length;}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}


}
