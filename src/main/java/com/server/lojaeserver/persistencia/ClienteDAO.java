/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.ClienteBEAN;
import com.server.lojaserver.beans.ClienteBEAN;
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
public class ClienteDAO {

    private Connection connection;

    public ClienteDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public int adicionar(ClienteBEAN cliente) {
        int lastId = 0;
        String sql = "INSERT INTO cliente (cliNome, cliEmail, cliProfissao, cliDataNascimento,"
                + " cliDataCadastro, cliCPF, cliTelefone, cliFoto, cliSenha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getProfissao());
            stmt.setString(4, cliente.getNascimento());
            stmt.setString(5, cliente.getData_cadasto());
            stmt.setString(6, cliente.getCpf());
            stmt.setString(7, cliente.getTelefone());
            stmt.setBytes(8, cliente.getFoto());
            stmt.setString(9, cliente.getSenha());
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

    public void editar(ClienteBEAN cliente) {
        String sql = "update cliente set cliNome = ? , cliEmail = ? , cliProfissao = ? , cliDataNascimento = ? ,"
                + " cliDataCadastro = ?, cliCPF = ?,  cliTelefone = ?, cliTelefone = ?, cliFoto = ?, cliSenha = ? where cliCodigo = " + cliente.getCodigo() + ";";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getProfissao());
            stmt.setString(4, cliente.getNascimento());
            stmt.setString(5, cliente.getData_cadasto());
            stmt.setString(6, cliente.getCpf());
            stmt.setString(7, cliente.getTelefone());
            stmt.setBytes(8, cliente.getFoto());
            stmt.setString(9, cliente.getSenha());
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<ClienteBEAN> listarALl() {
        ArrayList<ClienteBEAN> c = new ArrayList<>();

        String sql = " select * from cliente ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClienteBEAN ca = new ClienteBEAN();
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setEmail(rs.getString(3));
                ca.setProfissao(rs.getString(4));
                ca.setNascimento(rs.getDate(5) + "");
                ca.setData_cadasto(rs.getDate(6) + "");
                ca.setCpf(rs.getString(7));
                ca.setTelefone(rs.getString(8));
                ca.setFoto(rs.getBytes(9));
                ca.setSenha(rs.getString(10));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ArrayList<ClienteBEAN> listarPorPesquisa(String nome) {
        ArrayList<ClienteBEAN> c = new ArrayList<>();

        String sql = " select * from cliente where cliNome like '" + nome + "%' ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ClienteBEAN ca = new ClienteBEAN();
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setEmail(rs.getString(3));
                ca.setProfissao(rs.getString(4));
                ca.setNascimento(rs.getDate(5) + "");
                ca.setData_cadasto(rs.getDate(6) + "");
                ca.setCpf(rs.getString(7));
                ca.setTelefone(rs.getString(8));
                ca.setFoto(rs.getBytes(9));
                ca.setSenha(rs.getString(10));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ClienteBEAN listarPorEmail(String email) {
        ClienteBEAN ca = new ClienteBEAN();
        ca.setCodigo(0);

        String sql = " select * from cliente where cliEmail = '" + email + "' ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setEmail(rs.getString(3));
                ca.setProfissao(rs.getString(4));
                ca.setNascimento(rs.getDate(5) + "");
                ca.setData_cadasto(rs.getDate(6) + "");
                ca.setCpf(rs.getString(7));
                ca.setTelefone(rs.getString(8));
                ca.setFoto(rs.getBytes(9));
                ca.setSenha(rs.getString(10));

            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ca;
    }
}
