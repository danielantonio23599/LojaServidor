/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Daniel
 */
public class GeradorRelatorio {

    private Connection conexao;
    private ServletContext contexto;

    public GeradorRelatorio(Connection conexao, ServletContext contexto) {
        this.conexao = conexao;
        this.contexto = contexto;
    }

    public File geraPdfDownload(String jrxml, String nome, Map<String, Object> parametros) {

        try {
            String tmp = contexto.getRealPath("/relatorios/" + nome + ".jasper");
            File arquivo = new File(tmp);

            if (!arquivo.exists()) { // Arquivo não existe ainda. Relatório não foi compilado.
                System.out.println("Compilou jasper");
                JasperCompileManager.compileReportToFile(jrxml); // Compilando antes.
            }
            File path = getPath(nome);
            OutputStream saida = new FileOutputStream(path.getAbsolutePath());
            // compila jrxml em memoria

            String jasper = contexto.getRealPath("/relatorios/" + nome + ".jasper");
            // preenche relatorio
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conexao);
            System.out.println("sucesso...");

            // exporta para pdf
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(print));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(saida));

            exporter.exportReport();
            //Aqui vc chama o relatório
            return path;
        } catch (Exception e) {
            System.out.println("erro : " + e.getMessage());
        }
        return null;

    }

    public File getPath(String nome) {
        boolean aux = true;
        String path = contexto.getRealPath("/relatorios/" + nome + ".pdf");
        File pdf = new File(path);
        for (int i = 0; aux == true; i++) {
            pdf = new File(path);
            if (pdf.exists() == false) {
                System.out.println("novo arquivo " + path);
                aux = false;
            } else {

                System.out.print(path + " ");
                System.out.println("delet : " + pdf.delete());
                path = contexto.getRealPath("/relatorios/" + nome + i + ".pdf");
            }
        }
        return pdf;
    }

}
