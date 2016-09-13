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

public class CategoriaDAO {

    // a conexão com o banco de dados
    private Connection con;

    public CategoriaDAO() {
        this.con=ConnectionFactory.getConnection();
    }
    
    
    public void subCatDelete(int codigo){
        
        String sql="delete from subcategoria where cat_scaCod=?;";
        
        try {
            PreparedStatement stmt=con.prepareStatement(sql);
            stmt.setInt(1, codigo);
            
            stmt.execute();
            stmt.close();
                       
            
        } catch (Exception e) {
        throw new RuntimeException(e);
        }
        
        
    
    
    }
    
    public void atualizaCat(CategoriaBEAN cat) {
        String sql = "update empresa.categoria set catNome=? where catCod=?;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cat.getCatNome());
            stmt.setInt(2, cat.getCatCod());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    

    public void adiciona(CategoriaBEAN contato) {
        String sql = "insert into categoria (catNome,catDesc) values (?,?)";
        
        try {
            // prepared statement para inserção
            PreparedStatement stmt = con.prepareStatement(sql);

            // seta os valores
            stmt.setString(1, contato.getCatNome());
            stmt.setString(2, contato.getCatDescricao());
            // executa
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleta(int codigo) {
        String sql = "delete from categoria where catCod = ?;";

        try {
            // prepared statement para inserção
            PreparedStatement stmt = con.prepareStatement(sql);

            // seta os valores
            stmt.setInt(1, codigo);

            // executa
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList getCategoria() {
        String sql = "select * from categoria;";
        ArrayList<String> a = new ArrayList();
 
        try {
            // prepared statement para inserção
            PreparedStatement stmt = con.prepareStatement(sql);

            // executa
            ResultSet rs = stmt.executeQuery();
            //joga resultado da consulta no ArrayList
            while (rs.next()) {
         
                String x = rs.getString("catCod") + ", " + rs.getString("catNome") + "," + rs.getString("catDesc");
                a.add(x);
               
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return a;
    }
    
    public void pegaCat(ArrayList<CategoriaBEAN> a) {
       
        String sql = "select * from categoria;";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CategoriaBEAN x1 = new CategoriaBEAN();
                x1.setCatCod(rs.getInt("catCod"));
                x1.setCatNome(rs.getString("catNome"));
                x1.setCatDescricao(rs.getString("catDesc"));
                a.add(x1);

            }
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
      public ArrayList<CategoriaBEAN> pegaCat() {
        ArrayList<CategoriaBEAN> a = new ArrayList<CategoriaBEAN>();
        String sql = "select * from categoria;";
       
        try {
            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CategoriaBEAN x1 = new CategoriaBEAN();
                x1.setCatCod(rs.getInt("catCod"));
                x1.setCatNome(rs.getString("catNome"));
                x1.setCatDescricao(rs.getString("catDesc"));
                a.add(x1);

            }
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
 
        return a;
    }
    
}
