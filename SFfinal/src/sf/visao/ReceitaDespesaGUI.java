/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.visao;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import sf.controle.DespesasReceitasCONTROLE;
import sf.controle.GerenciarCategoriaCONTROLE;
import sf.modelo.CategoriaBEAN;
import sf.modelo.DespesaBEAN;
import sf.modelo.ReceitaBEAN;

/**
 *
 * @author Administrador
 */
public class ReceitaDespesaGUI extends javax.swing.JFrame {

    /**
     * Creates new form ReceitaDespesaGUI
     */
    //pega o código do último lançamento no caso da necessidade de parcela
    DespesasReceitasCONTROLE drc = new DespesasReceitasCONTROLE();
    GerenciarCategoriaCONTROLE gcc;

    private int index = -10;
    private int tableCatCodigo = -10;
    private boolean DRATT = false;

    private final int TIPO_FIXO = 1;
    private final int TIPO_PARCELADO = 2;
    private final int TIPO_AVISTA = 3;

    private final boolean RECEITA = false;
    private final boolean DESPESA = true;

    private final int DIARIO = 1;
    private final int SEMANAL = 2;
    private final int MENSAL = 3;
    private final int BIMESTRAL = 4;
    private final int TRIMESTRAL = 5;
    private final int SEMESTRAL = 6;
    private final int ANUAL = 7;

    private ArrayList<DespesaBEAN> despesas;
    private ArrayList<ReceitaBEAN> receitas;

    public ReceitaDespesaGUI() {
        initComponents();
        meuInit();
        //      System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())));
        AutoCompleteDecorator.decorate(this.jComboCat);
        AutoCompleteDecorator.decorate(this.jComboTempo);
        AutoCompleteDecorator.decorate(this.jComboNumero);
        AutoCompleteDecorator.decorate(this.jComboPeriodo);

    }

    public void meuInit() {

        ArrayList<CategoriaBEAN> cb = drc.retornaCategoria();

        gcc = new GerenciarCategoriaCONTROLE();

        for (CategoriaBEAN c : cb) {
            jComboCat.addItem(c.getCatNome());

        }
        //  jComboCat.addItem("Nova Sub-Categoria");

        jFormattedData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())));

        jPanel3.setVisible(false);
        jPanelFixo.setVisible(false);
        jPanelParcela.setVisible(false);

        jRadioDespesa.setSelected(true);

        carregaLancamentos();

    }

    public void carregaLancamentos() {
        despesas = drc.pegaDespesa();
        receitas = drc.pegaReceita();

        String[] colunas = new String[]{"Valor", "Data", "Categoria", "Pago", "Descrição"};

        DefaultTableModel tablemodel = new DefaultTableModel(null, colunas);

        String[] linha = new String[]{"", "", "", ""};

        for (DespesaBEAN c : despesas) {

            String pago;

            if (c.getDesPago() == true) {
                pago = "Sim";
            } else {
                pago = "Não";
            }

            double valor = drc.getValor(c.getDesCod(), DESPESA);
            linha = new String[]{
                valor + "",
                drc.getData(c.getDesCod(), DESPESA) + "",
                drc.getCat(c.getDes_catCod()) + "",
                pago + "",
                c.getDesDesc() + ""
            };
            tablemodel.addRow(linha);
        }

        jtDespesas.setModel(tablemodel);

        tablemodel = new DefaultTableModel(null, colunas);

        for (ReceitaBEAN r : receitas) {

            String pago;

            if (r.getRecPago() == true) {
                pago = "Sim";
            } else {
                pago = "Não";
            }

            linha = new String[]{String.valueOf(drc.getValor(r.getRecCod(), RECEITA)),
                drc.getData(r.getRecCod(), RECEITA),
                drc.getCat(r.getRec_catCod()),
                pago,
                r.getRecDesc()};
            tablemodel.addRow(linha);
        }

        jtReceita.setModel(tablemodel);

    }

    public void pegaLancamento(int index, boolean tipo) {

        if (tipo == DESPESA) {
            tableCatCodigo = despesas.get(index).getDes_catCod();
            DRATT = true;

            jRadioDespesa.setSelected(true);
            jRadioReceita.setSelected(false);
            jFormattedValor.setText(jtDespesas.getModel().getValueAt(index, 0).toString());
            jFormattedData.setText(jtDespesas.getModel().getValueAt(index, 1).toString());
            jTextDesc.setText(jtDespesas.getModel().getValueAt(index, 3).toString());

            if (despesas.get(index).getDesPago() == true) {
                jRadioPago.setSelected(true);
            } else {
                jRadioPago.setSelected(false);
            }

        } else if (tipo == RECEITA) {
            tableCatCodigo = receitas.get(index).getRec_catCod();
            DRATT = false;

            jRadioDespesa.setSelected(false);
            jRadioReceita.setSelected(true);
            jFormattedValor.setText(jtReceita.getModel().getValueAt(index, 0).toString());
            jFormattedData.setText(jtReceita.getModel().getValueAt(index, 1).toString());
            jTextDesc.setText(jtReceita.getModel().getValueAt(index, 3).toString());

            if (receitas.get(index).getRecPago() == true) {
                jRadioPago.setSelected(true);
            } else {
                jRadioPago.setSelected(false);
            }

        }
    }

    public void botaoExcluir() {
        int cod;

        if (index >= 0) {
            System.out.println(index);
            if (DRATT == DESPESA) {
                cod = despesas.get(index).getDesCod();
            } else {
                cod = receitas.get(index).getRecCod();
            }

            drc.deleta(cod, DRATT);
            index = -10;
        } else {
            JOptionPane.showMessageDialog(null, "Você não selecionou um lançamento", "Notificação", WIDTH);
        }

    }

    public void botaoAtualizar() {
        if (tableCatCodigo > 0) {
            botaoExcluir();
            System.out.println(index);
            tableCatCodigo = -10;
            botaoInserir();
            carregaLancamentos();
        }

    }

    public void botaoInserir() {

        if (!jFormattedData.getText().equals(" ")
                && !jFormattedValor.getText().equals(" ")) {

            drc.selecionaCategoria(jComboCat.getSelectedIndex());

            double VALOR = Double.parseDouble(jFormattedValor.getText());

            boolean REPETIR = false;

            boolean PAGO = false;

            if (jRadioPago.isSelected()) {

                PAGO = true;

            }

            if (jRadioRepetir.isSelected()) {

                REPETIR = true;

            }

            String DESCRICAO = " ";
            if (!jTextDesc.getText().equals(" ")) {

                DESCRICAO = jTextDesc.getText();

            }
            Date DATAFINAL = new Date();
            try {

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                DATAFINAL = formatter.parse(jFormattedData.getText());

            } catch (Exception e) {

            }

            int PERIODO;

            if (jRadioRepetir.isSelected()) {

                if (jRadioFixo.isSelected()) {

                    PERIODO = jComboPeriodo.getSelectedIndex() + 1;

                    if (jRadioDespesa.isSelected()) {
                        drc.adicionaDespesa(DESCRICAO, PAGO, 7, true);
                        drc.adicionaParcela(VALOR, DATAFINAL, PAGO, 7, PERIODO, DESPESA, TIPO_FIXO);

                    } else if (jRadioReceita.isSelected()) {
                        drc.adicionaReceita(DESCRICAO, PAGO, 7, true);
                        drc.adicionaParcela(VALOR, DATAFINAL, PAGO, 7, PERIODO, RECEITA, TIPO_FIXO);
                    }

                } else if (jRadioParcelado.isSelected()) {

                    PERIODO = jComboTempo.getSelectedIndex() + 1;
                    int NROPARCELA = jComboNumero.getSelectedIndex() + 1;

                    if (jRadioDespesa.isSelected()) {

                        drc.adicionaDespesa(DESCRICAO, PAGO, NROPARCELA, false);
                        drc.adicionaParcela(VALOR, DATAFINAL, PAGO, NROPARCELA, PERIODO, DESPESA, TIPO_PARCELADO);

                    } else if (jRadioReceita.isSelected()) {

                        drc.adicionaReceita(DESCRICAO, PAGO, NROPARCELA, false);
                        drc.adicionaParcela(VALOR, DATAFINAL, PAGO, NROPARCELA, PERIODO, RECEITA, TIPO_PARCELADO);

                    }

                }

            } else if (jRadioDespesa.isSelected()) {

                drc.adicionaDespesa(DESCRICAO, PAGO, 1, false);
                drc.adicionaParcela(VALOR, DATAFINAL, PAGO, 1, -1, DESPESA, TIPO_AVISTA);

            } else if (jRadioReceita.isSelected()) {
                drc.adicionaReceita(DESCRICAO, PAGO, 1, false);
                drc.adicionaParcela(VALOR, DATAFINAL, PAGO, 1, -1, RECEITA, TIPO_AVISTA);

            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jFormattedData = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jFormattedValor = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboCat = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jTextDesc = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jRadioFixo = new javax.swing.JRadioButton();
        jRadioParcelado = new javax.swing.JRadioButton();
        jPanelParcela = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboNumero = new javax.swing.JComboBox();
        jComboTempo = new javax.swing.JComboBox();
        jPanelFixo = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jComboPeriodo = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtDespesas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jRadioPago = new javax.swing.JRadioButton();
        jRadioRepetir = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtReceita = new javax.swing.JTable();
        jButtonInserir = new javax.swing.JButton();
        jRadioDespesa = new javax.swing.JRadioButton();
        jRadioReceita = new javax.swing.JRadioButton();
        jButtonAtualizar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lançamentos");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setPreferredSize(new java.awt.Dimension(795, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(752, 600));

        jLabel1.setText("Tipo de lançamento:");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados do lançamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel3.setText("Data:");

        try {
            jFormattedData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedDataActionPerformed(evt);
            }
        });

        jLabel4.setText("Valor:");

        jFormattedValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedValorActionPerformed(evt);
            }
        });

        jLabel5.setText("Categoria:");

        jComboCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCatActionPerformed(evt);
            }
        });

        jLabel6.setText("Descrição:");

        jTextDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDescActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Recorrência do lançamento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 204))); // NOI18N

        buttonGroup1.add(jRadioFixo);
        jRadioFixo.setText("Fixo");
        jRadioFixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioFixoActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioParcelado);
        jRadioParcelado.setText("Parcelado");
        jRadioParcelado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioParceladoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioFixo)
                    .addComponent(jRadioParcelado))
                .addContainerGap(214, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioFixo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioParcelado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelParcela.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Parcelada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel7.setText("Número de parcelas:");

        jComboNumero.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));

        jComboTempo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "dias", "semanas", "meses", "bimestres", "trimestres", "semestres", "anos" }));

        javax.swing.GroupLayout jPanelParcelaLayout = new javax.swing.GroupLayout(jPanelParcela);
        jPanelParcela.setLayout(jPanelParcelaLayout);
        jPanelParcelaLayout.setHorizontalGroup(
            jPanelParcelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParcelaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelParcelaLayout.setVerticalGroup(
            jPanelParcelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelParcelaLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanelParcelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelFixo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fixo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel8.setText("Repetir");

        jComboPeriodo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "diária", "semanal", "mensal", "bimestral", "trimestral", "semestral", "anual", " " }));
        jComboPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPeriodoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelFixoLayout = new javax.swing.GroupLayout(jPanelFixo);
        jPanelFixo.setLayout(jPanelFixoLayout);
        jPanelFixoLayout.setHorizontalGroup(
            jPanelFixoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFixoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanelFixoLayout.setVerticalGroup(
            jPanelFixoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFixoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanelFixoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jtDespesas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Receitas", "Despesas"
            }
        ));
        jtDespesas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtDespesasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jtDespesas);

        jLabel2.setText("Já foi paga?:");

        jRadioPago.setText("Sim");

        jRadioRepetir.setText("Sim");
        jRadioRepetir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioRepetirActionPerformed(evt);
            }
        });

        jLabel9.setText("Repetir?:");

        jtReceita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Receitas", "Despesas"
            }
        ));
        jtReceita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtReceitaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jtReceita);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboCat, 0, 287, Short.MAX_VALUE)
                                    .addComponent(jTextDesc)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFormattedData)
                                    .addComponent(jFormattedValor)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioRepetir)
                                    .addComponent(jRadioPago))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(333, 333, 333))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jRadioPago))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioRepetir)
                            .addComponent(jLabel9)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelParcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonInserir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Inserir.png"))); // NOI18N
        jButtonInserir.setText("Inserir");
        jButtonInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInserirActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioDespesa);
        jRadioDespesa.setText("Despesa");

        buttonGroup2.add(jRadioReceita);
        jRadioReceita.setText("Receita");

        jButtonAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Editar.png"))); // NOI18N
        jButtonAtualizar.setText("Editar");
        jButtonAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtualizarActionPerformed(evt);
            }
        });

        jButtonExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Deletar.png"))); // NOI18N
        jButtonExcluir.setText("Excluir");
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Cancelar.png"))); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jButtonInserir)
                .addGap(108, 108, 108)
                .addComponent(jButtonAtualizar)
                .addGap(101, 101, 101)
                .addComponent(jButtonExcluir)
                .addGap(83, 83, 83)
                .addComponent(jButton1)
                .addContainerGap(120, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 829, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(jRadioDespesa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jRadioReceita, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioDespesa)
                    .addComponent(jRadioReceita))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInserir)
                    .addComponent(jButtonAtualizar)
                    .addComponent(jButtonExcluir)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 861, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFormattedDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedDataActionPerformed

    private void jTextDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDescActionPerformed

    private void jRadioFixoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioFixoActionPerformed
        // TODO add your handling code here:
        jPanelFixo.setVisible(true);
        jPanelParcela.setVisible(false);
    }//GEN-LAST:event_jRadioFixoActionPerformed

    private void jButtonInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInserirActionPerformed

        // TODO add your handling code here:
        //   BotaoInserir();
        botaoInserir();
        carregaLancamentos();


    }//GEN-LAST:event_jButtonInserirActionPerformed

    private void jRadioParceladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioParceladoActionPerformed
        // TODO add your handling code here:
        jPanelFixo.setVisible(false);
        jPanelParcela.setVisible(true);
    }//GEN-LAST:event_jRadioParceladoActionPerformed

    private void jFormattedValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedValorActionPerformed

    private void jComboPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPeriodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboPeriodoActionPerformed

    private void jComboCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboCatActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        // TODO add your handling code here:
        botaoExcluir();
        carregaLancamentos();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jRadioRepetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioRepetirActionPerformed
        // TODO add your handling code here:
        if (jPanel3.isVisible() == false) {

            jPanel3.setVisible(true);

        } else if (jPanel3.isVisible() == true) {

            jPanel3.setVisible(false);

        }
    }//GEN-LAST:event_jRadioRepetirActionPerformed

    private void jtDespesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtDespesasMouseClicked
        // TODO add your handling code here:
        int linha = jtDespesas.getSelectedRow();
        index = linha;
        pegaLancamento(linha, DESPESA);


    }//GEN-LAST:event_jtDespesasMouseClicked

    private void jtReceitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtReceitaMouseClicked
        // TODO add your handling code here:
        int linha = jtReceita.getSelectedRow();
        index = linha;
        pegaLancamento(linha, RECEITA);
    }//GEN-LAST:event_jtReceitaMouseClicked

    private void jButtonAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtualizarActionPerformed
        // TODO add your handling code here:
        botaoAtualizar();
    }//GEN-LAST:event_jButtonAtualizarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReceitaDespesaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReceitaDespesaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReceitaDespesaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReceitaDespesaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReceitaDespesaGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAtualizar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonInserir;
    private javax.swing.JComboBox jComboCat;
    private javax.swing.JComboBox jComboNumero;
    private javax.swing.JComboBox jComboPeriodo;
    private javax.swing.JComboBox jComboTempo;
    private javax.swing.JFormattedTextField jFormattedData;
    private javax.swing.JFormattedTextField jFormattedValor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelFixo;
    private javax.swing.JPanel jPanelParcela;
    private javax.swing.JRadioButton jRadioDespesa;
    private javax.swing.JRadioButton jRadioFixo;
    private javax.swing.JRadioButton jRadioPago;
    private javax.swing.JRadioButton jRadioParcelado;
    private javax.swing.JRadioButton jRadioReceita;
    private javax.swing.JRadioButton jRadioRepetir;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextDesc;
    private javax.swing.JTable jtDespesas;
    private javax.swing.JTable jtReceita;
    // End of variables declaration//GEN-END:variables
}
