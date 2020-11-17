/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.DespesaBEAN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class DespesaDAO {

    private Connection connection;

    public DespesaDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public boolean adicionar(DespesaBEAN c, String u, String s) {
        String sql = "insert into despesa(disNome,disDescricao,disPreco,dis_caiCodigo) "
                + "values (?,?,?,"
                + "(select caiCodigo from empresa join caixa "
                + "where empCodigo = cai_empCodigo and caiStatus = 'aberto' and empEmail = '" + u + "' and empSenha = '" + s + "'))";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getDescricao());
            stmt.setFloat(3, c.getPreco());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DespesaBEAN> listarAll(String u, String s) {
        ArrayList<DespesaBEAN> c = new ArrayList<DespesaBEAN>();

        String sql = "select * from empresa join caixa join despesa where empCodigo = cai_empCodigo and dis_caiCodigo = caiCodigo"
                + "and caiStatus = 'aberto' and empCodigo = "
                + "(select caiCodigo from empresa join caixa where empCodigo = cai_empCodigo and caiStatus = 'aberto' and empEmail = '" + u + "' and empSenha = '" + s + "');";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DespesaBEAN ca = new DespesaBEAN();
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setDescricao(rs.getString(3));
                ca.setPreco(rs.getFloat(4));
                ca.setCaixa(rs.getInt(5));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return c;
    }

    public ArrayList<DespesaBEAN> localizar(int i) {
        String sql = "select * from despesa where disCodigo = ?;";
        DespesaBEAN ca = new DespesaBEAN();
        ArrayList<DespesaBEAN> k = new ArrayList<DespesaBEAN>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, i);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setDescricao(rs.getString(3));
                ca.setPreco(rs.getFloat(4));

                k.add(ca);
            }
            stmt.close();
            return k;
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    public void excluir(int cod) {
        String sql = "delete from despesa where disCodigo = ? ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cod);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Float getTotalDespesasCaixa(String u, String s) {
        String sql = "select COALESCE(sum(disPreco),0) from despesa join caixa join empresa where empCodigo = cai_empCodigo and dis_caiCodigo = caiCodigo and caiStatus = 'aberto' and empEmail = '" + u + "' and empSenha = '" + s + "' ;";
        float total = 0;
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getFloat(1);
            }
            stmt.close();
            return total;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}
