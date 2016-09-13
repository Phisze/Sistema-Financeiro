/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.controle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sf.modelo.CategoriaBEAN;
import sf.modelo.CategoriaDAO;
import sf.modelo.ConnectionFactory;
import sf.modelo.DespesaBEAN;
import sf.modelo.ParcelaBEAN;
import sf.modelo.ParcelaDAO;
import sf.modelo.ReceitaBEAN;
import sf.modelo.ReceitaDespesaDAO;

/**
 *
 * @author Alunos
 */
public class DespesasReceitasCONTROLE {

    private ReceitaDespesaDAO rdc;
    private ParcelaDAO pdao;
    private CategoriaDAO cdao;
    private Connection con = ConnectionFactory.getConnection();
    private int categoriaSelecionada;
    private int codRDacicionado;

    public void meuInit() {
//    Preenche o JList com dados do BD;
        cdao = new CategoriaDAO();

    }

    public ArrayList<CategoriaBEAN> retornaCategoria() {
        cdao = new CategoriaDAO();
        ArrayList<CategoriaBEAN> catbox = new ArrayList<CategoriaBEAN>();
        cdao.pegaCat(catbox);
        return catbox;

    }

   
    public void selecionaCategoria(int index) {
        ArrayList<CategoriaBEAN> p = new ArrayList();
         cdao.pegaCat(p);
        categoriaSelecionada = p.get(index).getCatCod();
    }

    public int adicionaDespesa(String desc, Date data, double valor, int nropago) {
        ReceitaDespesaDAO rdd = new ReceitaDespesaDAO();
        DespesaBEAN db = new DespesaBEAN();
        db.setDesDesc(desc);
        db.setDesData(data);
        db.setDesValor(valor);
        db.setDesNroPago(nropago);
        db.setDes_SubCod(categoriaSelecionada);
        rdd.adicionaDespesa(db);

        //pegando o codigo da despesa adicionada
        ArrayList<DespesaBEAN> cod = new ArrayList<DespesaBEAN>();
        cod = rdd.getDespesa();

        return codRDacicionado = cod.get(cod.size() - 1).getDesCod();

    }

    public void adicionaReceita(String desc, Date data, double valor, int nropago) {
        ReceitaDespesaDAO rdd = new ReceitaDespesaDAO();
        ReceitaBEAN rb = new ReceitaBEAN();
        rb.setRecDesc(desc);
        rb.setRecData(data);
        rb.setRecValor(valor);
        rb.setRecNroPago(nropago);
        rb.setRec_SubCod(categoriaSelecionada);
        rdd.adicionaReceita(rb);

        //pegando o codigo da receita adicionada
        ArrayList<ReceitaBEAN> cod = new ArrayList<ReceitaBEAN>();
        cod = rdd.getReceita();

        codRDacicionado = cod.get(cod.size() - 1).getRecCod();
    }

    public void adicionaParcela(String periodo, int parNroParcela, int tipo) {
        ParcelaDAO pd = new ParcelaDAO();
        ParcelaBEAN pb = new ParcelaBEAN();
        pb.setParPeriodoParcela(periodo);
        pb.setParNroParcela(parNroParcela);

        //direciona o trato correto dependendo se é uma despesa ou receita
        if (tipo == 1) {
            pb.setPar_desCod(codRDacicionado);
            pd.adicionaParcelaDespesa(pb);
        } else if (tipo == 2) {
            pb.setPar_recCod(codRDacicionado);
            pd.adicionaParcelaReceita(pb);
        }
        codRDacicionado = -10;

    }

    public void adicionaParcelaFixa(String periodo, int tipo) {
        ParcelaDAO pd = new ParcelaDAO();
        ParcelaBEAN pb = new ParcelaBEAN();
        pb.setParPeriodoParcela(periodo);

        //direciona o trato correto dependendo se é uma despesa ou receita
        if (tipo == 1) {
            pb.setPar_desCod(codRDacicionado);
            pd.adicionaParcelaDespesaFixa(pb);
        } else if (tipo == 2) {
            
            pb.setPar_recCod(codRDacicionado);
            pd.adicionaParcelaReceitaFixa(pb);
            
        }
        codRDacicionado = -10;

    }
}
