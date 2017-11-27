package Graphs;

import Distance.HammingDistance;
import Sequences.Mutation;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.graph.Node;

import java.util.Iterator;

public class BinaryHammingGraph extends BaseGenerator {
    private String sequence;
    private int hammingDistanceThreshold;

    private int numberOfIterations;
    private int currentIteration;
    private HammingDistance hd;
    private double p;

    public BinaryHammingGraph(String sequence,
                              int hammingDistanceThreshold,
                              double p,
                              int numberOfIterations) {
        this.p = p;
        this.setUseInternalGraph(true);
        this.sequence = sequence;
        this.hammingDistanceThreshold = hammingDistanceThreshold;
        //this.addNodeLabels(true);
        this.hd = new HammingDistance();
        this.numberOfIterations = numberOfIterations;
    }


    @Override
    public void begin() {
        this.addNode(sequence);
    }

    @Override
    public boolean nextEvents()
    {
        if (currentIteration >= numberOfIterations) {
            return false;
        }

        String sequenceMutation = Mutation.binaryMutation(sequence, p);


        Node node = this.internalGraph.getNode(sequenceMutation);
        if (node == null)
        {
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

    private void createNewEdgeIfHammingDistance(Node mutationNode) {
        Iterator<Node> graphNodesIterator = this.internalGraph.getNodeIterator();

        String mutationSequence = mutationNode.getId();

        while (graphNodesIterator.hasNext()) {
            Node node = graphNodesIterator.next();

            String nodeSequence = node.getId();

            if (hd.compare(mutationSequence, nodeSequence) <= hammingDistanceThreshold) {
                this.addEdge(this.edgeId(nodeSequence, mutationSequence), mutationSequence, nodeSequence);
            }
        }
    }


    private String edgeId(String from, String to) {

        return from + to;
    }
}