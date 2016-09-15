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
import sf.modelo.Categoria;
import sf.modelo.CategoriaDAO;
import sf.modelo.ConnectionFactory;

/**
 *
 * @author Alunos
 */
public class GerenciarCategoriaCONTROLE {

    private CategoriaDAO cdao;
    private Connection con = ConnectionFactory.getConnection();
    private int catcod;
    //   private int categoriaSelecionada;

    public ArrayList<Categoria> retornaCategorias() {
        cdao = new CategoriaDAO();
        ArrayList<Categoria> categorias = new ArrayList<>();
        categorias = cdao.getCategoria();
        return categorias;
        //  jList1.setListData(categorias.toArray());
    }

    public int selecionaCategoria(int index) {

        cdao=new CategoriaDAO();
        ArrayList<Categoria> cb=cdao.pegaCat();
        return cb.get(index).getCatCod();
   
    }

    public void getCatCodigo(int index) {
        ArrayList<Categoria> cate = new ArrayList<>();
        this.pegaCat(cate);
        catcod = cate.get(index).getCatCod();
    }

    public void deletaCategoria(int index) {
       int CODIGO = selecionaCategoria(index);

       cdao.deleta(CODIGO);

    }

    public void atualizaCategoria(Categoria cat) {
        CategoriaDAO cd = new CategoriaDAO();
        cd.atualizaCat(cat);
    }

    public void pegaCat(ArrayList<Categoria> a) {
        CategoriaDAO cd = new CategoriaDAO();
        cd.pegaCat(a);

    }

    public void inserirCategoria(String nome, String desc) {
        Categoria c = new Categoria();
        c.setCatNome(nome);
        c.setCatDesc(desc);
        cdao = new CategoriaDAO();
        cdao.adiciona(c);

    }

    public ArrayList<Categoria> pegaCat() {
        CategoriaDAO cd = new CategoriaDAO();
        return cd.pegaCat();
    }

}
