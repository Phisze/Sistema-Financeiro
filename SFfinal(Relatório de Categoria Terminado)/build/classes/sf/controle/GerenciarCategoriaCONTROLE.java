/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sf.modelo.CategoriaBEAN;
import sf.modelo.CategoriaDAO;
import sf.modelo.ConnectionFactory;

/**
 *
 * @author Alunos
 */
public class GerenciarCategoriaCONTROLE {
    

    private CategoriaDAO cdao;
    private Connection con =  ConnectionFactory.getConnection(); 
    private int catcod;
    public ArrayList<CategoriaBEAN> retornaCategorias() {
        cdao = new CategoriaDAO();
        ArrayList<CategoriaBEAN> categorias = new ArrayList<>();
        categorias = cdao.getCategoria();
        return categorias;
        //  jList1.setListData(categorias.toArray());
    }
    
    
    public void getCatCodigo(int index){
    ArrayList<CategoriaBEAN> cate=new ArrayList<>();
    this.pegaCat(cate);
    catcod=cate.get(index).getCatCod();
  }
    
    public void deletaCategoria(){
    cdao.subCatDelete(catcod);
    cdao.deleta(catcod);   
    
    }
    
    public void atualizaCategoria(String nome){
    CategoriaDAO cd=new CategoriaDAO();
    cd.atualizaCat(nome, catcod);
    }
    
    public void pegaSubCat(ArrayList<CategoriaBEAN> a, int cod){
       
       CategoriaDAO cd=new CategoriaDAO();
       cd.pegaSubCat(a, cod);
    
    }
    
    
    public void pegaCat(ArrayList<CategoriaBEAN> a) {
       
        CategoriaDAO cd=new CategoriaDAO();
        cd.pegaCat(a);

    }


    public void inserirCategoria(String nome, String desc) {
        CategoriaBEAN c=new CategoriaBEAN();
        c.setCatNome(nome);
        c.setCatDescricao(desc);
        cdao=new CategoriaDAO();
        cdao.adiciona(c);

    }
    
    public void inserirSubCategoria(String nome, String desc){
        CategoriaBEAN c=new CategoriaBEAN();
        c.setCatNome(nome);
        c.setCatDescricao(desc);
        c.setCat_scaCod(this.catcod);
        cdao=new CategoriaDAO();
        cdao.adicionaSubCat(c);
        
    }
    


}
