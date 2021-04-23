/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.formedici.carros.util.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author victor
 */
public class ValorHora implements Converter {

    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        return arg2;
    }

    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        SimpleDateFormat formata = new SimpleDateFormat("HH:mm");
        if (arg2 instanceof Date) {
            return formata.format(arg2);
        } else {
            return "";
        }
    }
}