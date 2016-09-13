
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
        String sql = "insert into receita (recCod,recValor,recDesc,recData,rec_SubCod,recNroPago) values (?,?,?,?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, receita.getRecCod());
            stmt.setDouble(2, receita.getRecValor());
            stmt.setString(3, receita.getRecDesc());
            stmt.setDate(4, receita.getRecData());
            stmt.setInt(5, receita.getRec_SubCod());
            stmt.setInt(6, receita.getRecNroPago());
                    
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void adicionaDespesa(DespesaBEAN despesa) {
        String sql = "insert into despesa (desCod,desValor,desDesc,desData,des_SubCod,desNroPago) values (?,?,?,?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, despesa.getDesCod());
            stmt.setDouble(2, despesa.getDesValor());
            stmt.setString(3, despesa.getDesDesc());
            stmt.setDate(4, despesa.getDesData());
            stmt.setInt(5, despesa.getDes_SubCod());
            stmt.setInt(6, despesa.getDesNroPago());

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
                r1.setRecValor(rs.getDouble("recValor"));
                
                if(!rs.getString("recDesc").equals(" ")){
                r1.setRecDesc(rs.getString("recDesc"));
                }
                
                r1.setRecData(rs.getDate("recData"));
                r1.setRec_SubCod(rs.getInt("rec_subCod"));
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
                d1.setDesValor(rs.getDouble("desValor"));
                
                if(!rs.getString("desDesc").equals(" ")){
                d1.setDesDesc(rs.getString("desDesc"));
                }
                
                d1.setDesData(rs.getDate("desData"));
                d1.setDes_SubCod(rs.getInt("des_subCod"));
                d.add(d1);
            }
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return d;
    }
}
