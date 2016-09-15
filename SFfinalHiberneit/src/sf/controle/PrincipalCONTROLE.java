/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sf.controle;

import java.util.ArrayList;
import sf.modelo.Despesa;
import sf.modelo.Parcela;
import sf.modelo.ParcelaDAO;
import sf.modelo.Receita;
import sf.modelo.ReceitaDespesaDAO;

/**
 *
 * @author Alunos
 */
public class PrincipalCONTROLE {
    private boolean DESPESA=true;
    private boolean RECEITA=false;
    ReceitaDespesaDAO rdao;
    ParcelaDAO rcd= new ParcelaDAO();
    
    public double somaSaldo(){
        ArrayList<Parcela> rb = new ArrayList();
        rb=rcd.getParcela(1);
        double somaReceita=0;
        double somaDespesa=0;
        for(Parcela c: rb){
        if(c.getDespesa().getDesCod()>0){
            somaDespesa=c.getParValor()+ somaDespesa;
        }else{
            somaReceita=c.getParValor()+somaReceita;
        }
        }

        return somaReceita-somaDespesa;
        
    }
    
    
    
    
      public ArrayList<Despesa>retornaDespesa(){
        rdao =new ReceitaDespesaDAO();
       ArrayList<Despesa>despesa=rdao.getDespesa();
        
       return despesa;
     }
     public ArrayList<Receita>retornaReceita(){
        rdao =new ReceitaDespesaDAO();
       ArrayList<Receita>receita= rdao.getReceita();
     
       return receita;
     }
     
     public ArrayList<Parcela>retornaParcelas(boolean tipo){
         ArrayList<Parcela> nao= new ArrayList();
         ArrayList indexesRemover=new ArrayList();
         nao=rcd.getParcela(0); 
        
         for(int i=0; i<nao.size(); i++){
             if(tipo==DESPESA && nao.get(i).getDespesa().getDesCod()>0){
                 indexesRemover.add(i);
             }
             else if(tipo==RECEITA && nao.get(i).getReceita().getRecCod()>0){
                 indexesRemover.add(i);
             }
         }
         indexesRemover.size();
         for(int t=0; t<indexesRemover.size();t++){
             nao.remove(indexesRemover.get(t));
         }
         System.out.println(nao.get(0).getReceita().getRecCod());
         return nao;
     }
     
     public void pagaParcela(int index, boolean tipo){
         ArrayList<Parcela> p=retornaParcelas(tipo);
         ParcelaDAO pdao= new ParcelaDAO();
         pdao.atualizaStatusParcela(p.get(index).getParCod());
     }
}
