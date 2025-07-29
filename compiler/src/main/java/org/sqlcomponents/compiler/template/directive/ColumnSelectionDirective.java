package org.sqlcomponents.compiler.template.directive;

import freemarker.core.Environment;

import freemarker.template.DefaultListAdapter;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.utility.StringUtil;
import freemarker.template.TemplateDirectiveModel;
import org.sqlcomponents.core.model.Property;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FreeMarker user-defined directive for repeating a section of a template,
 * optionally with separating the output of the repetations with
 * <tt>&lt;hr></tt>-s.
 *
 *
 * <p><b>Directive info</b></p>
 *
 * <p>Parameters:
 * <ul>
 *   <li><code>count</code>: The number of repetations. Required!
 *       Must be a non-negative number. If it is not a whole number then it will
 *       be rounded <em>down</em>.
 *   <li><code>hr</code>: Tells if a HTML "hr" element could be printed between
 *       repetations. Boolean. Optional, defaults to <code>false</code>.
 * </ul>
 *
 * <p>Loop variables: One, optional. It gives the number of the current
 *    repetation, starting from 1.
 *
 * <p>Nested content: Yes
 */
public class ColumnSelectionDirective implements TemplateDirectiveModel {

    /**
    *  Parameter for Colum section properties.
    */
    private static final String PARAM_NAME_PROPERTIES = "properties";

    /**
     *
     * @param env Environment
     * @param params Map
     * @param loopVars TemplateModel[]
     * @param body TemplateDirectiveBody
     * @throws TemplateException
     */
    public void execute(final Environment env,
                        final Map params,
                        final TemplateModel[] loopVars,
                        final TemplateDirectiveBody body)
            throws TemplateException {

        List<Property> properties = new ArrayList<>();
        boolean countParamSet = false;

        Iterator paramIter = params.entrySet().iterator();
        while (paramIter.hasNext()) {
            Map.Entry ent = (Map.Entry) paramIter.next();

            String paramName = (String) ent.getKey();
            TemplateModel paramValue = (TemplateModel) ent.getValue();

            if (paramName.equals(PARAM_NAME_PROPERTIES)) {
                if (!(paramValue instanceof DefaultListAdapter)) {
                    throw new TemplateModelException("The \""
                            + PARAM_NAME_PROPERTIES + "\"" + " parameter "
                            + "must be a number.");
                }
                properties = (List<Property>) ((DefaultListAdapter) paramValue)
                        .getWrappedObject();
                countParamSet = true;
            } else {
                throw new TemplateModelException("Unsupported parameter: "
                        + paramName);
            }
        }
        if (!countParamSet) {
            throw new TemplateModelException("The required \""
                    + PARAM_NAME_PROPERTIES + "\""
                    + " paramter" + "is missing.");
        }

        if (loopVars.length > 1) {
            throw new TemplateModelException("At most "
                    + "one loop variable is allowed.");
        }

        Writer out = env.getOut();

        String escapedColumnNames = properties.stream()
                .map(property -> {
                    SimpleScalar scalar = new SimpleScalar(property
                            .getColumn()
                            .getEscapedName());
                    return StringUtil.javaStringEnc(scalar.getAsString());
                })
                .collect(Collectors.joining(","));

        try {
            out.write(escapedColumnNames);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

