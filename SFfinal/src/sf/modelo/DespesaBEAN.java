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
    private String desDesc;
    private int des_catCod;
    private boolean desPago=false;    
    private int desNrodeParcelas;
    private boolean desFixo;
    
    

    public int getDes_catCod() {
        return des_catCod;
    }

    public void setDes_catCod(int des_catCod) {
        this.des_catCod = des_catCod;
    }

    public int getDesNrodeParcelas() {
        return desNrodeParcelas;
    }

    public void setDesNrodeParcelas(int desNrodeParcelas) {
        this.desNrodeParcelas = desNrodeParcelas;
    }

    public boolean isDesFixo() {
        return desFixo;
    }

    public void setDesFixo(boolean desFixo) {
        this.desFixo = desFixo;
    }
    
       

    public boolean getDesPago() {
        return desPago;
    }

    public void setDesPago(boolean desNroPago) {
        this.desPago = desNroPago;
    }
    
    
    public int getDesCod() {
        return desCod;
    }

    public void setDesCod(int desCod) {
        this.desCod = desCod;
    }



    public String getDesDesc() {
        return desDesc;
    }

    public void setDesDesc(String desTipo) {
        this.desDesc = desTipo;
    }

}
