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

public class UsuarioDAO {

    Scanner sc = new Scanner(System.in);
    // a conexão com o banco de dados
    private Connection con;
    private PreparedStatement stat;

    public UsuarioDAO() {
        //inicializa a conexão com o BD
      this.con =  ConnectionFactory.getConnection();
    }

    public boolean logar(UsuarioBEAN usuario) {
        int logado = 0;
        UsuarioDAO udao = new UsuarioDAO();
        ArrayList<UsuarioBEAN> u = udao.getUsuarios();
        for (UsuarioBEAN us : u) {
            if (us.getUsuario().equals(usuario.getUsuario()) && us.getSenha().equals(usuario.getSenha())) {
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

    public void adiciona(UsuarioBEAN usuario) {
        UsuarioDAO udao = new UsuarioDAO();
        ArrayList<UsuarioBEAN> u = udao.getUsuarios();
        for (UsuarioBEAN us : u) {
            if (us.getUsuario().equals(usuario.getUsuario())) {
                System.out.println("Usuario já existente, tente com um novo usuário");
                return;
            }

        }

        String sql = "insert into login (logUsuario,logSenha) values (?,?)";

        try {
            // prepared statement para inserção
            PreparedStatement stmt = con.prepareStatement(sql);

            // seta os valores
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getSenha());
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

    public ArrayList<UsuarioBEAN> getUsuarios() {
        String sql = "select * from login;";
        ArrayList<UsuarioBEAN> a = new ArrayList();
        try {
            // prepared statement para inserção
            PreparedStatement stmt = con.prepareStatement(sql);

            // executa
            ResultSet rs = stmt.executeQuery();
            //joga resultado da consulta no ArrayList
            while (rs.next()) {
                UsuarioBEAN x = new UsuarioBEAN();
                x.setUsuario(rs.getString("logUsuario"));
                x.setSenha(rs.getString("logSenha"));
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
public boolean logar(String nome, String senha) {
        ResultSet rs = null;
        boolean res = false;
        String sql = "select * from contato where conNome = ? and conSenha = md5(?);";

        try {
            stat = con.prepareStatement(sql);

            stat.setString(1, nome);
            stat.setString(2, senha);
            rs = stat.executeQuery();

            if (rs.next()) {
                res = true;
            }

            stat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
