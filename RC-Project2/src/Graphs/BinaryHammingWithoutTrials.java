package Graphs;

import Distance.HammingDistance;
import Sequences.Mutation;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class BinaryHammingWithoutTrials extends BaseGenerator
{
    private String sequence;

    private long edgeId= 0;

    private int hammingDistanceThreshold;

    private int currentIteration;

    private HammingDistance hd;

    private double p;

    private int maxNumberOfSequences;

    public BinaryHammingWithoutTrials(
            String sequence,
                              int hammingDistanceThreshold,
                              double p)
    {
        this.p = p;
        this.setUseInternalGraph(true);
        this.sequence = sequence;
        this.hammingDistanceThreshold = hammingDistanceThreshold;
        this.hd = new HammingDistance();
        this.maxNumberOfSequences = (int)Math.pow(2,sequence.length());
        currentIteration = 0;
    }


    @Override
    public void begin()
    {

        this.addNode(sequence);
        currentIteration++;
    }

    @Override
    public boolean nextEvents()
    {
        if (currentIteration >= maxNumberOfSequences)
        {
            return false;
        }

        String sequenceMutation = Mutation.binaryMutation(sequence, p);

        Node node = this.internalGraph.getNode(sequenceMutation);

        if (node == null)
        {

            //System.out.println("Sequence: " + sequence + " Mutation: " + sequenceMutation + " Hamming Distance: " + hd.compare(sequence, sequenceMutation));
            this.addNode(sequenceMutation);

            node = this.internalGraph.getNode(sequenceMutation);
        }else
        {
            return true;
        }

        createNewEdgeIfHammingDistance(node);

        currentIteration++;

        return true;
    }

    private void createNewEdgeIfHammingDistance(Node mutationNode)
    {
        String mutationSequence = mutationNode.getId();

        List<Node> nodes = GetCandidates(this.internalGraph);

        for (Node node : nodes)
        {
            String nodeSequence = node.getId();

            if (hd.compare(mutationSequence, nodeSequence) <= hammingDistanceThreshold)
            {
                this.addEdge(this.edgeId(nodeSequence, mutationSequence), mutationSequence, nodeSequence);
            }
        }
    }

    private List<Node> GetCandidates(Graph graph)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();

        for (Node node : graph)
        {
            nodes.add(node);
        }

        return nodes;

    }



    private String edgeId(String from, String to) {

        edgeId++;
        return Long.toString(edgeId);
    }


}
