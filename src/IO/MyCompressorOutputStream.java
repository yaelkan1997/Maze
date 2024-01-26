package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {

    OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }


    /**
     * compresses the input byte array b by storing information about the maze size,
     * start and goal positions, and the maze cells in a more compact representation
     * using a compressed byte format.
     * @param b  input byte array
     * @return compressed version of the input array as a new byte array.
     */
    public static byte[] compress(byte[] b) {
        int row_amount;
        int col_amount = 0;
        while (b[col_amount] != 2){
            col_amount++;
        }
        row_amount = (b.length - 1) / (col_amount);
        int[] size = new int[]{row_amount, col_amount};
        ArrayList<Byte> final_array = new ArrayList<>();

        // Maze size
        addMazeSize(final_array, size[0], size[1]);

        //adds eight 0 bytes to final_array to represent the maze start and goal positions.
        for (int i = 0; i < 8; i++)
            final_array.add((byte) 0);

        byte[] temp;
        byte two = 0;
        for(int i=0; i< b.length;i += 8){
            temp = new byte[8];
            for (int j = 0; j < 8; j++) {
                if (i + j == b.length) {
                    i = b.length;
                    break;
                }
                byte cell = b[i + j];
                if (cell == 0 || cell == 1) {
                    temp[j] = cell;
                }
                if (cell == 2 ) {
                    temp[j] = cell;
                    two = 1;
                    j--;
                    i++;
                }
                if (cell == 3 || cell == 4) {
                    temp[j] = 0;
                    //store the position of the current cell in terms of row and column indices.
                    int[] pos = new int[2];
                    // calculate the row index of the current cell based on
                    // its position in the flattened array representation of the maze.
                    while ( i + j - size[1]>pos[0] * (size[1])){
                        pos[0]++;
                    }
                    pos[1] = i + j - (pos[0] * (size[1])) - two;
                    int k =0;
                    if(cell==3){
                        k=4;
                    }else {
                        k=8;
                    }
                    final_array.set(k++, (byte) (pos[0] >> 8));
                    final_array.set(k++, (byte) (pos[0]));
                    final_array.set(k++, (byte) (pos[1] >> 8));
                    final_array.set(k++, (byte) (pos[1]));
                }

            }
            final_array.add(ByteToBin(temp));
        }

        byte[] array = new byte[final_array.size()];
        for (int j = 0; j < final_array.size(); j++) {
            array[j] = final_array.get(j);
        }
        return array;
    }

    private static byte ByteToBin(byte[] b) {
        byte bin = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i] == 1) bin += (byte) Math.pow(2, i);
        }
        return bin;
    }

    /**
     * uses to extract the most significant 8 bits and
     * adds the rowAmount and colAmount values to finalArray as bytes.
     * @param rowAmount represents by size[0]
     * @param colAmount represents by size[1]
     */
    private static void addMazeSize(ArrayList<Byte> finalArray, int rowAmount, int colAmount) {
        finalArray.add((byte) (rowAmount >> 8));
        finalArray.add((byte) rowAmount);
        finalArray.add((byte) (colAmount >> 8));
        finalArray.add((byte) colAmount);
    }

    @Override
    public void write(int b) throws IOException {}

    public void write(byte[] b) throws IOException {
        for (Byte aByte : compress(b)) out.write(aByte);
    }
}