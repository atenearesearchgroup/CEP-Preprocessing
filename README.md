# CEP for data stream preprocessing

This repository contains additional material for the paper entitled _Rule-based preprocessing for data stream mining using complex event processing_, currently under revision.

The repository includes code and datasets for reproducibility purposes, as well as extended experimental results and statistical analysis.

## Requirements/dependencies

The code is provided as a Maven project, which has been developed in Eclipse v.2019-03 with Java 8.

The following dependecies are required:

* [Esper 8](http://www.espertech.com/esper/), a Java implementation of a CEP engine (already configured as Maven dependency)
* [MOA 19.03](https://moa.cms.waikato.ac.nz/), a Java library for massive online analysis (available in [Github](https://github.com/waikato/moa))

Some classes related to instances in MOA have been modified to allow external manipulation, they can be found in the folder **code/dependencies/moa**. 

In order to use MOA timing functionalities, the _sizeofag_ dependency has to be properly configured. If you run the code outside an IDE, you might need to specify it as a JVM parameter, e.g., in Windows:

```
java -javaagent:"C:\Users\YourUserName\.m2\repository\com\github\fracpete\sizeofag\1.0.4\sizeofag-1.0.4.jar"
```

## Datasets

The data streams used in the experiments are generated from public datasets, currently available from [UCI](https://archive.ics.uci.edu/ml/datasets.php) and [OpenML](https://openml.org/) repositories:

* [Electricity](https://www.openml.org/d/151) dataset, which includes electricity price and demand records to predict whether electricity price will rise or not.
* [Airlines](https://www.openml.org/d/1169) dataset, which contains flight schedules in American airports from which delays can be predicted.
* [Occupancy](https://archive.ics.uci.edu/ml/datasets/Occupancy+Detection+) dataset, which provides sensor measurements to predict room occupancy.

The two first datasets can be found as part of MOA distribution in [Sourceforge](https://sourceforge.net/projects/moa-datastream/files/Datasets/Classification/) too.

## Running experiments

For each experiment, a CEP service has been developed following these steps:

1. Register preprocessing rules in the CEP engine.
2. Load instances from an ARFF dataset.
3. Convert MOA instances to CEP events and send them to the engine.
4. Apply CEP rules and process their outcomes in the subscribers.
5. Convert the derived events into MOA instances and write them in an ARFF dataset.

The CEP services can be found in the package _es.uma.atenea.cepdm.service_. Each one includes a demo program that applies some of the preprocessing rules described in the paper. The resulting data streams are available in the folder **datasets**.

For experimental purposes, classification algorithms are executed using ARFF datasets produced by CEP as inputs. The main program to run six classification algorithms (Hoeffding tree, kNN, naive Bayes, rule-based classifier and two ensemble methods) is available in the package _es.uma.atenea.cepdm.learning_ (see MainMOAClassificationExperiment). 

For the electricity case study, we provide an additional main program that applies both preprocessing and learning every time a new instance arrives. It can be found in the package _es.uma.atenea.cepdm.service.electricity_ (see CEPOnlineServiceElectricity).

## Results and statistical analysis

The folder **results** contains a spreadsheet for each experiment with the following classification measures: accuracy, precision, recall and F1. The metrics are computed in absolute terms (_total_ in file names) and in windows (_window_ in file names). Time and memory results are also detailed.

Accuracy results are provided as CSV files, from which Kruskal-Wallis and Wilcoxon statistical tests have been run in R. The outputs of the tests have been dumped to text files, which are provided too.
