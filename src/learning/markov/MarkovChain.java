package learning.markov;

import learning.core.Histogram;

import java.util.*;

public class MarkovChain<L,S> {
    private LinkedHashMap<L, HashMap<Optional<S>, Histogram<S>>> label2symbol2symbol = new LinkedHashMap<>();

    public Set<L> allLabels() {return label2symbol2symbol.keySet();}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (L language: label2symbol2symbol.keySet()) {
            sb.append(language);
            sb.append('\n');
            for (Map.Entry<Optional<S>, Histogram<S>> entry: label2symbol2symbol.get(language).entrySet()) {
                sb.append("    ");
                sb.append(entry.getKey());
                sb.append(":");
                sb.append(entry.getValue().toString());
                sb.append('\n');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // Increase the count for the transition from prev to next.
    // Should pass SimpleMarkovTest.testCreateChains().


    //https://stackoverflow.com/questions/2774608/how-do-i-access-nested-hashmaps-in-java
    // Solution or answer from Jay Askren from May 5 2010 really helped me figure out how I needed to structure checking vaules
    // and going about bumping the values if they needed to get bumped
    public void count(Optional<S> prev, L label, S next) {
        // TODO: YOUR CODE HERE
        if(!label2symbol2symbol.containsKey(label)){
            label2symbol2symbol.put(label,new HashMap<>());
        }if(!label2symbol2symbol.get(label).containsKey(prev)) {
            label2symbol2symbol.get(label).put(prev, new Histogram<>());
        }
        label2symbol2symbol.get(label).get(prev).bump(next);
    }

    // Returns P(sequence | label)
    // Should pass SimpleMarkovTest.testSourceProbabilities() and MajorMarkovTest.phraseTest()
    //
    // HINT: Be sure to add 1 to both the numerator and denominator when finding the probability of a
    // transition. This helps avoid sending the probability to zero.


    //https://stackoverflow.com/questions/32578239/markov-chains-random-text-based-on-probability-java#:~:text=import%20java.util.Random%3B%20public%20class%20MarkovChainTest%20%7B%20private%20static,%2B%20%22%29%20%3C%20%22%20%2B%20val%29%3B%20%7D%20%7D
    // This and the previous link helped me sort of figure out how I would need to arrange my probability
    // function with dealing with doubles and running through a for loop.
    // Java help me remember about the simplifying for loop code.
    public double probability(ArrayList<S> sequence, L label) {
        // TODO: YOUR CODE HERE
        double transProb = 1.0;
        Optional<S> previous = Optional.empty();
        for (S s : sequence) {
            if (label2symbol2symbol.get(label).containsKey(previous)) {
                Histogram<S> hist = label2symbol2symbol.get(label).get(previous);
                double histprob = ((double) (hist.getCountFor(s) + 1) / (double) (hist.getTotalCounts() + 1));
                transProb *= histprob;
            }
            // I initially passed the simple test and had to go back and add this line because if the
            // linked Hashmap didn't have the previous key with the label it would
            previous = Optional.of(s);
        }
        return transProb;
    }

    // Return a map from each label to P(label | sequence).
    // Should pass MajorMarkovTest.testSentenceDistributions()
    public LinkedHashMap<L,Double> labelDistribution(ArrayList<S> sequence) {
        // TODO: YOUR CODE HERE
        LinkedHashMap<L, Double> DisMap = new LinkedHashMap<>();
        double disProb = 0.0;
        for(L lable: label2symbol2symbol.keySet()) {
            double chances = probability(sequence, lable);
            DisMap.put(lable, chances);
            disProb += chances;
        }for(L lable: DisMap.keySet()){
            double normalize = DisMap.get(lable)/disProb;
            DisMap.put(lable, normalize);
        }
        return DisMap;
    }

    // Calls labelDistribution(). Returns the label with highest probability.
    // Should pass MajorMarkovTest.bestChainTest()


    //https://stackoverflow.com/questions/49470423/get-the-highest-values-in-a-hashmap-in-java#:~:text=int%20max%20%3D%20Collections.max%20%28map.values%20%28%29%29%3B%20Now%20iterate,%28entry.getValue%20%28%29%3D%3Dmax%29%20%7B%20keys.add%20%28entry.getKey%20%28%29%29%3B%20%7D%20%7D
    // Helped piece together some of the starting code for finding the right way to getting the highest value from
    // the labelDistribution function and get an idea of looking through all the proceeding codes to find the rest.
    public L bestMatchingChain(ArrayList<S> sequence) {
        // TODO: YOUR CODE HERE
        LinkedHashMap<L, Double> highest = labelDistribution(sequence);
        return highest.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }
}
