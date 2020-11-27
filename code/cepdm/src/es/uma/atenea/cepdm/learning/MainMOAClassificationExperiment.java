package es.uma.atenea.cepdm.learning;

import es.uma.atenea.cepdm.learning.classification.ExperimentAdaptiveRF;
import es.uma.atenea.cepdm.learning.classification.ExperimentHoeffingTree;
import es.uma.atenea.cepdm.learning.classification.ExperimentKNN;
import es.uma.atenea.cepdm.learning.classification.ExperimentLeveragingBag;
import es.uma.atenea.cepdm.learning.classification.ExperimentNaiveBayes;
import es.uma.atenea.cepdm.learning.classification.ExperimentRuleClassifier;

/**
 * A main program to run classification algorithms from a folder containing datasets.
 * For testing purposes.
 * @author Aurora
 * @version 2
 * */
public class MainMOAClassificationExperiment {

	public static void main(String[] args) {

		String dir = "./datasets";
		String [] datasets = {"airlines.arff", "elecNormNew.arff"};
		
		int numExecutions = 2;
		int frequency = 20;
		
		for(int i=0; i<datasets.length; i++) {

			System.out.println("DATASET: " + datasets[i]);
			
			String dataset = dir+"/"+datasets[i];
			
			for(int j=0; j<numExecutions; j++) {

				System.out.println("\tIteration: " + j);

				// Decision tree
				System.out.println("\t\tRunning HoeffingTree...");
				ExperimentHoeffingTree exp1 = new ExperimentHoeffingTree(dataset, j, frequency);
				exp1.run();

				// Naive Bayes
				System.out.println("\t\tRunning NaiveBayes...");
				ExperimentNaiveBayes exp2 = new ExperimentNaiveBayes(dataset, j, frequency);
				exp2.run();

				// kNN
				System.out.println("\t\tRunning kNN...");
				ExperimentKNN exp3 = new ExperimentKNN(dataset, j, frequency);
				exp3.run();

				// Rule classifier
				System.out.println("\t\tRunning Rule-based Classifier");
				ExperimentRuleClassifier exp4 = new ExperimentRuleClassifier(dataset, j, frequency);
				exp4.run();
				
				// Leveraging Bag ensemble
				System.out.println("\t\tRunning LeveragingBag Ensemble");
				ExperimentLeveragingBag exp5 = new ExperimentLeveragingBag(dataset, j, frequency);
				exp5.run();
				
				// Adaptive RF ensemble
				System.out.println("\t\tRunning AdaptiveRF Ensemble");
				ExperimentAdaptiveRF exp6 = new ExperimentAdaptiveRF(dataset, j, frequency);
				exp6.run();
			}
		}
	}
}
