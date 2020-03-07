package org.scube.ide.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ApplicationFileFilter extends FileFilter {

	private String extension;
	private String description;

	public ApplicationFileFilter(String extension, String description) {
		this.extension = extension;
		this.description = description;
	}

	@Override
	public boolean accept(File file) {
		String fileExtension = getExtension(file);
		return file.isDirectory() || extension.equals(fileExtension);
	}

	@Override
	public String getDescription() {
		return description;
	}

	/*
	 * Get the extension of a file.
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

}
