package Sequences;

import java.util.Arrays;
import java.util.Random;

public class Generator
{
    public static String randomDNAString(int dnaLength) {
        Random rand = new Random();
        StringBuilder dna = new StringBuilder();
        for(int i=0;i<dnaLength;i++) {
            switch(rand.nextInt(4)) {
                case 0:
                    dna.append("A");
                    break;
                case 1:
                    dna.append("C");
                    break;
                case 2:
                    dna.append("G");
                    break;
                case 3:
                    dna.append("T");
                    break;
            }
        }
        return dna.toString();
    }

    public static String randomBinaryString(int sequenceLength)
    {
        Random rand = new Random();
        StringBuilder binarySequence = new StringBuilder();
        for(int i=0;i<sequenceLength;i++)
        {
            switch(rand.nextInt(2))
            {
                case 0:
                    binarySequence.append("0");
                    break;
                case 1:
                    binarySequence.append("1");
                    break;
            }

        }
        return binarySequence.toString();
    }

    public static String[] randomAllBinarySequences(int sequenceLength)
    {
        int[] arrA = new int[sequenceLength];

        String [] sequences = new String[sequenceLength];

        nBits(sequenceLength,arrA);

        for (int i=0; i<sequenceLength; i++)
        {
            sequences[i] = Integer.toString(arrA[i]);
        }

        return sequences;
    }


    private static void nBits(int n, int[] arrA)
    {
        if (n > 0)
        {
            arrA[n - 1] = 0;
            nBits(n - 1,arrA);
            arrA[n - 1] = 1;
            nBits(n - 1,arrA);
        }
    }


}
