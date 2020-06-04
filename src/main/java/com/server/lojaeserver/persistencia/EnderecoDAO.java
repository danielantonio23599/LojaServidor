/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.EnderecoBEAN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class EnderecoDAO {

    private Connection connection;

    public EnderecoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public boolean adicionar(EnderecoBEAN endereco) {
        String sql = "INSERT INTO endereco (endCEP, endCidade, endUF, endBairro,"
                + " endEndereco, endNumero, endComplemento, endLatitude, endLongitude, end_cliCodigo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getUf());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getEndereco());
            stmt.setString(6, endereco.getNumero());
            stmt.setString(7, endereco.getComplemento());
            stmt.setFloat(8, endereco.getLatitude());
            stmt.setFloat(9, endereco.getLongitude());
            stmt.setInt(10, endereco.getCliente());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editar(EnderecoBEAN endereco) {
        String sql = "update endereco set endCEP = ?, endCidade = ?, endUF = ?, endBairro =?,"
                + " endEndereco = ?, endNumero = ?, endComplemento = ?, endLatitude = ?, endLongitude = ? where endCodigo = " + endereco.getCodigo() + ";";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getUf());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getEndereco());
            stmt.setString(6, endereco.getNumero());
            stmt.setString(7, endereco.getComplemento());
            stmt.setFloat(8, endereco.getLatitude());
            stmt.setFloat(9, endereco.getLongitude());
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<EnderecoBEAN> listarALl(int cliente) {
        ArrayList<EnderecoBEAN> c = new ArrayList<>();

        String sql = " select * from endereco  where end_cliCodigo = " + cliente + " ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                EnderecoBEAN ca = new EnderecoBEAN();
                ca.setCodigo(rs.getInt(1));
                ca.setCep(rs.getString(2));
                ca.setCidade(rs.getString(3));
                ca.setUf(rs.getString(4));
                ca.setBairro(rs.getDate(5) + "");
                ca.setEndereco(rs.getDate(6) + "");
                ca.setNumero(rs.getString(7));
                ca.setComplemento(rs.getString(8));
                ca.setLatitude(rs.getFloat(9));
                ca.setLongitude(rs.getFloat(10));
                ca.setCliente(rs.getInt(11));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}
