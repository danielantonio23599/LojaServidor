/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.controle;

import com.server.lojaserver.beans.ItemBEAN;
import com.server.lojaserver.beans.ItemDAO;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ControleItem {

    public void inserirItens(ArrayList<ItemBEAN> i, int pedCod) {
        ItemDAO dao = new ItemDAO();
        for (ItemBEAN item : i) {
            item.setPedido(pedCod);
            dao.adicionar(item);
        }
    }

}
