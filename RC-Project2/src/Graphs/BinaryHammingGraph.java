package Graphs;

import Distance.HammingDistance;
import Sequences.Mutation;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class BinaryHammingGraph extends BaseGenerator
{
    private String sequence;

    private int hammingDistanceThreshold;

    private int currentIteration;

    private HammingDistance hd;

    private double p;

    private int maxNumberOfSequences;

    private int maxTrials;

    public BinaryHammingGraph(String sequence,
                              int hammingDistanceThreshold,
                              double p,
                              int maxTrials)
    {
        this.p = p;
        this.setUseInternalGraph(true);
        this.sequence = sequence;
        this.hammingDistanceThreshold = hammingDistanceThreshold;
        this.hd = new HammingDistance();
        this.maxNumberOfSequences = (int)Math.pow(2,sequence.length());
        currentIteration = 0;
        this.maxTrials = maxTrials;
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

            System.out.println("Sequence: " + sequence + " Mutation: " + sequenceMutation + " Hamming Distance: " + hd.compare(sequence, sequenceMutation));
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

        List<Node> trialNodes = GetCandidates(this.internalGraph, maxTrials, mutationNode);

        for (Node node : trialNodes)
        {
            String nodeSequence = node.getId();

            if (hd.compare(mutationSequence, nodeSequence) <= hammingDistanceThreshold)
            {
                try {
                    this.addEdge(this.edgeId(nodeSequence, mutationSequence), mutationSequence, nodeSequence);
                }catch (Exception e)
                {
                    e.getMessage();
                }
            }
        }
    }

    private List<Node> GetCandidates(Graph graph, int maxCandidates, Node mutationNode)
    {
        int graphNodeCount = graph.getNodeCount();

        ArrayList<Node> nodes = new ArrayList<Node>();

        if(maxCandidates >= (graph.getNodeCount()- 1))
        {
            maxCandidates = graph.getNodeCount() - 1;
        }

        for (Node node : graph)
        {
            if(!node.getId().equals(mutationNode.getId()))
            {
                nodes.add(node);
            }
        }

        ArrayList<Node> candidates =  pickNRandomCandidates(nodes,maxCandidates);

        return candidates;

    }

    public static List<Node> pickNRandom(List<Node> lst, int n)
    {
        List<Node> copy = new LinkedList<Node>(lst);
        Collections.shuffle(copy);
        return copy.subList(0, n);
    }

    private String edgeId(String from, String to) {

        return from + to;
    }

    private ArrayList<Node> pickNRandomCandidates(ArrayList<Node> lst, int n)
    {

        Random rand = new Random();

        int low = 0;
        int high = lst.size();

        ArrayList<Node> candidates = new ArrayList<Node>();

        for (int i = 0; i < n; i++)
        {
            int randomIndex =  rand.nextInt(high-low) + low;
            Node randomNode = lst.get(randomIndex);
            lst.remove(randomIndex);
            high = lst.size();
            candidates.add(randomNode);
        }

        return candidates;
    }
}