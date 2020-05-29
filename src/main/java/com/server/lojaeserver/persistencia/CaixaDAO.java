/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.CaixaBEAN;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class CaixaDAO {

    private Connection connection;

    public CaixaDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public boolean abrirCaixa(CaixaBEAN c) {
        String sql = "insert into caixa (caiData,caiIn,caiTrocoIn,caiStatus,cai_funCodigo,cai_empCodigo) values (?,?,?,?,?,?)";
        System.out.println("dados fun " + c.getFuncionario());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, c.getData());
            stmt.setString(2, c.getIn());
            stmt.setFloat(3, c.getTrocoIn());
            stmt.setString(4, "aberto");
            stmt.setInt(5, c.getFuncionario());
            stmt.setInt(6, c.getEmpresa());
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public CaixaBEAN listar(int emp) {

        CaixaBEAN ca = new CaixaBEAN();
        ca.setCodigo(0);
        System.out.println("codigo empresa" + emp);
        String sql = "select * from caixa where caiStatus = 'aberto' and cai_empCodigo = " + emp + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                ca.setCodigo(rs.getInt(1));
                ca.setData(rs.getDate(2) + "");
                ca.setIn(rs.getString(3));
                ca.setOut(rs.getString(4));
                ca.setTrocoIn(rs.getFloat(5));
                ca.setTrocoFin(rs.getFloat(6));
                ca.setStatus(rs.getString(7));

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ca;
    }

    public void fecharCaixa(CaixaBEAN c) {
        String sql = "update caixa set caiOut = ? , caiTrocoFin = ? , caiStatus = ? where caiStatus = 'aberto';";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, c.getOut());
            stmt.setFloat(2, c.getTrocoFin());
            stmt.setString(3, "fechado");
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public float getSaldoAtual(int caixa, int emp) {

        float saldo = 0;
        String sql = "select	round(( (COALESCE(sum(venValor) ,0)+ caiTrocoIn) -\n"
                + "			  ( \n"
                + "				(select COALESCE(sum(disPreco),0) from caixa join despesa_dia join despesa where caiCodigo =  ded_caiCodigo and disCodigo = ded_disCodigo and caiCodigo = " + caixa + ")\n"
                + "					+ (select COALESCE(sum(sanValor),0) as sangria from caixa join sangria where caiCodigo = san_caiCodigo and caiCodigo = " + caixa + ")\n"
                + "			  )\n"
                + "		 ),2) as Saldo\n"
                + "			from caixa join venda where caiCodigo = ven_caiCodigo and venStatus = 'fechada' and caiCodigo = " + caixa + " and cai_empCodigo = " + emp + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                saldo = rs.getFloat(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return saldo;
    }

    public float getTotalVendido(int caixa, int emp) {

        float saldo = 0;
        String sql = "select	 round(COALESCE(sum(venValor)),2) as Vendas\n"
                + "from caixa join venda where caiCodigo = ven_caiCodigo and venStatus = 'fechada' and caiCodigo = " + caixa + " and cai_empCodigo = " + emp + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                saldo = rs.getFloat(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return saldo;
    }

}
