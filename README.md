targetSumProdGA


This is a simplistic version of a genetic algorithm to solve the target sum and product problem.

The problem:

There are n cards, labeled from 1 to n. 
A target sum value and target product value are chosen.
You need to organize the cards into 2 piles, the sum pile and the product pile
 such that:
-the sum of all of the cards in the sum pile is equal to the target sum 
-the product of all of the cards in the product pile is equal to the target product


Solution using GA:

The genotype of each individual consists of n genes, encoded either as a 0 or 1
This corresponds to the n-th card being in the sum pile (0) or product pile (1)
Each inidividual's fitness is calculated reflecting how "close" he is to the targets.

With each run:
A population of individuals is generated at random.

Then a number of "tournaments" happen:
    2 individuals from the population are compared (by fitness).
    the worst one then with a chance equal to the mutation rate, mutates
                   and with a chance equal to recombination rate, recombinates with the winner

  

  

geneticAlg.java and Individual.java should be in the same directory.

to compile: javac geneticAlg.java
to run: java geneticAlg

this will run the genetic algorithm 15 times and output the outcome both on the stdout and in a file named outcome.txt
(that will be created in the working directory, and overwritten each time you run the command "java geneticAlg")

