package org.sqlcomponents.compiler.base;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.sqlcomponents.compiler.java.JavaCompiler;
import org.sqlcomponents.core.utils.CoreConsts;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Thkere type Ftl template.
 *
 * @param <T> the type parameter
 */
public final class FTLTemplate<T> {
    /**
     * The Template.
     */
    private final freemarker.template.Template template;

    /**
     * Instantiates a new Ftl template.
     *
     * @param aFTLFile the a ftl file
     * @throws IOException the io exception
     */
    public FTLTemplate(final String aFTLFile) throws IOException {
        Configuration lFreemarkerConfiguration =
                new Configuration(Configuration.VERSION_2_3_29);
        lFreemarkerConfiguration.setClassForTemplateLoading(JavaCompiler.class,
                CoreConsts.BACK_SLASH);
        template = lFreemarkerConfiguration.getTemplate(aFTLFile);
    }

    /**
     * Gets content.
     *
     * @param aObjOfType the a obj of type
     * @return the content
     * @throws IOException       the io exception
     * @throws TemplateException the template exception
     */
    public String getContent(final T aObjOfType)
            throws IOException, TemplateException {
        StringWriter lStringWriter = new StringWriter();
        template.process(aObjOfType, lStringWriter);
        return lStringWriter.toString();
    }
}
