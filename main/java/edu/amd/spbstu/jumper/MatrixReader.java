package edu.amd.spbstu.jumper;


import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MatrixReader {
    public static int[][] ReadMatrix(String filename) {
        // read in the data
        ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
        Scanner input;
        try {
            input = new Scanner(new File(filename));

            while(input.hasNextLine())
            {
                Scanner colReader = new Scanner(input.nextLine());
                ArrayList col = new ArrayList();
                while(colReader.hasNextInt())
                {
                    col.add(colReader.nextInt());
                }
                a.add(col);
            }
        }
        catch (FileNotFoundException e) {
            Log.d("", "FileNotFoundException");
            System.out.println("FileNotFoundException");
        }

        int N = a.size();
        int M = a.get(0).size();
        int[][] res = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                res[i][j] = a.get(i).get(j);
            }
        }

        return res;
    }

}