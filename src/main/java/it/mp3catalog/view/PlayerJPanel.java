/*
 *    Copyright 2012 Luca Tagliani
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package it.mp3catalog.view;

import it.mp3catalog.model.Song;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 *
 * @author luca
 */
public class PlayerJPanel extends javax.swing.JPanel implements BasicPlayerListener {

    private MainJFrame frame;
    private BasicPlayer player;
    private BasicController control;
    private Song currentSong;
    private boolean paused;
    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    private DecimalFormat df = new DecimalFormat("00");
    private long currentSongTime;
    private Timer timer;

    /**
     * Creates new form PlayerJPanel
     */
    public PlayerJPanel(MainJFrame frame) {
        initComponents();
        this.frame = frame;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSlider2 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();

        setToolTipText("Player");

        jButton1.setText("Play");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSlider1.setValue(0);
        jSlider1.setDoubleBuffered(true);
        jSlider1.setOpaque(false);

        jButton2.setText("Stop");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Pause/Resume");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jSlider2.setMajorTickSpacing(50);
        jSlider2.setPaintLabels(true);
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addGap(56, 56, 56)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3))
                        .addComponent(jLabel1))
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // retrieve the current selected song
        try {
            currentSong = frame.getPropertiesPanel().editedSong;
            if (player != null) {
                player.stop();
                timer.stop();
            }
            Hashtable<Integer, JLabel> labels =
                    new Hashtable<Integer, JLabel>();
            labels.put(0, new JLabel("00:00"));

            Long value = currentSong.getDuration();
            String res = (value == null) ? "00:00" : "" + ((((Long) value) - (((Long) value) % 60)) / 60) + ":" + df.format((((Long) value) % 60));
            labels.put(currentSong.getDuration().intValue(), new JLabel(res));
            jSlider1.setLabelTable(labels);
            jSlider1.setPaintLabels(true);

            player = new BasicPlayer(); // new FileInputStream(frame.getPropertiesPanel().editedSong.getFileName()));
            player.addBasicPlayerListener(this);
            control = (BasicController) player;
            jSlider1.setMaximum(currentSong.getDuration().intValue() * 100);
            MyChangeListener listener = new MyChangeListener();
            jSlider1.addMouseListener(listener);
            jSlider1.addChangeListener(listener);

            control.open(new File(currentSong.getFileName()));
            currentSongTime = 0;
            control.play();

            timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jSlider1.setValue((int) currentSongTime * 100);
                }
            });
            timer.start();
        } catch (Exception ex) {
        }
        // play it
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            control.stop();
            timer.stop();
            jSlider1.setValue(0);
        } catch (BasicPlayerException ex) {
            System.out.println("Errore: " + ex.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            if (player != null) {
                if (!paused) {
                    player.pause();
                    timer.stop();
                    paused = true;
                } else {
                    player.resume();
                    timer.restart();
                    paused = false;
                }
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged
        try {
            player.setGain(((double) jSlider2.getValue()) / 100);
        } catch (BasicPlayerException ex) {
            //
        }
    }//GEN-LAST:event_jSlider2StateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void opened(Object stream, Map properties) {
        // throw new UnsupportedOperationException("Not supported yet.");
        System.out.println("Opened");
    }

    @Override
    public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
        // throw new UnsupportedOperationException("Not supported yet.");
        // System.out.println("Adjusting slider to " + Long.valueOf(microseconds * 100 / (currentSong.getDuration() * 1000000)).intValue());
        // jSlider1.setValue(Long.valueOf(microseconds / 1000000).intValue());
        if ((bytesread > 0) && ((currentSong.getFileSize() > 0))) {
            currentSongTime = currentSong.getDuration() * bytesread / currentSong.getFileSize();
        }
        jLabel1.setText(sdf.format(new Date(currentSongTime * 1000)));
    }

    @Override
    public void stateUpdated(BasicPlayerEvent event) {
        System.out.println("State changed");
        if (event.getCode() == BasicPlayerEvent.EOM) {
            jSlider1.setValue(0);
            timer.stop();
            jLabel1.setText("");
        }
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setController(BasicController controller) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public class MyChangeListener extends MouseAdapter implements ChangeListener {

        boolean animationMode = true;

        public void setAnimationMode(boolean mode) {
            this.animationMode = mode;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            timer.stop();
            setAnimationMode(false);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setAnimationMode(true);
            timer.start();
        }

        @Override
        public void stateChanged(ChangeEvent arg0) {
            if (!animationMode) {
                if (!((JSlider) arg0.getSource()).getValueIsAdjusting()) {
                    long skip = (((jSlider1.getValue() /100) * currentSong.getFileSize()) / currentSong.getDuration()) - currentSongTime;
                    try {
                        control.seek(skip);
                        currentSongTime = jSlider1.getValue() / 100;
                    } catch (BasicPlayerException ex) {
                    }
                }
            }
        }
    }
}