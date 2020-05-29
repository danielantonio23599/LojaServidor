/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.lojaeserver.persistencia;

import com.server.lojaserver.beans.CargoBEAN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class CargoDAO {

    private Connection connection;

    public CargoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public boolean adicionar(CargoBEAN c) {
        String sql = "insert into cargo(carNome,carAtribuicao,carRequisitos,carPermicao) values (?,?,?,?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getAtribuicao());
            stmt.setString(3, c.getRequisito());
            stmt.setString(4, c.getPermicao());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<CargoBEAN> listarALl() {
        ArrayList<CargoBEAN> c = new ArrayList<CargoBEAN>();

        String sql = "select * from cargo;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CargoBEAN ca = new CargoBEAN();
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setAtribuicao(rs.getString(3));
                ca.setRequisito(rs.getString(4));
                ca.setPermicao(rs.getString(5));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return c;
    }

    public void editar(CargoBEAN c) {
        String sql = "update cargo set carNome = ? , carAtribuicao = ? , carRequisitos = ? where carNome = '" + c.getNome() + "';";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getAtribuicao());
            stmt.setString(3, c.getRequisito());
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<CargoBEAN> localizar(int i) {
        String sql = "select * from cargo where carCodigo = ?;";
        CargoBEAN ca = new CargoBEAN();
        ArrayList<CargoBEAN> k = new ArrayList<CargoBEAN>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, i);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setAtribuicao(rs.getString(3));
                ca.setRequisito(rs.getString(4));
                ca.setPermicao(rs.getString(5));
                k.add(ca);
            }
            stmt.close();
            return k;
        } catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    public void excluir(String cod) {
        String sql = "delete from cargo where carNome = ? ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cod);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CargoBEAN pegaCodigo(String cargo) {
        CargoBEAN c = new CargoBEAN();
        String sql = "select carCodigo from cargo where carNome = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cargo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                c.setCodigo(rs.getInt(1));
            }
            stmt.close();
            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CargoBEAN localizarPorNome(String cargo) {
        CargoBEAN c = new CargoBEAN();
        String sql = "select * from cargo where carNome = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cargo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                c.setCodigo(rs.getInt(1));
                c.setNome(rs.getString(2));
                c.setAtribuicao(rs.getString(3));
                c.setRequisito(rs.getString(4));
                c.setPermicao(rs.getString(5));
            }
            stmt.close();
            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CargoBEAN listarPorFuncionario(int funcionario) {
        CargoBEAN ca = new CargoBEAN();
        String sql = "select carNome from funcionario join cargo where carCodigo = fun_carCodigo and funCodigo = " + funcionario + " ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setAtribuicao(rs.getString(3));
                ca.setRequisito(rs.getString(4));
                ca.setPermicao(rs.getString(5));

            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ca;
    }
}
