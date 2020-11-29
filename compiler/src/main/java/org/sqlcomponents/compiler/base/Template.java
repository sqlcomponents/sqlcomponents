package org.sqlcomponents.compiler.base;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.sqlcomponents.compiler.java.JavaCompiler;

import java.io.IOException;
import java.io.StringWriter;

public final class Template<T> {

    private final Configuration freemarkerConfiguration;
    private final freemarker.template.Template template;

    public Template(final String path) throws IOException {
        this.freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_29);
        this.freemarkerConfiguration.setClassForTemplateLoading(
                JavaCompiler.class, "/");
        template = freemarkerConfiguration.getTemplate(path);
    }

    public String getContent(T obj) throws IOException, TemplateException {
        String content = null;
        StringWriter stringWriter = new StringWriter();
        template.process(obj, stringWriter);
        content = stringWriter.toString();
        stringWriter.flush();
        stringWriter.close();
        return content;
    }

}
