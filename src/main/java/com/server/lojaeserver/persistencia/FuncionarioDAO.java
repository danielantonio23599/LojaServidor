/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.FuncionarioBEAN;
import com.server.lojaserver.beans.SharedPreferencesBEAN;
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
public class FuncionarioDAO {

    private Connection connection;

    public FuncionarioDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public int adicionar(FuncionarioBEAN c) {
        int i = 0;
        String sql = "INSERT INTO funcionario (funDataNascimento, funTelefone,"
                + " funNome,funEmail, funCPF, funRG, funSenha,funLogradouro,"
                + "funNumero, funBairro, funComplemento, funCidade, funUF, funCEP)"
                + " VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?,?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getDataNacimento() + "");
            stmt.setString(2, c.getTelefone());
            stmt.setString(3, c.getNome());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getCPF());
            stmt.setString(6, c.getRG());
            stmt.setString(7, c.getSenha());
            stmt.setString(8, c.getLogradouro());
            stmt.setString(9, c.getNumero());
            stmt.setString(10, c.getBairro());
            stmt.setString(11, c.getComplemento());
            stmt.setString(12, c.getCidade());
            stmt.setString(13, c.getUf());
            stmt.setString(14, c.getCep());
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

    public int adicionarAdm(FuncionarioBEAN c) {
        int i = 0;
        String sql = "INSERT INTO funcionario ("
                + "funEmail,funSenha,funLogradouro,"
                + "funNumero, funBairro, funComplemento, funCidade, funUF, funCEP)"
                + " VALUES (?,?, ?, ?, ?, ?, ?,?,?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getEmail());
            stmt.setString(2, c.getSenha());
            stmt.setString(3, c.getLogradouro());
            stmt.setString(4, c.getNumero());
            stmt.setString(5, c.getBairro());
            stmt.setString(6, c.getComplemento());
            stmt.setString(7, c.getCidade());
            stmt.setString(8, c.getUf());
            stmt.setString(9, c.getCep());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                i = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }

    public ArrayList<FuncionarioBEAN> listarALl(int emp) {
        ArrayList<FuncionarioBEAN> c = new ArrayList<>();

        String sql = " select funCodigo,funDataNascimento ,funTelefone ,funNome,funFoto,funEmail,funCPF,funRG,funSenha"
                + " ,funLogradouro,funNumero ,funBairro ,funComplemento ,funCidade ,funCEP,adm_CarCodigo, COALESCE(admDataSaida,'') as saida from funcionario join admicao"
                + " where adm_funCodigo = funCodigo and adm_empCodigo = " + emp + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FuncionarioBEAN ca = new FuncionarioBEAN();
                ca.setCodigo(rs.getInt(1));
                ca.setDataNacimento(rs.getDate(2) + "");
                ca.setTelefone(rs.getString(3));
                ca.setNome(rs.getString(4));
                //foto
                ca.setEmail(rs.getString(6));
                ca.setCPF(rs.getString(7));
                ca.setRG(rs.getString(8));
                ca.setSenha(rs.getString(9));
                ca.setLogradouro(rs.getString(10));
                ca.setNumero(rs.getString(11));
                ca.setBairro(rs.getString(12));
                ca.setComplemento(rs.getString(13));
                ca.setCidade(rs.getString(14));
                ca.setUf(rs.getString(15));
                ca.setCep(rs.getString(16));
                int i = rs.getInt("adm_carCodigo");
                if (i > 0) {
                    ca.setStatus("Admitido");

                } else {
                    ca.setStatus("Aguardado Admição");
                }
                String j = rs.getString("saida");
                if (j.equals("")) {
                } else {
                    ca.setStatus("Demitido");
                }
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ArrayList<FuncionarioBEAN> listarALl() {
        ArrayList<FuncionarioBEAN> c = new ArrayList<>();

        String sql = "select * from funcionario;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FuncionarioBEAN ca = new FuncionarioBEAN();
                ca.setCodigo(rs.getInt(1));
                ca.setDataNacimento(rs.getDate(2) + "");
                ca.setTelefone(rs.getString(3));
                ca.setNome(rs.getString(4));
                //foto
                ca.setEmail(rs.getString(6));
                ca.setCPF(rs.getString(7));
                ca.setRG(rs.getString(8));
                ca.setSenha(rs.getString(9));
                ca.setLogradouro(rs.getString(10));
                ca.setNumero(rs.getString(11));
                ca.setBairro(rs.getString(12));
                ca.setComplemento(rs.getString(13));
                ca.setCidade(rs.getString(14));
                ca.setUf(rs.getString(15));
                ca.setCep(rs.getString(16));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public int funCargo(int cargo) {
        int cont = 0;
        String sql = "select count(funCodigo) from funcionario where fun_carCodigo = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cargo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cont = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cont;
    }

    public Boolean numeroCartaoExistente(int codigo) {
        int cont = 0;
        String sql = "select count(funCodigo) from funcionario where funNunCartao = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cont = rs.getInt(1);
            }
            stmt.close();
            if (cont == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void editar(FuncionarioBEAN c) {
        String sql = "update funcionario set funDataNascimento = ? , funTelefone = ? ,"
                + " funNome = ? ,funEmail = ? , funCPF = ? , funRG = ? , funSenha = ? ,funLogradouro = ? ,"
                + "funNumero = ? , funBairro = ? , funComplemento = ? , funCidade = ? , funUF = ? , funCEP = ?, funFoto = ? "
                + " where funCodigo = " + c.getCodigo() + ";";
        System.out.println(sql);
        System.out.println("Data :" + c.getDataNacimento());

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, c.getDataNacimento() + "");
            stmt.setString(2, c.getTelefone());
            stmt.setString(3, c.getNome());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getCPF());
            stmt.setString(6, c.getRG());
            stmt.setString(7, c.getSenha());
            stmt.setString(8, c.getLogradouro());
            stmt.setString(9, c.getNumero());
            stmt.setString(10, c.getBairro());
            stmt.setString(11, c.getComplemento());
            stmt.setString(12, c.getCidade());
            stmt.setString(13, c.getUf());
            stmt.setString(14, c.getCep());
            stmt.setBytes(15, c.getFoto());
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public FuncionarioBEAN localizar(String email) {
        String sql = "select * from funcionario where funEmail = ?;";
        FuncionarioBEAN ca = new FuncionarioBEAN();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setCodigo(rs.getInt(1));
                ca.setDataNacimento(rs.getDate(2) + "");
                ca.setTelefone(rs.getString(3));
                ca.setNome(rs.getString(4));
                //foto
                ca.setEmail(rs.getString(6));
                ca.setCPF(rs.getString(7));
                ca.setRG(rs.getString(8));
                ca.setSenha(rs.getString(9));
                ca.setLogradouro(rs.getString(10));
                ca.setNumero(rs.getString(11));
                ca.setBairro(rs.getString(12));
                ca.setComplemento(rs.getString(13));
                ca.setCidade(rs.getString(14));
                ca.setUf(rs.getString(15));
                ca.setCep(rs.getString(16));
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ca;
    }

    public FuncionarioBEAN localizar(int codigo) {
        String sql = "select * from funcionario where funCodigo = ?;";
        FuncionarioBEAN ca = new FuncionarioBEAN();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setCodigo(rs.getInt(1));
                ca.setDataNacimento(rs.getDate(2) + "");
                ca.setTelefone(rs.getString(3));
                ca.setNome(rs.getString(4));
                //foto
                ca.setEmail(rs.getString(6));
                ca.setCPF(rs.getString(7));
                ca.setRG(rs.getString(8));
                ca.setSenha(rs.getString(9));
                ca.setLogradouro(rs.getString(10));
                ca.setNumero(rs.getString(11));
                ca.setBairro(rs.getString(12));
                ca.setComplemento(rs.getString(13));
                ca.setCidade(rs.getString(14));
                ca.setUf(rs.getString(15));
                ca.setCep(rs.getString(16));

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ca;
    }

    public Boolean isExiste(String email) {
        String sql = "select funCodigo from funcionario where funEmail = ?;";
        boolean retorno = false;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = true;

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }

    public int Login(String email, String senha, int emp) {
        String sql = "select funCodigo from funcionario join admicao where adm_funCodigo = funCodigo and funEmail = '" + email
                + "' and funSenha = '" + senha + "' and adm_empCodigo = " + emp + ";";
        System.out.println(sql);
        int codigo = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                codigo = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return codigo;
    }

    public int Login(String email, String senha) {
        String sql = "select funCodigo from funcionario where funEmail = '" + email
                + "' and funSenha = '" + senha + "' ;";
        System.out.println(sql);
        int codigo = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                codigo = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return codigo;
    }

    public void excluir(int cod) {
        String sql = "delete from funcionario where funCodigo = ? ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cod);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> buscar(String funcionario) {

        ArrayList<String> p = new ArrayList<>();
        String sql = "SELECT funEmail FROM funcionario ;";//WHERE funEmail LIKE '" + funcionario + "%';";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                p.add(rs.getString(1));
                // modelo.addElement(rs.getInt(1) + " : " + rs.getString(2) + String.format("%80s"," R$ " + rs.getFloat(3)+""));
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return p;

    }

    public SharedPreferencesBEAN listarSharedPreferences(int cod, int emp) {
        String sql = "select COALESCE(adm_carCodigo,0),funCodigo,funEmail,funNome,funSenha \n"
                + "from funcionario join admicao where funCodigo = adm_funCodigo and adm_empCodigo = ? and adm_funCodigo = ?;";
        SharedPreferencesBEAN ca = new SharedPreferencesBEAN();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, emp);
            stmt.setInt(2, cod);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setFunCargo(rs.getInt(1));
                ca.setFunCodigo(rs.getInt(2));
                ca.setFunEmail(rs.getString(3));
                ca.setFunNome(rs.getString(4));
                ca.setFunSenha(rs.getString(5));
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ca;
    }

}
