/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sf.controle;

import java.sql.Date;
import java.util.ArrayList;
import sf.modelo.DespesaBEAN;
import sf.modelo.ParcelaBEAN;
import sf.modelo.ParcelaDAO;
import sf.modelo.ReceitaBEAN;
import sf.modelo.ReceitaDespesaDAO;

/**
 *
 * @author Alunos
 */
public class PrincipalCONTROLE {
    private boolean DESPESA=true;
    private boolean RECEITA=false;
    private boolean ATRASO = true;
    private boolean ARECEBER = false;
    
    ReceitaDespesaDAO rdao;
    ParcelaDAO rcd= new ParcelaDAO();
    
    public double somaSaldo(){
        ArrayList<ParcelaBEAN> rb = new ArrayList();
        rb=rcd.getParcela(1);
        double somaReceita=0;
        double somaDespesa=0;
        for(ParcelaBEAN c: rb){
        if(c.getPar_desCod()>0){
            somaDespesa=c.getParValor()+ somaDespesa;
        }else{
            somaReceita=c.getParValor()+somaReceita;
        }
        }

        return somaReceita-somaDespesa;
        
    }
    
    
    
    
      public ArrayList<DespesaBEAN>retornaDespesa(){
        rdao =new ReceitaDespesaDAO();
       ArrayList<DespesaBEAN>despesa=rdao.getDespesa();
        
       return despesa;
     }
     public ArrayList<ReceitaBEAN>retornaReceita(){
        rdao =new ReceitaDespesaDAO();
       ArrayList<ReceitaBEAN>receita= rdao.getReceita();
     
       return receita;
     }
     //retornaParcelas
    public ArrayList<ParcelaBEAN> retornaParcelas(boolean tipo) {
        ArrayList<ParcelaBEAN> nao = new ArrayList();

        nao = rcd.getParcela(0);

        for (int i = 0; i < nao.size(); i++) {
            if (tipo == DESPESA && nao.get(i).getPar_recCod() > 0) {

                nao.remove(i);
                i--;
            } else if (tipo == RECEITA && nao.get(i).getPar_desCod() > 0) {
                nao.remove(i);
                i--;
            }
        }

        for (ParcelaBEAN p : nao) {
            if (tipo == DESPESA) {

            } else if (tipo == RECEITA) {

            }
        }
        return nao;
    }
     
     public void pagaParcela(int index, boolean tipo, boolean recdes) {
        ArrayList<ParcelaBEAN> p = retornaParcelas(tipo);

        if (recdes == ARECEBER) {
            for (int i = 0; i < p.size(); i++) {
                if (!p.get(i).getParData().after(new Date(System.currentTimeMillis()))) {
                    p.remove(i);
                    i--;
                }
            }

        } else if (recdes == ATRASO) {
            for (int i = 0; i < p.size(); i++) {
                if (!p.get(i).getParData().before(new Date(System.currentTimeMillis()))) {
                    p.remove(i);
                    i--;
                }
            }

        }

        ParcelaDAO pdao = new ParcelaDAO();
        pdao.atualizaStatusParcela(p.get(index).getParCod());
        p.get(index).setParParcelaPaga(true);

        int cod;
        

        if (tipo == DESPESA) {
            for (ParcelaBEAN pb : p) {

                System.out.println("Codigo da despesa: " + pb.getPar_desCod());

            }

            cod = p.get(index).getPar_desCod();
            for (int i = 0; i < p.size(); i++) {
                if (p.get(i).getPar_desCod() != cod) {
                    p.remove(i);
                    i--;
                }
                if (index == p.size() - 1 && verificaPago(p)) {
                    rdao = new ReceitaDespesaDAO();
                    rdao.pagaDespesa(cod);
                }
            }

        } else if (tipo == RECEITA) {
            for (ParcelaBEAN pb : p) {

                System.out.println("Codigo da receita: " + pb.getPar_recCod());

            }

            cod = p.get(index).getPar_recCod();

            for (int i = 0; i < p.size(); i++) {
                if (p.get(i).getPar_recCod() != cod) {
                    p.remove(i);
                    i--;
                }
            }
            if (index == p.size() - 1 && verificaPago(p)) {
                rdao = new ReceitaDespesaDAO();
                rdao.pagaReceita(cod);
            }
        }

    }

     public boolean verificaPago(ArrayList<ParcelaBEAN> p) {

        int pago = 0;
        for (ParcelaBEAN par : p) {
            if (par.isParParcelaPaga() == true) {
                pago++;
            }
        }

        if (pago == p.size()) {

            return true;

        }

        return false;
    }
}
