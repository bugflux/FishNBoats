/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SimulationLaunch.java
 *
 * Created on May 27, 2011, 12:17:27 PM
 */
package pt.ua.sd.gui;

/**
 *
 * @author eriksson
 */
public class SimulationLaunch extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2376294771040084027L;
	private Faina caller;
    public SimulationLaunch(Faina caller) {
        initComponents();
        this.caller=caller;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtRMIAddress = new javax.swing.JTextField();
        txtRMIPort = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLogAddress = new javax.swing.JTextField();
        txtLogPort = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtOceanAddress = new javax.swing.JTextField();
        txtOceanPort = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtShoalsAddress = new javax.swing.JTextField();
        txtShoalsPort = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDirOperAddress = new javax.swing.JTextField();
        txtDirOperPort = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtBoatsAddress = new javax.swing.JTextField();
        txtBoatsPort = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTBoatsAddress = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTShoalsAddress = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTDiroperAddress = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("RMI server");

        jLabel2.setText("Address");

        jLabel3.setText("Port");

        txtRMIAddress.setText("127.0.0.1");

        txtRMIPort.setText("22145");

        jLabel4.setText("Log");

        txtLogAddress.setText("127.0.0.1");
        txtLogAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLogAddressActionPerformed(evt);
            }
        });

        txtLogPort.setText("22140");

        jLabel5.setText("Ocean");

        txtOceanAddress.setText("127.0.0.1");

        txtOceanPort.setText("22141");

        jLabel6.setText("Shoals");

        txtShoalsAddress.setText("127.0.0.1");

        txtShoalsPort.setText("22142");

        jLabel7.setText("DirOpers");

        txtDirOperAddress.setText("127.0.0.1");

        txtDirOperPort.setText("22143");

        jLabel8.setText("Boats");

        txtBoatsAddress.setText("127.0.0.1");

        txtBoatsPort.setText("22144");

        jLabel9.setText("Thread Boats");

        txtTBoatsAddress.setText("127.0.0.1");

        jLabel10.setText("Thread Shoals");

        txtTShoalsAddress.setText("127.0.0.1");
        txtTShoalsAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTShoalsAddressActionPerformed(evt);
            }
        });

        jLabel11.setText("Thread DirOper");

        txtTDiroperAddress.setText("127.0.0.1");

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTDiroperAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTBoatsAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTShoalsAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(txtLogAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(66, 66, 66)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtLogPort, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtRMIAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtRMIPort, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBoatsAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtBoatsPort, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDirOperAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDirOperPort, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOceanAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtOceanPort, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtShoalsAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtShoalsPort, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                        .addGap(67, 67, 67))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRMIAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRMIPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLogAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLogPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOceanAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOceanPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtShoalsAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtShoalsPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDirOperAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDirOperPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBoatsAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBoatsPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTBoatsAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTShoalsAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTDiroperAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String[] config = new String[15];
        config[0]=txtRMIAddress.getText();
        config[1]=txtRMIPort.getText();
        config[2]=txtLogAddress.getText();
        config[3]=txtLogPort.getText();
        config[4]=txtOceanAddress.getText();
        config[5]=txtOceanPort.getText();
        config[6]=txtShoalsAddress.getText();
        config[7]=txtShoalsPort.getText();
        config[8]=txtDirOperAddress.getText();
        config[9]=txtDirOperPort.getText();
        config[10]=txtBoatsAddress.getText();
        config[11]=txtBoatsPort.getText();
        config[12]=txtTBoatsAddress.getText();
        config[13]=txtTShoalsAddress.getText();
        config[14]=txtTDiroperAddress.getText();
        caller.setServersConfigurations(config);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtLogAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLogAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLogAddressActionPerformed

    private void txtTShoalsAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTShoalsAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTShoalsAddressActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtBoatsAddress;
    private javax.swing.JTextField txtBoatsPort;
    private javax.swing.JTextField txtDirOperAddress;
    private javax.swing.JTextField txtDirOperPort;
    private javax.swing.JTextField txtLogAddress;
    private javax.swing.JTextField txtLogPort;
    private javax.swing.JTextField txtOceanAddress;
    private javax.swing.JTextField txtOceanPort;
    private javax.swing.JTextField txtRMIAddress;
    private javax.swing.JTextField txtRMIPort;
    private javax.swing.JTextField txtShoalsAddress;
    private javax.swing.JTextField txtShoalsPort;
    private javax.swing.JTextField txtTBoatsAddress;
    private javax.swing.JTextField txtTDiroperAddress;
    private javax.swing.JTextField txtTShoalsAddress;
    // End of variables declaration//GEN-END:variables
}