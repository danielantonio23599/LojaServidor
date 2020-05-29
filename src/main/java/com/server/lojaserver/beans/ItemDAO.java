/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaserver.beans;

import com.server.lojaeserver.persistencia.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ItemDAO {

    private Connection connection;

    private PreparedStatement stmt;

    public ItemDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public void adicionar(ItemBEAN i) {

        String sql = "INSERT INTO item (ite_pedCodigo,"
                + " ite_proCodigo,iteQTD,iteValor )"
                + " VALUES (?, ?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, i.getPedido());
            stmt.setInt(2, i.getProduto());
            stmt.setFloat(3, i.getQuantidade());
            stmt.setFloat(4, i.getValor());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
