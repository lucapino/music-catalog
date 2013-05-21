/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lucapino.catalog.view;

import com.github.lucapino.catalog.model.Song;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.jdesktop.swingx.MultiSplitLayout;

/**
 *
 * @author Tagliani
 */
public class StatusBarJPanel extends javax.swing.JPanel {

    private MainJFrame frame;
    private ResourceBundle bundle = ResourceBundle.getBundle("bundle");

    /**
     * Creates new form StatusBarJPanel
     */
    public StatusBarJPanel(MainJFrame frame) {
        this.frame = frame;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        propertiesToggleButton = new javax.swing.JToggleButton();
        coverToggleButton = new javax.swing.JToggleButton();
        lyricsToggleButton = new javax.swing.JToggleButton();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bundle"); // NOI18N
        jLabel1.setText(bundle.getString("STATUSBAR")); // NOI18N

        propertiesToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/properties.png"))); // NOI18N
        propertiesToggleButton.setSelected(true);
        propertiesToggleButton.setToolTipText("Hide properties");
        propertiesToggleButton.setContentAreaFilled(false);
        propertiesToggleButton.setOpaque(true);
        propertiesToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertiesToggleButtonActionPerformed(evt);
            }
        });

        coverToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cover.png"))); // NOI18N
        coverToggleButton.setSelected(true);
        coverToggleButton.setToolTipText("Hide cover");
        coverToggleButton.setContentAreaFilled(false);
        coverToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coverToggleButtonActionPerformed(evt);
            }
        });

        lyricsToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lyrics.png"))); // NOI18N
        lyricsToggleButton.setSelected(true);
        lyricsToggleButton.setToolTipText("Hide lyrics");
        lyricsToggleButton.setContentAreaFilled(false);
        lyricsToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lyricsToggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 415, Short.MAX_VALUE)
                .addComponent(propertiesToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coverToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lyricsToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(propertiesToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(coverToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lyricsToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void propertiesToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertiesToggleButtonActionPerformed
//        if (propertiesToggleButton.isSelected()) {
//            propertiesToggleButton.setText(bundle.getString("HIDE PROPERTIES"));
//        } else {
//            propertiesToggleButton.setText(bundle.getString("SHOW PROPERTIES"));
//        }
        frame.getMultiSplitLayout().displayNode("properties", propertiesToggleButton.isSelected());
    }//GEN-LAST:event_propertiesToggleButtonActionPerformed

    private void coverToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coverToggleButtonActionPerformed
        if (coverToggleButton.isSelected()) {
//            coverToggleButton.setText(bundle.getString("HIDE COVER"));
            // update image
            Song editedSong = frame.getPropertiesPanel().getEditedSong();
            if (editedSong != null) {
                File realFile = new File(editedSong.getFileName());
                if (realFile.exists()) {
                    try {
                        AudioFile audioFile = AudioFileIO.read(realFile);
                        Tag tag = audioFile.getTag();
                        if (tag.getFirstArtwork() != null) {
                            try (InputStream in = new ByteArrayInputStream(tag.getFirstArtwork().getBinaryData())) {
                                BufferedImage bImageFromConvert = ImageIO.read(in);
                                frame.getCoverPanel().setImage(bImageFromConvert);
                            }
                        } else {
                            frame.getCoverPanel().setImage(new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB));
                        }

                    } catch (Exception ex) {
                    }
                }
            }
//        } else {
//            coverToggleButton.setText(bundle.getString("SHOW COVER"));
        }
        frame.getMultiSplitLayout().displayNode("cover", coverToggleButton.isSelected());
    }//GEN-LAST:event_coverToggleButtonActionPerformed

    private void lyricsToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lyricsToggleButtonActionPerformed
        if (lyricsToggleButton.isSelected()) {
//            lyricsToggleButton.setText(bundle.getString("HIDE LYRICS"));
            // update lyrics
            Song editedSong = frame.getPropertiesPanel().getEditedSong();
            if (editedSong != null) {
                frame.getLyricsPanel().getLyricsTextArea().setText(editedSong.getLyrics());
                frame.getLyricsPanel().getLyricsTextArea().setCaretPosition(0);
            }
//        } else {
//            lyricsToggleButton.setText(bundle.getString("SHOW LYRICS"));
        }
        frame.getMultiSplitLayout().displayNode("lyrics", lyricsToggleButton.isSelected());
    }//GEN-LAST:event_lyricsToggleButtonActionPerformed

    public void syncButtonState() {
        MultiSplitLayout layout = frame.getMultiSplitLayout();
        if (!layout.getNodeForName("properties").isVisible()) {
            propertiesToggleButton.doClick();
        }
        if (layout.getNodeForName("lyrics") != null
                && !layout.getNodeForName("lyrics").isVisible()) {
            lyricsToggleButton.doClick();
        }
        if (layout.getNodeForName("cover") != null
                && !layout.getNodeForName("cover").isVisible()) {
            coverToggleButton.doClick();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton coverToggleButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToggleButton lyricsToggleButton;
    private javax.swing.JToggleButton propertiesToggleButton;
    // End of variables declaration//GEN-END:variables
}
