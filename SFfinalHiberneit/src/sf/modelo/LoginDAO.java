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
/**
 *
 * @author Alunos
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class LoginDAO {

    Scanner sc = new Scanner(System.in);
    // a conexão com o banco de dados
    private Connection con;

    public LoginDAO() {
        //inicializa a conexão com o BD
      this.con =  ConnectionFactory.getConnection();
    }

    public boolean logar(Login usuario) {
        int logado = 0;
        LoginDAO udao = new LoginDAO();
        ArrayList<Login> u = udao.getUsuarios();
        for (Login us : u) {
            if (us.getLogUsuario().equals(usuario.getLogUsuario()) && us.getLogSenha().equals(usuario.getLogSenha())) {
                logado = 1;
                break;
            }
        }
        if (logado == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void adiciona(Login usuario) {
        LoginDAO udao = new LoginDAO();
        ArrayList<Login> u = udao.getUsuarios();
        for (Login us : u) {
            if (us.getLogUsuario().equals(usuario.getLogUsuario())) {
                System.out.println("Usuario já existente, tente com um novo usuário");
                return;
            }

        }

        String sql = "insert into login (logUsuario,logSenha) values (?,?)";

        try {
            // prepared statement para inserção
            PreparedStatement stmt = con.prepareStatement(sql);

            // seta os valores
            stmt.setString(1, usuario.getLogUsuario());
            stmt.setString(2, usuario.getLogSenha());
            // executa
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleta(int usuario) {
        String sql = "delete from login where logUsuario = ?;";

        try {
            // prepared statement para inserção
            PreparedStatement stmt = con.prepareStatement(sql);

            // seta os valores
            stmt.setInt(1, usuario);

            // executa
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Login> getUsuarios() {
        String sql = "select * from login;";
        ArrayList<Login> a = new ArrayList();
        try {
            // prepared statement para inserção
            PreparedStatement stmt = con.prepareStatement(sql);

            // executa
            ResultSet rs = stmt.executeQuery();
            //joga resultado da consulta no ArrayList
            while (rs.next()) {
                Login x = new Login();
                x.setLogUsuario(rs.getString("logUsuario"));
                x.setLogSenha(rs.getString("logSenha"));
                a.add(x);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return a;

    }

    public void atualizar() {
        String sql = "update `empresa`.`funcionarios` set funNome = ? where funCodigo=?;";

        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            System.out.println("\nDigite novo nome do funcionário:");
            stmt.setString(1, sc.next());
            System.out.println("\nDigite o codigo desse funcionário");
            stmt.setInt(2, sc.nextInt());

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
