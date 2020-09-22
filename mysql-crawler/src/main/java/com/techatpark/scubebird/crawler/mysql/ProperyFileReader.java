package com.techatpark.scubebird.crawler.mysql;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ProperyFileReader {
	private static final String BUNDLE_NAME = "com.techatpark.scubebird.mysql.queries";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private ProperyFileReader() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
