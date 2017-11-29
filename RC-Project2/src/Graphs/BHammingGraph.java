package Graphs;


        import org.graphstream.algorithm.generator.BaseGenerator;
        import org.graphstream.graph.Node;

        import java.util.ArrayList;
        import java.util.HashSet;
        import java.util.Set;

public class BHammingGraph extends BaseGenerator {

    protected ArrayList<Integer> degrees;


    protected int maxLinksPerStep;


    protected boolean exactlyMaxLinksPerStep = false;


    protected int sumDeg;


    protected int sumDegRemaining;

    protected Set<Integer> connected;

    private String sequence;

    private int sequenceLengh;

    private int hammingThreshold;

    private int maxPossibleCombinations;

    private int count = 0;


    public BHammingGraph() {
        this(1, false);
    }

    public BHammingGraph(int maxLinksPerStep) {
        this(maxLinksPerStep, false);
    }

    public BHammingGraph(int maxLinksPerStep, int sequenceLengh, int hammingThreshold) {

        this(maxLinksPerStep, false);
        this.setUseInternalGraph(true);
        this.sequenceLengh = sequenceLengh;
        this.hammingThreshold = hammingThreshold;
        this.addNodeLabels(true);
    }

    public BHammingGraph(int maxLinksPerStep,
                                   boolean exactlyMaxLinksPerStep) {
        this.directed = false;
        this.maxLinksPerStep = maxLinksPerStep;
        this.exactlyMaxLinksPerStep = exactlyMaxLinksPerStep;

    }


    public int getMaxLinksPerStep() {
        return maxLinksPerStep;
    }


    public boolean produceExactlyMaxLinkPerStep() {
        return exactlyMaxLinksPerStep;
    }


    public void setMaxLinksPerStep(int max) {
        maxLinksPerStep = max > 0 ? max : 1;
    }


    public void setExactlyMaxLinksPerStep(boolean on) {
        exactlyMaxLinksPerStep = on;
    }


    public void begin()
    {
        maxPossibleCombinations =  (int)Math.pow(2, sequenceLengh);
        sequence = Sequences.Generator.randomBinaryString(sequenceLengh);
        String mutation= Sequences.Mutation.binaryMutationWithPrefixNumberOfMutation(sequence,0.4, hammingThreshold);

        addNode(sequence);
        addNode(mutation);
        addEdge(sequence+ "_" + mutation, mutation, sequence);
        degrees = new ArrayList<Integer>();
        degrees.add(1);
        degrees.add(1);
        sumDeg = 2;
        count = 2;
        connected = new HashSet<Integer>();
    }


    public boolean nextEvents()
    {
        if(maxPossibleCombinations <= count)
            return false;

        int nodeCount = degrees.size();

        String newId = Sequences.Mutation.binaryMutation(sequence,0.4);

        Node node = this.internalGraph.getNode(newId);

        if(node!= null)
            return true;

        addNode(newId);
        count++;

        int n = maxLinksPerStep;

        if (!exactlyMaxLinksPerStep)
            n = random.nextInt(n) + 1;
        n = Math.min(n, nodeCount);

        // Choose the nodes to attach to.
        sumDegRemaining = sumDeg;
        for (int i = 0; i < n; i++)
            chooseAnotherNode();

        for (int i : connected)
        {
            addEdge(newId + "_" + i, newId, i + "");
            degrees.set(i, degrees.get(i) + 1);
        }
        connected.clear();
        degrees.add(n);
        sumDeg += 2 * n;

        // It is always possible to add an element.
        return true;
    }

    /**
     * Choose randomly one of the remaining nodes
     */
    protected void chooseAnotherNode()
    {
        int r = random.nextInt(sumDegRemaining);
        int runningSum = 0;
        int i = 0;
        while (runningSum <= r)
        {
            if (!connected.contains(i))
                runningSum += degrees.get(i);
            i++;
        }
        i--;
        connected.add(i);
        sumDegRemaining -= degrees.get(i);
    }


    /**
     * Clean degrees.
     *
     * @see org.graphstream.algorithm.generator.Generator#end()
     */
    @Override
    public void end()
    {
        degrees.clear();
        degrees = null;
        connected = null;
        super.end();
    }
}