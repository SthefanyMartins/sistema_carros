/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.util.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author victor
 */
public class ValorNumero implements Converter {

    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        return arg2;
    }

    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        DecimalFormat formatar = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));
        if (arg2 instanceof Number) {
            return formatar.format(arg2);
        } else{
            return (String)arg2;
        }               
        
    }

}
