/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.PedidoBEAN;
import com.server.lojaserver.beans.ProdutosGravados;
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
public class PedidoDAO {

    private Connection connection;

    public PedidoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public int adicionar(PedidoBEAN c) {
        int i = 0;
        String sql = "INSERT INTO pedido (pedQTD,"
                + " pedObs,ped_venCodigo,ped_proCodigo,pedStatus )"
                + " VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setFloat(1, c.getQuantidade());
            stmt.setString(2, c.getObservacao());
            stmt.setInt(3, c.getVenda());
            stmt.setInt(4, c.getProduto());
            stmt.setString(5, c.getStatus());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                i = rs.getInt(1);
            }
            stmt.close();
            return i;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return i;
        }
    }

    public ArrayList<PedidoBEAN> listarPedidos(int empresa, int caixa) {
        ArrayList<PedidoBEAN> c = new ArrayList<PedidoBEAN>();

        String sql = "SELECT pedCodigo,pedQTD,pedObs,pedStatus\n"
                + "                FROM empresa join caixa join venda join pedido join produto\n"
                + "                where\n"
                + "                empCodigo = cai_empCodigo and caiCodigo = ven_caiCodigo and venCodigo = ped_venCodigo and ped_proCodigo = proCodigo\n"
                + "                and caiCodigo=" + caixa + " and empCodigo = " + empresa + " and venStatus = 'aberta' and ped_devCodigo is null;";
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PedidoBEAN ca = new PedidoBEAN();
                ca.setCodigo(rs.getInt(1));
                ca.setQuantidade(rs.getFloat(4));
                ca.setObservacao(rs.getString(7));
                ca.setStatus(rs.getString(9));
                c.add(ca);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ArrayList<ProdutosGravados> produtosVenda(int venda) {
        ArrayList<ProdutosGravados> c = new ArrayList<ProdutosGravados>();

        String sql = "SELECT pedCodigo,ped_proCodigo, proNome,pedQTD,proPreco, (proPreco * pedQTD) "
                + "FROM banco_loja.produto join banco_loja.pedido join banco_loja.venda"
                + " where"
                + " venCodigo = ped_venCodigo and ped_proCodigo = proCodigo and venCodigo = " + venda + " and ped_devCodigo is null;";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProdutosGravados ca = new ProdutosGravados();
                ca.setCodPedidVenda(rs.getInt(1));
                ca.setCodProduto(rs.getInt(2));
                ca.setNome(rs.getString(3));
                ca.setQuantidade(rs.getFloat(4));
                ca.setValorUNI(rs.getFloat(5));
                ca.setValorFinal(rs.getFloat(6));
                c.add(ca);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public PedidoBEAN localizar(int produto, int venda, String time) {
        PedidoBEAN ca = new PedidoBEAN();

        String sql = "select * from pedido where ped_proCodigo = " + produto + " and ped_venCondigo = " + venda + " and pedTime = '" + time + "' and ped_devCodigo is null;";
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setCodigo(rs.getInt(1));
                ca.setQuantidade(rs.getFloat(2));
                ca.setObservacao(rs.getString(3));
                ca.setExcluzao(rs.getInt(4));
                ca.setProduto(rs.getInt(5));
                ca.setVenda(rs.getInt(6));

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ca;
    }

    public PedidoBEAN localizar(int pedido) {
        PedidoBEAN ca = new PedidoBEAN();

        String sql = "select * from pedido where pedCodigo = " + pedido + ";";
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setCodigo(rs.getInt(1));
                ca.setQuantidade(rs.getFloat(2));
                ca.setObservacao(rs.getString(3));
                ca.setExcluzao(rs.getInt(4));
                ca.setProduto(rs.getInt(5));
                ca.setVenda(rs.getInt(6));

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ca;
    }

    public void transferir(int pedido, int destino) {
        String sql = "update pedido set ped_venCodigo = " + destino + "  "
                + "where pedCodigo = " + pedido + " ;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void mudarStatusAtrazado(int pedido) {
        String sql = "update pedido set pedStatus = 'Atrazado'  "
                + "where pedCodigo = " + pedido + " ;";

        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void mudarStatusRealizado(int pedido, String horas) {
        String sql = "update pedido set pedStatus = 'Realizado', pedTimeF ='" + horas + "'  "
                + "where pedCodigo = " + pedido + ";";

        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void devolver(int codigo, int devolucao) {
        String sql = "update pedido set ped_devCodigo = " + devolucao + " where pedCodigo = " + codigo + " ;";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferirMesa(int origem, int destino) {
        String sql = "update pedido set ped_venCodigo = " + destino + "  where ped_venCodigo = " + origem + " ;";

        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public int getMaxMesa(int caixa) {
        int mesa = 0;
        String sql = "select max(venMesa) from venda where ven_caiCodigo = " + caixa + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mesa = rs.getInt(1);

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mesa;
    }

}
