package util;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;

public class LectorTags {

	private String directorio;
	private MP3File file;

	public void setArchivo(String pDir){

		directorio = pDir;
		File arch = new File(directorio);
		try {
			file = (MP3File) AudioFileIO.read(arch);

		} catch (CannotReadException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}catch (Exception ex){ 
		}finally{

		}

	}

	public String getAutor(){
		String ret = "";

		if (file.hasID3v2Tag()) {
			Tag tag = (AbstractID3v2Tag) file.getID3v2TagAsv24();
			ret = tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
		}else{
			if (file.hasID3v1Tag()){
				ID3v1Tag tag = (ID3v1Tag) file.getTag();
				ret = tag.getFirstArtist();
			}
		}

		return ret;
	}

	public String getCompositor(){
		
		String ret = "";

		if (file.hasID3v2Tag()) {
			Tag tag = (AbstractID3v2Tag) file.getID3v2TagAsv24();
			ret = tag.getFirst(ID3v24Frames.FRAME_ID_COMPOSER);
		}
		//No se guarda en Compositor en ID3v1

		return ret;
	}
	
	public String getAlbum(){
		String ret = "";

		if (file.hasID3v2Tag()) {
			Tag tag = (AbstractID3v2Tag) file.getID3v2TagAsv24();
			ret = tag.getFirst(ID3v24Frames.FRAME_ID_ALBUM);
		}else{
			if (file.hasID3v1Tag()){
				ID3v1Tag tag = (ID3v1Tag) file.getTag();
				ret = tag.getFirstAlbum();
			}
		}

		return ret;
	}

	public String getTitulo(){
		String ret = "";

		if (file.hasID3v2Tag()) {
			Tag tag = (AbstractID3v2Tag) file.getID3v2TagAsv24();
			ret = tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
		}else{
			if (file.hasID3v1Tag()){
				ID3v1Tag tag = (ID3v1Tag) file.getTag();
				ret = tag.getFirstTitle();
			}
		}

		return ret;
	}

	public String getAnio(){
		String ret = "";

		if (file.hasID3v2Tag()) {
			Tag tag = (AbstractID3v2Tag) file.getID3v2TagAsv24();
			ret = tag.getFirst(ID3v24Frames.FRAME_ID_YEAR);
			if(ret.length() > 4){
				ret = "0";
			}
			if (ret.equals("")){
				ret = "0";
			}
		}else{
			if (file.hasID3v1Tag()){
				ID3v1Tag tag = (ID3v1Tag) file.getTag();
				ret = tag.getFirstYear();
			}
		}

		return ret;
	}

	public String getEstilo(){
		String ret = "";

		if (file.hasID3v2Tag()) {
			Tag tag = (AbstractID3v2Tag) file.getID3v2TagAsv24();
			ret = tag.getFirst(ID3v24Frames.FRAME_ID_GENRE);
		}else{
			if (file.hasID3v1Tag()){
				ID3v1Tag tag = (ID3v1Tag) file.getTag();
				ret = tag.getFirstGenre();
			}
		}

		return ret;
	}

	public String getAtributo(String pAtrib){
		String ret = "";

		if (file.hasID3v2Tag()) {
			Tag tag = (AbstractID3v2Tag) file.getID3v2TagAsv24();
			ret = tag.getFirst(FieldKey.valueOf(pAtrib));
		}

		return ret;
	}

}
