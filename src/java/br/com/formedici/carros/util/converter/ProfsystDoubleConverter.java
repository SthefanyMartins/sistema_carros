package br.com.formedici.carros.util.converter;

import br.com.formedici.carros.util.Util;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 * @author caio
 */
public class ProfsystDoubleConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String valorTela) throws ConverterException {

        if (valorTela == null || valorTela.toString().trim().equals("")) {
            //return 0;//0.0d;
            return null;

        } else {
            valorTela = valorTela.replace(",", ".");


            try {
                Double valorNumerico = Double.parseDouble(valorTela);
                return valorNumerico;

                //NumberFormat nf = NumberFormat.getInstance();
                //nf.setMaximumFractionDigits(2);
                //return nf.parse(valorTela).doubleValue();


            } catch (Exception e) {
                //return 0;//0.0d;
                ConverterException ce = new ConverterException("Valor inválido");
                throw ce;
                //return null;
            }
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object valorTela) throws ConverterException {

        if (valorTela == null || valorTela.toString().trim().equals("")) {
            //return "0";
            return null;

        } else {
            //NumberFormat nf = NumberFormat.getInstance();
            //nf.setMaximumFractionDigits(2);
            //return nf.format(Double.valueOf(valorTela.toString())).replace(".", ",");

            String valorNumerico = valorTela.toString();
            if ( (valorTela != null) && (valorTela instanceof Double) ) {
                valorNumerico = Util.formataNumericoCompleto((Double) valorTela).replace(".", ",");
            }
            return valorNumerico;
        }
    }
}