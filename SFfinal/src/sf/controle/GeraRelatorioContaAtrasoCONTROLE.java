package sf.controle;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import static sf.modelo.ConnectionFactory.getConnection;

public class GeraRelatorioContaAtrasoCONTROLE {


    public static void main(String[] args) {
        java.sql.Date datatual=new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
        geraRelatorioContasAtraso(datatual,"SELECT desDesc, parData, parValor FROM empresa.despesa join empresa.parcela where par_desCod=desCod and\n" +
"parParcelaPaga=0 and parData=? group by parData;","Contas em Atraso");
    }

    public static void geraRelatorioContasAtraso(java.sql.Date data,String sql, String relatorio) {
        Connection con = getConnection();
        ResultSet rs;
        
        try {
            
           
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDate(1, data);
            // executa
            rs = stmt.executeQuery();

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            Map parameters = new HashMap();

            JasperReport report = JasperCompileManager.compileReport("C:\\Users\\Weverton\\Desktop\\Sistema Financeiro\\SFfinal\\rel\\ContasemAtraso.jrxml");
            JasperPrint impressao = JasperFillManager.fillReport(report, parameters, jrRS);

            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
            JasperExportManager.exportReportToPdfFile(impressao, relatorio + dt.format(Calendar.getInstance().getTime()) + ".pdf");

            // fecha a conexão
            stmt.close();
        } catch (SQLException | JRException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório, " + e.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(e.getMessage());
        }

        try {
            SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
            File arquivo = new File(relatorio + dt.format(Calendar.getInstance().getTime()) + ".pdf");
            Desktop.getDesktop().open(arquivo);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir o relatório, " + ex.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(ex.getMessage());
        }

    }
      public java.sql.Date formataDataUtil(String data){
        
        try{
            DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date datautil = new java.util.Date();
            datautil=formatter.parse(data);
            System.out.println(datautil.toString());
            java.sql.Date datasql=new java.sql.Date(datautil.getTime());
            System.out.println(datasql.toString());
            
            return datasql;
                   
        }catch(Exception e){
        e.printStackTrace();
        return null;
        }    
    
    }

}
