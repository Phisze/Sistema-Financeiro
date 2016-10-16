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
public class DespesaBEAN {
    private int desCod;
    private double desValor;
    private String desDesc;
    private Date desData;
    private int des_SubCod;
    private int desNroPago;
    
    

    public int getDesNroPago() {
        return desNroPago;
    }

    public void setDesNroPago(int desNroPago) {
        this.desNroPago = desNroPago;
    }
    
    

  
    
    
    public int getDesCod() {
        return desCod;
    }

    public void setDesCod(int desCod) {
        this.desCod = desCod;
    }

    public double getDesValor() {
        return desValor;
    }

    public void setDesValor(double desValor) {
        this.desValor = desValor;
    }

    public String getDesDesc() {
        return desDesc;
    }

    public void setDesDesc(String desTipo) {
        this.desDesc = desTipo;
    }

    public Date getDesData() {
        return desData;
    }

    public void setDesData(Date desData) {
        this.desData = desData;
    }

    public int getDes_SubCod() {
        return des_SubCod;
    }

    public void setDes_SubCod(int des_SubCod) {
        this.des_SubCod = des_SubCod;
    }
    
    
    
}
