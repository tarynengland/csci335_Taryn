package learning.decisiontree;

import core.Duple;
import learning.core.Histogram;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DTTrainer<V,L, F, FV extends Comparable<FV>> {
	private ArrayList<Duple<V,L>> baseData;
	private boolean restrictFeatures;
	private Function<ArrayList<Duple<V,L>>, ArrayList<Duple<F,FV>>> allFeatures;
	private BiFunction<V,F,FV> getFeatureValue;
	private Function<FV,FV> successor;
	
	public DTTrainer(ArrayList<Duple<V, L>> data, Function<ArrayList<Duple<V, L>>, ArrayList<Duple<F,FV>>> allFeatures,
					 boolean restrictFeatures, BiFunction<V,F,FV> getFeatureValue, Function<FV,FV> successor) {
		baseData = data;
		this.restrictFeatures = restrictFeatures;
		this.allFeatures = allFeatures;
		this.getFeatureValue = getFeatureValue;
		this.successor = successor;
	}
	
	public DTTrainer(ArrayList<Duple<V, L>> data, Function<ArrayList<Duple<V,L>>, ArrayList<Duple<F,FV>>> allFeatures,
					 BiFunction<V,F,FV> getFeatureValue, Function<FV,FV> successor) {
		this(data, allFeatures, false, getFeatureValue, successor);
	}

	public static <V,L, F, FV  extends Comparable<FV>> ArrayList<Duple<F,FV>>
	reducedFeatures(ArrayList<Duple<V,L>> data, Function<ArrayList<Duple<V, L>>, ArrayList<Duple<F,FV>>> allFeatures,
					int targetNumber) {
		ArrayList<Duple<F,FV>> fts = allFeatures.apply(data);
		Collections.shuffle(fts);
		ArrayList<Duple<F,FV>> feats = new ArrayList<>();
		for (int i=0 ; i<targetNumber; i++){
			Duple<F, FV> ft = fts.get(i);
			feats.add(ft);
		}
		return feats;
    }
	
	public DecisionTree<V,L,F,FV> train() {
		return train(baseData);
	}

	public static <V,L> int numLabels(ArrayList<Duple<V,L>> data) {
		return data.stream().map(Duple::getSecond).collect(Collectors.toUnmodifiableSet()).size();
	}
	
	private DecisionTree<V,L,F,FV> train(ArrayList<Duple<V,L>> data) {
		ArrayList<Duple<F, FV>> feat;
		Duple<ArrayList<Duple<V, L>>, ArrayList<Duple<V, L>>> best = null;
		F decision = null;
		FV maxFeatVal = null;
		double Gain = - Double.MAX_VALUE;
		if (numLabels(data) == 1) {
			return new DTLeaf<>(data.get(0).getSecond());
		} else {
			if(!restrictFeatures){
				feat = allFeatures.apply(data);
			}
			else{
				feat = reducedFeatures(data, allFeatures, (int) Math.sqrt(data.size()));
			}
			for(Duple<F, FV> feature: feat){
				Duple<ArrayList<Duple<V, L>>, ArrayList<Duple<V, L>>> split = splitOn(data, feature.getFirst(), feature.getSecond(), getFeatureValue);
				double gain = gain(data, split.getFirst(), split.getSecond());
				if(gain > Gain){
					best = split;
					decision = feature.getFirst();
					maxFeatVal = feature.getSecond();
					Gain = gain;
				}
			}
			if(best.getFirst().isEmpty()){
				L best_label = mostPopularLabelFrom(best.getSecond());
				return new DTLeaf<>(best_label);
			}
			if(best.getSecond().isEmpty()){
				L best_label = mostPopularLabelFrom(best.getFirst());
				return new DTLeaf<>(best_label);
			}
			return new DTInterior<>(decision, maxFeatVal, train(best.getFirst()), train(best.getSecond()),getFeatureValue,successor);
		}		
	}

	public static <V,L> L mostPopularLabelFrom(ArrayList<Duple<V, L>> data) {
		Histogram<L> h = new Histogram<>();
		for (Duple<V,L> datum: data) {
			h.bump(datum.getSecond());
		}
		return h.getPluralityWinner();
	}

	public static <V,L> ArrayList<Duple<V,L>> resample(ArrayList<Duple<V,L>> data) {
		Collections.shuffle(data);
		return data;
	}

	public static <V,L> double getGini(ArrayList<Duple<V,L>> data) {
		Histogram<L> hgram = new Histogram<>();
		for (Duple<V,L> duple: data){
			hgram.bump(duple.getSecond());
		}
		double gini = 1;
		for(L l:hgram){
			gini -= (hgram.getPortionFor(l)*hgram.getPortionFor(l));
		}
		return gini;
	}

	public static <V,L> double gain(ArrayList<Duple<V,L>> parent, ArrayList<Duple<V,L>> child1,
									ArrayList<Duple<V,L>> child2) {
		return (getGini(parent)-(getGini(child1)+getGini(child2)));
	}

	public static <V,L, F, FV  extends Comparable<FV>> Duple<ArrayList<Duple<V,L>>,ArrayList<Duple<V,L>>> splitOn
			(ArrayList<Duple<V,L>> data, F feature, FV featureValue, BiFunction<V,F,FV> getFeatureValue) {
		ArrayList<Duple<V,L>> more = new ArrayList<>();
		ArrayList<Duple<V,L>> less = new ArrayList<>();
		for (Duple<V,L> duple :data){
			if(getFeatureValue.apply(duple.getFirst(),feature).compareTo(featureValue) <= 0){
				less.add(duple);
			}else{
				more.add(duple);
			}
		}
		return new Duple<>(less,more);
	}
}
