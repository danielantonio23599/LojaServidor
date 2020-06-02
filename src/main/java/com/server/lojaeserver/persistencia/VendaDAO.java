/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.Mesa;
import com.server.lojaserver.beans.ProdutosGravados;
import com.server.lojaserver.beans.Venda;
import com.server.lojaserver.beans.VendaBEAN;
import com.server.lojaserver.util.Time;
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
public class VendaDAO {

    private Connection connection;

    public VendaDAO() {
        this.connection = ConnectionFactory.getConnection();;
    }

    public int abrirVenda(VendaBEAN c) {
        int lastId = 0;
        String sql = "insert into venda(venTime,ven_caiCodigo, venStatus) values (?,?,?)";
        System.out.println(" caixa " + c.getCaixa());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getHora());
            stmt.setInt(2, c.getCaixa());
            stmt.setString(3, "aberta");
            stmt.executeUpdate();
            final ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                lastId = rs.getInt(1);
            }

            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lastId;
    }

    //INSERE QRCODE NA VENDA
    public void inserirQRCode(byte[] qr, int cod) {
        String sql = "update venda set venQRcode = '" + qr + "'  where venCodigo = " + cod + ";";
        System.out.println(sql);
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

    private int getVenda(String hora, int caixa) {
        int cod = 0;
        String sql = "select venCodigo from venda where venTime = '" + hora + "' and ven_caiCodigo =" + caixa + " ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cod = rs.getInt(1);

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cod;
    }

    public VendaBEAN listarVenda(int venda) {
        VendaBEAN v = new VendaBEAN();
        String sql = "select * from venda where venCodigo = " + venda + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                v.setCodigo(rs.getInt(1));
                v.setQRcode(rs.getBytes(2));
                v.setHora(rs.getString(3));
                v.setValor(rs.getFloat(4));
                v.setCusto(rs.getFloat(5));
                v.setStatus(rs.getString(6));
                v.setPagamento(rs.getString(7));
                v.setDesconto(rs.getFloat(8));
                v.setFrete(rs.getFloat(9));
                v.setCaixa(rs.getInt(10));

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return v;
    }

    public Float getCustoVenda(int venda) {
        float valor = 0;

        String sql = "select COALESCE(sum(pedQTD*proCusto),0) from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and \n"
                + "ped_proCodigo = proCodigo and ped_excCodigo is null and venCodigo = " + venda + ";";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                valor = rs.getFloat(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return valor;
    }

    public ArrayList<Venda> listarVendasAbertas(int caixa) {
        ArrayList<Venda> c = new ArrayList<>();

        String sql = "select (venCodigo)as venda, venStatus,venTime,ven_cliCodigo,\n"
                + "                COALESCE((select  sum(pedQTD*proPreco) \n"
                + "                from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo and venda = venCodigo ),0) \n"
                + "                as valor, COALESCE((select sum(devValor)from devolucao join pedido where devCodigo = ped_excCodigo and ped_venCodigo = venda),0) as devolucao from\n"
                + "                banco_loja.caixa join banco_loja.venda \n"
                + "                where \n"
                + "                caiCodigo = ven_caiCodigo  and caiCodigo = " + caixa + " and venStatus = 'aberta';";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venda ca = new Venda();
                ca.setCodigo(rs.getInt(1));
                ca.setStatus(rs.getString(2));
                ca.setHora(rs.getString(3));
                ca.setCliente(rs.getString(4));
                ca.setValor(rs.getFloat(5));
                ca.setDevolucao(rs.getFloat(6));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ArrayList<Venda> listarVendas(int empresa) {
        ArrayList<Venda> c = new ArrayList<>();

        String sql = "select (venCodigo)as venda, venStatus,venTime,venPagamento,(select cliNome from venda join cliente where ven_cliCodigo = cliCodigo and venCodigo = venda) as cliente,\n"
                + "                COALESCE((select  sum(pedQTD*proPreco) \n"
                + "                from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo and venda = venCodigo ),0) \n"
                + "                as valor,COALESCE((select  sum(pedQTD*proCusto) from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo \n"
                + "				and venCodigo = venda and ped_excCodigo is null and venda = venCodigo ),0) as custo,                \n"
                + "                COALESCE((select sum(devValor)from devolucao join pedido where devCodigo = ped_excCodigo and ped_venCodigo = venda),0) as devolucao\n"
                + "                ,venFrete, venDesconto,venValorFin, caiData\n"
                + "                from banco_loja.empresa join\n"
                + "                banco_loja.caixa join banco_loja.venda \n"
                + "                where empCodigo = cai_empCodigo and\n"
                + "                caiCodigo = ven_caiCodigo  and empCodigo = " + empresa + " ;";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venda ca = new Venda();
                ca.setCodigo(rs.getInt(1));
                ca.setStatus(rs.getString(2));
                ca.setHora(rs.getString(3));
                ca.setPagamento(rs.getString(4));
                ca.setCliente(rs.getString(5));
                ca.setValor(rs.getFloat(6));
                ca.setCusto(rs.getFloat(7));
                ca.setDevolucao(rs.getFloat(8));
                ca.setFrete(rs.getFloat(9));
                ca.setDesconto(rs.getFloat(10));
                ca.setValorFinal(rs.getFloat(11));
                ca.setData(Time.formataDataBR(rs.getString(12)));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ArrayList<Venda> listarVendasPorStatus(int empresa, String status) {
        ArrayList<Venda> c = new ArrayList<>();

        String sql = "select (venCodigo)as venda, venStatus,venTime,venPagamento,(select cliNome from venda join cliente where ven_cliCodigo = cliCodigo and venCodigo = venda) as cliente,\n"
                + "                COALESCE((select  sum(pedQTD*proPreco) \n"
                + "                from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo and venda = venCodigo ),0) \n"
                + "                as valor,COALESCE((select  sum(pedQTD*proCusto) from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo \n"
                + "				and venCodigo = venda and ped_excCodigo is null and venda = venCodigo ),0) as custo,                \n"
                + "                COALESCE((select sum(devValor)from devolucao join pedido where devCodigo = ped_excCodigo and ped_venCodigo = venda),0) as devolucao\n"
                + "                ,venFrete, venDesconto,venValorFin, caiData\n"
                + "                from banco_loja.empresa join\n"
                + "                banco_loja.caixa join banco_loja.venda \n"
                + "                where empCodigo = cai_empCodigo and\n"
                + "                caiCodigo = ven_caiCodigo  and venStatus = '" + status + "' and empCodigo = " + empresa + " ;";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venda ca = new Venda();
                ca.setCodigo(rs.getInt(1));
                ca.setStatus(rs.getString(2));
                ca.setHora(rs.getString(3));
                ca.setPagamento(rs.getString(4));
                ca.setCliente(rs.getString(5));
                ca.setValor(rs.getFloat(6));
                ca.setCusto(rs.getFloat(7));
                ca.setDevolucao(rs.getFloat(8));
                ca.setFrete(rs.getFloat(9));
                ca.setDesconto(rs.getFloat(10));
                ca.setValorFinal(rs.getFloat(11));
                ca.setData(Time.formataDataBR(rs.getString(12)));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ArrayList<Venda> listarVendasPorConsulta(int empresa, String cliente) {
        ArrayList<Venda> c = new ArrayList<>();

        String sql = "select (venCodigo)as venda, venStatus,venTime,venPagamento,(select cliNome from venda join cliente where ven_cliCodigo = cliCodigo and venCodigo = venda ) as cli,\n"
                + "                COALESCE((select  sum(pedQTD*proPreco) \n"
                + "                from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo and venda = venCodigo ),0) \n"
                + "                as valor,COALESCE((select  sum(pedQTD*proCusto) from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo \n"
                + "				and venCodigo = venda and ped_excCodigo is null and venda = venCodigo ),0) as custo,                \n"
                + "                COALESCE((select sum(devValor)from devolucao join pedido where devCodigo = ped_excCodigo and ped_venCodigo = venda),0) as devolucao\n"
                + "                ,venFrete, venDesconto,venValorFin, caiData\n"
                + "                from banco_loja.empresa join\n"
                + "                banco_loja.caixa join banco_loja.venda join cliente\n"
                + "                where empCodigo = cai_empCodigo and\n"
                + "                caiCodigo = ven_caiCodigo and ven_cliCodigo = cliCodigo and empCodigo = " + empresa + " and cliNome like '" + cliente + "%' ;";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venda ca = new Venda();
                ca.setCodigo(rs.getInt(1));
                ca.setStatus(rs.getString(2));
                ca.setHora(rs.getString(3));
                ca.setPagamento(rs.getString(4));
                ca.setCliente(rs.getString(5));
                ca.setValor(rs.getFloat(6));
                ca.setCusto(rs.getFloat(7));
                ca.setDevolucao(rs.getFloat(8));
                ca.setFrete(rs.getFloat(9));
                ca.setDesconto(rs.getFloat(10));
                ca.setValorFinal(rs.getFloat(11));
                ca.setData(Time.formataDataBR(rs.getString(12)));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ArrayList<Venda> listarVendasPorInData(int empresa, String dataIn, String dataFin) {
        ArrayList<Venda> c = new ArrayList<>();

        String sql = "select (venCodigo)as venda, venStatus,venTime,venPagamento,(select cliNome from venda join cliente where ven_cliCodigo = cliCodigo and venCodigo = venda ) as cli,\n"
                + "                COALESCE((select  sum(pedQTD*proPreco) \n"
                + "                from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo and venda = venCodigo ),0) \n"
                + "                as valor,COALESCE((select  sum(pedQTD*proCusto) from banco_loja.venda join banco_loja.pedido join banco_loja.produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo \n"
                + "				and venCodigo = venda and ped_excCodigo is null and venda = venCodigo ),0) as custo,                \n"
                + "                COALESCE((select sum(devValor)from devolucao join pedido where devCodigo = ped_excCodigo and ped_venCodigo = venda),0) as devolucao\n"
                + "                ,venFrete, venDesconto,venValorFin, caiData\n"
                + "                from banco_loja.empresa join\n"
                + "                banco_loja.caixa join banco_loja.venda\n"
                + "                where empCodigo = cai_empCodigo and\n"
                + "                caiCodigo = ven_caiCodigo  and empCodigo = 2 and caiData between '" + dataIn + "' and '" + dataFin + "'  ;";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venda ca = new Venda();
                ca.setCodigo(rs.getInt(1));
                ca.setStatus(rs.getString(2));
                ca.setHora(rs.getString(3));
                ca.setPagamento(rs.getString(4));
                ca.setCliente(rs.getString(5));
                ca.setValor(rs.getFloat(6));
                ca.setCusto(rs.getFloat(7));
                ca.setDevolucao(rs.getFloat(8));
                ca.setFrete(rs.getFloat(9));
                ca.setDesconto(rs.getFloat(10));
                ca.setValorFinal(rs.getFloat(11));
                ca.setData(Time.formataDataBR(rs.getString(12)));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public void atualizaVenda(VendaBEAN c) {
        String sql = "update venda set venTime = '" + c.getHora() + "' , venValor = " + c.getValor() + " , venPagamento = '" + c.getPagamento() + "' "
                + ", venStatus = 'fechada', venQRcode = '" + c.getQRcode() + "', venCusto = " + c.getCusto() + ", venDesconto = " + c.getDesconto() + ", venFrete = " + c.getFrete() + ", venValorFin = " + c.getValorFin() + "  where venCodigo = " + c.getCodigo() + " ;";
        System.out.println(sql);
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

    public boolean isPagamentoUtlizado(int pagamento, int caixa) {
        int cod = 0;
        String sql = "select venCodigo from venda where venPagamento= '" + pagamento + "' and ven_caiCodigo = " + caixa + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cod = rs.getInt(1);

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (cod == 0) {
            return false;
        } else {
            return true;
        }
    }

    public int getVenda(int codigo, int caixa) {
        int cod = 0;
        String sql = "select venCodigo from venda where venCodio = " + codigo + " and venStatus = 'aberta' and ven_caiCodigo = " + caixa + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cod = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //retorna 0 se não tiver nenhuma venda naquela mesa
        return cod;
    }

    public void excluir(int venda) {
        String sql = "delete from venda where venCodigo = " + venda + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public float getValorVenda(int venda) {
        float total = 0;
        String sql = "select round(sum(pedQTD*proPreco),2) \n"
                + "from\n"
                + " caixa join venda join pedido join produto \n"
                + "	where\n"
                + "    caiCodigo = ven_caiCodigo and ped_venCodigo = venCodigo \n"
                + "    and ped_proCodigo = proCodigo and caiStatus = 'aberto'and venStatus = 'aberta' and ped_excCodigo is null"
                + " and venCodigo = " + venda + " group by venCodigo;";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getFloat(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return total;
    }

    public ArrayList<VendaBEAN> listarVendasAbertass(int caixa) {
        ArrayList<VendaBEAN> vendas = new ArrayList<VendaBEAN>();
        String sql = "select * from venda where venStatus = 'aberta' and ven_caiCodigo = " + caixa + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VendaBEAN v = new VendaBEAN();
                v.setCodigo(rs.getInt(1));
                v.setQRcode(rs.getBytes(2));
                v.setHora(rs.getString(3));
                v.setValor(rs.getFloat(4));
                v.setCusto(rs.getFloat(5));
                v.setStatus(rs.getString(6));
                v.setPagamento(rs.getString(7));
                v.setDesconto(rs.getFloat(8));
                v.setFrete(rs.getFloat(9));
                v.setCaixa(rs.getInt(10));
                vendas.add(v);

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vendas;
    }

    public ArrayList<VendaBEAN> listarVendasFechadas(int caixa) {
        ArrayList<VendaBEAN> vendas = new ArrayList<VendaBEAN>();
        String sql = "select * from venda where venStatus = 'fechada' and ven_caiCodigo = " + caixa + ";";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VendaBEAN v = new VendaBEAN();
                v.setCodigo(rs.getInt(1));
                v.setQRcode(rs.getBytes(2));
                v.setHora(rs.getString(3));
                v.setValor(rs.getFloat(4));
                v.setCusto(rs.getFloat(5));
                v.setStatus(rs.getString(6));
                v.setPagamento(rs.getString(7));
                v.setDesconto(rs.getFloat(8));
                v.setFrete(rs.getFloat(9));
                v.setCaixa(rs.getInt(10));
                vendas.add(v);

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vendas;
    }

    public float getTotalVendido(int caixa) {
        float total = 0;
        String sql = "select COALESCE(sum(venValor),0)  "
                + "from venda where venStatus = 'fechada' and ven_caiCodigo = " + caixa + " group by ven_caiCodigo;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getFloat(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return total;
    }

    public ArrayList<ProdutosGravados> listarProdutosVendidosCaixa(int caixa) {
        ArrayList<ProdutosGravados> c = new ArrayList<ProdutosGravados>();

        String sql = "SELECT  proCodigo, proNome,sum(pedQTD) as unidades ,proPreco from \n"
                + "produto join pedido join venda where venCodigo = ped_venCodigo and ped_proCodigo = proCodigo and venStatus = 'fechada' and ped_excCodigo is null and\n"
                + "ven_caiCodigo = " + caixa + " group by proCodigo;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProdutosGravados ca = new ProdutosGravados();

                ca.setCodProduto(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setQuantidade(rs.getFloat(3));
                ca.setValorFinal(rs.getFloat(4));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public String isVendasAbertas(int caixa) {
        int total = 0;
        String sql = "SELECT count(venCodigo) FROM venda where ven_caiCodigo = " + caixa + " and venStatus = 'aberta';";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return total + "";
    }

}
