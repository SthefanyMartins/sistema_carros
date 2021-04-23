/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author victor
 */
public class ValorBoolean implements Converter {

    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        return arg2;
    }

    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        if (arg2 instanceof Boolean) {
            if ( ((Boolean)arg2) ) {
                return "Sim";
            } else {
                return "Não";
            }
        } else {
            return "";
        }
    }

}
