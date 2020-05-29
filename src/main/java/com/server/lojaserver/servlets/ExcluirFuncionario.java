/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.servlets;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Daniel
 */
@WebServlet(name = "ExcluirFuncionario", urlPatterns = {"/restaurante_server/ExcluirFuncionario"}, initParams = {
    @WebInitParam(name ="funcionario", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class ExcluirFuncionario {
    
}
