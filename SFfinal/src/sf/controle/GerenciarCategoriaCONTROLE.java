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
import sf.modelo.*;
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

    public ReceitaDespesaDAO deletaD() {
        ReceitaDespesaDAO dd = new ReceitaDespesaDAO();
        return dd;
    }

    public ParcelaDAO deletaP() {
        ParcelaDAO pd = new ParcelaDAO();
        return pd;
    }
     

    public ArrayList<CategoriaBEAN> retornaCategorias() {
        cdao = new CategoriaDAO();
        ArrayList<CategoriaBEAN> categorias = new ArrayList<>();
        categorias = cdao.getCategoria();
        return categorias;
        //  jList1.setListData(categorias.toArray());
    }

    public int selecionaCategoria(int index) {

        cdao = new CategoriaDAO();
        ArrayList<CategoriaBEAN> cb = cdao.pegaCat();
        return cb.get(index).getCatCod();

    }

    public void getCatCodigo(int index) {
        ArrayList<CategoriaBEAN> cate = new ArrayList<>();
        this.pegaCat(cate);
        catcod = cate.get(index).getCatCod();
    }

    public ReceitaDespesaDAO verificaDeletado() {
        ReceitaDespesaDAO dr = new ReceitaDespesaDAO();
        return dr;
    }

    public void deletaCategoria(int index) {
        int CODIGO = selecionaCategoria(index);

        cdao.deleta(CODIGO);

    }

    public void atualizaCategoria(CategoriaBEAN cat) {
        CategoriaDAO cd = new CategoriaDAO();
        cd.atualizaCat(cat);
    }

    public void pegaCat(ArrayList<CategoriaBEAN> a) {
        CategoriaDAO cd = new CategoriaDAO();
        cd.pegaCat(a);

    }

    public void inserirCategoria(String nome, String desc) {
        CategoriaBEAN c = new CategoriaBEAN();
        c.setCatNome(nome);
        c.setCatDescricao(desc);
        cdao = new CategoriaDAO();
        cdao.adiciona(c);

    }

    public ArrayList<CategoriaBEAN> pegaCat() {
        CategoriaDAO cd = new CategoriaDAO();
        return cd.pegaCat();
    }

}
