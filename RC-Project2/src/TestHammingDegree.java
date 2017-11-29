import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class TestHammingDegree
{

    public static void main(String[] args)
    {

        GenerateSequenceSize5000AndThreshold100AndMutationProbability001();
    }

    public static void GenerateSequenceSize5000AndThreshold100AndMutationProbability001()
    {
        int sequenceLenght = 5000;

        int hdThreshold = 100;

        double mutationProbability = 0.01;

        GenerateGraphAndShowDistribuitionDegree(sequenceLenght,hdThreshold,mutationProbability);

    }


    private static void GenerateGraphAndShowDistribuitionDegree(int sequenceLenght, int hdThreshold, double mutationProbability)
    {
        String sequence = Sequences.Generator.randomBinaryString(sequenceLenght);

        System.out.println("Sequence: " + sequence);

        Graph graph = new SingleGraph("Binary Hamming Distance");

        Generator gen = new Graphs.BinaryHammingGraph(sequence,hdThreshold,mutationProbability,15);

        gen.addSink(graph);

        gen.begin();

        int numberOfIterations = ((int)Math.pow(2,sequenceLenght))/2;

        for(int i=0; i<numberOfIterations; i++)
        {
            gen.nextEvents();
        }
        gen.end();
    }

    private static int[] DistribuitionDegree(Graph graph)
    {
        int [] degree = Toolkit.degreeDistribution(graph);

        return degree;
    }

    private static int[] CumulativeDistribuitionDegree(int[] distribuition)
    {
        int [] cumulativeDistribuition = new int[distribuition.length];

        cumulativeDistribuition[distribuition.length-1] = distribuition[distribuition.length-1];

        for (int i = distribuition.length-2; i> 0; i--)
        {
            cumulativeDistribuition[i] = cumulativeDistribuition[i-1] + distribuition[i];
        }

        return cumulativeDistribuition;
    }
}
