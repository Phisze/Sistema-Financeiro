package sf.controle;

/* pacotes necess�rios */
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import sf.modelo.CategoriaDAO;

public class GeraRelatorioCategoria {

    public GeraRelatorioCategoria() {
    }

    /* Gera o Relatorio e visualiza-o */
    public static void gerarRelatorio(String query, String relatJasp)
            throws JRException, Exception {
        
        CategoriaDAO dao = new CategoriaDAO();
        ResultSet rs = dao.consulta(query);

        try {
            /* implementacaoo da interface JRDataSource para DataSource ResultSet */
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

            /* HashMap de parametros utilizados no relatório. 
            Sempre instanciados mesmo se nao tiver parâmetros */
            Map parameters = new HashMap();

            /* Preenche o relat�rio com os dados. 
            Gera o arquivo Cliente.jrprint */
            JasperViewer.viewReport(JasperFillManager.fillReport(relatJasp, parameters, jrRS));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório, " + e.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(e.getMessage());
        }

    }

}
