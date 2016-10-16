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

    private Connection con;

    public ParcelaDAO() {
        this.con =  ConnectionFactory.getConnection();
        
    }

    public void adicionaParcelaReceita(ParcelaBEAN parcelaR) {
        String sql = "insert into parcela (par_recCod,parNroParcela,parPeriodoParcela) values (?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, parcelaR.getPar_recCod());
            stmt.setInt(2, parcelaR.getParNroParcela());
            stmt.setString(3, parcelaR.getParPeriodoParcela());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
    public void adicionaParcelaReceitaFixa(ParcelaBEAN parcelaR) {
        String sql = "insert into parcela (par_recCod,parPeriodoParcela) values (?,?);";
        try {
           
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, parcelaR.getPar_recCod());
            stmt.setString(2, parcelaR.getParPeriodoParcela());
            
            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    

    public void adicionaParcelaDespesa(ParcelaBEAN parcelaD) {
        String sql = "insert into parcela (par_desCod,parNroParcela,parPeriodoParcela) values (?,?,?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, parcelaD.getPar_desCod());
            stmt.setInt(2, parcelaD.getParNroParcela());
            stmt.setString(3, parcelaD.getParPeriodoParcela());


            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
    public void adicionaParcelaDespesaFixa(ParcelaBEAN parcelaD) {
        String sql = "insert into parcela (par_desCod,parPeriodoParcela) values (?,?);";
        try {
            
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, parcelaD.getPar_desCod());
            stmt.setString(2, parcelaD.getParPeriodoParcela());
                        

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
    
    public ArrayList<ParcelaBEAN> getParcela(){
    String sql="select * from parcela;";
    ArrayList<ParcelaBEAN> p=new ArrayList();
    try{
    PreparedStatement stmt=con.prepareStatement(sql);
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
        ParcelaBEAN p1= new ParcelaBEAN();
        p1.setParCod(rs.getInt("parCod"));
        p1.setParNroParcela(rs.getInt("parNroParcela"));
        p1.setParPeriodoParcela(rs.getString("parPeriodoParcela"));
        p1.setPar_recCod(rs.getInt("par_recCod"));
        p1.setPar_desCod(rs.getInt("par_desCod"));
        p.add(p1);
    
    }
    
    
    }catch(Exception e){throw new RuntimeException(e);}
    return p;
    }
    
}
