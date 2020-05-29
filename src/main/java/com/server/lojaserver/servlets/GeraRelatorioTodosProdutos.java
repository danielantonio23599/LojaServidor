/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.Gson;
import com.server.lojaserver.controle.ControleLogin;
import com.server.lojaserver.controle.ControleRelatorio;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Daniel
 */
@WebServlet(name = "GeraRelatorioTodosProdutos", urlPatterns = {"/restaurante_server/GeraRelatorioTodosProdutos"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class GeraRelatorioTodosProdutos extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleRelatorio r = new ControleRelatorio();

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String n = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String s = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaEmpresa(n, s);
        if (cod > 0) {
            ServletContext contexto = request.getServletContext();
            response.setHeader("auth", "1");
            File filePath = r.geraRelatorioTodosProdutosDownload(contexto, cod);
            if (filePath != null) {
                response.setHeader("nome", filePath.getName());
                File downloadFile = filePath;

                FileInputStream inStream = new FileInputStream(downloadFile);

                // obtém ServletContext
                ServletContext context = getServletContext();

                // obtém o tipo MIME do arquivo
                String mimeType = context.getMimeType(filePath.getAbsolutePath());
                if (mimeType == null) {
                    // definido como tipo binário se o mapeamento MIME não for encontrado
                    mimeType = "application/octet-stream";
                }
                System.out.println("MIME type: " + mimeType);

                // modifica a resposta
                response.setContentType(mimeType);
                response.setContentLength((int) downloadFile.length());

                // força o download
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                response.setHeader(headerKey, headerValue);

                // obtém o fluxo de saída da resposta
                OutputStream outStream = response.getOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inStream.close();
                outStream.close();
            } else {
                response.setHeader("nome", "0");
                System.out.println("nulll");
            }
        } else {
            response.setHeader("auth", "0");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
//
