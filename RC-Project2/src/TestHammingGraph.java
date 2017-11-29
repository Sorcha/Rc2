import Graphs.BHammingGraph;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class TestHammingGraph
{
    public static void main(String[] args)
    {

        Hamming();
    }

    private static void Hamming()
    {
        Graph graph = new SingleGraph("Hamming Graph");

        Generator gen = new BHammingGraph(3, 4,2);

        gen.addSink(graph);
        gen.begin();


        for(int i=0; i<100; i++) {
            gen.nextEvents();
        }

        gen.end();
        graph.display();


        int [] degree = Toolkit.degreeDistribution(graph);

        for (int i = 0; i<degree.length ; i++)
        {
            String d = Integer.toString(degree[i]);
            d = d.replace('.',',');
            System.out.println(d);
        }

        gen.end();
        graph.display();



    }
}
