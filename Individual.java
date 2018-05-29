import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individual {

    public int[] genotype;
    Random rand;
    String newline = System.getProperty("line.separator");

    int           product;
    int           sum;
    List<Integer> sumPile;
    List<Integer> productPile;
	int           numOfGenes;
    /*
    * Constructor for an individual
    * post: individual's genotype is randomized
    * */
    public Individual(int numOfGenes){
	
		this.numOfGenes = numOfGenes;
        rand = new Random();

        //randomize genotype
        genotype = new int[numOfGenes];
        for(int i = 0; i<numOfGenes;i++){
            genotype[i] = rand.nextInt(2);

        }

    }

    /*
    * mutates the genotype of the individual depending on mutation rate
    *
    * param: double representing mutationRate
    * pre: 0<mutationRate<1
    * post: individual's genotype is mutated
    * */
    public void mutate(double mutationRate){

        mutationRate = mutationRate*100;

        for(int i = 0; i<numOfGenes; i++) {

            if(rand.nextInt(100)<(int)mutationRate){

                genotype[i] = (genotype[i] + 1 )%2;
            }

        }
    }

    /*
    * returns the fitness of the individual, based on sumTarger and prodTarget
    * and on the sum of the absolute values
    * of the scaled sum error and the scaled product error
    *
    * param: int sumTarget and int prodTarget
    * returns: fitness as a Double
    *
    * */
    public Double calculateFitness(int sumTarget, int prodTarget){

        int runningSum = 0;
        int runningProduct = 1;

        for(int i = 0; i<numOfGenes; i++){

            if(genotype[i]==0){ //current site is a zero

                runningSum = runningSum + (i+1);


            }else{ //current site is a one


                runningProduct = runningProduct * (i+1);

            }

        }

        this.sum = runningSum;
        this.product = runningProduct;
        double scaledSumError = ((double)runningSum - (double)sumTarget)/(double)sumTarget;
        double scaledProductError = ((double)runningProduct - (double)prodTarget)/(double)prodTarget;

        double combinedError = Math.abs(scaledProductError) + Math.abs(scaledSumError);


        return  combinedError;
    }

    /*
    * recombines the individual's genotype with another based on some rate
    *
    * param: double encoding the recombination rate
    *        Individual whose genotype is to be recombined with this Individual
    *
    * pre: 0<recombRate<1
    *
    * post: this individuals genotype is recombined
    *
    * */
    public void recombination(double recombRate, Individual winner){
        recombRate = recombRate*100;

        for(int i = 0; i<numOfGenes; i++) {

            if(rand.nextInt(100)<(int)recombRate){

                this.genotype[i] = winner.genotype[i];
            }

        }

    }


    /*
    * generates the phenotype (the piles) encoded by the
    * individuals genotype
    *
    * post: sumPile and prodPile are updated.
    * */
    public void updatePiles(){

        sumPile = new ArrayList<Integer>();
        productPile = new ArrayList<Integer>();
        for(int i = 0; i<numOfGenes; i++){

            if(genotype[i]==0){ //current site is a zero

                sumPile.add((Integer)(i+1));

            }else{ //current site is a one

                productPile.add((Integer)(i+1));
            }

        }

    }

    public String toString(){

        String s = "";
        updatePiles();
        s = "genotype: [" ;

        for(int i = 0; i<numOfGenes-1;i++){
            s = s + genotype[i] + ",";

        }

        s = s + genotype[numOfGenes-1] + "]" + newline;

        String s2 = "";
        for(Integer i : sumPile){

            s2 = s2 + i + " ";
        }

        s = s + "sum pile: [ " + s2 + "]" +newline;

        s2 = "";

        for(Integer i : productPile){

            s2 = s2 + i + " ";
        }

        s = s + "product pile: [ " + s2 + "]" +newline;


        s = s + "sum: " + sum + newline;
        s = s + "product: " + product + newline;

        return s;
    }




}
