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
public class ParcelaBEAN {
    
    private int parCod;
    private Double parValor;
    private int par_desCod;
    private int par_recCod;  
    private java.sql.Date parData;
    private boolean parParcelaPaga=false;
    
    

    public boolean isParParcelaPaga() {
        return parParcelaPaga;
    }

    public void setParParcelaPaga(boolean parParcelaPaga) {
        this.parParcelaPaga = parParcelaPaga;
    }  
    

    public Date getParData() {
        return parData;
    }

    public void setParData(Date parData) {
        this.parData = parData;
    }
    
    public int getParCod() {
        return parCod;
    }

    public void setParCod(int parCod) {
        this.parCod = parCod;
    }



    public Double getParValor() {
        return parValor;
    }

    public void setParValor(Double parValor) {
        this.parValor = parValor;
    }

    public int getPar_desCod() {
        return par_desCod;
    }

    public void setPar_desCod(int par_desCod) {
        this.par_desCod = par_desCod;
    }

    public int getPar_recCod() {
        return par_recCod;
    }

    public void setPar_recCod(int par_recCod) {
        this.par_recCod = par_recCod;
    }
    
    
    
}
