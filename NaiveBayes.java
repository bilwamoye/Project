package Classification;
import java.lang.Math;

public class NaiveBayes {
    public static String finalResult;

    public static void NaiveBayes(double[][] training_features, String[] training_diseases, double[] test_features, String[] All_diseases){
        int i,j,m,n,cnt=0,k,num=0;
        double add=0.0,addv=0.0,prod=1.0;
        double mean[][]=new double[6][18];
        double var[][]=new double[6][18];
        int record_number[]= new int[1560];
        int target_counts[]=new int[6];
        double probabilities[]=new double[6];
        double gaussian[][] = new double[6][18];
        double p[]=new double[6];
        double posterior[]=new double[6];
        double x;
 
        for(i=0;i<All_diseases.length;i++){
      
        cnt=0;
            for(j=0;j<training_diseases.length;j++){
            if(All_diseases[i].equals(training_diseases[j])){
                     record_number[cnt]=j;
                     cnt++;
                }
            }
          
            if(cnt>0){
            target_counts[i]=cnt;  //storing counts of diseases
            //function to calculate mean
            for(m=1;m<training_features[0].length;m++){
                add=0.0;
                for(n=0;n<cnt;n++){
                    add+=training_features[record_number[n]][m];
                }
                mean[i][m-1]=add/cnt;
                add=0.0;
            }
            //function to calculate variance
             for(m=1;m<training_features[0].length;m++){
                addv=0.0;
                for(n=0;n<cnt;n++){
                   addv+=((training_features[record_number[n]][m]-mean[i][m-1])*(training_features[record_number[n]][m]-mean[i][m-1]));
                }
                var[i][m-1]=addv/cnt;
                addv=0.0;
            }
        }
            else
            {
                continue;
            }
        }
        
        //print mean and variance
        for(i=0;i<All_diseases.length;i++){
            for(j=0;j<training_features[0].length-1;j++){
                 System.out.print(" m: "+mean[i][j]+" v: "+var[i][j]);
            }
            System.out.println();
        } 
        
        //calculate probabilities for every disease
        for(i=0;i<All_diseases.length;i++){
            probabilities[i]=(double)target_counts[i]/(double)training_features.length;
        } 
   
        System.out.println();
        //calculate gaussian parameters
        for(i=0;i<All_diseases.length;i++){
            for(j=0,k=1;j<training_features[0].length -1 && k<training_features[0].length;j++,k++){
                x= -(Math.pow((test_features[k]-mean[i][j]),2.0))/(2.0*(var[i][j]));
                gaussian[i][j]=(1/Math.sqrt(2.0*Math.PI*var[i][j]))*Math.exp(x);
                System.out.print(" x= "+x+" gaussian: "+gaussian[i][j]);
            }
            System.out.println();
        }
        
        //print gaussian values
        for(i=0;i<All_diseases.length;i++){
            for(j=0;j<training_features[0].length-1;j++){
                System.out.print(" G: "+gaussian[i][j]);
            }
            System.out.println();
        }
        
        //calculate posterior values
        for(i=0;i<All_diseases.length;i++){
             prod=1.0;
            for(j=0;j<training_features[0].length-1;j++){
                prod=prod*gaussian[i][j];
            }
            p[i]=prod*probabilities[i];
        }
        //print the posterior values
        for(i=0;i<All_diseases.length;i++){
            posterior[i]=p[i];
                System.out.print(" Posterior: "+posterior[i]);
            }
        
        //sort the posterior values
       for(i=0;i<All_diseases.length;i++){
            for(j=i+1;j<All_diseases.length;j++){
                double temp;
                if(p[i]>p[j]){
                    temp=p[i];
                    p[i]=p[j];
                    p[j]=temp;
                }
            }
        } 
      System.out.println();
       
       //print the sorted values
       for(i=0;i<All_diseases.length;i++){
           System.out.print(" "+p[i]);
        }
       System.out.println();
       //loop to check the disease
         for(i=0;i<All_diseases.length;i++){
            if(p[All_diseases.length-1]==posterior[i]){
           System.out.println("The image is classified as: "+All_diseases[i]);
           finalResult=All_diseases[i];
           break;
            }
         }
       }

}

