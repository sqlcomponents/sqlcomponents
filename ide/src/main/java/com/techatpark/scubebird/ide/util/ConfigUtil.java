package com.techatpark.scubebird.ide.util;

import com.techatpark.scubebird.core.model.CrawlerConfig;
import com.techatpark.scubebird.ide.model.Method;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConfigUtil {

	private static final String METHOD_TAG = "method";
	private static final String CRAWLER_TAG = "crawler";
	private static final String ID_TAG = "id";
	private static final String NAME_TAG = "name";
	private static final String DESCRIPTION_TAG = "description";
	private static final String CLASS_NAME_TAG = "classname";
	private static final Object DRIVER_NAME_TAG = "drivername";

	private List<Method> methods;
	private List<CrawlerConfig> drivers;

	private static ConfigUtil configUtil;

	private ConfigUtil() {
		loadConfig();
	}

	private void loadConfig() {
		XMLInputFactory xmlif = null;
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
					Boolean.TRUE);
			xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
					Boolean.FALSE);
			// set the IS_COALESCING property to true , if application desires
			// to
			// get whole text data as one event.
			xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);

			XMLStreamReader xmlr = xmlif.createXMLStreamReader(ConfigUtil.class
					.getResourceAsStream("/scubebird.xml"));

			methods = new ArrayList<Method>();
			drivers = new ArrayList<CrawlerConfig>();
			Method method = null;
			CrawlerConfig crawler = null;
			// check if there are more events in the input stream
			while (xmlr.hasNext()) {
				xmlr.next() ;
				if (xmlr.isStartElement()) {
					if (METHOD_TAG.equals(xmlr.getName().toString())) {
						method = new Method();
					} else if (CRAWLER_TAG.equals(xmlr.getName().toString())) {
						crawler = new CrawlerConfig();
					} else if (NAME_TAG.equals(xmlr.getName().toString())) {
						if (method != null) {
							method.setName(xmlr.getElementText());
						} else {
							crawler.setName(xmlr.getElementText());
						}
					} else if (ID_TAG.equals(xmlr.getName().toString())) {
						method.setId(xmlr.getElementText());
					} else if (DESCRIPTION_TAG
							.equals(xmlr.getName().toString())) {
						method.setDescription(xmlr.getElementText());
					} else if (CLASS_NAME_TAG.equals(xmlr.getName().toString())) {
						crawler.setClassName(xmlr.getElementText());
					}
					else if (DRIVER_NAME_TAG.equals(xmlr.getName().toString())) {
						crawler.setDriverName(xmlr.getElementText());
					}
				} else if (xmlr.isEndElement()) {
					if (METHOD_TAG.equals(xmlr.getName().toString())) {
						methods.add(method);
						method = null;
					} else if (CRAWLER_TAG.equals(xmlr.getName().toString())) {
						drivers.add(crawler);
						crawler = null;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static final ConfigUtil getConfigUtil() {
		if (configUtil == null) {
			configUtil = new ConfigUtil();
		}
		return configUtil;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public List<CrawlerConfig> getDrivers() {
		return drivers;
	}

}
