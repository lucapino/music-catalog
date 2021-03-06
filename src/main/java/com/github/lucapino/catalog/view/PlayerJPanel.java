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
package com.github.lucapino.catalog.view;

import com.github.lucapino.catalog.controller.Audio;
import com.github.lucapino.catalog.controller.DnDList;
import com.github.lucapino.catalog.controller.PlayListItem;
import com.github.lucapino.catalog.model.Song;
import com.github.lucapino.catalog.model.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import javax.sound.sampled.Mixer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javazoom.jl.player.basic.BasicController;
import javazoom.jl.player.basic.BasicPlayer;
import javazoom.jl.player.basic.BasicPlayerEvent;
import javazoom.jl.player.basic.BasicPlayerException;
import javazoom.jl.player.basic.BasicPlayerListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author luca
 */
public class PlayerJPanel extends JPanel implements BasicPlayerListener, NativeKeyListener {
    
    private Logger logger = LoggerFactory.getLogger(PlayerJPanel.class);
    private MainJFrame frame;
    private BasicPlayer player;
    private BasicController control;
    private Song song;
    private boolean paused;
    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    private long currentSongTime;
    private Timer timer;
    private int previousVolume = 50;

    /**
     * Creates new form PlayerJPanel
     */
    public PlayerJPanel(MainJFrame frame) {
        initComponents();
        this.frame = frame;
        // Set the event dispatcher to a swing safe executor service.
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        // Initialze native hook.
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            
            System.exit(1);
        }
        
        GlobalScreen.addNativeKeyListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playButton = new javax.swing.JButton();
        timeSlider = new javax.swing.JSlider();
        stopButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        volumeSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        previousButton = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bundle"); // NOI18N
        setToolTipText(bundle.getString("PLAYER")); // NOI18N

        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_play.png"))); // NOI18N
        playButton.setBorder(null);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusable(false);
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_play_rollover.png"))); // NOI18N
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        timeSlider.setValue(0);
        timeSlider.setDoubleBuffered(true);
        timeSlider.setOpaque(false);

        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_stop.png"))); // NOI18N
        stopButton.setBorderPainted(false);
        stopButton.setContentAreaFilled(false);
        stopButton.setFocusable(false);
        stopButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stopButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_stop_rollover.png"))); // NOI18N
        stopButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        pauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_pause.png"))); // NOI18N
        pauseButton.setBorderPainted(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setFocusable(false);
        pauseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pauseButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_pause_rollover.png"))); // NOI18N
        pauseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        volumeSlider.setMajorTickSpacing(50);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volumeSliderStateChanged(evt);
            }
        });

        jLabel1.setMaximumSize(new java.awt.Dimension(58, 15));
        jLabel1.setMinimumSize(new java.awt.Dimension(58, 15));
        jLabel1.setPreferredSize(new java.awt.Dimension(58, 15));

        nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_fwd.png"))); // NOI18N
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_fwd_rollover.png"))); // NOI18N
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        previousButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_rew.png"))); // NOI18N
        previousButton.setBorderPainted(false);
        previousButton.setContentAreaFilled(false);
        previousButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/player_rew_rollover.png"))); // NOI18N
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(previousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(timeSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {nextButton, pauseButton, playButton, previousButton, stopButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(volumeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(previousButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(playButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(pauseButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nextButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(stopButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {nextButton, pauseButton, playButton, previousButton});

    }// </editor-fold>//GEN-END:initComponents

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        play();
    }//GEN-LAST:event_playButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        try {
            control.stop();
            timer.stop();
            timeSlider.setValue(0);
        } catch (BasicPlayerException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_stopButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
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
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void volumeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volumeSliderStateChanged
//        try {
//            player.setGain(((double) volumeSlider.getValue()) / 100);
        Audio.setMasterOutputVolume(((float) volumeSlider.getValue()) / 100);
//        } catch (BasicPlayerException ex) {
//            //
//        }
    }//GEN-LAST:event_volumeSliderStateChanged

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        DnDList<PlayListItem> list = frame.getNavigatorPanel().getList();
        if (list.getModel().getSize() > 0) {
            int selected = list.getSelectedIndex();
            if (selected < list.getModel().getSize()) {
                PlayListItem nextItem = list.getModel().getElementAt(selected + 1);
                list.setSelectedIndex(selected + 1);
                play(nextItem);
            }
        }

    }//GEN-LAST:event_nextButtonActionPerformed

    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        DnDList<PlayListItem> list = frame.getNavigatorPanel().getList();
        if (list.getModel().getSize() > 0) {
            int selected = list.getSelectedIndex();
            if (selected > 0) {
                selected -= 1;
            }
            PlayListItem nextItem = list.getModel().getElementAt(selected);
            list.setSelectedIndex(selected);
            play(nextItem);
        }
    }//GEN-LAST:event_previousButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton playButton;
    private javax.swing.JButton previousButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JSlider timeSlider;
    private javax.swing.JSlider volumeSlider;
    // End of variables declaration//GEN-END:variables

    @Override
    public void opened(Object stream, Map properties) {
    }
    
    @Override
    public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
        if ((bytesread > 0) && ((song.getFileSize() > 0))) {
            currentSongTime = (((long) song.getDuration() * bytesread) / song.getFileSize()) - 1;
        }
        jLabel1.setText(sdf.format(new Date(currentSongTime * 1000)));
    }
    
    @Override
    public void stateUpdated(BasicPlayerEvent event) {
        if (event.getCode() == BasicPlayerEvent.EOM) {
            timeSlider.setValue(0);
            timer.stop();
            jLabel1.setText("");
            // try to advance
            nextButtonActionPerformed(null);
        }
    }
    
    @Override
    public void setController(BasicController controller) {
    }
    
    public void play() {
        play(frame.getPropertiesPanel().getEditedSong());
    }
    
    private void play(Song currentSong) {
        this.song = currentSong;
        // retrieve the current selected song
        try {
            if (player != null) {
                player.stop();
                timer.stop();
            }
            Hashtable<Integer, JLabel> labels = new Hashtable<>();
            labels.put(0, new JLabel("00:00"));
            
            int value = song.getDuration();
            String res = Utils.formatDuration(value);
            labels.put(Integer.valueOf(song.getDuration() * 100), new JLabel(res));
            timeSlider.setLabelTable(labels);
            timeSlider.setPaintLabels(true);
            
            player = new BasicPlayer(); // new FileInputStream(frame.getPropertiesPanel().editedSong.getFileName()));
            player.addBasicPlayerListener(this);
            control = (BasicController) player;
            timeSlider.setMaximum(song.getDuration() * 100);
            MyChangeListener listener = new MyChangeListener();
            timeSlider.addMouseListener(listener);
            timeSlider.addChangeListener(listener);
            
            control.open(new File(song.getFileName()));
            currentSongTime = 0;
            control.play();
            
            float currentVolume = Audio.getMasterOutputVolume();
            volumeSlider.setValue((int) (currentVolume * 100));
            timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeSlider.setValue((int) currentSongTime * 100);
                }
            });
            timer.start();
        } catch (Exception ex) {
        }
        // play it
    }
    
    public void play(PlayListItem item) {
        play(item.getSong());
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        switch (nke.getKeyCode()) {
            case NativeKeyEvent.VC_VOLUME_MUTE:
                if (volumeSlider.getValue() == 0) {
                    volumeSlider.setValue(previousVolume);
                } else {
                    previousVolume = volumeSlider.getValue();
                    volumeSlider.setValue(0);
                }
                break;
            case NativeKeyEvent.VC_VOLUME_DOWN:
                int newValue = Math.max(0, volumeSlider.getValue() - 10);
                volumeSlider.setValue(newValue);
                break;
            case NativeKeyEvent.VC_VOLUME_UP:
                newValue = Math.min(100, volumeSlider.getValue() + 10);
                volumeSlider.setValue(newValue);
                break;
            case NativeKeyEvent.VC_MEDIA_NEXT:
                nextButtonActionPerformed(null);
                break;
            case NativeKeyEvent.VC_MEDIA_PREVIOUS:
                previousButtonActionPerformed(null);
                break;
            case NativeKeyEvent.VC_MEDIA_PLAY:
                if (player.getStatus() == BasicPlayer.PLAYING) {
                    pauseButtonActionPerformed(null);
                } else if (player.getStatus() == BasicPlayer.STOPPED) {
                    playButtonActionPerformed(null);
                }
                break;
            case NativeKeyEvent.VC_MEDIA_STOP:
                stopButtonActionPerformed(null);
        }
    }
    
    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        
    }
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        
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
                    long skip = (((long) (timeSlider.getValue() / 100) * song.getFileSize()) / song.getDuration()) - currentSongTime;
                    boolean wasPlaying = player.getStatus() == BasicPlayer.PLAYING;
                    try {
                        control.stop();
                        control.open(new File(song.getFileName()));
                        control.seek(skip);
                        currentSongTime = timeSlider.getValue() / 100;
                        if (wasPlaying) {
                            control.play();
                        }
                    } catch (BasicPlayerException e) {
                    }
                }
            }
        }
    }
}
