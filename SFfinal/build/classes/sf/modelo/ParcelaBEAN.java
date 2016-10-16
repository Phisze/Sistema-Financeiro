/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.modelo;

/**
 *
 * @author Administrador
 */
public class ParcelaBEAN {
    
    private int parCod;
    private Double parValor;
    private int par_desCod;
    private int par_recCod;
    private int parNroParcela;
    private String parPeriodoParcela;

    public int getParNroParcela() {
        return parNroParcela;
    }

    public void setParNroParcela(int parNroParcela) {
        this.parNroParcela = parNroParcela;
    }

    public String getParPeriodoParcela() {
        return parPeriodoParcela;
    }

    public void setParPeriodoParcela(String parPeriodoParcela) {
        this.parPeriodoParcela = parPeriodoParcela;
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
