import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AudioClip {
    int duration = 2;
    int sampleRate = 44100;
    int x = duration * sampleRate * 2;
    byte[] data = {1, 2, 3, 4, 5, 6};

    int getSample( int index ) {
        int sample;
        int leastSig = (int) Array.get(data, index * 2 );
        int mostSig = (int) Array.get(data, (index * 2) + 1);
        mostSig <<= 8;
        sample = leastSig + mostSig;
        return sample;
    }
    void setSample(int index, int value) {
        int mask = (~value) << 8;
        int helper = (mask ^ 1) >> 8;
        int helperTwo = value >> 8;
        Array.set(data, index*2, helper);
        Array.set(data, (index*2) + 1, helperTwo);
    }

    public byte[] getData() {

        return Arrays.copyOf(data, x);
    }


    public static void main(String[] args) {

        AudioClip test = new AudioClip();
        int sos=test.getSample(2);
        System.out.println(sos);
        ArrayList<Integer> listOfInts = new ArrayList<>(6);
        listOfInts.add(1);
        listOfInts.add(5);
        listOfInts.add(6);
        listOfInts.add(20);
        listOfInts.add(15);
        listOfInts.add(39);
    }
}