/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Administrador
 */
public class ParcelaDAO {

    private boolean DESPESA = true;
    private boolean RECEITA = false;

    private Connection con;

    public ParcelaDAO() {
        this.con = ConnectionFactory.getConnection();

    }

    public void adicionaParcelaReceita(Parcela parcelaR) {
        String sql = "insert into parcela (par_recCod,parValor,parData,parParcelaPaga) values (?,?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, parcelaR.getReceita().getCategoria().getCatCod());
            stmt.setDouble(2, parcelaR.getParValor());
            java.sql.Date dataSql=new java.sql.Date(parcelaR.getParData().getTime());
            stmt.setDate(3, dataSql);
            stmt.setBoolean(4, parcelaR.getParParcelaPaga());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

 

    public void adicionaParcelaDespesa(Parcela parcelaD) {
        String sql = "insert into parcela (par_desCod,parValor,parData,parParcelaPaga) values (?,?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, parcelaD.getDespesa().getCategoria().getCatCod());
            stmt.setDouble(2, parcelaD.getParValor());
            java.sql.Date dataSql=new java.sql.Date(parcelaD.getParData().getTime());
            stmt.setDate(3, dataSql);
            stmt.setBoolean(4, parcelaD.getParParcelaPaga());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    

    public void deletaParcela(int Codigo) {
        String sql = "delete from parcela where parCod=?;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Codigo);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deletaParcelaDespesa(int codigo) {
        String sql = "delete from parcela where par_desCod=?;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void deletaParcelaReceita(int Codigo) {
        String sql = "delete from parcela where par_recCod=?;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Codigo);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Parcela> getParcela(int codigo, boolean tipo) {
        
        String sql = " ";

        if (tipo == DESPESA) {

            sql = "select * from parcela where par_desCod=?;";

        } else if (tipo == RECEITA) {

            sql = "select * from parcela where par_recCod=?;";

        }

        ArrayList<Parcela> p = new ArrayList<Parcela>();

        try {

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);

            ResultSet rs = stmt.executeQuery();

            rs.first();
            if (rs.first()) {
                Parcela p1 = new Parcela();
                p1.setParCod(rs.getInt("parCod"));
                Despesa d1=new Despesa();
                d1.setDesCod(rs.getInt("par_desCod"));
                Receita r1=new Receita();
                r1.setRecCod(rs.getInt("par_recCod"));
                p1.setReceita(r1);
                p1.setDespesa(d1);
                p1.setParData(rs.getDate("parData"));
                p1.setParParcelaPaga(rs.getBoolean("parParcelaPaga"));
                p1.setParValor(rs.getDouble("parValor"));
                p.add(p1);
               
            }
        } catch (Exception e) {

            throw new RuntimeException(e);

        }
  
        return p;
    }



    public void atualizaStatusParcela(int codigo) {
        String sql = "update parcela set parParcelaPaga=1 where parCod=?;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);

            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }

    public ArrayList<Parcela> getParcela(int codigo) {
        String sql = "select * from parcela where parParcelaPaga=?";

        ArrayList<Parcela> p = new ArrayList();

        try {

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Parcela p1 = new Parcela();
                p1.setParCod(rs.getInt("parCod"));
                Despesa d1=new Despesa();
                Receita r1=new Receita();
                d1.setDesCod(rs.getInt("par_desCod"));
                r1.setRecCod(rs.getInt("par-recCod"));
                p1.setDespesa(d1);
                p1.setReceita(r1);              
                p1.setParData(rs.getDate("parData"));
                p1.setParParcelaPaga(rs.getBoolean("parParcelaPaga"));
                p1.setParValor(rs.getDouble("parValor"));
                p.add(p1);
            }
        } catch (Exception e) {

            throw new RuntimeException(e);

        }
        return p;
    }
}
