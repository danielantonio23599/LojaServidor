/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.CargoBEAN;
import com.server.lojaeserver.persistencia.CargoDAO;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ControleCargo {

    private CargoDAO car = new CargoDAO();

    public String adicionar(CargoBEAN c) {

        if (car.localizarPorNome(c.getNome()).getNome() == null) {
            car.adicionar(c);
            return "Sucesso!!";
        } else {
            return " Cargo já Cadastrado";
        }
    }

    public String editar(CargoBEAN c) {
        car.editar(c);
        return "Sucesso";
    }

    public String excluir(String cargo) {
        car.excluir(cargo);
        return "Sucesso";
    }

    public ArrayList<CargoBEAN> listarCargos() {
        return car.listarALl();
    }
    public CargoBEAN listarCargoFuncionario(String funcionario) {
        return car.listarPorFuncionario(Integer.parseInt(funcionario));
    }
}
