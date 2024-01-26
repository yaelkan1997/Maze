package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {return 0;}

    /**
     * Decompresses data compressed
     * @param b the buffer into which the data is read.
     * @return the total number of bytes read into the buffer, or -1 if
     * there is no more data because the end of th stream has been reached.
     */
    @Override
    public int read(byte[] b) throws IOException {
        // the 6 indicates = Row size, column size, start row, start col, goal row, goal col
        int[] mazeInfo = new int[6];
        int j=0;
        while (j<6) {
            byte[] reads = new byte[2];
            for (int i = 0; i < 2; i++) {
                reads[i] = (byte) in.read();
            }
            //convert two bytes into a single integer value and store it in the mazeInfo array.
            mazeInfo[j] = (reads[0] & 0xFF) * 256 + (reads[1] & 0xFF);
            j++;
        }
        int i = 0;
        int read = -1;
        byte[] converted;

        do {
            // Check if i has reached the row size in mazeInfo
            if (i == mazeInfo[0]) {
                b[i++] = 2;
                continue;
            }
            read = in.read();
            // Break the loop if the end of the stream is reached
            /*if (read == -1)
                break;*/
            // Convert the read byte into a binary representation
            byte[] bin = new byte[8];
            char[] c = String.format("%8s", Integer.toBinaryString(read & 0xFF)).replace(' ', '0').toCharArray();
            for ( int x = 0; x < 8; x++)
                if (c[x] == '1')
                    bin[x] = 1;

            converted = bin;
            // Process the converted bytes and store them in the b array
            for ( j = 7; j >= 0; j--) {
                if (i == b.length) {
                    in.readAllBytes();
                    break;
                }
                byte temp = converted[j];
                if (i == mazeInfo[1])
                    b[i++] = 2;
                b[i++] = temp;
            }


        } while (read != -1);//end of the stream has been reached.

        processMazeInfo(mazeInfo,b);
        return i;
    }

    /**
     * calculates the indices within the `b` array for the starting and goal
     * positions based on the row and column information provided in `mazeInfo`.
     * then assigns specific values (`3` for the start position and `4` for the goal position)
     * at those calculated indices in the `b` array, representing the maze structure
     */
    private void processMazeInfo(int[] mazeInfo,byte[] b){
        int index = mazeInfo[2] * mazeInfo[1];
        if (index > 0){
            index= index + 1;
        }
        else {
            index=0;
        }
        index =index+ mazeInfo[3];
        b[index] = 3;

        index = mazeInfo[1] * mazeInfo[4];
        if (index > 0){
            index= index + 1;
        }
        else {
            index=0;
        }
        index =index+ mazeInfo[5];
        b[index] = 4;
    }

}