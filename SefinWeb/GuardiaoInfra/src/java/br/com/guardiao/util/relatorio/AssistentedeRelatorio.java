/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.util.relatorio;

import br.com.guardiao.util.MenssagemUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * Classe do Projeto ******* - Criado em 16/05/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class AssistentedeRelatorio implements Serializable {

    public AssistentedeRelatorio() {
    }

    public String getDiretorioReal(String diretorio) {
        HttpSession hSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return hSession.getServletContext().getRealPath(diretorio);
    }

//    public void gerarRelatorioWeb(List lista, Map<String, Object> parametros, String arquivo) {
//        JRDataSource jrRS = new JRBeanCollectionDataSource(lista);
//        ServletOutputStream servletOutputStream;
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
//        try {
//            servletOutputStream = response.getOutputStream();
//            JasperRunManager.runReportToPdfStream(new FileInputStream(new File(getDiretorioReal(arquivo))), servletOutputStream, parametros, jrRS);
//            //JasperRunManager.
//            response.setContentType("application/pdf");
//            servletOutputStream.flush();
//            servletOutputStream.close();
//            context.getApplication().getStateManager().saveView(context);
//            context.renderResponse();
//            context.responseComplete();
//            System.out.println("Completou");
//        } catch (IOException | JRException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void gerarRelatorioWeb2(List lista, Map<String, Object> parametros, String arquivo) {
//        JRDataSource jrRS = new JRBeanCollectionDataSource(lista);
//        try {
//            String rel = getDiretorioReal(arquivo);
//            JasperPrint print = JasperFillManager.fillReport(rel, parametros, jrRS);
//            byte[] b = JasperExportManager.exportReportToPdf(print);
//            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            res.setContentType("application/pdf");
//            res.setHeader("Content-disposition", "attachment;filename=arquivo.pdf");
//            res.getOutputStream().write(b);
//            res.getCharacterEncoding();
//            FacesContext.getCurrentInstance().responseComplete();
//            System.out.println("Completou");
//        } catch (JRException | IOException e) {
//            e.printStackTrace();
//        }
//    }
    public byte[] relatorioemByte(List lista, Map<String, Object> parametros, String arquivo, String nomeRelatorio) throws JRException {
        byte[] b;
        parametros.put("img", getDiretorioReal("WEB-INF//relatorios//imagens//sefinweb.png"));
        parametros.put("img1", getDiretorioReal("WEB-INF//relatorios//imagens//logo.png"));
        parametros.put("relatorio", nomeRelatorio);
        parametros.put("SUBREPORT_DIR", getDiretorioReal("WEB-INF/relatorios/sub_relatorios") + "/");
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
        parametros.put("rodapeEmail", "suporte@sefin.caucaia.ce.gov.br");
        parametros.put("rodapeFone", "(85) 3011-0661");
        String rel = getDiretorioReal(arquivo);
        JRDataSource jrRS = new JRBeanCollectionDataSource(lista);
        JasperPrint print = JasperFillManager.fillReport(rel, parametros, jrRS);
        b = JasperExportManager.exportReportToPdf(print);
        return b;
    }

}
