package org.sqlcomponents.core.utils;

import org.sqlcomponents.core.model.Application;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CoreConsts {
    public static final String BACK_SLASH = "/";

    public static Application buildApplication(final File configFile) throws IOException {
        Application lApplication = new Yaml(new Constructor(Application.class)).load(new FileReader(configFile));

        lApplication.setMethodSpecification(Application.METHOD_SPECIFICATION);

        return lApplication;
    }
}
