/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.OrdemServicoBEAN;
import com.server.lojaserver.beans.ProdutoBEAN;
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
public class OrdemServicoDAO {

    private Connection connection;

    public OrdemServicoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public ArrayList<OrdemServicoBEAN> buscar(String os, int emp) {

        ArrayList<OrdemServicoBEAN> p = new ArrayList<>();
        String sql = "SELECT * FROM ordem_servico WHERE os_empCodigo = " + emp + " and (osCodigo LIKE '" + os + "%' or osNome LIKE '" + os + "%');";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrdemServicoBEAN pp = new OrdemServicoBEAN();
                pp.setCodigo(rs.getInt(1));
                pp.setNome(rs.getString(2));
                pp.setDescricao(rs.getString(3));
                pp.setData(rs.getString(4));
                pp.setHora(rs.getString(5));
                pp.setStatus(rs.getString(6));
                pp.setEndereco(rs.getString(7));
                pp.setValor(rs.getFloat(8));
                pp.setFuncionario(rs.getInt(9));
                pp.setEmpresa(rs.getInt(10));
                pp.setVenda(rs.getInt(11));
                p.add(pp);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return p;

    }

    public boolean adicionar(OrdemServicoBEAN c, int emp) {
        String sql = "INSERT INTO ordem_servico (osNome, osDescricao, osData, osHora, osStatus,"
                + " osEndereco, osValor, os_funCodigo,os_empCodigo)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?,?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getDescricao());
            stmt.setString(3, c.getData());
            stmt.setString(4, c.getHora());
            stmt.setString(5, c.getStatus());
            stmt.setString(6, c.getEndereco());
            stmt.setFloat(7, c.getValor());
            stmt.setInt(8, c.getFuncionario());
            stmt.setInt(9, emp);
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<OrdemServicoBEAN> listarALl(int emp) {
        ArrayList<OrdemServicoBEAN> c = new ArrayList<OrdemServicoBEAN>();

        String sql = "select * from ordem_servico where os_empCodigo  = " + emp + " order by osData;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrdemServicoBEAN pp = new OrdemServicoBEAN();
                 pp.setCodigo(rs.getInt(1));
                pp.setNome(rs.getString(2));
                pp.setDescricao(rs.getString(3));
                pp.setData(rs.getString(4));
                pp.setHora(rs.getString(5));
                pp.setStatus(rs.getString(6));
                pp.setEndereco(rs.getString(7));
                pp.setValor(rs.getFloat(8));
                pp.setFuncionario(rs.getInt(9));
                pp.setEmpresa(rs.getInt(10));
                pp.setVenda(rs.getInt(11));
                c.add(pp);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
public ArrayList<OrdemServicoBEAN> listarOSHoje(int emp,String data) {
        ArrayList<OrdemServicoBEAN> c = new ArrayList<OrdemServicoBEAN>();

        String sql = "select * from ordem_servico where os_empCodigo  = " + emp + "  and osData = '"+data+"' ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrdemServicoBEAN pp = new OrdemServicoBEAN();
                 pp.setCodigo(rs.getInt(1));
                pp.setNome(rs.getString(2));
                pp.setDescricao(rs.getString(3));
                pp.setData(rs.getString(4));
                pp.setHora(rs.getString(5));
                pp.setStatus(rs.getString(6));
                pp.setEndereco(rs.getString(7));
                pp.setValor(rs.getFloat(8));
                pp.setFuncionario(rs.getInt(9));
                pp.setEmpresa(rs.getInt(10));
                pp.setVenda(rs.getInt(11));
                c.add(pp);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
    public ArrayList<OrdemServicoBEAN> listarOrdemServicoVenda(int emp, int venda) {
        ArrayList<OrdemServicoBEAN> c = new ArrayList<OrdemServicoBEAN>();

        String sql = "select * from ordem_servico where os_empCodigo  = " + emp + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrdemServicoBEAN pp = new OrdemServicoBEAN();
                pp.setCodigo(rs.getInt(1));
                pp.setNome(rs.getString(2));
                pp.setDescricao(rs.getString(3));
                pp.setData(rs.getString(4));
                pp.setHora(rs.getString(5));
                pp.setStatus(rs.getString(6));
                pp.setEndereco(rs.getString(7));
                pp.setValor(rs.getFloat(8));
                pp.setFuncionario(rs.getInt(9));
                pp.setEmpresa(rs.getInt(10));
                pp.setVenda(rs.getInt(11));
                c.add(pp);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public OrdemServicoBEAN localizar(int produto) {
        OrdemServicoBEAN pp = new OrdemServicoBEAN();

        String sql = "select * from ordem_servico where osCodigo = " + produto + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                 pp.setCodigo(rs.getInt(1));
                pp.setNome(rs.getString(2));
                pp.setDescricao(rs.getString(3));
                pp.setData(rs.getString(4));
                pp.setHora(rs.getString(5));
                pp.setStatus(rs.getString(6));
                pp.setEndereco(rs.getString(7));
                pp.setValor(rs.getFloat(8));
                pp.setFuncionario(rs.getInt(9));
                pp.setEmpresa(rs.getInt(10));
                pp.setVenda(rs.getInt(11));
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pp;
    }

    public OrdemServicoBEAN localizar(String produto, int emp) {
        OrdemServicoBEAN pp = new OrdemServicoBEAN();
        pp.setCodigo(0);
        String sql = "select * from ordem_servico where osNome = '" + produto + "' and os_empCodigo = " + emp + ";";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pp.setCodigo(rs.getInt(1));
                pp.setNome(rs.getString(2));
                pp.setDescricao(rs.getString(3));
                pp.setData(rs.getString(4));
                pp.setHora(rs.getString(5));
                pp.setStatus(rs.getString(6));
                pp.setEndereco(rs.getString(7));
                pp.setValor(rs.getFloat(8));
                pp.setFuncionario(rs.getInt(9));
                pp.setEmpresa(rs.getInt(10));
                pp.setVenda(rs.getInt(11));
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pp;
    }

    public void editar(OrdemServicoBEAN c) {
        String sql = "update ordem_servico set osNome = ? , osDescricao = ? , osData = ? , osHora = ?, osHora = ?"
                + ", osStatus = ?, oEndereco = ?, osValor = ?, os_funCodigo = ?, os_empCodigo = ?, os_venCodigo = ? where osCodigo = " + c.getCodigo() + ";";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
             stmt.setString(1, c.getNome());
            stmt.setString(2, c.getDescricao());
            stmt.setString(3, c.getData());
            stmt.setString(4, c.getHora());
            stmt.setString(5, c.getStatus());
            stmt.setString(6, c.getEndereco());
            stmt.setFloat(7, c.getValor());
            stmt.setInt(8, c.getFuncionario());
            stmt.setInt(9, c.getEmpresa());           
            stmt.setInt(10, c.getVenda());
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public ArrayList<OrdemServicoBEAN> listarOrdemServico(int emp) {
        ArrayList<OrdemServicoBEAN> c = new ArrayList<OrdemServicoBEAN>();

        String sql = "select * from ordem_servico where os_empCodigo = " + emp + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrdemServicoBEAN pp = new OrdemServicoBEAN();
                 pp.setCodigo(rs.getInt(1));
                pp.setNome(rs.getString(2));
                pp.setDescricao(rs.getString(3));
                pp.setData(rs.getString(4));
                pp.setHora(rs.getString(5));
                pp.setStatus(rs.getString(6));
                pp.setEndereco(rs.getString(7));
                pp.setValor(rs.getFloat(8));
                pp.setFuncionario(rs.getInt(9));
                pp.setEmpresa(rs.getInt(10));
                pp.setVenda(rs.getInt(11));
                c.add(pp);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public void excluir(int cod) {
        String sql = "delete from ordem_servico where osCodigo = ? ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cod);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet consulta(String strSql) {
        try {
            //criando o objeto Statement para que seja possivel enviar as consultas
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //objeto do ResulSet para receber o resultado da consulta
            ResultSet rs = stmt.executeQuery(strSql);
            return rs;
        } catch (SQLException erro) {
            System.err.println(erro.getMessage());
            return null;
        }
    }

}
