/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.DevolucaoBEAN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.server.lojaeserver.persistencia.ConnectionFactory;
import java.sql.Statement;

/**
 *
 * @author Daniel
 */
public class DevolucaoDAO {

    private Connection connection;

    public DevolucaoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public int inserir(DevolucaoBEAN c) {
        int lastId = 0;
        String sql = "INSERT INTO devolucao (devMotivo, devTime, devQTD, devValor, dev_caiCodigo)"
                + " VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getMotivo());
            stmt.setString(2, c.getTime());
            stmt.setFloat(3, c.getQuantidade());
            stmt.setFloat(4, c.getValor());
            stmt.setInt(5, c.getCaixa());
            stmt.executeUpdate();
            final ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                lastId = rs.getInt(1);
            }

            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lastId;
    }

    public ArrayList<DevolucaoBEAN> listarDevolucaoVenda(int venda) {
        ArrayList<DevolucaoBEAN> c = new ArrayList<>();

        String sql = "select devCodigo,devMotivo,devTime,devValor,devQTD,dev_caiCodigo,proNome\n"
                + "                  from  caixa join devolucao join pedido join produto where\n"
                + "                  caiCodigo = dev_caiCodigo and devCodigo = ped_excCodigo and proCodigo = ped_proCodigo and ped_venCodigo = " + venda + " order by devTime;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DevolucaoBEAN e = new DevolucaoBEAN();
                e.setCodigo(rs.getInt(1));
                e.setMotivo(rs.getString(2));
                e.setTime(rs.getString(3));
                e.setValor(rs.getFloat(4));
                e.setQuantidade(rs.getFloat(5));
                e.setCaixa(rs.getInt(6));
                e.setProduto(rs.getString(7));
                c.add(e);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return c;
    }

    public DevolucaoBEAN listarUm(String cod) {
        DevolucaoBEAN e = new DevolucaoBEAN();
        System.out.println("Codigo " + cod);
        String sql = "select * from devolucao where "
                + " devCodigo = " + cod + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                e.setCodigo(rs.getInt(1));
                e.setMotivo(rs.getString(2));
                e.setTime(rs.getString(3));
                e.setValor(rs.getFloat(4));
                e.setQuantidade(rs.getFloat(5));
                e.setCaixa(rs.getInt(6));
            }
            stmt.close();

        } catch (SQLException er) {
            throw new RuntimeException();
        }
        return e;
    }

    public ArrayList<DevolucaoBEAN> listarDevolucaoCaixa(int caixa) {
        ArrayList<DevolucaoBEAN> c = new ArrayList<>();

        String sql = "select devCodigo,devMotivo,devTime,devValor,devQTD,dev_caiCodigo,proNome\n"
                + "                  from  caixa join devolucao join pedido join produto where\n"
                + "                  caiCodigo = dev_caiCodigo and devCodigo = ped_excCodigo and proCodigo = ped_proCodigo and caiCodigo = " + caixa + "  order by devTime;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DevolucaoBEAN e = new DevolucaoBEAN();
                e.setCodigo(rs.getInt(1));
                e.setMotivo(rs.getString(2));
                e.setTime(rs.getString(3));
                e.setValor(rs.getFloat(4));
                e.setQuantidade(rs.getFloat(5));
                e.setCaixa(rs.getInt(6));
                e.setProduto(rs.getString(7));
                c.add(e);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return c;
    }

    public Float getValorDevolucaoCaixa(int caixa) {
        float total = 0;
        String sql = "select coalesce(sum(devQTD*proPreco),0)\n"
                + "                  from  caixa join devolucao join pedido join produto where\n"
                + "                  caiCodigo = dev_caiCodigo and devCodigo = ped_excCodigo and proCodigo = ped_proCodigo and caiCodigo = " + caixa + " group by caiCodigo;";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getFloat(1);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return total;
    }

    public Float getValorDevolucao(int devolucao) {
        float total = 0;
        String sql = "select coalesce(devValor,0)\n"
                + "                  from devolucao where\n"
                + "                   devCodigo =" + devolucao + ";";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getFloat(1);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return total;
    }

    public Float getValorDevolucaoVenda(int caixa) {
        float total = 0;
        String sql = "select coalesce((pedQTD*proPreco),0)\n"
                + "                  from  caixa join devolucao join pedido join produto where\n"
                + "                  caiCodigo = dev_caiCodigo and devCodigo = ped_excCodigo and proCodigo = ped_proCodigo and caiCodigo = " + caixa + "group by caiCodigo;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getFloat(1);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return total;
    }
}
