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
public class CategoriaBEAN {
    private int catCod;
    private String catNome;
    private String catDescricao;
    private int cat_scaCod;

    public int getCat_scaCod() {
        return cat_scaCod;
    }

    public void setCat_scaCod(int cat_scaCod) {
        this.cat_scaCod = cat_scaCod;
    }
    
    

    public int getCatCod() {
        return catCod;
    }

    public void setCatCod(int catCod) {
        this.catCod = catCod;
    }

    public String getCatNome() {
        return catNome;
    }

    public void setCatNome(String catNome) {
        this.catNome = catNome;
    }

    public String getCatDescricao() {
        return catDescricao;
    }

    public void setCatDescricao(String catDescricao) {
        this.catDescricao = catDescricao;
    }
    
    
    
}
