import java.util.Arrays;
import java.io.File;

public class Driver { 
  public static void main(String [] args) throws Exception { 
   Polynomial p = new Polynomial(); 
   System.out.println(p.evaluate(3)); 
   double [] c1 = {6, 6, -6}; 
   int [] e1 = {2, 3, 4};
   Polynomial p1 = new Polynomial(c1, e1); 
   double [] c2 = {-6, 6, -6};
   int [] e2 = {2, 3, 4}; 
   Polynomial p2 = new Polynomial(c2, e2); 
   Polynomial s = p1.add(p2); 
   System.out.println(Arrays.toString(s.coefficients));
   System.out.println(Arrays.toString(s.exponents));
   System.out.println(" ");
   Polynomial s1 = p1.multiply(p2);
   s.saveToFile("Polynomial.txt");
   System.out.println(Arrays.toString(s1.coefficients));
   System.out.println(Arrays.toString(s1.exponents));
   System.out.println(" ");
   File F = new File ("/Users/faizannaseer/b07lab1/Polynomial.txt");
   Polynomial s3 = new Polynomial(F);
   System.out.println(Arrays.toString(s3.coefficients));
   System.out.println(Arrays.toString(s3.exponents));
   System.out.println(" ");

   System.out.println("s(0.1) = " + s.evaluate(0.1)); 
   if(s.hasRoot(1)) 
    System.out.println("1 is a root of s"); 
   else 
    System.out.println("1 is not a root of s"); 
  }

  
 }