package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    OutputStream out;

    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }
    @Override
    public void write(int b) throws IOException {

    }

    /**
     *  implements a compression technique as we told to do in the work assignment
     *  where consecutive occurrences of the same byte in the array are replaced
     *  by a single byte followed by a count.
     */
    public void write(byte[] b) throws IOException {
        if (b.length == 0) {
            return;
        }

        int startIndex = 0;
        int currentIndex = 1;

        while (currentIndex < b.length) {
            while (currentIndex < b.length && b[startIndex] == b[currentIndex]) {
                currentIndex++;
            }

            int count = currentIndex - startIndex;
            write(b[startIndex], count);
            startIndex = currentIndex;
        }
    }

    /**
     * responsible for writing the compressed data.
     * @param b represents the value
     * @param amount represents the count of consecutive occurrences.
     */
    private void write(byte b, int amount) throws IOException {
        while (amount > 0) {
            // ensures that the count value does not exceed the maximum value
            byte count = (byte) Math.min(255, amount);
            out.write(b);
            out.write(count);
            amount -= 255;
        }
    }
}
