import java.lang.Math;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Integer;
import java.io.FileWriter;
import java.util.ArrayList;


public class Polynomial {
  double[] coefficients = {0.0};
  int[] exponents = {0};

  public Polynomial() {
  }

  public Polynomial(File F) {
    try {
      Scanner reader = new Scanner(F);
      String data = reader.nextLine();
      ArrayList<Double> c = new ArrayList<Double> ();
      ArrayList<Integer> e = new ArrayList<Integer> ();
      int i = 0;
      String term = "";
      if (data.charAt(0) == '-') {
        term += "-";
        i += 1;
      }
      while (i < data.length()) {
        while (i < data.length() && data.charAt(i) != '+' && data.charAt(i) != '-') {
          term += data.charAt(i);
          i += 1;
        }

        // if exponent is 0

        if (term.contains("x") == false) {
          c.add(Double.parseDouble(term));
          e.add(0);
        }

        // if exponent is 1

        else if (term.charAt(term.length() - 1) == 'x') {
          c.add(Double.parseDouble(term.substring(0,term.length() - 1 )));
          e.add(1);
        }

        // if exponent is more than 1
        else {
          int j = 0;
          String exp = "";
          String coef = "";
          while (term.charAt(j) != 'x'){
            coef += term.charAt(j);
            j += 1;
          }

          j += 1;

          while (j < term.length()) {
            exp += term.charAt(j);
            j += 1;
          }

          c.add(Double.parseDouble(coef));
          e.add(Integer.parseInt(exp));
        }

        if (i == data.length()) {
          break;
        }
        else if (data.charAt(i) == '+') {
          i += 1;
          term = "";
        }
        else {
          term = "-";
          i += 1;
        }
        
      }

      coefficients = c.stream().mapToDouble(z -> z).toArray();
      exponents = e.stream().mapToInt(z -> z).toArray();
      reader.close();
    } 
    
    catch (FileNotFoundException e) {
      System.out.println("An error occured.");
      e.printStackTrace();
    }
  }

  public Polynomial(double[] c, int [] e) {
    coefficients = c;
    exponents = e;
  }

  public Polynomial add (Polynomial p) {
    Integer[] combinedArray = new Integer[p.exponents.length + this.exponents.length];
    
    int lengthOfSum = 0;
    while (lengthOfSum < this.exponents.length) {
      combinedArray[lengthOfSum] = this.exponents[lengthOfSum];
      lengthOfSum += 1;
    }

    int j = 0;
    while (j < p.coefficients.length) {
      if (Arrays.asList(combinedArray).contains(p.exponents[j]) == false) {
        combinedArray[lengthOfSum] = p.exponents[j];
        lengthOfSum += 1;
      }
      j += 1;
    }

    Integer[] e = new Integer[lengthOfSum];
    for (int i = 0; i < lengthOfSum; i++){
      e[i] = combinedArray[i];
    }
    double[] c = new double[lengthOfSum];
    
    
    for (int i = 0; i < p.exponents.length; i++) {
      int index = Arrays.asList(e).indexOf(p.exponents[i]);
      c[index] = p.coefficients[i];
    }

    for (int i = 0; i < this.coefficients.length; i++){
      int index = Arrays.asList(e).indexOf(this.exponents[i]);
      c[index] += this.coefficients[i];
    }

    for (int i = 0; i < e.length; i++){
      if (c[i] == 0.0) {
        lengthOfSum -= 1;
      }
    }
    
    int[] e1 = new int[lengthOfSum];
    int i = 0;
    int count = 0;
    while (i < e.length) {
      if (c[i] != 0.0){
        e1[count] = e[i].intValue();
        count += 1;
      }
      i += 1;

    }

    double[] c1 = new double[lengthOfSum];
    i = 0;
    count = 0;
    while (i < e.length) {
      if (c[i] != 0.0){
        c1[count] = c[i];
        count += 1;
      }
      i += 1;
    }
    Polynomial pNew = new Polynomial(c1, e1);
    return pNew;
  }

  public double evaluate (double x) {
    double answer = 0;
    for (int i = 0; i < this.coefficients.length; i++){
      answer += this.coefficients[i] * Math.pow(x, this.exponents[i]);
    }
    return answer;
  }

  public boolean hasRoot (double root) {
    if (this.evaluate(root) == 0)
      return true;
    else 
      return false;
  }

  public Polynomial multiply (Polynomial p) {
    int[] combinedArrayExponents = new int[this.exponents.length * p.exponents.length];
    double[] combinedArrayCoefficients = new double[this.exponents.length * p.exponents.length];

    int counter = 0;
    for (int i = 0; i < this.exponents.length; i++) {
      for (int j = 0; j < p.exponents.length; j++) {
        combinedArrayCoefficients[counter] = this.coefficients[i] * p.coefficients[j];
        combinedArrayExponents[counter] = this.exponents[i] + p.exponents[j];
        counter += 1;
      }
    }

    // need to remove redundant exponents

    for (int i = 0; i < combinedArrayExponents.length; i++) {
      for (int j = i + 1; j < combinedArrayExponents.length; j++) {
          if (combinedArrayExponents[i] == combinedArrayExponents[j]) {
            combinedArrayCoefficients[i] += combinedArrayCoefficients[j];
            combinedArrayCoefficients[j] = 0.0;
            combinedArrayExponents[j] = -1;
          }
      }
    }

    for (int i = 0; i < combinedArrayExponents.length; i++) {
      if (combinedArrayExponents[i] != 0 && combinedArrayCoefficients[i] == 0.0) {
        combinedArrayCoefficients[i] = 0.0;
        combinedArrayExponents[i] = -1;
      }
    }

    // length of array

    int lengthOfMultiplication = 0;
    counter = 0;
    while (counter < combinedArrayExponents.length) {

      if (combinedArrayExponents[counter] == 0 && combinedArrayCoefficients[counter] == 0.0) {
        if (counter + 1 == combinedArrayExponents.length || combinedArrayExponents[counter] == 0){
          break;
        }
      }

      else if (combinedArrayExponents[counter] != -1){
        lengthOfMultiplication += 1;
      }

      counter += 1;
    }
    


    double c[] = new double[lengthOfMultiplication];
    int e[] = new int[lengthOfMultiplication];

    counter = 0;
    int arrayCounter = 0;
    while (counter < combinedArrayExponents.length) {

      if (combinedArrayExponents[counter] == 0 && combinedArrayCoefficients[counter] == 0.0) {
        if (counter + 1 == combinedArrayExponents.length || combinedArrayExponents[counter] == 0){
          break;
        }
      }

      else if (combinedArrayExponents[counter] != -1){
        c[arrayCounter] = combinedArrayCoefficients[counter];
        e[arrayCounter] = combinedArrayExponents[counter];
        arrayCounter += 1;
      }

      counter += 1;
    }

    Polynomial pNew = new Polynomial(c, e);
    return pNew;
  }

  public void saveToFile(String s) throws Exception {
    String writefile = "";
    for (int i = 0; i < this.exponents.length; i++) {
      if (this.exponents[i] == 0) {
        writefile += Double.toString(this.coefficients[i]);
      }

      else if (this.exponents[i] == 1){
        writefile += Double.toString(this.coefficients[i]) + 'x';
      }

      else {
        writefile += Double.toString(this.coefficients[i]) + 'x' + Integer.toString(this.exponents[i]);
      }

      if (i < this.exponents.length - 1 && this.coefficients[i+1] > 0) {
        writefile += '+';
      }

    }
    FileWriter output = new FileWriter(s);
    output.write(writefile);
    output.close();
  }


} 
