import synthesizer.GuitarString;

/**
 *  is similar to GuitarHeroLite, but supports a total of 37 notes
 *  on the chromatic scale from 110Hz to 880Hz.
 */
public class GuitarHero {
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        GuitarString[] gs = new GuitarString[37];
        for (int i=0; i<37; i++) {
            double concert = 440 * Math.pow(2, (i-24)/12);
            gs[i] = new GuitarString(concert);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index != -1) {
                    gs[index].pluck();
                } else{
                    continue;
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (int i=0; i<37; i++) {
                sample += gs[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i=0; i<37; i++) {
                gs[i].tic();
            }
        }
    }
}
