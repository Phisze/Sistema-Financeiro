/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.controle;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import net.sf.jasperreports.engine.*;
import sf.modelo.ConnectionFactory;

/**
 *
 * @author Alunos
 */
public class RelatorioCONTROLE {

    private Connection con;
    
    private boolean DESPESA = true;
    private boolean RECEITA = false;

    public RelatorioCONTROLE() {
        this.con = ConnectionFactory.getConnection();
    }

    public void geraRelatorioT(String query, java.sql.Date parum, java.sql.Date pardois, boolean tipo) {
        ResultSet rs;
        try {

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setDate(1, parum);
            stmt.setDate(2, pardois);
            System.out.println(parum.toString());
            System.out.println(pardois.toString());
            rs = stmt.executeQuery();

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            
          

            JasperReport report = JasperCompileManager.compileReport("C:\\Users\\Administrador\\Documents\\NetBeansProjects\\SFRelatThomas\\SFfinal\\relatorios\\ReTRec.jrxml");;

            if (tipo == DESPESA) {

                report = JasperCompileManager.compileReport("C:\\Users\\Administrador\\Documents\\NetBeansProjects\\SFRelatThomas\\SFfinal\\relatorios\\ReTDes.jrxml");

            }

            JasperPrint impressao = JasperFillManager.fillReport(report, new HashMap(), jrRS);

            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
            
            if (tipo == DESPESA) {
                
                JasperExportManager.exportReportToPdfFile(impressao, "ReTDes" + dt.format(Calendar.getInstance().getTime()) + ".pdf");
            
            } else if (tipo == RECEITA) {
            
                JasperExportManager.exportReportToPdfFile(impressao, "ReTRec" + dt.format(Calendar.getInstance().getTime()) + ".pdf");
            
            }
            

            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");

            File arquivo=null;

            if (tipo == DESPESA) {

                arquivo = new File("RetDes" + dt.format(Calendar.getInstance().getTime()) + ".pdf");

            }else if(tipo==RECEITA){
               arquivo = new File("ReTRec" + dt.format(Calendar.getInstance().getTime()) + ".pdf");
            }
               

            Desktop.getDesktop().open(arquivo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void geraRelatorioR(String query, java.sql.Date parum) {
        ResultSet rs;
        try {

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setDate(1, parum);
            System.out.println(parum.toString());
            rs = stmt.executeQuery();

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            JasperReport report = JasperCompileManager.compileReport("C:\\Users\\Administrador\\gitin\\SFfinal\\relatorios\\contasR.jrxml");
            JasperPrint impressao = JasperFillManager.fillReport(report, new HashMap(), jrRS);

            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
            JasperExportManager.exportReportToPdfFile(impressao, "Contas a Receber" + dt.format(Calendar.getInstance().getTime()) + ".pdf");
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
            File arquivo = new File("Contas a Receber" + dt.format(Calendar.getInstance().getTime()) + ".pdf");
            Desktop.getDesktop().open(arquivo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void geraRelatorioP(String query, java.sql.Date parum) {
        ResultSet rs;
        try {

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setDate(1, parum);
            System.out.println(parum.toString());
            rs = stmt.executeQuery();

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            JasperReport report = JasperCompileManager.compileReport("C:\\Users\\Administrador\\gitin\\SFfinal\\relatorios\\contasP.jrxml");
            JasperPrint impressao = JasperFillManager.fillReport(report, new HashMap(), jrRS);

            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
            JasperExportManager.exportReportToPdfFile(impressao, "Contas a pagar" + dt.format(Calendar.getInstance().getTime()) + ".pdf");
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
            File arquivo = new File("Contas a pagar" + dt.format(Calendar.getInstance().getTime()) + ".pdf");
            Desktop.getDesktop().open(arquivo);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
