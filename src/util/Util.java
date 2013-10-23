package util;

import java.awt.Frame;
import java.io.File;

import javax.swing.JFileChooser;

public class Util {

	public static enum OS{
		WIN,
		MAC,
		NIX,
	}

	private Util(){}

	public static OS OSDetect(){
		OS miSO = null;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")){miSO = OS.WIN;}
		if (os.contains("nux") || os.contains("nix")){miSO = OS.NIX;}
		if (os.contains("mac")){miSO = OS.MAC;}
		return miSO;
	}


	public static String abrirArchivo(){

		String strRet = "";
		//
		//		if (OSDetect() == OS.MAC){
		//			System.setProperty("apple.awt.fileDialogForDirectories", "true");
		//
		//			FileDialog fd = new FileDialog(new Frame()); 
		//			fd.setMode(FileDialog.LOAD);
		//
		//			fd.setDirectory(System.getProperty("user.home")); 
		//			fd.setLocation(50,50);
		//			fd.setVisible(true); 
		//
		//			if(fd.getFile() != null){
		//				File selectedFile = new File(fd.getFile());
		//				strRet = fd.getDirectory() + selectedFile.getName();
		//			}
		//
		//			System.setProperty("apple.awt.fileDialogForDirectories", "false");
		//
		//		}else if(OSDetect() == OS.WIN){

		JFileChooser jFc = new JFileChooser();
		jFc.setCurrentDirectory(new File(System.getProperty("user.home")));
		jFc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jFc.setAcceptAllFileFilterUsed(false);
		if( jFc.showOpenDialog(new Frame()) == JFileChooser.APPROVE_OPTION){
			File miArchivo = jFc.getSelectedFile();
			strRet = miArchivo.getPath();
		}
		return strRet;
	}

}