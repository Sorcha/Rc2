package Networks;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.algorithm.generator.WattsStrogatzGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.AdjacencyListGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Random;

public class RandomsNetwork
{

    public static void main(String[] args)
    {
        ErdosNetwork();
        SmallWorld();
        BarabasiAlbertNetwork();
    }

    private static void SmallWorld()
    {
        Graph graph = new SingleGraph("This is a small world!");
        Generator gen = new WattsStrogatzGenerator(2000,
                10,
                1);

        gen.addSink(graph);
        gen.begin();
        while(gen.nextEvents()) {}
        gen.end();

        graph.display(false);

        int [] degree = Toolkit.degreeDistribution(graph);

        for (int i = 0; i<degree.length ; i++)
        {
            String d = Integer.toString(degree[i]);
            d = d.replace('.',',');
            System.out.println(d);
        }
    }

    private static void RandomNetworkByDegree()
    {
        Graph graph = new SingleGraph("Random Network");
        Generator gen = new RandomGenerator(2);
        gen.addSink(graph);
        gen.begin();
        for(int i=0; i<1000; i++)
            gen.nextEvents();
        gen.end();
        graph.display();

        int [] degree = Toolkit.degreeDistribution(graph);

        for (int i = 0; i<degree.length ; i++)
        {
            String d = Integer.toString(degree[i]);
            d = d.replace('.',',');
            System.out.println(d);
        }
    }

    private static void ErdosNetwork()
    {
        int N = 1000;

        double p = 0.1;

        Graph graph = new AdjacencyListGraph("Erdos Graph");
        Random rnd = new Random();
        for (int i = 0; i < N; i++)
            graph.addNode(i + "");
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                if (rnd.nextDouble() < p)
                    graph.addEdge(i + "_" + j, i, j);

        graph.display();

        int [] degree = Toolkit.degreeDistribution(graph);

        for (int i = 0; i<degree.length ; i++)
        {
            String d = Integer.toString(degree[i]);
            d = d.replace('.',',');
            System.out.println(d);
        }
    }

    private static void BarabasiAlbertNetwork()
    {
        Graph graph = new SingleGraph("Barabàsi-Albert Network");

        Generator gen = new BarabasiAlbertGenerator(3);

        gen.addSink(graph);
        gen.begin();

        for(int i=0; i<100; i++) {
            gen.nextEvents();
        }

        gen.end();
        graph.display();
    }
}
