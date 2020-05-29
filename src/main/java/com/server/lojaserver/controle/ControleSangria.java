/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.SangriaBEAN;
import com.server.lojaeserver.persistencia.SangriaDAO;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ControleSangria {

    private SangriaDAO s = new SangriaDAO();
    private ControleCaixa c = new ControleCaixa();

    public String cadastrar(SangriaBEAN sangria, int emp) {
        sangria.setCaixa(c.getCaixa(emp));
        s.adicionar(sangria);
        return "Sucesso!";
    }

    public ArrayList<SangriaBEAN> listarSangriasCaixa(int emp) {
        return s.buscar(c.getCaixa(emp));
    }

    public float getTotalSangriasCaixa(int emp) {
        return s.getTotalSangriasCaixa(c.getCaixa(emp));
    }

}
