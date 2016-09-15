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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import sf.modelo.Categoria;
import sf.modelo.CategoriaDAO;
import sf.modelo.ConnectionFactory;
import sf.modelo.Despesa;
import sf.modelo.Parcela;
import sf.modelo.ParcelaDAO;
import sf.modelo.Receita;
import sf.modelo.ReceitaDespesaDAO;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Alunos
 */
public class DespesasReceitasCONTROLE {

    private ReceitaDespesaDAO rdd;
    private ParcelaDAO pdao;
    private CategoriaDAO cdao;
    private Connection con = ConnectionFactory.getConnection();
    private int categoriaSelecionada;
    private int codRDacicionado;

    private final int FIXO = 1;
    private final int PARCELADO = 2;
    private final int AVISTA = 3;

    private final int DIARIO = 1;
    private final int SEMANAL = 2;
    private final int MENSAL = 3;
    private final int BIMESTRAL = 4;
    private final int TRIMESTRAL = 5;
    private final int SEMESTRAL = 6;
    private final int ANUAL = 7;

    private final boolean DESPESA = true;
    private final boolean RECEITA = false;

    public void meuInit() {
//    Preenche o JList com dados do BD;
        cdao = new CategoriaDAO();

    }
    

    
    public double getValor(int LANCAMENTOCOD, boolean tipo){
        ParcelaDAO pd=new ParcelaDAO();
        
        ArrayList<Parcela> pb;
        
        pb=pd.getParcela(LANCAMENTOCOD, tipo);
        pb.add(new Parcela());
       
       
        double valor =(double) pb.get(0).getParValor();
        
        System.out.println("Teste: "+valor);
        
        
        
        return valor;
        
    }
    
    public String getData(int LANCAMENTOCOD, boolean tipo){
        
        ParcelaDAO pd= new ParcelaDAO();
        
        ArrayList<Parcela> pb= new ArrayList<Parcela>();
        
        pb=pd.getParcela(LANCAMENTOCOD, tipo);
       
        pb.add(new Parcela());
        
        GregorianCalendar gc= new GregorianCalendar();
        
        gc.setTime(pb.get(0).getParData());
        
        String datafinal = new SimpleDateFormat("dd/MM/yyyy").format(gc.getTimeInMillis());
      
        return datafinal;
        
        
    
    }
    
    public String getCat(int CODCATEGORIA){
        
        String categoria="nulo";
        
        ArrayList<Categoria> cb= new ArrayList<Categoria>();
        
        cb=retornaCategoria();
        
        for(Categoria c: cb){
            if(c.getCatCod()==CODCATEGORIA){
            categoria=c.getCatNome();
            }
        
        }
    
        return categoria;       
    
    }

    public ArrayList<Categoria> retornaCategoria() {
        cdao = new CategoriaDAO();
        ArrayList<Categoria> catbox = new ArrayList<Categoria>();
        cdao.pegaCat(catbox);
        return catbox;

    }

    public void selecionaCategoria(int index) {
        ArrayList<Categoria> p = new ArrayList();
        cdao.pegaCat(p);
        categoriaSelecionada = p.get(index).getCatCod();
    }

    public int adicionaDespesa(String desc, boolean desPago, int desNrodeParcelas, boolean desFixo) {
        ReceitaDespesaDAO rdd = new ReceitaDespesaDAO();
        Despesa db = new Despesa();

        db.setDesDesc(desc);
        db.setDesPago(desPago);
        db.setDesNrodeParcelas(desNrodeParcelas);
        db.setDesFixo(desFixo);
        Categoria c=new Categoria();
        c.setCatCod(categoriaSelecionada);
        db.setCategoria(c);
        rdd.adicionaDespesa(db);

        //pegando o codigo da despesa adicionada
        ArrayList<Despesa> cod = new ArrayList<Despesa>();
        cod = rdd.getDespesa();

        return codRDacicionado = cod.get(cod.size() - 1).getDesCod();

    }    
    
    public void adicionaReceita(String desc, boolean recPago, int recNrodeParcelas, boolean recFixo) {
        ReceitaDespesaDAO rdd = new ReceitaDespesaDAO();
        Receita rb = new Receita();
        rb.setRecDesc(desc);
        rb.setRecPago(recPago);
        rb.setRecNrodeParcelas(recNrodeParcelas);
        rb.setRecFixo(recFixo);
        Categoria c=new Categoria();
        c.setCatCod(categoriaSelecionada);
        rb.setCategoria(c);
        rdd.adicionaReceita(rb);

        //pegando o codigo da receita adicionada
        ArrayList<Receita> cod = new ArrayList<Receita>();
        cod = rdd.getReceita();

        codRDacicionado = cod.get(cod.size() - 1).getRecCod();
    }

    public ArrayList<Receita> pegaReceita(){
        
        ReceitaDespesaDAO rdd=new ReceitaDespesaDAO();
        
        return rdd.getReceita();
        
    }
    
    public ArrayList<Despesa> pegaDespesa(){
        
        ReceitaDespesaDAO rdd=new ReceitaDespesaDAO();
        
        return rdd.getDespesa();    
    
    }
    
    

    public void add(Parcela pb, boolean receitaoudespesa) {
        ParcelaDAO pd = new ParcelaDAO();

        if (receitaoudespesa == DESPESA) {

            pb.getDespesa().setDesCod(codRDacicionado);
            pd.adicionaParcelaDespesa(pb);

        } else if (receitaoudespesa == RECEITA) {

            pb.getReceita().setRecCod(codRDacicionado);
            pd.adicionaParcelaReceita(pb);

        }

    }

    public void adicionaParcela(double valor, java.util.Date data, boolean parParcelaPaga, int nroParcela, int periodo, boolean receitaoudespesa, int tipo) {
        ParcelaDAO pd = new ParcelaDAO();
        Parcela pb = new Parcela();
        pb.setParParcelaPaga(parParcelaPaga);
        pb.setParValor(valor);
      //  pb.setParData(data);

        if (tipo == AVISTA) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
            add(pb, receitaoudespesa);

        }else if(tipo == PARCELADO){
        
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            
            for(int i=0; i<nroParcela; i++){
                if(periodo==DIARIO){
                    
                   
                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb,receitaoudespesa);
                    
                    if(gc.get(Calendar.DAY_OF_MONTH)==gc.getActualMaximum(Calendar.DAY_OF_MONTH)){
                    
                        if(gc.get(Calendar.MONTH)==gc.getMaximum(Calendar.MONTH)){
                        
                            gc.add(Calendar.YEAR, 1);
                            gc.add(Calendar.MONTH, Calendar.JANUARY);
                        
                        }
                        
                        gc.roll(GregorianCalendar.MONTH, 1);
                        gc.set(Calendar.DAY_OF_MONTH,1);
                    }else{
                        
                        gc.roll(GregorianCalendar.DAY_OF_MONTH, 1);
                    
                    }
                    
                    
                }
                
            }
           
        
        }      
        else if (tipo == FIXO) {

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);

            if (periodo == DIARIO) {

                for (int i = 0; i < 7; i++) {
                    
                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);
                    
                    if (gc.get(Calendar.DAY_OF_MONTH) == gc.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                        
                        if (gc.get(Calendar.MONTH) == gc.getMaximum(Calendar.MONTH)) {
                        
                            gc.add(Calendar.YEAR, 1);
                            gc.add(Calendar.MONTH, Calendar.JANUARY);
                        
                        }
                        
                        gc.roll(GregorianCalendar.MONTH, true);
                        gc.set(Calendar.DAY_OF_MONTH, 1);

                    } else {

                        gc.roll(GregorianCalendar.DAY_OF_MONTH, true);
                        
                    }
                    
                      
                      

                }

            } else if (periodo == SEMANAL) {

                for (int i = 1; i < 8; i++) {
                    gc.roll(GregorianCalendar.WEEK_OF_MONTH, i);
                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);
                }
            } else if (periodo == MENSAL) {
            } else if (periodo == BIMESTRAL) {
            } else if (periodo == TRIMESTRAL) {
            } else if (periodo == SEMESTRAL) {
            } else if (periodo == ANUAL) {
            }

        } else if (tipo == PARCELADO) {

        } else if (periodo == DIARIO) {
            //1=diario
            for (int i = 1; i < nroParcela; i++) {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(data);
                gc.roll(GregorianCalendar.DAY_OF_MONTH, 1);
                System.out.println(new java.util.Date(gc.getTimeInMillis()));
                //pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                // add(pb, receitaoudespesa);
            }

        }
        codRDacicionado = -10;

    }

    public void deleta(int codigo, boolean tipo){
        rdd= new ReceitaDespesaDAO();
        pdao= new ParcelaDAO();
        if(tipo==DESPESA){
            pdao.deletaParcelaDespesa(codigo);
            rdd.deletaDespesa(codigo);
        }
        else if(tipo==RECEITA){
            
            pdao.deletaParcelaReceita(codigo);
            rdd.deletaReceita(codigo);
            
        }   
    
    }
}
