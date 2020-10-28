package org.sqlcomponents.compiler.util;

//Test

import java.io.*;

public class FileUtil {
	public static File deleteDir(String srcDir) {
		File dir = new File(srcDir);
		File[] files = dir.listFiles();
		File file = null;
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				file = files[i];
				if (file.isDirectory()) {
					deleteDir(file.getAbsolutePath()).delete();
				} else {
					file.delete();
				}
			}
		}
		return dir;
	}

	public static boolean writeFileContent(String content, String path) {
		FileWriter writer = null;
		boolean result = true;
		new File(new File(path).getParent()).mkdirs();
		try {
			writer = new FileWriter(path);
			writer.write(content);
			result = true;
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public static boolean writeFileContent(String content, String path,
			String fileName) {
		FileWriter writer = null;
		boolean result = true;
		new File(path).mkdirs();
		try {
			writer = new FileWriter(path + File.separator + fileName);
			writer.write(content);
			result = true;
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public static String readFileContent(String filePath) {
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(FileUtil.class
							.getResourceAsStream(filePath)));
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str + System.getProperty("line.separator"));
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return buffer.toString();
	}

	public static String getFilePath(String packageStr) {
		char[] charArray = packageStr.toCharArray();
		StringBuffer filePath = new StringBuffer();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '.') {
				filePath.append(File.separatorChar);
			} else {
				filePath.append(charArray[i]);
			}

		}
		return filePath.toString();
	}

	public static File write(Serializable object, String filename)
			throws IOException {
		ObjectOutputStream out = null;

		out = new ObjectOutputStream(new FileOutputStream(filename));
		out.writeObject(object);
		out.flush();
		out.close();
		return new File(filename);
	}

	public static Object read(String filename) throws ClassNotFoundException,
			IOException {
		ObjectInputStream in = null;

		in = new ObjectInputStream(new FileInputStream(filename));

		return in.readObject();

	}

}
