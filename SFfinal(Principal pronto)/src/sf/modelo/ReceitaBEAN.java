/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.modelo;

import java.sql.Date;

/**
 *
 * @author Administrador
 */
public class ReceitaBEAN {
    private int recCod;
    private String recDesc;
    private int rec_catCod;
    private boolean recPago=false;
    private int recNrodeParcelas;
    private boolean recFixo;

    public boolean isRecFixo() {
        return recFixo;
    }

    public void setRecFixo(boolean recFixo) {
        this.recFixo = recFixo;
    }
    
    

    public int getRecNrodeParcelas() {
        return recNrodeParcelas;
    }

    public void setRecNrodeParcelas(int recNrodeParcelas) {
        this.recNrodeParcelas = recNrodeParcelas;
    }
    
    

    public boolean getRecPago() {
        return recPago;
    }

    public void setRecPago(boolean recNroPago) {
        this.recPago = recNroPago;
    }
    
    
    

    

    public int getRec_catCod() {
        return rec_catCod;
    }

    public void setRec_catCod(int catCod) {
        this.rec_catCod = catCod;
    }     

    public int getRecCod() {
        return recCod;
    }

    public void setRecCod(int recCod) {
        this.recCod = recCod;
    }

  

    public String getRecDesc() {
        return recDesc;
    }

    public void setRecDesc(String recTipo) {
        this.recDesc = recTipo;
    }

   
    
    
}
