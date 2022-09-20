import java.lang.Math;

public class Polynomial {
  double[] coefficients = {0.0};

  public Polynomial() {
  }

  public Polynomial(double[] c) {
    coefficients = c;
  }

  public Polynomial add (Polynomial p) {
    int smallerArray = Math.min(p.coefficients.length, this.coefficients.length);
    int biggerArray = Math.max(p.coefficients.length, this.coefficients.length);
    double[] c = new double[biggerArray];
    for (int i = 0; i < smallerArray; i++) {
      c[i] = p.coefficients[i] + this.coefficients[i];
    }
    if (biggerArray > smallerArray) 
      if (biggerArray == p.coefficients.length)
        for (int i = smallerArray; i < biggerArray; i++){
          c[i] = p.coefficients[i];
        }
      else 
        for (int i = smallerArray; i < biggerArray; i++){
          c[i] = this.coefficients[i];
        }
    Polynomial pNew = new Polynomial(c);
    return pNew;
  }

  public double evaluate (double x) {
    double answer = 0;
    for (int i = 0; i < this.coefficients.length; i++){
      answer += this.coefficients[i] * Math.pow(x, i);
    }
    return answer;
  }

  public boolean hasRoot (double root) {
    if (this.evaluate(root) == 0)
      return true;
    else 
      return false;
  }
} 
