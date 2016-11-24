
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

/**
 *
 * @author Administrador
 */
public class ReceitaDespesaDAO {

    private Connection con;

    public ReceitaDespesaDAO() {
        this.con =  ConnectionFactory.getConnection();
    }

    public void adicionaReceita(ReceitaBEAN receita) {
        String sql = "insert into receita (recDesc,rec_catCod,recPago,recNrodeParcelas,recFixo) values (?,?,?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, receita.getRecDesc());
            stmt.setInt(2, receita.getRec_catCod());
            stmt.setBoolean(3, receita.getRecPago());
            stmt.setInt(4, receita.getRecNrodeParcelas());
            stmt.setBoolean(5, receita.isRecFixo());

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void adicionaDespesa(DespesaBEAN despesa) {
        String sql = "insert into despesa (desDesc,des_catCod,desPago,desNrodeParcelas,desFixo) values (?,?,?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, despesa.getDesDesc());
            stmt.setInt(2, despesa.getDes_catCod());
            stmt.setBoolean(3, despesa.getDesPago());
            stmt.setInt(4, despesa.getDesNrodeParcelas());
            stmt.setBoolean(5, despesa.isDesFixo());

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deletaReceita(int codigo) {
        String sql = "delete from receita where recCod=?;";

        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, codigo);

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void deletaDespesa(int codigo) {
        String sql = "delete from despesa where desCod=?;";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, codigo);

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ReceitaBEAN> getReceita() {
        String sql = "select * from receita;";
        ArrayList<ReceitaBEAN> r = new ArrayList();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReceitaBEAN r1 = new ReceitaBEAN();
                r1.setRecCod(rs.getInt("recCod"));
            
                
                if(!rs.getString("recDesc").equals(" ")){
                r1.setRecDesc(rs.getString("recDesc"));
                }
                r1.setRecPago(rs.getBoolean("recPago"));
                r1.setRecNrodeParcelas(rs.getInt("recNrodeParcelas"));
                r1.setRecFixo(rs.getBoolean("recFixo"));  
                r1.setRec_catCod(rs.getInt("rec_catCod"));
                r.add(r1);
            }
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return r;
    }
    
    
    public ArrayList<DespesaBEAN> getDespesa() {
        String sql = "select * from despesa;";
        ArrayList<DespesaBEAN> d = new ArrayList();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DespesaBEAN d1 = new DespesaBEAN();
                d1.setDesCod(rs.getInt("desCod"));
      
                
                if(!rs.getString("desDesc").equals(" ")){
                d1.setDesDesc(rs.getString("desDesc"));
                }
                d1.setDesPago(rs.getBoolean("desPago"));
                d1.setDesNrodeParcelas(rs.getInt("desNrodeParcelas"));
                d1.setDesFixo(rs.getBoolean("desFixo"));          
                d1.setDes_catCod(rs.getInt("des_catCod"));
                d.add(d1);
            }
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return d;
    }
    public void pagaDespesa(int codigo) {
        String sql = "update despesa set desPago=1 where desCod=?;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);

            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }
    
    public void pagaReceita(int codigo) {
        String sql = "update receita set recPago=1 where recCod=?;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);

            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }
}
