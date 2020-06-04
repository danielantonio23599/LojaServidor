/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import com.google.gson.GsonBuilder;
import com.server.lojaserver.beans.CargoBEAN;
import com.server.lojaserver.beans.ClienteBEAN;
import com.server.lojaserver.beans.EnderecoBEAN;
import com.server.lojaserver.beans.FuncionarioBEAN;
import com.server.lojaserver.beans.SharedPreferencesEmpresaBEAN;
import com.server.lojaserver.controle.ControleAdmicao;
import com.server.lojaserver.controle.ControleCargo;
import com.server.lojaserver.controle.ControleCliente;
import com.server.lojaserver.controle.ControleFuncionario;
import com.server.lojaserver.controle.ControleLogin;
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
@WebServlet(name = "AdicionarCliente", urlPatterns = {"/loja_server/AdicionarCliente"}, initParams = {
    @WebInitParam(name = "cliente", value = ""),
    @WebInitParam(name = "endereco", value = "")
})
public class CadastrarCliente extends HttpServlet {

    ControleCliente con = new ControleCliente();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String str = new String(request.getParameter("cliente").getBytes("iso-8859-1"), "UTF-8");
        String end = new String(request.getParameter("endereco").getBytes("iso-8859-1"), "UTF-8");
        ClienteBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, ClienteBEAN.class);
        EnderecoBEAN e = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(end, EnderecoBEAN.class);
        int cod = con.adicionar(c,e);
        if (cod == 0) {
            response.setHeader("sucesso", "Cliente CADASTRADO COM SECESSO!!");
        } else {
            response.setHeader("sucesso", "CLIENTE COM ESTE EMAIL já ESTA CADASTRADO!!");
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
