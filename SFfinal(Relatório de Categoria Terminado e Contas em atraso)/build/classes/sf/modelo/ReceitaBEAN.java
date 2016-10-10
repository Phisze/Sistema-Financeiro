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
    private double recValor;
    private String recDesc;
    private Date recData;
    private int rec_SubCod;
    private int recNroPago; 

    public int getRecNroPago() {
        return recNroPago;
    }

    public void setRecNroPago(int recNroPago) {
        this.recNroPago = recNroPago;
    }
    
    
    

    

    public int getRec_SubCod() {
        return rec_SubCod;
    }

    public void setRec_SubCod(int rec_SubCod) {
        this.rec_SubCod = rec_SubCod;
    }     

    public int getRecCod() {
        return recCod;
    }

    public void setRecCod(int recCod) {
        this.recCod = recCod;
    }

    public double getRecValor() {
        return recValor;
    }

    public void setRecValor(double recValor) {
        this.recValor = recValor;
    }

    public String getRecDesc() {
        return recDesc;
    }

    public void setRecDesc(String recTipo) {
        this.recDesc = recTipo;
    }

    public Date getRecData() {
        return recData;
    }

    public void setRecData(Date recData) {
        this.recData = recData;
    }
    
    
}
