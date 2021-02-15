/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.Caixa;
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

    public boolean abrirCaixa(CaixaBEAN c, String email, String senha) {
        String sql = "insert into caixa (caiData,caiIn,caiTrocoIn,caiStatus,cai_funCodigo,cai_empCodigo) values (?,?,?,?,?,(select empCodigo from empresa where empEmail = '" + email + "' and empSenha = '" + senha + "'))";
        System.out.println("dados fun " + c.getFuncionario());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, c.getData());
            stmt.setString(2, c.getIn());
            stmt.setFloat(3, c.getTrocoIn());
            stmt.setString(4, "aberto");
            stmt.setInt(5, c.getFuncionario());
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

    public int listar(String u, String s) {
        int caixa = 0;
        String sql = "select caiCodigo from empresa join caixa where empCodigo = cai_empCodigo and caiStatus = 'aberto' and empEmail = '" + u + "' and empSenha = '" + s + "';";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                caixa = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return caixa;
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

    public float getSaldoAtual(String email, String senha) {
        float saldo = 0;
        String sql = "select caiCodigo as cai,round(((COALESCE(sum(venValorFin) ,0)+ caiTrocoIn) - ( (select COALESCE(sum(disPreco),0) from caixa join despesa where caiCodigo =  dis_caiCodigo and caiCodigo = cai )\n"
                + "                           				+ (select COALESCE(sum(sanValor),0) as sangria from caixa join sangria where caiCodigo = san_caiCodigo and caiCodigo = cai )\n"
                + "                         		  )- (select COALESCE(sum(devValor),0) from caixa join devolucao where caiCodigo = dev_caiCodigo  and caiCodigo = cai)\n"
                + "                          		 ),2) as Saldo                        			\n"
                + "                                 from empresa join caixa join venda where empCodigo = cai_empCodigo and caiCodigo = ven_caiCodigo and venStatus = 'Fechada' and caiStatus = 'aberto' and cai_empCodigo = (select empCodigo from empresa where  empEmail = '" + email + "' and empSenha = '" + senha + "');";
        System.out.println(sql);
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                saldo = rs.getFloat(2);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return saldo;
    }

    public float getTotalVendido(String email, String senha) {
        float saldo = 0;
        String sql = "select	 round(COALESCE(sum(venValorFin)),2) as Vendas\n"
                + "from empresa join caixa join venda where empCodigo = cai_empCodigo and caiCodigo = ven_caiCodigo and \n"
                + "venStatus = 'Fechada' and caiStatus = 'aberto' and empEmail = '" + email + "' and empSenha = '" + senha + "';";
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

    public Caixa getValoresCaixa(String emai, String senha) {
        String sql = "select caiCodigo as cai,empCodigo as emp,\n"
                + "(select COALESCE(sum(disPreco),0) from despesa join caixa join empresa where dis_caiCodigo = caiCodigo and empCodigo = cai_empCodigo and empCodigo = emp and caiStatus='aberto') as des,\n"
                + "(SELECT COALESCE(sum(sanValor),0) FROM sangria join caixa join empresa where san_caiCodigo = caiCodigo and empCodigo = cai_empCodigo and empCodigo = emp and caiStatus='aberto' ) as san,\n"
               +  " caiTrocoIn,"
                + "(select	 round(COALESCE(sum(venValorFin)),2) as Vendas\n"
                + "from empresa join caixa join venda where empCodigo = cai_empCodigo and caiCodigo = ven_caiCodigo and \n"
                + "venStatus = 'Fechada' and caiStatus = 'aberto' and empCodigo = emp) as faturamento,\n"
                + "(select coalesce(sum(devQTD*proPreco),0)\n"
                + "	from  caixa join devolucao join pedido join produto where\n"
                + "	caiCodigo = dev_caiCodigo and devCodigo = ped_devCodigo and proCodigo = ped_proCodigo and caiCodigo = cai group by caiCodigo) as devolucao\n"
                + "FROM empresa join caixa where empCodigo = cai_empCodigo and caiStatus='aberto' and empEmail = '" + emai + "' and empSenha = '" + senha + "' ;";

        System.out.println(sql);
        Caixa c = new Caixa();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                float troco;
                c.setCaixa(rs.getInt(1));
                c.setDespesas(rs.getFloat(3));
                c.setSangria(rs.getFloat(4));
                 troco  = rs.getFloat(5);
                c.setFaturamento(rs.getFloat(6));
                c.setDevolucao(rs.getFloat(7));
                c.setSaldo(troco + c.getFaturamento() -c.getDevolucao() - c.getSangria() - c.getDespesas());

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

}
