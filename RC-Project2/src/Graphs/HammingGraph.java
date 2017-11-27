package Graphs;

import Distance.HammingDistance;
import Sequences.Mutation;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.graph.Node;

import java.util.Iterator;

public class HammingGraph  extends BaseGenerator
{
    private String sequence;
    private int hammingDistanceThreshold;
    private int numberOfMutations;

    private int numberOfIterations ;
    private int currentIteration;
    private HammingDistance hd;

    public HammingGraph(String sequence, int hammingDistanceThreshold, int numberOfMutations)
    {
        this.setUseInternalGraph(true);
        this.sequence = sequence;
        this.hammingDistanceThreshold = hammingDistanceThreshold;
        this.numberOfMutations = numberOfMutations;
        //this.addNodeLabels(true);
        this.hd = new HammingDistance();
        numberOfIterations = 1000;
    }


    @Override
    public void begin()
    {
        this.addNode(sequence);
    }

    @Override
    public boolean nextEvents()
    {
        if(currentIteration >= numberOfIterations)
        {
            return false;
        }

        String sequenceMutation = Mutation.dnaMutation(sequence,numberOfMutations);


        Node node = this.internalGraph.getNode(sequenceMutation);
        if(node == null)
        {
            this.addNode(sequenceMutation);
            node = this.internalGraph.getNode(sequenceMutation);
        }


        createNewEdgeIfHammingDistance(node);

        currentIteration++;

        return true;
    }

    private void createNewEdgeIfHammingDistance(Node mutationNode)
    {
        Iterator<Node> graphNodesIterator = this.internalGraph.getNodeIterator();

        String mutationSequence = mutationNode.getId();

        while (graphNodesIterator.hasNext())
        {
            Node node = graphNodesIterator.next();

            String nodeSequence = node.getId();

            if(hd.compare(mutationSequence,nodeSequence)<= hammingDistanceThreshold)
            {
                this.addEdge(this.edgeId(nodeSequence, mutationSequence), mutationSequence, nodeSequence);
            }
        }
    }


    private String edgeId(String from, String to) {

        return from+to;
    }
}
