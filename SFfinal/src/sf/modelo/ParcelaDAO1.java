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
public class ParcelaDAO1 {

    private boolean DESPESA = true;
    private boolean RECEITA = false;

    private Connection con;

    public ParcelaDAO1() {
        this.con = ConnectionFactory.getConnection();

    }

    public void adicionaParcelaReceita(ParcelaBEAN parcelaR) {
        String sql = "insert into parcela (par_recCod,parValor,parData,parParcelaPaga) values (?,?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, parcelaR.getPar_recCod());
            stmt.setDouble(2, parcelaR.getParValor());
            stmt.setDate(3, parcelaR.getParData());
            stmt.setBoolean(4, parcelaR.isParParcelaPaga());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void adicionaParcelaReceitaFixa(ParcelaBEAN parcelaR) {
        String sql = "insert into fixo (fix_recCod,fixPago,fixData,fixValor) values (?,?,?,?);";
        try {

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, parcelaR.getPar_recCod());
            stmt.setBoolean(2, parcelaR.isParParcelaPaga());
            stmt.setDate(3, parcelaR.getParData());
            stmt.setDouble(4, parcelaR.getParValor());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void adicionaParcelaDespesa(ParcelaBEAN parcelaD) {
        String sql = "insert into parcela (par_desCod,parValor,parData,parParcelaPaga) values (?,?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, parcelaD.getPar_desCod());
            stmt.setDouble(2, parcelaD.getParValor());
            stmt.setDate(3, parcelaD.getParData());
            stmt.setBoolean(4, parcelaD.isParParcelaPaga());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void adicionaParcelaDespesaFixa(ParcelaBEAN parcelaD) {
        String sql = "insert into fixo (fix_desCod,fixPago,fixData,fixValor) values (?,?,?,?);";
        try {

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, parcelaD.getPar_desCod());
            stmt.setBoolean(2, parcelaD.isParParcelaPaga());
            stmt.setDate(3, parcelaD.getParData());
            stmt.setDouble(4, parcelaD.getParValor());

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

    public ArrayList<ParcelaBEAN> getParcela(int codigo, boolean tipo) {
        String sql = " ";

        if (tipo == DESPESA) {

            sql = "select * from parcela where par_desCod=?;";

        } else if (tipo == RECEITA) {

            sql = "select * from parcela where par_recCod=?";

        }

        ArrayList<ParcelaBEAN> p = new ArrayList();

        try {

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);

            ResultSet rs = stmt.executeQuery();

            rs.first();

            ParcelaBEAN p1 = new ParcelaBEAN();
            p1.setParCod(rs.getInt("parCod"));
            p1.setPar_recCod(rs.getInt("par_recCod"));
            p1.setPar_desCod(rs.getInt("par_desCod"));
            p1.setParData(rs.getDate("parData"));
            p1.setParParcelaPaga(rs.getBoolean("parParcelaPaga"));
            p1.setParValor(rs.getDouble("parValor"));
            p.add(p1);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
        return p;
    }
    
    public void atualizaStatusParcela(int codigo){
        String sql = "update parcela set parParcelaPaga=1 where parCod=?;";
        try{
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);
            
            stmt.execute();
            stmt.close();
        }
        catch (Exception e) {

            throw new RuntimeException(e);

    }
    }

    public ArrayList<ParcelaBEAN> getParcelaFixa() {
        String sql = "select * from fixo;";
        ArrayList<ParcelaBEAN> p = new ArrayList();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ParcelaBEAN p1 = new ParcelaBEAN();
                p1.setParCod(rs.getInt("fixCod"));
                p1.setPar_recCod(rs.getInt("fix_recCod"));
                p1.setPar_desCod(rs.getInt("fix_desCod"));
                p1.setParData(rs.getDate("fixData"));
                p1.setParParcelaPaga(rs.getBoolean("fixPago"));
                p1.setParValor(rs.getDouble("fixValor"));
                p.add(p1);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    public ArrayList<ParcelaBEAN> getParcela(int codigo) {
        String sql = "select * from parcela where parParcelaPaga=?";

        ArrayList<ParcelaBEAN> p = new ArrayList();

        try {

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                ParcelaBEAN p1 = new ParcelaBEAN();
                p1.setParCod(rs.getInt("parCod"));
                p1.setPar_recCod(rs.getInt("par_recCod"));
                p1.setPar_desCod(rs.getInt("par_desCod"));
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
