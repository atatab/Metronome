package atatab.metronome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    Metronome m;
    private short noteValue;
    private short beats = 4;
    private double beatSound;
    private double sound;

    SeekBar bpmBar;

    Button playButton;
    Button stopButton;
    int value;
    TextView result;
    Thread metroThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bpmBar = (SeekBar) findViewById(R.id.BPMSeekBar);
        result = (TextView) findViewById(R.id.BPMText);
        value = bpmBar.getProgress()+40;
        m = new Metronome();
        noteValue = 4;
        beats = 4;
        beatSound = 2440;
        sound = 6440;

        bpmBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                value = progress + 40;
                result.setText(value + " BPM");
                m.setBpm(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playButton = (Button) findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                metroThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        m = new Metronome();
                        m.setBpm(value);
                        m.setBeat(beats);
                        m.setNoteValue(noteValue);
                        m.setBeatSound(beatSound);
                        m.setSound(sound);
                        m.play();
                    }
                });
                metroThread.start();

                stopButton.setEnabled(true);
                playButton.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Metronome Started at " + value + "BPM", Toast.LENGTH_LONG).show();
            }
        });

        stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setEnabled(false);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.stop();
                metroThread = null;

                stopButton.setEnabled(false);
                playButton.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Metronome Stoped", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onBackPressed(){
        m.stop();
        metroThread = null;
        Runtime.getRuntime().gc();
        finish();
    }

}
