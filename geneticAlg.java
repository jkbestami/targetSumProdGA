import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;  

public class geneticAlg{

	public static void main(String[] args) {


		String newline = System.getProperty("line.separator");

		//parameters
		int POPULATIONSIZE = 30;
		double MUTATIONRATE = 0.1;
		double RECOMBINATIONRATE = 0.5;
		int NUMGENES = 10;
		int SUMTARGET = 36;
		int PRODTARGET = 360;
		int MAXNUMTOURNAMENTS = 1000;
		int NUMBEROFRUNS = 15;


		Scanner sc = new Scanner(System.in);
		System.out.println("Enter paramaters or leave blank for default");
		
		System.out.println("Population Size (default: 30)");
		String s = sc.nextLine();
		if(!s.isEmpty()){
			POPULATIONSIZE = Integer.parseInt(s);
		}
		
		System.out.println("Mutation Rate (default: 0.1)");
		s = sc.nextLine();
		if(!s.isEmpty()){
			MUTATIONRATE = Double.parseDouble(s);
		}
		
		System.out.println("Recombination Rate (default: 0.5)");
		s = sc.nextLine();
		if(!s.isEmpty()){
			RECOMBINATIONRATE = Double.parseDouble(s);
		}
		
		System.out.println("Number of Cards (default: 10)");
		s = sc.nextLine();
		if(!s.isEmpty()){
			NUMGENES = Integer.parseInt(s);
		}
		
		
		int maxSum = 0;
		int maxProd = 1;
		for(int i = 1; i<NUMGENES; i++){
			
			maxSum = maxSum + i;
			maxProd = maxProd * i;
		
		}
		
		if(maxProd==0){
			maxProd = 2147483647;
		}
		
		System.out.println("Target Sum (default: 36) " + "Max Sum: " + maxSum);
		s = sc.nextLine();
		if(!s.isEmpty()){
			SUMTARGET = Integer.parseInt(s);
		}
		
		System.out.println("Target Product (default: 360) " + "Max Product: " + maxProd);
		s = sc.nextLine();
		if(!s.isEmpty()){
			PRODTARGET = Integer.parseInt(s);
		}
		
		System.out.println("Maximum # of Tournaments (default: 1000)");
		s = sc.nextLine();
		if(!s.isEmpty()){
			MAXNUMTOURNAMENTS = Integer.parseInt(s);
		}
		
		System.out.println("# of runs (default: 15)");
		s = sc.nextLine();
		if(!s.isEmpty()){
			NUMBEROFRUNS = Integer.parseInt(s);
		}
		
		List<String> output= new ArrayList<String>();
		output.add("Population Size: " + POPULATIONSIZE);
		output.add("Mutation Rate: " + MUTATIONRATE);
		output.add("Recombination Rate: " + RECOMBINATIONRATE);
		output.add("Target Sum: " + SUMTARGET);
		output.add("Target Product: "+ PRODTARGET);

		
		for(int run = 1; run<=NUMBEROFRUNS; run++) {

			//resetting the seed for each run
			Random rand = new Random();

			ArrayList<Individual> population = new ArrayList<Individual>();

			//initialize population
			for (int i = 0; i < POPULATIONSIZE; i++) {
				Individual individual = new Individual(NUMGENES);
				population.add(individual);
			}

			//following 2 will be replaced as better individuals appear
			Individual bestIndividual = new Individual(NUMGENES);
			Double bestFitness = 1.0;


			int numTournaments = 0;


			while (numTournaments < MAXNUMTOURNAMENTS && bestFitness > 0.001) {

				//choose two individuals at random
				Individual individual1 = population.get(rand.nextInt(POPULATIONSIZE));
				Double fitness1 = individual1.calculateFitness(SUMTARGET, PRODTARGET);
				Individual individual2 = population.get(rand.nextInt(POPULATIONSIZE));
				Double fitness2 = individual2.calculateFitness(SUMTARGET, PRODTARGET);

				if (fitness1 > fitness2) {

					individual1.recombination(RECOMBINATIONRATE, individual2);
					individual1.mutate(MUTATIONRATE);

					if (bestFitness > fitness2) {
						bestFitness = fitness2;
						bestIndividual = individual2;
					}

				} else {

					individual2.recombination(RECOMBINATIONRATE, individual1);
					individual2.mutate(MUTATIONRATE);
					if (bestFitness > fitness1) {
						bestFitness = fitness1;
						bestIndividual = individual1;
					}
				}

				numTournaments++;
			}

			//following is I/O STUFF
			System.out.println("RUN#" + run);
			System.out.println("# of tournaments: " + numTournaments);
			System.out.println("best fitness: " + bestFitness);
			System.out.println("best individual: " + newline + bestIndividual);

			output.add("RUN#" + run);

			if(bestFitness>0){
				output.add("no solution found ... " + newline);
			}else {

				output.add("# of tournaments: " + numTournaments);
				output.add("best fitness: " + bestFitness);
				output.add("best individual: " + newline + bestIndividual);
			}
		}
		
		try{
			Path file = Paths.get("outcome.txt");
			Files.write(file, output, Charset.forName("UTF-8"));
			} catch (IOException e) {
			System.err.println("IO ERROR");
		}

	}
}
