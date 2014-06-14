package br.org.furb.sic.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class FileUtil {
	private FileUtil() {
	}

	public static void writeFile(String texto, String pathFile)
			throws IOException {
		File originalFile = new File(pathFile);
		if(!originalFile.exists()){
			originalFile.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile,
				false));
		writer.write(texto);
		writer.flush();
		writer.close();
	}
	
	public static void main(String[] args) {
		try {
			FileUtil.writeFile("inicializado como escravo.",
					"C:\\temp\\log.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que retorna somente o nome do arquivo sem a extensão do mesmo.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName) {
		if (fileName.contains(".")) {
			String[] path = fileName.split("\\.");
			if (path.length == 2) {
				return path[0];
			} else {
				String completePath = "";
				for (int i = 0; i < path.length - 1; i++) {
					completePath += path[i];
				}
				return completePath;
			}
		} else {
			return fileName;
		}
	}

}
