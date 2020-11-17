/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaeserver.persistencia.OrdemServicoDAO;
import com.server.lojaserver.beans.OrdemServicoBEAN;
import com.server.lojaserver.util.Time;
import java.util.ArrayList;
import javafx.scene.chart.PieChart;

/**
 *
 * @author Daniel
 */
public class ControleOS {

    public String adicionar(OrdemServicoBEAN od, int emp) {
        OrdemServicoDAO dao = new OrdemServicoDAO();
        dao.adicionar(od, emp);
        return "Sucesso!";
    }

    public String atualizar(OrdemServicoBEAN os) {
        OrdemServicoDAO dao = new OrdemServicoDAO();
        dao.editar(os);
        return "Sucesso!";
    }

    public ArrayList<OrdemServicoBEAN> listarOSs(int emp) {
        OrdemServicoDAO dao = new OrdemServicoDAO();
        return dao.listarALl(emp);
    }
    public ArrayList<OrdemServicoBEAN> listarOSHoje(int emp) {
        OrdemServicoDAO dao = new OrdemServicoDAO();
        return dao.listarOSHoje(emp,Time.getData());
    }

}
