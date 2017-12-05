import Distance.HammingDistance;
import Sequences.Mutation;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.WattsStrogatzGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {



    public static void main(String[] args)
    {
         //TestGenerator();
        //TestBinarySequencesWithoutTrials();
        //TestBinarySequencesUniformWithoutTrials();

        TestBinarySequences3();
        //TestBinarySequencesWithoutTrials();



    }

    private static void TestGenerator()
    {
        String sequence = Sequences.Generator.randomBinaryString(60);

        Sequences.Mutation.InitCount(60);

        for (int i=0; i<1000; i++)
        {
            Sequences.Mutation.binaryMutation(sequence,0.01);
        }

        for (int i = 0; i< Mutation.mutationsCount.length ; i++)
        {
            System.out.println(Mutation.mutationsCount[i]);
        }

    }




    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());

        }
    }

    private static void TestDnaGraph() {
        int hdThreshold = 20;
        int sequenceLenght = 100;
        int numberOfMutations = 20;

        String sequence = Sequences.Generator.randomDNAString(sequenceLenght);
        Graph graph = new SingleGraph("DNA Hamming Distance");

        Generator gen = new Graphs.HammingGraph(sequence,hdThreshold,numberOfMutations);

        gen.addSink(graph);
        gen.begin();
        while(gen.nextEvents()) {}
        gen.end();

        graph.display(true);

        int [] degree = Toolkit.degreeDistribution(graph);

        for (int i = 0; i<degree.length ; i++)
        {
            String d = Integer.toString(degree[i]);
            d = d.replace('.',',');
            System.out.println(d);
        }
    }

    public static void TestBinarySequences()
    {
        int sequenceLenght = 5000;

        int hdThreshold = 100;

        double mutationProbability = 0.01;

        String sequence = Sequences.Generator.randomBinaryString(sequenceLenght);

        System.out.println("Sequence: " + sequence);

        Graph graph = new SingleGraph("Binary Hamming Distance");

        Generator gen = new Graphs.BinaryHammingGraph(sequence,hdThreshold,mutationProbability,15);

        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<100; i++)
        {
            gen.nextEvents();
        }
        gen.end();

        graph.display(true);

        int [] degree = Toolkit.degreeDistribution(graph);

        for (int i = 0; i<degree.length ; i++)
        {
            String d = Integer.toString(degree[i]);
            d = d.replace('.',',');
            System.out.println(d);
        }
    }

    public static void TestBinarySequences3()
    {
        int sequenceLenght = 50;

        int hdThreshold = 3;

        double mutationProbability = 0.005;

        int trials = 2;

        String sequence = Sequences.Generator.randomBinaryString(sequenceLenght);

        System.out.println("Sequence: " + sequence);

        Graph graph = new SingleGraph("Binary Hamming Distance");

        Generator gen = new Graphs.BinaryHammingGraph(sequence,hdThreshold,mutationProbability, trials);

        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<5000; i++)
        {
            gen.nextEvents();
        }
        gen.end();

        graph.display(true);

        int [] degree = Toolkit.degreeDistribution(graph);

        for (int i = 0; i<degree.length ; i++)
        {


                String d = Integer.toString(degree[i]);
                d = d.replace('.', ',');
                System.out.println(d);
            
        }
    }

    public static void TestBinarySequencesWithoutTrials()
    {
        int sequenceLenght = 50;

        int hdThreshold = 13;

        double mutationProbability = 0.06;

        int trials = 2;

        String sequence = Sequences.Generator.randomBinaryString(sequenceLenght);

        System.out.println("Sequence: " + sequence);

        Graph graph = new SingleGraph("Binary Hamming Distance");

        Generator gen = new Graphs.BinaryHammingWithoutTrials(sequence,hdThreshold,mutationProbability);

        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<6000; i++)
        {
            gen.nextEvents();
        }
        gen.end();

       /* Iterator it = graph.getNodeIterator();

         while(it.hasNext())
         {
             Node n = (Node)it.next();
                                                                                
             if(n.getDegree() == 0 || n.getDegree() == 1 || n.getDegree() < 0)
                 graph.removeNode(n.getId());
         }

         graph.getNodeCount();         */
        // graph.display(true);
         int [] degree = Toolkit.degreeDistribution(graph);

        for (int i = 0; i<degree.length ; i++)
        {
            if(degree[i]!=0) {
            String d = Integer.toString(degree[i]);
            d = d.replace('.', ',');
            System.out.println(i + 1 + " " + d);
        }
            
            
            

        }
    }

    public static void TestBinarySequencesUniformWithoutTrials()
    {
        int sequenceLenght = 50;

        int hdThreshold = 20;

        double mutationProbability = 0.9;

        int trials = 2;

        String sequence = Sequences.Generator.randomBinaryString(sequenceLenght);

        System.out.println("Sequence: " + sequence);

        Graph graph = new SingleGraph("Binary Hamming Distance");

        Generator gen = new Graphs.BinaryHammingUniformGraphWithoutTrials(sequence,hdThreshold,mutationProbability);

        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<5000; i++)
        {
            gen.nextEvents();
        }
        gen.end();

     

        Iterator it = graph.getNodeIterator();

        while(it.hasNext())
        {
            Node n = (Node)it.next();

            if(n.getDegree() == 0 || n.getDegree() == 1 || n.getDegree() < 0)
                graph.removeNode(n);
        }

        graph.display();

        int [] degree = Toolkit.degreeDistribution(graph);

        System.out.println("DistribuitionDegree");

        for (int i = 0; i<degree.length ; i++)
        {
            if(degree[i]!=0)
            {
                String d = Integer.toString(degree[i]);
                d = d.replace('.',',');
                System.out.println(i+" "+d);

            }



        }














      
    }

    private static int[] CumulativeDistribuitionDegree(int[] distribuition)
    {
        int [] cumulativeDistribuition = new int[distribuition.length];

        cumulativeDistribuition[distribuition.length-1] = distribuition[distribuition.length-1];

        for (int i = distribuition.length-2; i> 0; i--)
        {
            int aux = cumulativeDistribuition[i+1];
            int ausOne = distribuition[i];
            cumulativeDistribuition[i] = aux+ ausOne;
        }

        return cumulativeDistribuition;
    }
}
