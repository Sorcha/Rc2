package Sequences;

import Distance.HammingDistance;
import Utils.StdRandom;

import java.util.Random;

public class Mutation
{

    public static int[] mutationsCount;

    public static void InitCount(int size){

        mutationsCount = new int[size];
    }

    public static String dnaMutation(String sequence, int numberMutations)
    {
        HammingDistance hd = new HammingDistance();
        final String A = "A";
        final String C = "C";
        final String G = "G";
        final String T = "T";
        String prefix;
        String suffix;
        String newLetter;
        String newSequence = new String(sequence);
        int index;

        Random randomSource;
        randomSource = new Random(

        );

        int count = 0;

        while (numberMutations > count)
        {
            // Determine the position of the character to be
            // mutated.
            index = randomSource.nextInt(newSequence.length());

            // Extract whatever comes before (the prefix) and after
            // (the suffix) the character to be mutated.
            prefix = newSequence.substring(0, index);
            suffix = newSequence.substring(index + 1,
                    newSequence.length());

            // Generate a random DNA sequence letter.
            newLetter = "";
            switch (randomSource.nextInt(4))
            {
                case 0:
                    newLetter = A;
                    break;
                case 1:
                    newLetter = C;
                    break;
                case 2:
                    newLetter = G;
                    break;
                case 3:
                    newLetter = T;
                    break;
            }


            newSequence = prefix + newLetter + suffix;

            if(count+1 == hd.compare(sequence, newSequence))
            {
                ++count;
            }
        }

        System.out.println("Sequence " + sequence);
        System.out.println("Mutation " + newSequence);

        return newSequence;
    }

    public static String binaryMutation(String binaryString, double p)
    {
       // Random rand = new Random();



        StringBuilder mutationBinarySequence = new StringBuilder();

        for (int i= 0; i < binaryString.length(); i++)
        {
           // double r = rand.nextDouble();

            double r = StdRandom.uniform();
            //System.out.println(r);

            char character = binaryString.charAt(i);

            if(r<p)
            {
                //++mutationsCount[i];
                mutationBinarySequence.append(character == '1'?'0':'1');
            }else
                {
                    mutationBinarySequence.append(character);
                }
        }

        return mutationBinarySequence.toString();

    }

    public static String binaryMutationWithPrefixNumberOfMutation(String binaryString, double p, int numberOfMutation)
    {
        Random rand = new Random();

        StringBuilder mutationBinarySequence = new StringBuilder(binaryString.length());

        int count = 0;

        while (count < numberOfMutation)
        {
            for (int i= 0; i < binaryString.length(); i++)
            {
                double r = rand.nextDouble();

                char character = binaryString.charAt(i);

                if(r>=p && count < numberOfMutation)
                {
                    ++count;
                    mutationBinarySequence.insert(i,character == '1'?'0':'1');
                }else
                {
                    mutationBinarySequence.insert(i,character);
                }
            }

            if(count >= numberOfMutation)
            {
                break;
            }else
                count = 0;
        }



        return mutationBinarySequence.toString();

    }
}
