/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.AdmicaoBEAN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Daniel
 */
public class AdmicaoDAO {

    private Connection connection;

    public AdmicaoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public boolean preAdicionar(int funcionario, int empresa) {
        String sql = "INSERT INTO admicao (adm_empCodigo,adm_funCodigo) VALUES (?,?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, empresa);
            stmt.setInt(2, funcionario);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean admitir(AdmicaoBEAN a) {
        String sql = "INSERT INTO admicao (adm_empCodigo,adm_funCodigo,adm_carCodigo,admDataAdmicao) VALUES (?,?,?,?);";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, a.getEmpresa());
            stmt.setInt(2, a.getFuncionario());
            stmt.setInt(3, 1);
            stmt.setString(4, a.getAdmicao());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean confirmarAdmicao(AdmicaoBEAN c, String email, String senha) {
        boolean ret = false;
        String sql = "update admicao set admDataAdmicao = ?, admUniforme = ?, admNumCartao = ?,"
                + " admSalario = ?, adm_carCodigo = ? where adm_funCodigo = " + c.getFuncionario() + ""
                + " and adm_empCodigo = (select empCodigo from empresa where empEmail = '" + email + "' and empSenha = '" + senha + "');";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, c.getAdmicao());
            stmt.setInt(2, c.getUniforme());
            stmt.setInt(3, c.getCartaoPonto());
            stmt.setFloat(4, c.getSalario());
            stmt.setInt(5, c.getCargo());
            ret = stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public boolean demitir(AdmicaoBEAN c, String email, String senha) {
        boolean ret;
        String sql = "update admicao set admDataSaida = ?"
                + " where adm_funCodigo = " + c.getFuncionario() + ""
                + " and adm_empCodigo = (select empCodigo from empresa where empEmail = '" + email + "' and empSenha = '" + senha + "') and adm_carCodigo = " + c.getCargo() + ";";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, c.getSaida());
            ret = stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    public AdmicaoBEAN localizar(int funcionario, String email, String senha) {
        AdmicaoBEAN ca = new AdmicaoBEAN();
        String sql = "select COALESCE(admDataAdmicao,''), COALESCE(admDataSaida,''), admUniforme, admNumCartao, admSalario,adm_empCodigo,adm_funCodigo,adm_carCodigo from admicao where adm_funCodigo = " + funcionario + " and adm_empCodigo = (select empCodigo from empresa where empEmail = '" + email + "' and empSenha = '" + senha + "') ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setAdmicao(rs.getString(1));
                ca.setSaida(rs.getString(2));
                ca.setUniforme(rs.getInt(3));
                ca.setCartaoPonto(rs.getInt(4));
                ca.setSalario(rs.getFloat(5));
                ca.setEmpresa(rs.getInt(6));
                ca.setFuncionario(rs.getInt(7));
                ca.setCargo(rs.getInt(8));
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return ca;
    }

    public boolean excluir(AdmicaoBEAN ad, String email, String senha) {
        boolean ret;
        String sql = "delete from Admicao where adm_funCodigo = " + ad.getFuncionario() + " and adm_empCodigo = (select empCodigo from empresa where empEmail = '" + email + "' and empSenha = '" + senha + "'); ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ret = stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }
}
