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

    public RelatorioCONTROLE() {
        this.con = ConnectionFactory.getConnection();
    }

    public void geraRelatorioR(String query, java.sql.Date parum) {
        ResultSet rs;
        try {

            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setDate(1, parum);
            System.out.println(parum.toString());
            rs = stmt.executeQuery();

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            JasperReport report = JasperCompileManager.compileReport("C:\\Users\\Weverton\\Desktop\\Sistema Financeiro\\SFfinal\\relatorios\\contasR.jrxml");
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

            JasperReport report = JasperCompileManager.compileReport("C:\\Users\\Weverton\\Desktop\\Sistema Financeiro\\SFfinal\\relatorios\\contasP.jrxml");
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
