package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream{

    InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {

        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }



    /**
     * reads compressed data from an input stream, where consecutive occurrences of the
     * same byte are represented by a single byte followed by a count.
     * @return total number of bytes read and stored in the byte array `b`.
     */
    @Override
    public int read(byte[] b) throws IOException {
        //keep track of the number of bytes read.
        int count = 0;
        //store the current byte read from the input stream
        int data;
        //store the count of consecutive occurrences of the byte
        int amount;
        do {
            data = in.read();
            if (data == -1)
                return count;
            amount = in.read();
            for(int i = 0; i < amount; i++)
                b[count++] = (byte) data;

        }
        while(amount != -1);
        return count;
    }
}
