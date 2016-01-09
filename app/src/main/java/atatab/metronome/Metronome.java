package atatab.metronome;

/**
 * Created by leandro.alves on 1/7/2016.
 */
public class Metronome {

    private double bpm;
    private int beat;
    private int noteValue;
    private int silence;

    private double beatSound;
    private double sound;
    private final int tick = 1000;

    private boolean play = true;

    private AudioGen audioGen = new AudioGen(8000);
    private double[] soundTickArray;
    private double[] soundTockArray;
    private double[] silenceSoundArray;
    private int currentBeat = 1;

    public Metronome() {
        audioGen.createPlayer();
    }

    public void calcSilence() {
        silence = (int) (((60/bpm)*8000)-tick);
        soundTickArray = new double[this.tick];
        soundTockArray = new double[this.tick];
        silenceSoundArray = new double[this.silence];
        double[] tick = audioGen.getSineWave(this.tick, 8000, beatSound);
        double[] tock = audioGen.getSineWave(this.tick, 8000, sound);
        for(int i=0;i<this.tick;i++) {
            soundTickArray[i] = tick[i];
            soundTockArray[i] = tock[i];
        }
        for(int i=0;i<silence;i++)
            silenceSoundArray[i] = 0;
    }

    public void play() {
        calcSilence();
        do {
            if(currentBeat == 1)
                audioGen.writeSound(soundTockArray);
            else
                audioGen.writeSound(soundTickArray);
            audioGen.writeSound(silenceSoundArray);
            currentBeat++;
            if(currentBeat > beat)
                currentBeat = 1;
        } while(play);
    }

    public void stop() {
        play = false;
        audioGen.destroyAudioTrack();
    }

    public double getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {

        this.bpm = bpm;
        calcSilence();
    }

    public int getNoteValue() {
        return noteValue;
    }

    public void setNoteValue(int bpmetre) {
        this.noteValue = bpmetre;
    }

    public int getBeat() {
        return beat;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public double getBeatSound() {
        return beatSound;
    }

    public void setBeatSound(double sound1) {
        this.beatSound = sound1;
    }

    public double getSound() {
        return sound;
    }

    public void setSound(double sound2) {
        this.sound = sound2;
    }

}