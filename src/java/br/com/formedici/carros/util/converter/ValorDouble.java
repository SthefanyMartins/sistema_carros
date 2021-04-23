package br.com.formedici.carros.util.converter;

import com.sun.faces.util.MessageFactory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author Ricardo
 */
public class ValorDouble implements Converter {

    public static final String CONVERTER_ID = "javax.faces.Double";
    public static final String DOUBLE_ID = "javax.faces.converter.DoubleConverter.DOUBLE";
    public static final String STRING_ID = "javax.faces.converter.STRING";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        if (value == null) {
            return (null);
        }
        value = value.trim().replace(',', '.');
        if (value.length() < 1) {
            return (null);
        }

        try {
            return (Double.valueOf(value));
        } catch (NumberFormatException nfe) {
            throw new ConverterException(MessageFactory.getMessage(context, DOUBLE_ID, value, "1999999", MessageFactory.getLabel(context, component)), nfe);
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (context == null || component == null) {
            throw new NullPointerException();
        }

        if (value == null) {
            return "";
        }

        if (value instanceof String) {
            return (String) value;
        }

        try {
            return (Double.toString(((Number) value).doubleValue()).replace(".", ","));
        } catch (Exception e) {
            throw new ConverterException(MessageFactory.getMessage(context, STRING_ID, value, MessageFactory.getLabel(context, component)), e);
        }
    }
}
