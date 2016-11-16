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
import sf.modelo.CategoriaBEAN;
import sf.modelo.CategoriaDAO;
import sf.modelo.ConnectionFactory;
import sf.modelo.DespesaBEAN;
import sf.modelo.ParcelaBEAN;
import sf.modelo.ParcelaDAO;
import sf.modelo.ReceitaBEAN;
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

    public double getValor(int LANCAMENTOCOD, boolean tipo) {
        ParcelaDAO pd = new ParcelaDAO();

        ArrayList<ParcelaBEAN> pb;

        pb = pd.getParcela(LANCAMENTOCOD, tipo);
        pb.add(new ParcelaBEAN());

        double valor = (double) pb.get(0).getParValor();

        System.out.println("Teste: " + valor);

        return valor;

    }

    public String getData(int LANCAMENTOCOD, boolean tipo) {

        ParcelaDAO pd = new ParcelaDAO();

        ArrayList<ParcelaBEAN> pb = new ArrayList<ParcelaBEAN>();

        pb = pd.getParcela(LANCAMENTOCOD, tipo);

        pb.add(new ParcelaBEAN());

        GregorianCalendar gc = new GregorianCalendar();

        gc.setTime(pb.get(0).getParData());

        String datafinal = new SimpleDateFormat("dd/MM/yyyy").format(gc.getTimeInMillis());

        return datafinal;

    }

    public String getCat(int CODCATEGORIA) {

        String categoria = "nulo";

        ArrayList<CategoriaBEAN> cb = new ArrayList<CategoriaBEAN>();

        cb = retornaCategoria();

        for (CategoriaBEAN c : cb) {
            if (c.getCatCod() == CODCATEGORIA) {
                categoria = c.getCatNome();
            }

        }

        return categoria;

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

    public int adicionaDespesa(String desc, boolean desPago, int desNrodeParcelas, boolean desFixo) {
        ReceitaDespesaDAO rdd = new ReceitaDespesaDAO();
        DespesaBEAN db = new DespesaBEAN();

        db.setDesDesc(desc);
        db.setDesPago(desPago);
        db.setDesNrodeParcelas(desNrodeParcelas);
        db.setDesFixo(desFixo);
        db.setDes_catCod(categoriaSelecionada);
        rdd.adicionaDespesa(db);

        //pegando o codigo da despesa adicionada
        ArrayList<DespesaBEAN> cod = new ArrayList<DespesaBEAN>();
        cod = rdd.getDespesa();

        return codRDacicionado = cod.get(cod.size() - 1).getDesCod();

    }

    public void adicionaReceita(String desc, boolean recPago, int recNrodeParcelas, boolean recFixo) {
        ReceitaDespesaDAO rdd = new ReceitaDespesaDAO();
        ReceitaBEAN rb = new ReceitaBEAN();
        rb.setRecDesc(desc);
        rb.setRecPago(recPago);
        rb.setRecNrodeParcelas(recNrodeParcelas);
        rb.setRecFixo(recFixo);
        rb.setRec_catCod(categoriaSelecionada);
        rdd.adicionaReceita(rb);

        //pegando o codigo da receita adicionada
        ArrayList<ReceitaBEAN> cod = new ArrayList<ReceitaBEAN>();
        cod = rdd.getReceita();

        codRDacicionado = cod.get(cod.size() - 1).getRecCod();
    }

    public ArrayList<ReceitaBEAN> pegaReceita() {

        ReceitaDespesaDAO rdd = new ReceitaDespesaDAO();

        return rdd.getReceita();

    }

    public ArrayList<DespesaBEAN> pegaDespesa() {

        ReceitaDespesaDAO rdd = new ReceitaDespesaDAO();

        return rdd.getDespesa();

    }

    public void add(ParcelaBEAN pb, boolean receitaoudespesa) {
        ParcelaDAO pd = new ParcelaDAO();

        if (receitaoudespesa == DESPESA) {

            pb.setPar_desCod(codRDacicionado);
            pd.adicionaParcelaDespesa(pb);

        } else if (receitaoudespesa == RECEITA) {

            pb.setPar_recCod(codRDacicionado);
            pd.adicionaParcelaReceita(pb);

        }

    }

    public void adicionaParcela(double valor, java.util.Date data, boolean parParcelaPaga, int nroParcela, int periodo, boolean receitaoudespesa, int tipo) {

        ParcelaDAO pd = new ParcelaDAO();
        ParcelaBEAN pb = new ParcelaBEAN();
        pb.setParParcelaPaga(parParcelaPaga);
        pb.setParValor(valor);
        //  pb.setParData(data);

        if (tipo == AVISTA) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
            add(pb, receitaoudespesa);

        } else if (tipo == PARCELADO) {

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);

            for (int i = 0; i < nroParcela; i++) {
                if (periodo == DIARIO) {

                    //Adiciona a primeira parcela
                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    //Verifica se a parcela adicionada era do último dia do mês
                    if (gc.get(Calendar.DAY_OF_MONTH) == gc.getActualMaximum(Calendar.DAY_OF_MONTH)) {

                        //Verifica se o mês da parcela adicionada era do o último do ano
                        if (gc.get(Calendar.MONTH) == gc.getMaximum(Calendar.MONTH)) {

                            //Se é o último mês do ano, adiciona mais um ano ao mês começando do mês de Janeiro
                            gc.add(Calendar.YEAR, 1);
                            gc.add(Calendar.MONTH, Calendar.JANUARY);

                        }
                        //Se é o último dia do mês, passa pro próximo mês, comeãndo no dia 01
                        gc.roll(GregorianCalendar.MONTH, 1);
                        gc.set(Calendar.DAY_OF_MONTH, 1);
                    } else {
                        //Se não é o último dia do mês, só adiciona um dia para a próx parcela
                        gc.roll(GregorianCalendar.DAY_OF_MONTH, 1);
                    }

                    //faz o processo novamente
                } else if (periodo == SEMANAL) {

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    if (gc.get(GregorianCalendar.WEEK_OF_MONTH) == gc.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH)) {

                        if (gc.get(GregorianCalendar.MONTH) == gc.getActualMaximum(GregorianCalendar.MONTH)) {

                            gc.add(GregorianCalendar.YEAR, 1);
                            gc.set(GregorianCalendar.MONTH, GregorianCalendar.DECEMBER);

                        }
                        gc.add(GregorianCalendar.MONTH, 1);
                    } else {
                        gc.add(GregorianCalendar.WEEK_OF_MONTH, 1);
                    }
                } else if (periodo == MENSAL) {

                    int diames = gc.get(GregorianCalendar.DAY_OF_MONTH);

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.MONTH, 1);

                    //Se o dia do mês da parcela for menorou igual que a quantidade máxima de dias (28,29,30,31) num mês
                    //Seta o dia do mês na data.
                    if (diames <= gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
                        gc.set(GregorianCalendar.DAY_OF_MONTH, diames);

                    }

                } else if (periodo == BIMESTRAL) {

                    int diames = gc.get(GregorianCalendar.DAY_OF_MONTH);

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.MONTH, 2);

                    if (diames <= gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
                        gc.set(GregorianCalendar.DAY_OF_MONTH, diames);
                    }

                } else if (periodo == TRIMESTRAL) {

                    int diames = gc.get(GregorianCalendar.DAY_OF_MONTH);

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.MONTH, 3);

                    if (diames <= gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
                        gc.set(GregorianCalendar.DAY_OF_MONTH, diames);
                    }

                }else if (periodo == SEMESTRAL) {

                int diames = gc.get(GregorianCalendar.DAY_OF_MONTH);

                

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.MONTH, 6);

                    if (diames <= gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
                        gc.set(GregorianCalendar.DAY_OF_MONTH, diames);
                    }

                

            }else if (periodo == ANUAL) {

             

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.YEAR, 1);
                }

            

            }

        } else if (tipo == FIXO) {

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);

            if (periodo == DIARIO) {

                for (int i = 0; i <= 7; i++) {

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    if (gc.get(Calendar.DAY_OF_MONTH) == gc.getActualMaximum(Calendar.DAY_OF_MONTH)) {

                        if (gc.get(Calendar.MONTH) == gc.getMaximum(Calendar.MONTH)) {

                            gc.add(Calendar.YEAR, 1);
                            gc.set(Calendar.MONTH, Calendar.JANUARY);

                        }

                        gc.roll(GregorianCalendar.MONTH, true);
                        gc.set(Calendar.DAY_OF_MONTH, 1);

                    } else {

                        gc.roll(GregorianCalendar.DAY_OF_MONTH, true);

                    }

                }

            } else if (periodo == SEMANAL) {

                for (int i = 1; i <= 7; i++) {

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    if (gc.get(GregorianCalendar.WEEK_OF_MONTH) == gc.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH)) {

                        if (gc.get(GregorianCalendar.MONTH) == gc.getActualMaximum(GregorianCalendar.MONTH)) {

                            gc.add(GregorianCalendar.YEAR, 1);
                            gc.set(GregorianCalendar.MONTH, GregorianCalendar.DECEMBER);

                        }
                        gc.add(GregorianCalendar.MONTH, 1);
                    } else {
                        gc.add(GregorianCalendar.WEEK_OF_MONTH, 1);
                    }

                }
            } else if (periodo == MENSAL) {

                //Pega o dia do mês que da parcela
                int diames = gc.get(GregorianCalendar.DAY_OF_MONTH);

                for (int i = 1; i <= 7; i++) {

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.MONTH, 1);

                    //Se o dia do mês da parcela for menorou igual que a quantidade máxima de dias (28,29,30,31) num mês
                    //Seta o dia do mês na data.
                    if (diames <= gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
                        gc.set(GregorianCalendar.DAY_OF_MONTH, diames);

                    }
                }
            } else if (periodo == BIMESTRAL) {

                int diames = gc.get(GregorianCalendar.DAY_OF_MONTH);

                for (int i = 1; i <= 7; i++) {

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.MONTH, 2);

                    if (diames <= gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
                        gc.set(GregorianCalendar.DAY_OF_MONTH, diames);
                    }

                }
            } else if (periodo == TRIMESTRAL) {

                int diames = gc.get(GregorianCalendar.DAY_OF_MONTH);

                for (int i = 1; i <= 7; i++) {

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.MONTH, 3);

                    if (diames <= gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
                        gc.set(GregorianCalendar.DAY_OF_MONTH, diames);
                    }

                }

            } else if (periodo == SEMESTRAL) {

                int diames = gc.get(GregorianCalendar.DAY_OF_MONTH);

                for (int i = 1; i <= 7; i++) {

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.MONTH, 6);

                    if (diames <= gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)) {
                        gc.set(GregorianCalendar.DAY_OF_MONTH, diames);
                    }

                }

            } else if (periodo == ANUAL) {

                for (int i = 1; i <= 7; i++) {

                    pb.setParData(new java.sql.Date(gc.getTimeInMillis()));
                    add(pb, receitaoudespesa);

                    gc.add(GregorianCalendar.YEAR, 1);
                }

            }

        }
        codRDacicionado = -10;

    }

    public void deleta(int codigo, boolean tipo) {
        rdd = new ReceitaDespesaDAO();
        pdao = new ParcelaDAO();
        if (tipo == DESPESA) {
            pdao.deletaParcelaDespesa(codigo);
            rdd.deletaDespesa(codigo);
        } else if (tipo == RECEITA) {

            pdao.deletaParcelaReceita(codigo);
            rdd.deletaReceita(codigo);

        }

    }
}