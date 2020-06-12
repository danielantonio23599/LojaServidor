/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.CaixaBEAN;
import com.server.lojaserver.beans.EmpresaBEAN;
import com.server.lojaserver.beans.FuncionarioBEAN;
import com.server.lojaserver.beans.SharedPreferencesEmpresaBEAN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Daniel
 */
public class EmpresaDAO {

    private Connection connection;
    PreparedStatement stmt;

    public EmpresaDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public int adcionar(EmpresaBEAN c) {
        int i = 0;
        String sql = "INSERT INTO empresa (empRazaoSocial,empNomeFantazia,empCNPJ,empInsEstadual,"
                + "empEmail,empTelefone,empSenha,empDataCadastro,empExpiraLicenca,empLogradouro,"
                + "empNumero,empBairro,empComplemento,empCidade,empUF,empCEP,empLogo,empDataFundacao,empNomeResp,empTipo)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, c.getRazaoSocial());
            stmt.setString(2, c.getFantazia());
            stmt.setString(3, c.getCnpj());
            stmt.setString(4, c.getInscEstad());
            stmt.setString(5, c.getEmail());
            stmt.setString(6, c.getTelefone());
            stmt.setString(7, c.getSenha());
            stmt.setString(8, c.getDataCadastro());
            stmt.setString(9, c.getExpriraLicenca());
            stmt.setString(10, c.getLogradouro());
            stmt.setString(11, c.getNumero());
            stmt.setString(12, c.getBairro());
            stmt.setString(13, c.getComplemento());
            stmt.setString(14, c.getCidade());
            stmt.setString(15, c.getUf());
            stmt.setString(16, c.getCep());
            stmt.setBytes(17, c.getLogo());
            stmt.setString(18, c.getDataFundacao());
            stmt.setString(19, c.getNomeResp());
            stmt.setString(20, c.getTipo());
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

    public void editar(EmpresaBEAN c) {
        String sql = "update empresa set empRazaoSocial = ? ,empNomeFantazia = ? ,empCNPJ = ? ,empInsEstadual = ? ,"
                + "empEmail = ? ,empTelefone = ? ,empSenha = ? ,empDataCadastro = ? ,empExpiraLicenca = ? ,empLogradouro = ? ,"
                + "empNumero = ? ,empBairro = ? ,empComplemento = ? ,empCidade = ? ,empUF = ? ,empCEP = ? ,empLogo = ? ,"
                + "empDataFundacao = ?,empNomeResp = ?,empTipo = ?"
                + " where empCodigo = " + c.getCodigo() + ";";
        System.out.println(sql);

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, c.getRazaoSocial());
            stmt.setString(2, c.getFantazia());
            stmt.setString(3, c.getCnpj());
            stmt.setString(4, c.getInscEstad());
            stmt.setString(5, c.getEmail());
            stmt.setString(6, c.getTelefone());
            stmt.setString(7, c.getSenha());
            stmt.setString(8, c.getDataCadastro());
            stmt.setString(9, c.getExpriraLicenca());
            stmt.setString(10, c.getLogradouro());
            stmt.setString(11, c.getNumero());
            stmt.setString(12, c.getBairro());
            stmt.setString(13, c.getComplemento());
            stmt.setString(14, c.getCidade());
            stmt.setString(15, c.getUf());
            stmt.setString(16, c.getCep());
            stmt.setBytes(17, c.getLogo());
            stmt.setString(18, c.getDataFundacao());
            stmt.setString(19, c.getNomeResp());
            stmt.setString(20, c.getTipo());
            stmt.executeUpdate();
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public EmpresaBEAN localizar(String email) {

        EmpresaBEAN e = new EmpresaBEAN();

        String sql = "select * from empresa where empEmail = " + email + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                e.setCodigo(rs.getInt(1));
                e.setRazaoSocial(rs.getString(2));;
                e.setFantazia(rs.getString(3));
                e.setCnpj(rs.getString(4));
                e.setInscEstad(rs.getString(5));
                e.setEmail(rs.getString(6));
                e.setTelefone(rs.getString(7));
                e.setSenha(rs.getString(8));
                e.setDataCadastro(rs.getString(9));
                e.setExpriraLicenca(rs.getString(10));
                e.setLogradouro(rs.getString(11));
                e.setNumero(rs.getString(12));
                e.setBairro(rs.getString(13));
                e.setComplemento(rs.getString(14));
                e.setCidade(rs.getString(15));
                e.setUf(rs.getString(16));
                e.setCep(rs.getString(17));
                e.setLogo(rs.getBytes(18));
                e.setDataFundacao(rs.getString(19));
                e.setTipo(rs.getString(20));

            }
            stmt.close();

        } catch (SQLException c) {
            System.out.println(c.getMessage());
        }
        return e;
    }

    public EmpresaBEAN listar(int cod) {
        EmpresaBEAN e = new EmpresaBEAN();
        String sql = "select empCodigo,empRazaoSocial,empNomeFantazia,empCNPJ,empInsEstadual,"
                + "empEmail,empTelefone,empSenha,empDataCadastro,empExpiraLicenca,empLogradouro,"
                + "empNumero,empBairro,empComplemento,empCidade,empUF,empCEP,empLogo,empDataFundacao,empNomeResp,empTipo from empresa where empCodigo = " + cod + " ;";
        System.out.println(sql);
        try {
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                e.setCodigo(rs.getInt(1));
                e.setRazaoSocial(rs.getString(2));
                e.setFantazia(rs.getString(3));
                e.setCnpj(rs.getString(4));
                e.setInscEstad(rs.getString(5));
                e.setEmail(rs.getString(6));
                e.setTelefone(rs.getString(7));
                e.setSenha(rs.getString(8));
                e.setDataCadastro(rs.getString(9));
                e.setExpriraLicenca(rs.getString(10));
                e.setLogradouro(rs.getString(11));
                e.setNumero(rs.getString(12));
                e.setBairro(rs.getString(13));
                e.setComplemento(rs.getString(14));
                e.setCidade(rs.getString(15));
                e.setUf(rs.getString(16));
                e.setCep(rs.getString(17));
                if (rs.getBytes(18) != null) {
                    e.setLogo(rs.getBytes(18));
                }
                e.setDataFundacao(rs.getString(19));
                e.setNomeResp(rs.getString(20));
                e.setTipo(rs.getString(21));

            }
            stmt.close();

        } catch (SQLException c) {
            System.out.println(c.getSQLState());
        }
        return e;
    }

    public SharedPreferencesEmpresaBEAN localizar(int cod) {
        SharedPreferencesEmpresaBEAN s = new SharedPreferencesEmpresaBEAN();
        s.setEmpCodigo(0);
        String sql = "select empCodigo,empEmail, empSenha,empLogo,empNomeFantazia from empresa where empCodigo = " + cod + ";";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                s.setEmpCodigo(rs.getInt(1));
                s.setEmpEmail(rs.getString(2));
                s.setEmpSenha(rs.getString(3));
                s.setEmpLogo(rs.getBytes(4));
                s.setEmpFantazia(rs.getString(5));
            }
            stmt.close();
            return s;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return s;
        }

    }

    public int login(String email, String senha) {
        int cod = 0;
        String sql = "select empCodigo from empresa where empEmail = '" + email + "' and empSenha = '" + senha + "';";
        System.out.println(sql);
        ResultSet rs = null;
        try {
            try {
                 stmt = (PreparedStatement) connection.prepareStatement(sql);
                rs = stmt.executeQuery();
            } catch (Exception e) {
                System.out.println("erro login :" + e.getMessage());
            }

            while (rs.next()) {
                cod = rs.getInt(1);
            }
            this.stmt.close();
            return cod;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return cod;
        }

    }
}
