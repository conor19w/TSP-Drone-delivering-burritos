package burrito;
import java.util.Random;

public class burrito {
	public static void main(String args[]){
		////////////////////////////////////////////////////////////////////////////////////
		///SETUP/////////////////////////////////////////////////
		int populationsize=150;//population of paths
		place[] data=new place[101];// array of places storing (times latitudes longitudes)
		
		int[] randompath=new int[100];//random path for generating population
		for(int i=0;i<100;i++){
			randompath[i]=i+1;
		}
		 double time[]={0,18,18,18,18,17,16,15,15,14,14,14,14,13,13,12,
				 12,12,11,10,9,9,9,8,8,7,7,5,4,3,3,2,2,2,1,0,0,0,-1,
				 -1,-1,-1,-1,-1,-2,-2,-2,-3,-4,-4,-4,-4,-5,-5,-6,-6,
				 -6,-7,-7,-7,-9,-11,-11,-12,-12,-13,-13,-13,-13,-15,
				 -15,-15,-16,-17,-18,-18,-18,-18,-19,-20,-20,-20,-20,
				 -21,-21,-21,-22,-22,-22,-23,-23,-24,-25,-25,-26,-26,
				 -27,-27,-27,-28,-28};//time overdue
		 
		 
		 double lat[]={53.38195,53.3473,53.37077,53.35152,53.37278,53.40126,53.34484,53.34133,53.34492,53.29206,53.36483,53.33067,53.36287,53.37416,53.39549,53.33239,53.34678,53.36869,53.37521,53.33751,53.37184,
				 53.36143,53.37473,53.34514,53.36115,53.39351,53.33886,53.36115,53.37497,53.37565,53.3354,53.36143,53.36115,53.39459,53.37122,53.36656,53.36141,53.37323,53.36292, 53.38122,53.35098,53.34312,53.34197,53.37954,
				 53.33835,53.36976,53.37811,53.39847,53.38579,53.37626,53.37336,53.33032,53.37201,53.37416,53.36164,53.37043,53.35372,53.2908,53.39792,53.39315,53.34439,53.33591,53.3632,53.36833,53.35298,53.38122,53.33605,
				 53.33324,53.38039,53.36883,53.35401,53.34745,53.39839,53.3473,53.29128,53.36158,53.3727,53.35321,53.31159,53.36455,53.39999,53.37414,53.37402,53.38983,53.34033,53.28973,53.36518,53.36873,53.33256,53.37565,
				 53.38895,53.34648,53.35202,53.37247,53.37449,53.36559,53.39648,53.33239,53.39512,53.33835,53.37538};//latitudes
		 
		 
		 double lon[]={-6.59192,-6.55057,-6.48279,-6.54989,-6.59611,-6.6683,-6.54766,-6.51856,-6.5557,-6.67685,-6.51278,-6.54686,-6.52468,-6.49494,-6.67647,-6.55163,-6.53415,-6.48314,
				 -6.6103,-6.53173,-6.50065,-6.51849,-6.59338,-6.53615,-6.48907,-6.5542,-6.55468,-6.48907,-6.4991,-6.60716,-6.55111,-6.51849,-6.48907,-6.66995,-6.586,-6.49183,-6.51834,
				 -6.58859,-6.50203,-6.59226,-6.54915,-6.54747,-6.55492,-6.58793,-6.53984,-6.59828,-6.57952,-6.66787,-6.58673,-6.59308,-6.48219,-6.55311,-6.48517,-6.49731,-6.50526,
				 -6.48193,-6.54564,-6.67746,-6.6711,-6.66909,-6.53841,-6.53566,-6.51178,-6.50589,-6.54921,-6.59226,-6.53414,-6.53978,-6.59368,-6.51468,-6.54603,-6.53401,-6.66767,-6.55057,
				 -6.67836,-6.50533,-6.58757,-6.55412,-6.60538,-6.51435,-6.66807,-6.60028,-6.49363,-6.5951,-6.54596,-6.67445,-6.48913,-6.49619,-6.55056,-6.60701,-6.60579,-6.54552,-6.55099,
				 -6.60044,-6.60005,-6.51914,-6.66612,-6.55163,-6.67084,-6.53984,-6.60707};//longitudes
		
		 for(int i=0;i<data.length;i++){
				data[i]=new place();
			}//create place in each slot in data
		 for(int i=0;i<data.length;i++){
				data[i].settime(time[i]);
				data[i].setlat(lat[i]);
				data[i].setlon(lon[i]);
			}//fill each slot in data with corresponding time lat and lon
		 
		 double[] geneticpath=genetic(data,randompath,time,lat,lon,populationsize);
	}
	public static double[] genetic(place[] data,int[] randompath,double[] time,double[] lat,double[] lon,int populationsize){
		int val=1;//loop through generations
		double timecustomers=0; //time customers are left waiting
		double timepassed=0;
		double distancetravelled=0;
		double bestscore=100000000.0; //variable used to keep track of lowest time
		int[] bestsolution=new int[101];//variable used to store the path for lowest time
		for(int i=0;i<101;i++){
			bestsolution[i]=0;
		}
		int[][] population=createpop(randompath,populationsize);//initial population of paths
		
		
		/////////////////////LOOP THROUGH GENERATIONS OF POPULATIONS///////////////////////////////
		do{
		double timewasted[]=timeWasted(data,population,time,populationsize);//Stores time customers are left waiting on each path
		
		
		for(int i=0;i<timewasted.length;i++){//loop through fitness
			if(timewasted[i]<bestscore){//check for new bestscore
				bestscore=timewasted[i];
				for(int j=0;j<101;j++){
				bestsolution[j]=population[i][j];
				}
				System.out.println("bestscore is "+bestscore+"\n");
				for(int j=1;j<101;j++){
				System.out.print(bestsolution[j]);
				if(j!=100){
					System.out.print(",");
				}else System.out.println("\n");
				}
				}
		}
		double[] fitness=calcfitness(timewasted,populationsize);//fitness associated with each member of population used as a probability for being selected
		population=nextGen(population,fitness,bestsolution,populationsize);
		}while(val==1);
		/////////////////////DONE LOOPING///////////////////////////////
		
		
		double[] returnval={timecustomers,timepassed,distancetravelled};
		return returnval;
	}
	
	public static int[][] createpop(int[] randompath,int populationsize){
		int[][] population= new int[populationsize][101];//2d array of paths each row a different path
		for(int i=0;i<populationsize;i++){
			for(int j=0;j<101;j++){
				population[i][j]=0;
			}
		}
		/*
		int[] x={0,39,65,68,43,49,22,94,81,100,89,29,18,
				93,45,4,37,76,34,77,92,64,70,56,3,40,1,73,
				8,42,26,30,88,97,15,51,11,67,61,66,19,99,44,
				84,41,6,91,60,23,16,71,31,21,36,79,10,62,75,
				54,38,27,24,32,86,35,17,55,2,50,52,82,13,53,28,
				20,87,63,69,95,12,46,48,83,90,96,72,47,80,5,58,
				14,98,33,59,9,74,57,85,78,7,25};//best solution i've found
		for(int i=0;i<101;i++){
			population[0][i]=x[i];
		}//include best solution in population  */
		for(int i=0;i<populationsize;i++){//change to i=1 to include best solution
			randompath=RandomizeArray(randompath);
			for(int j=1;j<101;j++){//index zero in every path is burrito bear so we start at index 1
				population[i][j]=randompath[j-1];
			}
		}
		return population;
	}
	
	public static double[] timeWasted(place[] data,int[][] population,double[] time,int populationsize){
		double[] timewasted=new double[populationsize];///Store the times customers are left waiting
		for(int i=0;i<populationsize;i++){
			timewasted[i]=0.0;
		}
		for(int path=0;path<populationsize;path++){//loop through each row in population (each path)
			//re initialize time as it is altered in each route calculation
			for(int i=0;i<data.length;i++){
				data[i].settime(time[i]);
			}
			double temptime=0.0;//time in traveling from one place to the next
			for(int i=0;i<data.length-1;i++){//loop through each place in each path
				temptime=(haversine(data[population[path][i]].getlat(),data[population[path][i]].getlon(),data[population[path][i+1]].getlat(),data[population[path][i+1]].getlon())*.75);//time taken in travelling from population[path][i] to population[path][i+1]
				for(int j=i+1;j<data.length;j++){
					data[population[path][j]].settime(data[population[path][j]].gettime()+temptime);//adds on the time passed to each place as every single customer has waited this time
				}
				if(data[population[path][i+1]].gettime()>0){
					//if the time for the place that we arrive at is positive then the customer has been waiting longer than 30 mins and their time is added to timewasted for that path
					timewasted[path]+=data[population[path][i+1]].gettime();
				}
			}
		}
		//All paths time wasted calculated so return time wasted an array whos index corresponds to each row in population
		return timewasted;
	}
	
	public static double[] calcfitness(double[] timewasted,int populationsize){
			/////turn timewasted into fitness value
			for(int i=0;i<populationsize;i++){
				timewasted[i]=Math.pow(timewasted[i], 3);//make fitness function exponential
				timewasted[i]=1/timewasted[i];
			}
			///////////////////////////////
			//normalize fitness values//
			double sum=0;
			for(int i=0;i<populationsize;i++){
				sum+=timewasted[i];
			}
			for(int i=0;i<populationsize;i++){
				timewasted[i]=timewasted[i]/sum;
			}
			
			return timewasted;
	}
	
	public static int[][] nextGen(int[][] population,double[] fitness,int[] bestsolution,int populationsize){
		int[][] newpop=new int[populationsize][101];//new population
		int[] temppop1=new int[101];//stores next path to be inserted into newpop
		int[] temppop2=new int[101];//stores next path to be inserted into newpop
		for(int i=0;i<populationsize;i++){
			for(int j=0;j<101;j++){
				newpop[i][j]=0;
			}
		}
		for(int i=0;i<101;i++){
		newpop[0][i]=bestsolution[i];//keep best solution as next gen could be worse
		}
		for(int i=1;i<populationsize;i++){
			int count=0;
			/*
			do{
			//*/
			temppop1=pickfittest(population,fitness);//using fitness as probability pick a path from previous population
			temppop2=pickfittest(population,fitness);//using fitness as probability pick a path from previous population
			count++;
			/*
			}while(temppop1==temppop2&&count<5);//breed two different paths
			//*/
			if(i<20){
				newpop[i]=temppop1;///keep some from last gen more than likely be fittest ones
			}
			else{
			newpop[i]=mutate(temppop2,temppop1);//change path to add variation and add to new pop
			}
		}
		return newpop;
	}
	public static int[] pickfittest(int[][] population,double[] fitness){
		int index=0;
		Random rgen=new Random();
		double r=rgen.nextDouble();
		while(r>0){
			r-=fitness[index];
			index++;
		}
		index--;
		return population[index];
	}
	
	public static int[] mutate(int[] pop1,int[] pop2){
		Random rgen=new Random();
		int[] pop3=new int[pop1.length];
		for(int i=0;i<pop3.length;i++){
			pop3[i]=0; //initialize the array
		}
		/////////crossover
		if(rgen.nextInt(10)<8){ 
		int R4=1+rgen.nextInt(pop1.length-2);//length of crossover
		int R3=1+rgen.nextInt(pop1.length-R4);///pick start index
		
		//////////////////////////Crossover where index remains same as pop1
		if(rgen.nextInt(10)>3){
		for(int i=R3;i<R3+R4;i++){
			pop3[i]=pop1[i];
		}
		for(int i=0;i<pop2.length;i++){
			int check=1;
			for(int j=0;j<pop3.length;j++){
				if(pop3[j]==pop2[i]){
					check=0;
				}
			}
			if(check==1){
				for(int j=1;j<pop3.length;j++){
					if(pop3[j]==0){
						pop3[j]=pop2[i];
						break;
					}
				}
			}
		}
		}
		else{
			/////Crossover where index starts at 1
			///fill pop3 with first r4 elements in pop1 starting at index r3
			for(int i=0;i<R4;i++){
				pop3[i+1]=pop1[R3];
				R3++;
			}
			////check pop2 for remaining elements
			for(int i=0;i<pop2.length;i++){
				int check=1;
				for(int j=0;j<=R4;j++){
					if(pop3[j]==pop2[i]){
						check=0;
					}
				}
				//if pop3 is missing the next element in pop 2 add it to pop 3
				if(check==1){
					pop3[R4+1]=pop2[i];	
					R4++;
				}
		}
		}
		
		///////////////////////////
		if(rgen.nextInt(10)<2){
			///randomly swap elements until i reaches upper bound
			for(int i=0;i<rgen.nextInt(6);i++){
				int R1=1+rgen.nextInt(pop3.length-1);
				int R2=1+rgen.nextInt(pop3.length-1);
				int temp=pop3[R1];
				pop3[R1]=pop3[R2];
				pop3[R2]=temp;
			}
			}
		}
		else{//keep pop same but swap elements
			for(int i=0;i<pop1.length;i++){
				pop3[i]=pop1[i];
			}
			///randomly swap elements until i reaches upper bound
			for(int i=0;i<rgen.nextInt(3);i++){
				int R1=1+rgen.nextInt(pop3.length-1);
				int R2=1+rgen.nextInt(pop3.length-1);
				int temp=pop3[R1];
				pop3[R1]=pop3[R2];
				pop3[R2]=temp;
			}
		}
		
		return pop3;
	}
	
	public static double haversine(Double latstart,Double lonstart,Double latfinish,Double lonfinish) {		 
		 final int R = 6371; // Radius of the earth
		 
		 Double latDistance = toRad(latfinish-latstart);
		 Double lonDistance = toRad(lonfinish-lonstart);
		 Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
		 Math.cos(toRad(latstart)) * Math.cos(toRad(latfinish)) * 
		 Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		 Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		 Double distance = R * c;
		 
		 return distance;

		}
		 
	public static int[] RandomizeArray(int[] array){
		Random rgen = new Random(); // Random number generator
		for (int i=0; i<array.length; i++) {
		int randomPosition = rgen.nextInt(array.length);
		int temp = array[i];
		array[i] = array[randomPosition];
		array[randomPosition] = temp;
		}
		return array;
		}
	
		 private static Double toRad(Double value) {
		 return value * Math.PI / 180;
		 }
}
class place{
	private double time;
	private double lat;
	private double lon;
	private int visited=0;
	public place(){
		this.time=0;
		this.lat=0.0;
		this.lon=0.0;
	}
	public void settime(double time){
		this.time=time;
	}
	public void setlat(double lat){
		this.lat=lat;
	}
	public void setlon(double lon){
		this.lon=lon;
	}
	public void setvisted(int visited){
		this.visited=visited;
	}
	////////////////////////////////////
	public double gettime(){
		return time;
	}
	public double getlat(){
		return lat;
	}
	public double getlon(){
		return lon;
	}
	public int getvisted(){
		return visited;
	}
}