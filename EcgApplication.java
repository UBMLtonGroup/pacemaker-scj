import java.lang.Exception;
import javax.swing.UIManager;


public class EcgApplication extends javax.swing.JFrame {
    
    /* Main Calculation Objects */
    EcgParam paramOb;
    
    
    /* Main GUI-Window Objects*/
    EcgParamWindow paramWin;
    //EcgLogWindow logWin;
        
    /** Creates new form ecgApplication */
    public EcgApplication() {
        initComponents();
        initClasses();
        initWindow();
    }

    /*
     * Init Child Classes
     */
    private void initClasses(){
        paramOb = new EcgParam();
        //logWin = new EcgLogWindow();
        paramWin = new EcgParamWindow(paramOb);
    }    
    private void initWindow(){
        this.setSize(880,600);
        mainLabel.setSize(this.getWidth(),  this.getHeight());
        mainDesktop.add(paramWin);
        //mainDesktop.add(logWin);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        mainDesktop = new javax.swing.JDesktopPane();
        mainLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        appMenu = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JSeparator();
        quitMenu = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        paramMenu = new javax.swing.JMenuItem();
        systemMenu = new javax.swing.JMenu();
        generateMenu = new javax.swing.JMenuItem();
        sysLogMenu = new javax.swing.JMenuItem();
        //helpMenu = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JSeparator();
        aboutMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Pacemaker Simulator");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        mainLabel.setFont(new java.awt.Font("Serif", 3, 32));
        mainLabel.setForeground(new java.awt.Color(255, 255, 255));
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainLabel.setText("Pacemaker Simulator");
        mainLabel.setBounds(0, 0, 530, 260);
        mainDesktop.add(mainLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(mainDesktop, java.awt.BorderLayout.CENTER);

        appMenu.setText("Application");
        appMenu.add(jSeparator1);

        quitMenu.setText("Quit");
        quitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitMenuActionPerformed(evt);
            }
        });

        appMenu.add(quitMenu);

        jMenuBar1.add(appMenu);

        settingsMenu.setText("Settings");
        paramMenu.setText("ECG Parameters");
        paramMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paramMenuActionPerformed(evt);
            }
        });

        settingsMenu.add(paramMenu);

        jMenuBar1.add(settingsMenu);

        systemMenu.setText("Pacemaker");
        generateMenu.setText("DDD");
        generateMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateMenuActionPerformed(evt);
            }
        });

        systemMenu.add(generateMenu);

       /* sysLogMenu.setText("ECG System Log");
        sysLogMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sysLogMenuActionPerformed(evt);
            }
        });*/

        //systemMenu.add(sysLogMenu);

        jMenuBar1.add(systemMenu);

        //helpMenu.setText("Help");
        //helpMenu.add(jSeparator2);

       /* aboutMenu.setText("About ECG ...");
        aboutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuActionPerformed(evt);
            }
        });*/

        //helpMenu.add(aboutMenu);

        //jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        mainLabel.setSize(this.getWidth(),  this.getHeight());
    }//GEN-LAST:event_formComponentResized

    private void sysLogMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sysLogMenuActionPerformed
        // TODO add your handling code here:
        //logWin.show();
    }//GEN-LAST:event_sysLogMenuActionPerformed

    private void generateMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateMenuActionPerformed
        EcgPlotWindow plotWin;
        plotWin = new EcgPlotWindow(paramOb, this);
        mainDesktop.add(plotWin);
        plotWin.show();
    }//GEN-LAST:event_generateMenuActionPerformed

    private void paramMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paramMenuActionPerformed
        // TODO add your handling code here:       
        paramWin.show(); 
    }//GEN-LAST:event_paramMenuActionPerformed

    /*private void aboutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuActionPerformed
        // TODO add your handling code here:
        EcgAboutWindow aboutDialog = new EcgAboutWindow(this, true);
        aboutDialog.show();
        
    }//GEN-LAST:event_aboutMenuActionPerformed*/

    private void quitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitMenuActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_quitMenuActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        this.dispose();
        //System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new EcgApplication().show();

    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenu;
    private javax.swing.JMenu appMenu;
    private javax.swing.JMenuItem generateMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JDesktopPane mainDesktop;
    private javax.swing.JLabel mainLabel;
    private javax.swing.JMenuItem paramMenu;
    private javax.swing.JMenuItem quitMenu;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JMenuItem sysLogMenu;
    private javax.swing.JMenu systemMenu;
    // End of variables declaration//GEN-END:variables
    
}
