import Distance.HammingDistance;
import Sequences.Mutation;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.WattsStrogatzGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashMap;

public class Main {



    public static void main(String[] args)
    {
        TestBinarySequences();
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
        int hdThreshold = 20;
        int sequenceLenght = 50;
        int numberOfMutations = 20;
        HammingDistance hd = new HammingDistance();

        String sequence = Sequences.Generator.randomBinaryString(sequenceLenght);

        System.out.println("Sequence: " + sequence);


        Graph graph = new SingleGraph("Binary Hamming Distance");

        Generator gen = new Graphs.BinaryHammingGraph(sequence,hdThreshold,0.5,100);

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
}
