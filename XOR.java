
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class XOR
{
    public static void main(String args[] ) throws Exception 
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input=Integer.parseInt(br.readLine());
        int cols=input+1;
        int rows=(int)Math.pow(2,input);
        int [][]xor=new int[rows][cols];
        int number;
        //generating truth table for user provided input
        for(int i=0;i<rows;i++)
        {
            input=cols-1;
            number=i;
            while(number!=0)
            {
                xor[i][--input]=number%2;
                number=number/2;
            }
            while(input >0)
            {
                xor[i][--input]=0;
            }
        }
        //generating the xnor table specific for every function
        for(int i=0;i<rows;i++)
        {
            xor[i][cols-1]=0;
            for(int j=0;j<cols-1;j++)
            {
               xor[i][cols-1]^=xor[i][j];
            }
        }
        //modifying it for perceptron training algorithm
        for(int i=0;i<rows;i++)
        {
          if(xor[i][cols-1]==0)
          {
            for(int j=0;j<cols-1;j++)
            {
               xor[i][j]= -xor[i][j];
            }
            xor[i][cols-1]=1;
          }
          else
              xor[i][cols-1]=-1;
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
                check+=weight[j]*xor[i][j];
            }
            if(check <=0)
            {
                count++;
                String str="";
                for(int j=0;j<cols;j++)
                {
                  weight[j]=weight[j]+xor[i][j];
                  str+=weight[j];
                }
                if(weights.containsKey(str))
                {
                    System.out.println("Cycle detected at"+ count +"th iteration, not separable");
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
           System.out.print(count);
           break;
         }
        }
      }
    }
