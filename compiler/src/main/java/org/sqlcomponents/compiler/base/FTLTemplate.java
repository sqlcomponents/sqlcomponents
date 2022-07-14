package org.sqlcomponents.compiler.base;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.sqlcomponents.compiler.java.JavaFTLCompiler;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.IOException;
import java.io.StringWriter;

public final class FTLTemplate<T> {
    private final freemarker.template.Template template;

    public FTLTemplate(final String aFTLFile) throws IOException {
        Configuration lFreemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_29);
        lFreemarkerConfiguration.setClassForTemplateLoading(JavaFTLCompiler.class, CoreConsts.BACK_SLASH);
        template = lFreemarkerConfiguration.getTemplate(aFTLFile);
    }

    public String getContent(final T aObjOfType) throws IOException, TemplateException {
        StringWriter lStringWriter = new StringWriter();
        template.process(aObjOfType, lStringWriter);
        return lStringWriter.toString();
    }
}
