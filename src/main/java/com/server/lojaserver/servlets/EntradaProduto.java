/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.FuncionarioBEAN;
import com.server.lojaserver.beans.ProdutoBEAN;
import com.server.lojaserver.beans.VendaBEAN;
import com.server.lojaserver.controle.ControleFuncionario;
import com.server.lojaserver.controle.ControleLogin;
import com.server.lojaserver.controle.ControleProduto;
import com.server.lojaserver.controle.ControleVenda;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
@WebServlet(name = "AtualizaQuantidadeProduto", urlPatterns = {"/restaurante_server/AtualizaQuantidadeProduto"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = ""),
    @WebInitParam(name = "quantidade", value = ""),
    @WebInitParam(name = "produto", value = "")
})
public class EntradaProduto extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleProduto pro = new ControleProduto();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String senha = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaEmpresa(usuario, senha);
        if (cod > 0) {
            response.setHeader("auth", "1");
            float quantidade = Float.parseFloat(request.getParameter("quantidade"));
            int produto = Integer.parseInt(request.getParameter("produto"));
            pro.aumentaEstoque(produto, quantidade);
            response.setHeader("sucesso", "Sucesso");
        } else {
            response.setHeader("auth", "0");

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
