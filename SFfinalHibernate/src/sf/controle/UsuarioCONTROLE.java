/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.controle;

import sf.modelo.Login;
import sf.modelo.LoginDAO;
//import sf.modelo.Criptografia;
/**
 *
 * @author Alunos
 */
public class UsuarioCONTROLE {

    public int cadastraUsuario(String usuario, String senha1, String senha2) {
        int resp = 0;

        if (senha1.equals(senha2)) {
            LoginDAO udao = new LoginDAO();
            Login u = new Login();
            u.setLogUsuario(usuario);
            u.setLogSenha(senha1);
            udao.adiciona(u);

        } else {
            resp = 1;
        }
        return resp;
    }

    public int logar(String usuario, String senha) {
        int resp = 0;
        LoginDAO udao = new LoginDAO();
        Login u = new Login();
        u.setLogUsuario(usuario);
        u.setLogSenha(senha);
        if (udao.logar(u) == true) {
            resp = 1;
        }
        return resp;

    }

}
