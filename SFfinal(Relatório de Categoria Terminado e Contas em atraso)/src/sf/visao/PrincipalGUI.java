/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sf.visao;

import java.awt.Color;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import sf.controle.DespesasReceitasCONTROLE;
import sf.controle.GerenciarCategoriaCONTROLE;
import sf.controle.PrincipalCONTROLE;
import sf.modelo.CategoriaBEAN;
import sf.modelo.DespesaBEAN;
import sf.modelo.ParcelaBEAN;
import sf.modelo.ReceitaBEAN;
import sf.visao.ReceitaDespesaGUI;

/**
 *
 * @author alunos
 */
public class PrincipalGUI extends javax.swing.JFrame {

    private boolean DESPESA = true;
    private boolean RECEITA = false;
    
    private DespesasReceitasCONTROLE drc= new DespesasReceitasCONTROLE();
    private PrincipalCONTROLE pc = new PrincipalCONTROLE();
    private GerenciarCategoriaCONTROLE gcc = new GerenciarCategoriaCONTROLE();

    /**
     * Creates new form Principal
     */
    public PrincipalGUI() {
        initComponents();
        meuInit();
        preencheTableContasAtraso();
        preencheTableReceitasAtraso();
        preencheTableContasVencer();
        preencheTableReceitasVencer();
    }

    public void meuInit() {
        double saldo = pc.somaSaldo();
        if (saldo > 0) {
            jLabel5.setForeground(Color.green);
        } else {
            jLabel5.setForeground(Color.red);
        }
        jLabel5.setText(String.valueOf(saldo));
    }
// Terminar para quando
    public void preencheTableContasAtraso() {
        ArrayList<ParcelaBEAN> pb = new ArrayList();
        
        ArrayList<DespesaBEAN> db= drc.pegaDespesa();
        ArrayList<CategoriaBEAN> cb=new ArrayList<CategoriaBEAN>();
        gcc.pegaCat(cb);
        String[] colunas = new String[]{"Data", "Categoria"," Valor"};
        DefaultTableModel model = new DefaultTableModel(null, colunas);

        
        pb = pc.retornaParcelas(DESPESA);
        for(ParcelaBEAN p: pb){
            if(p.getParData().before(new Date(System.currentTimeMillis()))){
                String categoria="";
               for(DespesaBEAN d: db){
                   if(d.getDesCod()==p.getPar_desCod()){
                       for(CategoriaBEAN c: cb){
                           if(c.getCatCod()==d.getDes_catCod()){
                               categoria=c.getCatNome();
                           }
                       }
                   }
               }
               String data = new SimpleDateFormat("dd/MM/yyyy").format(p.getParData());
                String[] linha = new String[]{data, categoria,String.valueOf(p.getParValor())};
                model.addRow(linha);
            }
           
        }
        jTable4.setModel(model);
        
    }
    public void preencheTableReceitasAtraso() {
        ArrayList<ParcelaBEAN> pb = new ArrayList();
        ArrayList<ReceitaBEAN> rb= drc.pegaReceita();
        
        ArrayList<CategoriaBEAN> cb=new ArrayList<CategoriaBEAN>();
        gcc.pegaCat(cb);
        String[] colunas = new String[]{"Data", "Categoria"," Valor"};
        DefaultTableModel model = new DefaultTableModel(null, colunas);

        
        pb = pc.retornaParcelas(RECEITA);
        for(ParcelaBEAN p: pb){
          
            if(p.getParData().before(new Date(System.currentTimeMillis()))){
                String categoria="";
               for(ReceitaBEAN d: rb){
                   if(d.getRecCod()==p.getPar_recCod()){
                       for(CategoriaBEAN c: cb){
                           if(c.getCatCod()==d.getRec_catCod()){
                               categoria=c.getCatNome();
                           }
                       }
                   }
               }
               String data = new SimpleDateFormat("dd/MM/yyyy").format(p.getParData());
                String[] linha = new String[]{data, categoria,String.valueOf(p.getParValor())};
                model.addRow(linha);
            }
           
        }
        jTable5.setModel(model);
    }
    public void preencheTableContasVencer() {
        ArrayList<ParcelaBEAN> pb = new ArrayList();
        
        ArrayList<DespesaBEAN> db= drc.pegaDespesa();
        ArrayList<CategoriaBEAN> cb=new ArrayList<CategoriaBEAN>();
        gcc.pegaCat(cb);
        String[] colunas = new String[]{"Data", "Categoria"," Valor"};
        DefaultTableModel model = new DefaultTableModel(null, colunas);

        
        pb = pc.retornaParcelas(DESPESA);
        for(ParcelaBEAN p: pb){
            if(p.getParData().after(new Date(System.currentTimeMillis()))){
                String categoria="";
               for(DespesaBEAN d: db){
                   if(d.getDesCod()==p.getPar_desCod()){
                       for(CategoriaBEAN c: cb){
                           if(c.getCatCod()==d.getDes_catCod()){
                               categoria=c.getCatNome();
                           }
                       }
                   }
               }
               String data = new SimpleDateFormat("dd/MM/yyyy").format(p.getParData());
                String[] linha = new String[]{data, categoria,String.valueOf(p.getParValor())};
                model.addRow(linha);
            }
           
        }
        jTable6.setModel(model);
        
    }
    
     public void preencheTableReceitasVencer() {
        ArrayList<ParcelaBEAN> pb = new ArrayList();
        ArrayList<ReceitaBEAN> rb= drc.pegaReceita();
        
        ArrayList<CategoriaBEAN> cb=new ArrayList<CategoriaBEAN>();
        gcc.pegaCat(cb);
        String[] colunas = new String[]{"Data", "Categoria"," Valor"};
        DefaultTableModel model = new DefaultTableModel(null, colunas);

        
        pb = pc.retornaParcelas(DESPESA);
        for(ParcelaBEAN p: pb){
            if(p.getParData().after(new Date(System.currentTimeMillis()))){
                String categoria="";
               for(ReceitaBEAN d: rb){
                   if(d.getRecCod()==p.getPar_recCod()){
                       for(CategoriaBEAN c: cb){
                           if(c.getCatCod()==d.getRec_catCod()){
                               categoria=c.getCatNome();
                           }
                       }
                   }
               }
               String data = new SimpleDateFormat("dd/MM/yyyy").format(p.getParData());
                String[] linha = new String[]{data, categoria,String.valueOf(p.getParValor())};
                model.addRow(linha);
            }
           
        }
        jTable7.setModel(model);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Moneteasy");

        jToolBar1.setRollover(true);
        jToolBar1.add(jSeparator1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Categoria.png"))); // NOI18N
        jButton1.setText("Nova Categoria");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator6);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Despesa.png"))); // NOI18N
        jButton3.setText("Nova Despesa");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Check Book-28 (1).png"))); // NOI18N
        jButton4.setText("Nova Receita");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Check Book-28 (2).png"))); // NOI18N
        jButton5.setText("Editar Categoria");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);
        jToolBar1.add(jSeparator5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Check Book-28 (3).png"))); // NOI18N
        jButton6.setText("Editar Despesa/Receita");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(452, 40, 0, 0);
        jPanel1.add(jLabel2);
        jLabel2.setBounds(105, 13, 326, 0);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/logo.png"))); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(350, 30, 350, 480);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("Saldo Atual:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(30, 30, 120, 17);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 0));
        jLabel5.setText("xxxx");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel5);
        jLabel5.setBounds(130, 30, 160, 20);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contas a Vencer", 0, 0, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(0, 51, 255));
        jPanel2.setLayout(null);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Data", "Categoria", "Descrição", "Valor"
            }
        ));
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTable6);

        jPanel2.add(jScrollPane6);
        jScrollPane6.setBounds(20, 40, 260, 160);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(700, 70, 300, 220);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Receitas a Receber", 0, 0, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(0, 51, 255));
        jPanel3.setLayout(null);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Data", "Categoria", "Descrição", "Valor"
            }
        ));
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTable7);

        jPanel3.add(jScrollPane7);
        jScrollPane7.setBounds(20, 40, 260, 160);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(702, 310, 300, 220);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Receitas em Atraso", 0, 0, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 255))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(0, 51, 255));
        jPanel4.setLayout(null);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Data", "Categoria", "Descrição", "Valor"
            }
        ));
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);

        jPanel4.add(jScrollPane5);
        jScrollPane5.setBounds(20, 40, 260, 160);

        jPanel1.add(jPanel4);
        jPanel4.setBounds(20, 310, 300, 220);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contas em Atraso", 0, 0, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 255))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(0, 51, 255));
        jPanel5.setLayout(null);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Data", "Categoria", "Descrição", "Valor"
            }
        ));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jPanel5.add(jScrollPane4);
        jScrollPane4.setBounds(20, 40, 260, 160);

        jPanel1.add(jPanel5);
        jPanel5.setBounds(20, 70, 300, 220);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Saving Book-28.png"))); // NOI18N
        jMenu2.setText("Cadastrar");
        jMenu2.setToolTipText("");

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Categoria.png"))); // NOI18N
        jMenu4.setText("Categoria");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Saving Book-28 (1).png"))); // NOI18N
        jMenuItem1.setText("Nova Categoria Principal");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        jMenu2.add(jMenu4);

        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Lançar.png"))); // NOI18N
        jMenu3.setText("Lançar");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Despesa.png"))); // NOI18N
        jMenuItem4.setText("Despesa");
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem4MouseClicked(evt);
            }
        });
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Check Book-28 (1).png"))); // NOI18N
        jMenuItem5.setText("Receita");
        jMenuItem5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem5MouseClicked(evt);
            }
        });
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Editar.png"))); // NOI18N
        jMenu5.setText("Editar");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu5ActionPerformed(evt);
            }
        });

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Check Book-28 (2).png"))); // NOI18N
        jMenuItem7.setText("Categoria");
        jMenuItem7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem7MouseClicked(evt);
            }
        });
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icone/Check Book-28 (3).png"))); // NOI18N
        jMenuItem6.setText("Despesa/Receita");
        jMenu5.add(jMenuItem6);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        new GerenciarCategoriaGUI().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
     //      DespesasReceitasGUI guiDespesa = new DespesasReceitasGUI("Despesa");
        //guiDespesa.setVisible(true);

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
       //    DespesasReceitasGUI guiDespesa = new DespesasReceitasGUI("Receita");
        //  guiDespesa.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new GerenciarCategoriaGUI().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        new GerenciarCategoriaGUI().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
         new ReceitaDespesaGUI().setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:
        int index=jTable4.getSelectedRow();
        pc.pagaParcela(index, DESPESA);
        preencheTableContasAtraso();
        meuInit();
    }//GEN-LAST:event_jTable4MouseClicked

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        // TODO add your handling code here:
        
        int index=jTable5.getSelectedRow();
        pc.pagaParcela(index, RECEITA);
         preencheTableReceitasAtraso();
         meuInit();
    }//GEN-LAST:event_jTable5MouseClicked

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        // TODO add your handling code here:
        int index=jTable6.getSelectedRow();
        pc.pagaParcela(index, DESPESA);
        preencheTableContasVencer();
        meuInit();
    }//GEN-LAST:event_jTable6MouseClicked

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked
        // TODO add your handling code here:
         int index=jTable7.getSelectedRow();
        pc.pagaParcela(index, RECEITA);
        preencheTableReceitasVencer();
        meuInit();
        
    }//GEN-LAST:event_jTable7MouseClicked

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
        // TODO add your handling code here:
        new GerenciarCategoriaGUI().setVisible(true);
    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jMenuItem7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem7MouseClicked
        // TODO add your handling code here:
        new GerenciarCategoriaGUI().setVisible(true);
    }//GEN-LAST:event_jMenuItem7MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new ReceitaDespesaGUI().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new ReceitaDespesaGUI().setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        new ReceitaDespesaGUI().setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem4MouseClicked
        // TODO add your handling code here:
        new ReceitaDespesaGUI().setVisible(true);
    }//GEN-LAST:event_jMenuItem4MouseClicked

    private void jMenuItem5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem5MouseClicked
        // TODO add your handling code here:
        new ReceitaDespesaGUI().setVisible(true);
    }//GEN-LAST:event_jMenuItem5MouseClicked

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
        // TODO add your handling code here:
        new ReceitaDespesaGUI().setVisible(true);
    }//GEN-LAST:event_jMenu5MouseClicked

    private void jMenu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu5ActionPerformed
        // TODO add your handling code here:
        new GerenciarCategoriaGUI().setVisible(true);
    }//GEN-LAST:event_jMenu5ActionPerformed

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        // TODO add your handling code here:
         new ReceitaDespesaGUI().setVisible(true);
    }//GEN-LAST:event_jMenu3MouseClicked

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed
        // TODO add your handling code here:
         new ReceitaDespesaGUI().setVisible(true);
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        meuInit();
    }//GEN-LAST:event_jLabel5MouseClicked

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
            java.util.logging.Logger.getLogger(PrincipalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable7;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
