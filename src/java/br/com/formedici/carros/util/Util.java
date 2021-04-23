/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.util;

import br.com.formedici.carros.model.PadraoModel;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author victor
 */
public class Util {

    private static SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat formatoDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static SimpleDateFormat formatoDataSQL = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat formatoDataHoraSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DecimalFormat formatoValor = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    private static DecimalFormat formatoValor4Casas = new DecimalFormat("0.0000");
    private static DecimalFormat formatoValor6Casas = new DecimalFormat("#,##0.000000", new DecimalFormatSymbols(new Locale ("pt", "BR")));
    private static DecimalFormat formatoNumericoCompleto = new DecimalFormat("0.0###################");

    private static final String emptyString = "";

    public Util() {
    }

    public static Locale getDefaultLocale() {
        return new Locale("pt", "BR");
    }

    public static String formataNumericoCompleto(Double valor) {
        return formatoNumericoCompleto.format(valor);
    }

    public static String formataValor(Double valor) {
        return formatoValor.format(valor);
    }

    public static String formataValor4Casas(Double valor) {
        return formatoValor4Casas.format(valor);
    }

    public static String formataValor6Casas(Double valor) {
        return formatoValor6Casas.format(valor);
    }

    public static String formataData(Date data) {
        return formatoData.format(data);
    }

    public static String formataHora(Date data) {
        return formatoHora.format(data);
    }

    public static String formataDataHora(Date data) {
        return formatoDataHora.format(data);
    }

    public static String formataDataSQL(Date data) {
        return formatoDataSQL.format(data);
    }

    public static String formataDataHoraSQL(Date data) {
        return formatoDataHoraSQL.format(data);
    }

    public static String formataDataHoraInicioSQL(Date data) {
        return formatoDataSQL.format(data) + " 00:00:00";
    }

    public static String formataDataHoraFinalSQL(Date data) {
        return formatoDataSQL.format(data) + " 23:59:59";
    }

    public static Boolean isNullOrEmpty(String s) {
        if (s == null) {
            return true;
        } else if (s.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static Boolean isNullOrEmpty(Object s) {
        if (s == null) {
            return true;
        } else if ((s instanceof String) && (s.equals(""))) {
            return true;
        }
        return false;
    }

    public static Boolean stringVazia(String verificar) {
        if (verificar == null) {
            return true;
        } else if (verificar.equals("")) {
            return true;
        }
        return false;
    }

    public static Boolean stringEmBranco(String verificar) {
        if (verificar == null) {
            return true;
        } else if (verificar.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static String preencheZerosAEsquerda(String str, Integer qtde) {
        String s = str.trim();
        while (s.length() < qtde) {
            s = "0" + s;
        }
        return s;
    }

    public static String preencheZerosAEsquerda(String str) {
        String s = str.trim();
        while (s.length() < 14) {
            s = "0" + s;
        }
        return s;
    }

    public Boolean setaNullNosCamposObjetoVazios(Object objeto) {
        Boolean retorno = false;
        Field[] campos = objeto.getClass().getDeclaredFields();
        for (Field field : campos) {
            if (field.getModifiers() != 9) {
                if (field.getType().equals(String.class)) {
                    if (this.invocaGetDoCampoString(field, objeto)) {
                        retorno = true;
                    }
                } else {
                    if (this.invocaGetDoCampo(field, objeto)) {
                        retorno = true;
                    }
                }
            }
        }
        return retorno;
    }

    public Boolean instanciaOsSubCamposVazios(Object objeto) {
        Field[] campos = objeto.getClass().getDeclaredFields();
        for (Field field : campos) {
            if (field.getModifiers() != 9) {
                if (!field.getType().equals(String.class)) {
                    if (this.instanciaOCampo(field, objeto)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Boolean invocaGetDoCampo(Field field, Object objeto) {
        Boolean retorno = false;
        String nome = field.getName();
        String novoNome = field.getName().substring(1);
        novoNome = field.getName().substring(0, 1).toUpperCase() + novoNome;
        try {
            Method metodo = objeto.getClass().getMethod("get" + novoNome, new Class[0]);
            Object objRet = (Object) metodo.invoke(objeto, new Object[0]);
            if (objRet != null) {
                if (!objRet.getClass().getSuperclass().equals(PadraoModel.class)) {
                    if (field.getType().equals(List.class)) {
                        if (objRet != null) {
                            if (((List) objRet).isEmpty()) {
                                Object objNull;
                                objNull = null;
                                Method mSet = objeto.getClass().getMethod("set" + novoNome, field.getType());
                                mSet.invoke(objeto, objNull);
                                return false;
                            }
                        }

                        return true;
                    } else if (field.getType().equals(Integer.class)) {
                        if (((Integer) objRet) == 0) {
                            Object objNull;
                            objNull = null;
                            Method mSet = objeto.getClass().getMethod("set" + novoNome, field.getType());
                            mSet.invoke(objeto, objNull);
                            return false;
                        } else {
                            return true;
                        }
                    } else if (field.getType().equals(Double.class)) {
                        if (((Double) objRet) == 0.0) {
                            Object objNull;
                            objNull = null;
                            Method mSet = objeto.getClass().getMethod("set" + novoNome, field.getType());
                            mSet.invoke(objeto, objNull);
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    if (!this.setaNullNosCamposObjetoVazios(objRet)) {
                        Object objNull = field.getType().newInstance();
                        objNull = null;
                        Method mSet = objeto.getClass().getMethod("set" + novoNome, field.getType());
                        mSet.invoke(objeto, objNull);
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Boolean invocaGetDoCampoString(Field field, Object objeto) {
        String nome = field.getName();
        String novoNome = field.getName().substring(1);
        novoNome = field.getName().substring(0, 1).toUpperCase() + novoNome;

        try {
            Method metodo = objeto.getClass().getMethod("get" + novoNome, new Class[0]);
            String strRet = (String) metodo.invoke(objeto, new Object[0]);
            if (!Util.isNullOrEmpty(strRet)) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private Boolean instanciaOCampo(Field field, Object objeto) {
        String nome = field.getName();
        String novoNome = field.getName().substring(1);
        Boolean retorno = false;
        novoNome = field.getName().substring(0, 1).toUpperCase() + novoNome;
        try {
            Method metodo = objeto.getClass().getMethod("get" + novoNome, new Class[0]);
            Object objRet = (Object) metodo.invoke(objeto, new Object[0]);
            if (field.getType().equals(List.class)) {
                Type tipoDaLista = null;
                Type tipo = field.getGenericType();
                if (tipo instanceof ParameterizedType) {
                    ParameterizedType tipoDoParametro = (ParameterizedType) tipo;
                    for (Type object : tipoDoParametro.getActualTypeArguments()) {
                        tipoDaLista = object;
                    }
                }
                Object objNew = null;
                if (tipoDaLista == null) {
                    objNew = new LinkedList();
                } else {
                    String nomeDoTipo = tipoDaLista.toString().replace("class", "").trim();
                    objNew = Class.forName("java.util.LinkedList").newInstance();
                }
                if (objRet == null) {
                    Method mSet = objeto.getClass().getMethod("set" + novoNome, field.getType());
                    mSet.invoke(objeto, objNew);
                }
            } else if (field.getType().getSuperclass().equals(PadraoModel.class)) {
                Object objNew = field.getType().newInstance();
                if (objRet == null) {
                    Method mSet = objeto.getClass().getMethod("set" + novoNome, field.getType());
                    mSet.invoke(objeto, objNew);
                    if (!this.instanciaOsSubCamposVazios(objNew)) {
                        retorno = false;
                    } else {
                        retorno = true;
                    }
                } else {

                    if (!this.instanciaOsSubCamposVazios(objRet)) {
                        retorno = false;
                    } else {
                        retorno = true;
                    }

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retorno;
    }

    public static boolean copyFile(String inFile, String outFile) {
        InputStream is = null;
        OutputStream os = null;
        byte[] buffer;
        boolean success = true;
        try {
            is = new FileInputStream(inFile);
            os = new FileOutputStream(outFile);
            buffer = new byte[is.available()];
            is.read(buffer);
            os.write(buffer);
        } catch (IOException e) {
            success = false;
        } catch (OutOfMemoryError e) {
            success = false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
            }
        }
        return success;
    }

    public static String adicionaVirgula(String atual, String adicionar) {
        if (atual.equals("")) {
            return adicionar;
        } else {
            return ", " + adicionar;
        }
    }

    public static String adicionaCondicao(String atual, String adicionar) {
        if (atual.equals("")) {
            return adicionar;
        } else {
            return " AND " + adicionar;
        }
    }

    public static String colocaWhereOuAnd(String s) {
        if (s == null) {
            return "";
        }

        if (s.contains("WHERE")) {
            return " AND ";
        } else {
            return " WHERE ";
        }

    }

    public static String colocaWhereOuOr(String s) {
        if (s == null) {
            return "";
        }

        if (s.contains("WHERE")) {
            return " OR ";
        } else {
            return " WHERE ";
        }
    }

    public static String colocaGroupByOuVirgula(String s) {
        if (s == null) {
            return "";
        }

        if (s.contains("GROUP BY")) {
            return " , ";
        } else {
            return " GROUP BY ";
        }
    }

    public static String colocaOrderByOuVirgula(String s) {
        if (s == null) {
            return "";
        }

        if (s.contains("ORDER BY")) {
            return " , ";
        } else {
            return " ORDER BY ";
        }
    }

    public static String completaComEspacoDireita(String str, Integer qtde) {
        String s = str.trim();
        while (s.length() < qtde) {
            s = s + ' ';
        }
        return s;
    }

    public static String completaComEspacoEsquerda(String str, Integer qtde) {
        String s = str.trim();
        while (s.length() < qtde) {
            s = ' ' + s;
        }
        return s;
    }

    // Valida a hora no formato hh:nn
    public static Boolean validarHora(String hora) {
        if (!Util.isNullOrEmpty(hora.trim())) {
            Integer horas = Integer.parseInt(hora.substring(0, 2));
            Integer minutos = Integer.parseInt(hora.substring(3));
            if (!((horas.intValue() >= 0) && (horas.intValue() <= 23) && (minutos.intValue() >= 0) && (minutos.intValue() <= 59))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public static String pegaPosicao(String valor, Integer posInicial, Integer posFinal) {
        // Utilizado para pegar o valor conforme as posições do layout
        //return valor.substring(posInicial - 1, posFinal).trim();
        String s = valor.substring(posInicial - 1, posFinal).trim();
        if (s.isEmpty()) {
            return emptyString;
        } else {
            return new String(s.toCharArray());
        }
    }

    public static String pegaPosicao(String valor, Integer posInicial) {
        /* Utilizado para pegar o valor do último campo (sem posição final)
         * É necessário pois o arquivo pode vir sem espaçoes em branco no final
         */
        //return valor.substring(posInicial - 1).trim();
        String s = valor.substring(posInicial - 1).trim();
        if (s.isEmpty()) {
            return emptyString;
        } else {
            return new String(s.toCharArray());
        }
    }

    public static int diferencaDatas(Calendar data1, Calendar data2) {
        Long result = ((data1.getTimeInMillis()-data2.getTimeInMillis())/(1000 * 60 * 60 * 24));
        return result.intValue();
    }

    public static int diferencaDatas(Date data1, Date data2) {
        Long result = ((data1.getTime() - data2.getTime()) / (1000 * 60 * 60 * 24));
        return result.intValue();
    }

    public static Integer diferencaDatasMeses(Calendar data1, Calendar data2) {
         Integer months = data2.get(Calendar.MONTH) - data1.get(Calendar.MONTH)
            + (12 * (data2.get(Calendar.YEAR) - data1.get(Calendar.YEAR)));

        if(data2.get(Calendar.DAY_OF_MONTH) < data1.get(Calendar.DAY_OF_MONTH)){
            months--;
        }
        return months;
    }

    public static Date zeraSegundosMilissegundos(Date data){
        Calendar cAux = Calendar.getInstance();
        cAux.setTime(data);
        cAux.set(Calendar.SECOND, 0);
        cAux.set(Calendar.MILLISECOND, 0);
        return cAux.getTime();
    }


    public static boolean verificarParenteses(String s) {
        List<Character> abridores = new ArrayList<Character>(Arrays.asList('('));
        List<Character> fechadores = new ArrayList<Character>(Arrays.asList(')'));
        Stack<Character> stack = new Stack<Character>();

        for (char c : s.toCharArray()) {

            //se o caractere esta abrindo
            if (abridores.contains(c)) {
                stack.push(c);
            }
            //se o caractere esta dechando
            else if (fechadores.contains(c)) {

                if(stack.isEmpty()){
                //se está vazio é porque está fechando mais que abrindo
                    return false;
                }

                Character abridorCorrespondente = abridores.get(fechadores.indexOf(c));
                if (!abridorCorrespondente.equals(stack.pop())) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static Date zerarHorasData(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c.getTime();
    }

    public static Date converteData(String data) {
        try {
            return formatoData.parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static Date converteDataHora(Date data, String hora) {
        Date dataHora = null;
        try {
            dataHora = formatoDataHora.parse(formatoData.format(data) + " " + hora);
        } catch (ParseException ex) {
            return null;
        }
        return dataHora;
    }

    public static Integer calculaIdadeAnos(Date dataNascimento, Date dataBase){
        if (dataNascimento != null) {
            Calendar dateOfBirth = Calendar.getInstance();
            dateOfBirth.setTime(dataNascimento);

            // Cria um objeto calendar com a data do parametro
            Calendar dataSelecionada = Calendar.getInstance();
            dataSelecionada.setTime(dataBase);

            // Obtém a idade baseado no ano
            int age = dataSelecionada.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

            dateOfBirth.add(Calendar.YEAR, age);

            //se a data de hoje é antes da data de Nascimento, então diminui 1(um)
            if (dataSelecionada.before(dateOfBirth)) {
                age--;
            }
            return age;
        }
        return new Integer(999);
    }

    /*
     * Método utilizado para retornar uma string em branco caso a variável estiver nula
     * Foi criado para ser utilizado nos locais que montam os dados de relatórios,
     * para não ter que ficar fazendo os ifs lá ou então usando o operador ? :
     */
    public static String nullEmBranco(String texto) {
        if (texto == null) {
            return "";
        } else {
            return texto;
        }
    }

    /*
     * Utilizado para descrever a idade em anos, meses e dias.
     * O cálculo será feito com a diferença entre a data de nascimento e a data base.
     */
    public static String idadePorExtenso(Date dataNascimento, Date dataBase) {
        if (dataBase == null) {
            dataBase = new Date();
        }
        if (dataNascimento.after(dataBase)) {
            return null;
        }

        Calendar nascimento = Calendar.getInstance();
        nascimento.setTime(dataNascimento);
        Calendar atual = Calendar.getInstance();
        atual.setTime(dataBase);

        Integer anoAtual = atual.get(Calendar.YEAR);
        Integer anoNascimento = nascimento.get(Calendar.YEAR);
        Integer mesAtual = atual.get(Calendar.MONTH);
        Integer mesNascimento = nascimento.get(Calendar.MONTH);
        Integer diaAtual = atual.get(Calendar.DAY_OF_MONTH);
        Integer diaNascimento = nascimento.get(Calendar.DAY_OF_MONTH);

        Integer anos = anoAtual - anoNascimento;
        Integer meses = mesAtual - mesNascimento;
        Integer dias = diaAtual - diaNascimento;

        if (mesNascimento >= mesAtual) {
            if (anos > 0) {
                if (mesNascimento > mesAtual || diaNascimento > diaAtual) {
                    anos -= 1;
                }
            }
            if (mesNascimento > mesAtual || diaNascimento > diaAtual) {
                meses = mesAtual + 12 - mesNascimento;
            }
        }

        if (diaNascimento > diaAtual) {
            if (meses > 0) {
                meses -= 1;
            }
            dias = diaAtual + nascimento.getActualMaximum(Calendar.DAY_OF_MONTH) - diaNascimento;
        }

        String resultado = anos + " anos, " + meses + " meses, " + dias + " dias";

        return resultado;
    }

    public static XMLGregorianCalendar dateToXMLGregorianCalendarDate(Date data) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(data.getTime());

            int ano = calendar.get(Calendar.YEAR);
            int mes = calendar.get(Calendar.MONTH)+ 1;
            int dia = calendar.get(Calendar.DAY_OF_MONTH);

            XMLGregorianCalendar retorno = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(ano, mes,dia, 0);

            retorno.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            retorno.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);

            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static XMLGregorianCalendar dateToXMLGregorianCalendarTime(Date data) {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(data.getTime());

            XMLGregorianCalendar retorno = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            retorno.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            retorno.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
            retorno.setDay(DatatypeConstants.FIELD_UNDEFINED);
            retorno.setMonth(DatatypeConstants.FIELD_UNDEFINED);
            retorno.setYear(DatatypeConstants.FIELD_UNDEFINED);
            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Função para criar hash md5
    public static String calculaHashMD5(String data) throws UnsupportedEncodingException {
        try {
            byte[] mybytes = data.getBytes("ISO-8859-1");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5digest = md5.digest(mybytes);

            //System.out.println("Hash calculado =" + MapeadorUtil.bytesToHex(md5digest));
            return bytesToHex(md5digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            sb.append((Integer.toHexString((b[i] & 0xFF) | 0x100)).substring(1, 3));
        }
        return sb.toString();
    }

    public static String limitaCaracteres(String str, Integer tamanhoMaximo) {
        if (str.length() > tamanhoMaximo) {
            return str.substring(0, tamanhoMaximo);
        } else {
            return str;
        }
    }

    // Expressão regex que remove tudo que estvier entre < >
    public static String removeTagsXML(String htmlString) {
        String noTagString = htmlString.replaceAll("\\<.*?\\>", "");
        return noTagString;
    }

    public static Boolean isDataValida(String strData) {

        try {
            String[] valor = strData.split("/");
            if (valor.length != 3) {
                return false;
            }
            for (String item: valor) {
                Integer.parseInt(item);
            }

            SimpleDateFormat formatoDataValida = new SimpleDateFormat("dd/MM/yyyy");
            formatoDataValida.setLenient(false);
            formatoDataValida.parse(strData);
        } catch (NumberFormatException ex) {
            return false;
        } catch (ParseException ex) {
            return false;
        }

        return true;
    }

    public static Boolean dddValido(String ddd) {

        if (ddd == null || ddd.length() != 2 || !ddd.matches("\\d{2}")) {
            return false;
        }
        return true;
    }

    public static Boolean foneValido(String fone) {

        if (fone == null || fone.length() != 9 || !fone.matches("\\d{9}")) {
            return false;
        }
        return true;
    }
}