package Classification;
import java.lang.Math;

public class KNN {
    public static String finalResult;
    public static void knn(double[][] training_features, String[] disease, double[] test_features, int k,String[] All_diseases) {
        int i, j, count = 0, num = 0, m;
        int count1[] = new int[6000];
        double image_id[] = new double[6000];
        double sorted[] = new double[6000];
        double inverse[] = new double[6000];
        double disease_id[] = new double[6000];
        double sub = 0.0, sum = 0.0, temp, flag = 1.0, temp1 = 0.0, inv = 0.0,n=0.0;
        double distance[] = new double[6000];
        String out_disease[] = new String[6000];
        System.out.println("Distances from training to test datasets: ");
        //calculate distance from test data to training data using Euclidean distance
        System.out.println("T:"+training_features.length);
        for (i = 0; i < training_features.length; i++) {
            sum = 0.0;
            for (j = 1; j < training_features[0].length; j++) {
                sub = Math.abs(training_features[i][j] - test_features[j]);
                sum = sum + sub;
                sub = 0.0;
            }
            distance[i] = sum;
            System.out.println("Image " + flag + " =" + sum);
            image_id[count] = training_features[i][0];
            count++;
            flag++;
        }
        //sort the distances
        for (i = 0; i < training_features.length; i++) {
            for (j = i + 1; j < training_features.length; j++) {
                if (distance[i] > distance[j]) {
                    temp = distance[i];
                    temp1 = image_id[i];
                    distance[i] = distance[j];
                    image_id[i] = image_id[j];
                    distance[j] = temp;
                    image_id[j] = temp1;
                }
            }
        }
        //to display the sorted distances
        System.out.println("After sorting: ");
        for (i = 0; i < training_features.length; i++) {
            System.out.println("image " + image_id[i] + ": " + distance[i]);
        }  
        //for k=1
        if (k == 1) {
            for (i = 0; i < training_features.length; i++) {
                    if (training_features[i][0] == image_id[0]) {
                        System.out.print("image id " + test_features[0] + " belongs to disease type: " + disease[i]);
                        finalResult=disease[i];
                    }
            }
        }
        else if (k > 1) {
            int cnt = 0;
            System.out.println("k= " + k + " hence, the diseases are :");
            for (i = 0; i < k; i++) {
                for (j = 0; j < training_features.length; j++) {
                    if (image_id[i] == training_features[j][0]) {
                        disease_id[num] = image_id[i];
                        out_disease[num] = disease[j];
                        System.out.println(disease_id[num] + ": " + out_disease[num]);
                        num++;
                    }
                }
            }
            String stemp = " ";
            cnt = 1;
            count = 1;
            num = 0;
            int max_count = 1, equal = 0;
            for (i = 0; i < k; i++) {
                for (j = i + 1; j <= k; j++) {
                    if (out_disease[i].equals(out_disease[j])) {
                        ++cnt;
                    }
                    else
                        continue;
                }
                count1[i] = cnt;
                cnt = 1;
                if (count1[i] > max_count) {
                    stemp = out_disease[i];
                    finalResult=stemp;
                    
                    max_count = count1[i];
                }
                else if (count1[i] == max_count) {
                    stemp = " ";
                }
            }
 
            if (stemp.equals(" ")) {
                
                System.out.println("Since there's a tie we calculate inverse:");
                
                System.out.println("Inverse weighted measure: ");
                for(i=0;i<All_diseases.length;i++){
                    inv=0.0;
                    sum=0.0;
                    for(j=0;j<k;j++){
                        if(All_diseases[i].equals(out_disease[j])){
                            n=1.0;
                        }
                        else
                        {
                            n=0.0;
                        }
                        inv=((1.0 / Math.pow(distance[j],2.0)) * n);
                        sum=sum+inv;

                    }
                    sorted[i]=sum;
                    inverse[i]=sum;

                }
                //printing inv
                System.out.println(" Inverse measures: ");
                temp=0.0;
                // sorting the inverse measures
                for(i=0;i<All_diseases.length;i++){
                    for(j=i+1;j<All_diseases.length;j++){
                        if(sorted[i]>sorted[j]){
                            temp=sorted[i];
                            sorted[i]=sorted[j];
                            sorted[j]=temp;
                        }
                    }
                }
                //print sorted
                 for(i=0;i<All_diseases.length;i++){
                    System.out.println(sorted[i]);
                }
                 //print final result
                for(i=0;i<All_diseases.length;i++){
                    if(sorted[All_diseases.length-1]==inverse[i]){
                        stemp=All_diseases[i];
                        finalResult=All_diseases[i];
                        System.out.println("The disease is classified as " +finalResult);
                    }
                }
                System.out.println("The disease is classified as " +finalResult);
               
        }
            else{
                System.out.println("The disease is classified as " +finalResult);
            }
    }
    }
}
