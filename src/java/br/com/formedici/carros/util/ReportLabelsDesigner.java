/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.formedici.carros.util;

/**
 *
 * @author Ricardo
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.LineSpacingEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.type.PositionTypeEnum;
import net.sf.jasperreports.engine.type.StretchTypeEnum;

public class ReportLabelsDesigner {

    private Double alturaFolha = 0d;
    private Double larguraFolha = 0d;
    private Double alturaEtiqueta = 0d;
    private Double alturaEtiquetaAux = 0d;
    private Double larguraEtiqueta = 0d;
    private Double margemEsquerda = 0d;
    private Double margemDireita = 0d;
    private Double margemTopo = 0d;
    private Double margemBaixo = 0d;
    private Double espacamentoEntreColunas = 0d;
    private Double espacamentoEntreFileiras = 0d;
    private Integer fileiras = 0;
    private Integer colunas = 0;
    private static double PIXEL_POR_MILIMETRO_ALTURA = 2.8350168350168350168350168350168;
    private static double PIXEL_POR_MILIMETRO_LARGURA = 2.8333333333333333333333333333333;
    private Boolean formatoRetrato = true;
    private String tipoFonte = "helvetica";
    private Integer tamanhoFonte = 12;

    public ReportLabelsDesigner() {
    }

    /**
     * Retorna o desenho do relatório
     * @throws JRException
     */
    private JasperDesign getDesign() throws JRException {

        // Cria um novo relatório
        JasperDesign jasperDesign = new JasperDesign();

        // PAGE
        this.configPage(jasperDesign);

        // FIELD
        this.criaColunas(jasperDesign);

        return jasperDesign;
    }

    /**
     * Adiciona campos ao relatorio
     * @param name
     * @param clazz
     * @param jasperDesign
     * @throws JRException
     */
    private void addField(String name, Class clazz, JasperDesign jasperDesign) throws JRException {
        JRDesignField field = new JRDesignField();

        field.setName(name);
        field.setValueClass(clazz);
        jasperDesign.addField(field);
    }

    /**
     * Configura a pagina
     * @param jasperDesign
     */
    private void configPage(JasperDesign jasperDesign) {
        jasperDesign.setPageWidth(this.getLarguraFolha().intValue());
        jasperDesign.setPageHeight(this.getAlturaFolha().intValue());
        jasperDesign.setColumnWidth(this.getLarguraEtiqueta().intValue());
        jasperDesign.setColumnSpacing(this.getEspacamentoEntreColunas().intValue());
        jasperDesign.setLeftMargin(this.getMargemEsquerda().intValue());
        jasperDesign.setRightMargin(this.getMargemDireita().intValue());
        jasperDesign.setBottomMargin(this.getMargemBaixo().intValue());
        jasperDesign.setTopMargin(this.getMargemTopo().intValue());
        if (this.getFormatoRetrato()) {
            jasperDesign.setOrientation(OrientationEnum.PORTRAIT);
        } else {
            jasperDesign.setOrientation(OrientationEnum.LANDSCAPE);
        }
        jasperDesign.setName("RelatorioDeEtiquetas");
        jasperDesign.setFloatColumnFooter(true);

    }

    /**
     * Cria as colunas
     * @param jasperDesign
     */
    private void criaColunas(JasperDesign jasperDesign) {
        JRDesignBand jrDesignband = new JRDesignBand();
        jrDesignband.setHeight(this.getAlturaEtiqueta().intValue() + this.getEspacamentoEntreFileiras().intValue());
    
        Integer contaX = 2;

        for (int j = 0; j < this.getColunas(); j++) {
            try {
                this.addField(String.valueOf(j), String.class, jasperDesign);
            } catch (JRException ex) {
                Logger.getLogger(ReportLabelsDesigner.class.getName()).log(Level.SEVERE, null, ex);
            }

            JRDesignTextField textField = new JRDesignTextField();
            textField.setBlankWhenNull(false);
            textField.setX(contaX);
            textField.setY(2);
            textField.setWidth(this.getLarguraEtiqueta().intValue());
            textField.setHeight(this.getAlturaEtiquetaAux().intValue());
            textField.setStretchWithOverflow(true);
            textField.setLineSpacing(LineSpacingEnum.SINGLE);
            textField.setPositionType(PositionTypeEnum.FIX_RELATIVE_TO_TOP);
            textField.setPrintInFirstWholeBand(true);
            textField.setStretchType(StretchTypeEnum.RELATIVE_TO_BAND_HEIGHT);
            textField.setEvaluationTime(EvaluationTimeEnum.BAND);
            textField.setPdfFontName(this.getTipoFonte());
            textField.setFontSize(this.getTamanhoFonte());

            // Adiciona a expressao no campo
            JRDesignExpression expression = new JRDesignExpression();
            expression.setValueClass(java.lang.String.class);
            expression.setText("$F{" + j + "}");
            textField.setExpression(expression);
            jrDesignband.addElement(textField);

            contaX = contaX + this.getLarguraEtiqueta().intValue() + this.getEspacamentoEntreColunas().intValue();
        }

        jasperDesign.setDetail(jrDesignband);
    }

    /**
     * Organiza os dados do relatório
     * @param dados Lista simples de dados
     * @return List - Lista de Map
     */
    private List<Map> criaDados(List dados) {
        List<Map> lista = new ArrayList<Map>();

        Map valores = new HashMap();

        Integer contaColunas = 0;
        for (Object obj : dados) {
            valores.put(String.valueOf(contaColunas), obj.toString());
            if (contaColunas.equals(this.getColunas() - 1)) {
                lista.add(valores);
                valores = new HashMap();
                contaColunas = 0;
            } else {
                contaColunas++;
            }
        }

        return lista;
    }

    /**
     * Cria o relatório em pdf retornando-o.
     * @param margemInferior Margem inferior.
     * @param margemSuperior Margem superior.
     * @param margemDireita Margem direita.
     * @param margemEsquerda Margem esquerda.
     * @param larguraFolha Largura da folha.
     * @param alturaFolha Altura da folha.
     * @param formatoRetrato Passar true se for no fomato retrato. Caso contrário, passar false.
     * @param larguraEtiqueta Largura da etiqueta.
     * @param alturaEtiqueta Altura da etiqueta.
     * @param espacamentoEntreColunas Espaçamento entre as colunas.
     * @param espacamentoEntreFileiras Espaçamento entre as fileiras.
     * @param colunas Número de colunas.
     * @param fileiras Número de etiquetas ou fileiras por coluna.
     * @param tipoFonte Nome da fonte.
     * @param tamanhoFonte Tamanho da fonte.
     * @param dados Lista dos dados. Deverá ser uma lista simples.
     * @return byte[] - Relatório em pdf em forma de um array de bytes.
     */
    public byte[] criaRelatorioBytesPdf(Double margemInferior, Double margemSuperior,
            Double margemDireita, Double margemEsquerda,
            Double larguraFolha, Double alturaFolha,
            Boolean formatoRetrato, Double larguraEtiqueta,
            Double alturaEtiqueta, Double espacamentoEntreColunas,
            Double espacamentoEntreFileiras, Integer colunas,
            Integer fileiras, String tipoFonte,
            Integer tamanhoFonte, List dados) {
        try {
            this.setMargemBaixo(margemInferior);
            this.setMargemTopo(margemSuperior);
            this.setMargemDireita(margemDireita);
            this.setMargemEsquerda(margemEsquerda);
            this.setLarguraFolha(larguraFolha);
            this.setAlturaFolha(alturaFolha);
            this.setLarguraEtiqueta(larguraEtiqueta);
            this.setAlturaEtiqueta(alturaEtiqueta);
            this.setEspacamentoEntreColunas(espacamentoEntreColunas);
            this.setEspacamentoEntreFileiras(espacamentoEntreFileiras);
            this.setFormatoRetrato(formatoRetrato);
            this.setColunas(colunas);
            this.setFileiras(fileiras);
            this.setTipoFonte(tipoFonte);
            this.setTamanhoFonte(tamanhoFonte);
            JasperReport jasperReport = JasperCompileManager.compileReport(this.getDesign());

            JRDataSource dataSource = new JRBeanCollectionDataSource(this.criaDados(dados));
            return criaBytesPdf(jasperReport, dataSource);
        } catch (JRException ex) {
            Logger.getLogger(ReportLabelsDesigner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Transforma o JasperReport juntamente com o JRDataSource em pdf.
     * @param jasper Relatorio JasperReport.
     * @param dataSource Dados JRDataSource.
     * @return byte[] - pdf em forma de um array de bytes.
     */
    private byte[] criaBytesPdf(JasperReport jasper, JRDataSource dataSource) throws JRException {
        return JasperRunManager.runReportToPdf(jasper, null, dataSource);
    }

    //geters e seters
    private Double getAlturaFolha() {
        return alturaFolha;
    }

    private void setAlturaFolha(Double alturaFolha) {
        this.alturaFolha = (alturaFolha * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_ALTURA);
    }

    private Double getLarguraFolha() {
        return larguraFolha;
    }

    private void setLarguraFolha(Double larguraFolha) {
        this.larguraFolha = (larguraFolha * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_LARGURA);
    }

    private Boolean getFormatoRetrato() {
        return formatoRetrato;
    }

    private void setFormatoRetrato(Boolean formatoRetrato) {
        this.formatoRetrato = formatoRetrato;
    }

    private Double getAlturaEtiqueta() {
        return alturaEtiqueta;
    }

    private void setAlturaEtiqueta(Double alturaEtiqueta) {
        this.alturaEtiqueta = (alturaEtiqueta * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_ALTURA);
        this.setAlturaEtiquetaAux(alturaEtiqueta - 1);
    }

    private Double getEspacamentoEntreFileiras() {
        return espacamentoEntreFileiras;
    }

    private void setEspacamentoEntreFileiras(Double espacamentoEntreFileiras) {
        this.espacamentoEntreFileiras = (espacamentoEntreFileiras * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_ALTURA);
    }

    private Double getEspacamentoEntreColunas() {
        return espacamentoEntreColunas;
    }

    private void setEspacamentoEntreColunas(Double espacamentoEntreColunas) {
        this.espacamentoEntreColunas = (espacamentoEntreColunas * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_LARGURA);
    }

    private Double getLarguraEtiqueta() {
        return larguraEtiqueta;
    }

    private void setLarguraEtiqueta(Double larguraEtiqueta) {
        this.larguraEtiqueta = (larguraEtiqueta * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_LARGURA);
    }

    private Double getMargemBaixo() {
        return margemBaixo;
    }

    private void setMargemBaixo(Double margemBaixo) {
        this.margemBaixo = margemBaixo;
    }

    private Double getMargemDireita() {
        return margemDireita;
    }

    private void setMargemDireita(Double margemDireita) {
        this.margemDireita = (margemDireita * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_LARGURA);
    }

    private Double getMargemEsquerda() {
        return margemEsquerda;
    }

    private void setMargemEsquerda(Double margemEsquerda) {
        this.margemEsquerda = (margemEsquerda * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_LARGURA);
    }

    private Double getMargemTopo() {
        return margemTopo;
    }

    private void setMargemTopo(Double margemTopo) {
        this.margemTopo = (margemTopo * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_ALTURA);
    }

    private Integer getColunas() {
        return colunas;
    }

    private void setColunas(Integer colunas) {
        this.colunas = colunas;
    }

    private Integer getFileiras() {
        return fileiras;
    }

    private void setFileiras(Integer fileiras) {
        this.fileiras = fileiras;
    }

    private Integer getTamanhoFonte() {
        return tamanhoFonte;
    }

    private void setTamanhoFonte(Integer tamanhoFonte) {
        this.tamanhoFonte = tamanhoFonte;
    }

    private String getTipoFonte() {
        return tipoFonte;
    }

    private void setTipoFonte(String tipoFonte) {
        this.tipoFonte = tipoFonte;
    }

    private Double getAlturaEtiquetaAux() {
        return alturaEtiquetaAux;
    }

    private void setAlturaEtiquetaAux(Double alturaEtiquetaAux) {
        this.alturaEtiquetaAux = (alturaEtiquetaAux * ReportLabelsDesigner.PIXEL_POR_MILIMETRO_ALTURA);
    }
}
