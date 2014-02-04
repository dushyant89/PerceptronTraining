
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class EvenParity
{
    public static void main(String args[] ) throws Exception 
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input=Integer.parseInt(br.readLine());
        int cols=input+1;
        int rows=(int)Math.pow(2,input);
        int [][]parity=new int[rows][cols];
        int number;
        //generating truth table for user provided input
        for(int i=0;i<rows;i++)
        {
            input=cols-1;
            number=i;
            while(number!=0)
            {
                parity[i][--input]=number%2;
                number=number/2;
            }
            while(input >0)
            {
                parity[i][--input]=0;
            }
        }
        //generating the generic input even parity checker table
        for(int i=0;i<rows;i++)
        {
            parity[i][cols-1]=0;
            for(int j=0;j<cols-1;j++)
            {
                parity[i][cols-1]^=parity[i][j];
            }
            parity[i][cols-1]=1^parity[i][cols-1];
        }
        //modifying it for perceptron training algorithm
       for(int i=0;i<rows;i++)
        {
          if(parity[i][cols-1]==0)
          {
            for(int j=0;j<cols-1;j++)
            {
               parity[i][j]=-parity[i][j];
            }
            parity[i][cols-1]=1;
          }
          else
              parity[i][cols-1]=-1;
        }
        // now applying the pta   
        
      int []weight=new int[cols];
      int check;
      int count=0;
      HashMap<String,Boolean> weights= new HashMap<String,Boolean>();
      weights.put("000",true);
      outer:while(true)
      {
        int i;
        for(i=0;i<rows;i++)
        {
            check=0;
            for(int j=0;j<cols;j++)
            {
                check+=weight[j]*parity[i][j];
            }
            if(check <=0)
            {
                count++;
                String str="";
                for(int j=0;j<cols;j++)
                {
                  weight[j]=weight[j]+parity[i][j];
                  str+=weight[j]+",";
                }
                if(weights.containsKey(str))
                {
                    System.out.println("Cycle detected at "+ count +"th iteration, not separable");
                    break outer;
                }
                else
                {
                    weights.put(str,true);
                }
                break;
            }
        }
         if(i==rows)
         {
           for(i=0;i<cols;i++)
           {
               System.out.print(weight[i]+" ");
           }
           System.out.print("At "+count+" iteration the perceptron becomes stable");
           break;
         }
        }
      }
    }
