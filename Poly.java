import java.util.LinkedList;

/**
 * Class to represent a polynomial, e.g. 3.5x^4 + 3x^2 - 4.
 * 
 * Polynomials can be added, subtracted, multiplied, and divided.
 * 
 *
 * @author Ryan Metz
 */
public class Poly{

    /** the coefficient of the Polynomial **/ 
    double coef; 
    /** the exponent of the Polynomial **/
    int exp; 
    /** Representation of a Polynomial as a list of Monomials **/
    LinkedList<Mono> listy;
    /** the number of terms in the polynomial **/ 
    private int terms; 

    /**
     * Creates a new polynomial containing a single term with the coefficient
     * and exponent passed in as arguments. E.g. when called with coefficient
     * 3.5 and exponent 2, it should create a polynomial 3.5x^2.
     * 
     * You can create additional constructors if you'd like, but it's 
     * imperative that this one exists.
     * 
     * @param coef the single term's coefficient.
     * @param exp the single term's exponent.
     * @return the polynomial created.
     */
    public Poly(double coef, int exp){
        Mono m = new Mono(coef, exp); 
        this.listy = new LinkedList<Mono>(); 
        this.listy.add(m); 
        this.terms = 1; 
    }

    /**
     * defualt constructor for a Polynomial 
     * creates an empty list 
     */
    public Poly() {
        this.listy = new LinkedList<Mono>(); 
    }

    /**
     * Adds the polynomial passed in as an argument, p, to the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this + p".
     * 
     * @param p the polynomial to add onto the polynomial on which the method is called on.
     * @return a polynomial representing the result of the addition.
     */
    public Poly add(Poly p){
        Mono m1; 
        Mono m2; 
        Poly p1 = new Poly(); 

        //copy this. poly into the new poly
        p1 = this.copy(); 

        // merge p onto the new poly 
        for(int i = 0; i < p.terms; i++) {
            // get term from p
            m1 = p.listy.get(i); 
            //comparing term to the terms of p1
            for (int j = 0; j < p1.terms; j++) {
                // get term of p1
                m2 = p1.listy.get(j); 
                if (m1.exp > m2.exp) {
                    p1.listy.add(j, m1); 
                    p1.terms++;
                    break;
                }
                else if (m1.exp == m2.exp) {
                    m2.coef += m1.coef; 
                    break; 
                }
                else if (m1.exp < m2.exp && j == p1.terms-1) {
                    p1.listy.add(m1); 
                    p1.terms++;  
                    break;
                }
            }

        }

        p1 = p1.removeZeros(); 

        return p1; 
    }

    /**
     * Subtracts the polynomial passed in as an argument, p, from the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this - p".
     * 
     * @param p the polynomial to be subtracted from the polynomial on which the method is called on.
     * @return a polynomial representing the result of the subtraction.
     */
    public Poly subtract(Poly p){
        Mono m1; 
        Mono m2;
        Mono m3;
        Poly p1 = new Poly(); 

        //copy this. poly into the new poly
        p1 = this.copy(); 

        for(int i = 0; i < p.terms; i++) {
            m1 = p.listy.get(i); 
            for (int j = 0; j < p1.terms; j++) {
                m2 = p1.listy.get(j); 
                if (m1.exp > m2.exp) {
                    m3 = new Mono(m1.coef * -1, m1.exp); 
                    p1.listy.add(j, m3); 
                    p1.terms++;
                    break;
                }
                else if (m1.exp == m2.exp) {
                    m2.coef -= m1.coef;  
                    break; 
                }
                else if (m1.exp < m2.exp && j == p1.terms-1) {
                    m3 = new Mono(m1.coef * -1, m1.exp);  
                    p1.listy.add(m3); 
                    p1.terms++; 
                    break; 
                }
            }

        }

        p1 = p1.removeZeros();

        return p1; 
    }

    /**
     * Multiplies the polynomial passed in as an argument, p, by the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this * p".
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the result of the multiplication.
     */
    public Poly multiply(Poly p){
        Poly p1 = new Poly(); 
        Mono m1; 
        Mono m2; 
        Mono m3; 
        Mono m4; 

        // get the mono in p 
        for(int i = 0; i < p.terms; i++) {
            m1 = p.listy.get(i); 
            // get the each mono in this. and multiply it to 
            // the mono from p
            for (int j = 0; j < this.terms; j++) {
                m2 = this.listy.get(j); 
                m3 = new Mono(m1.coef * m2.coef, m1.exp + m2.exp); 
                if (p1.terms == 0) {
                    p1.listy.add(m3); 
                    p1.terms++; 
                }
                else {
                    for(int k = 0; k < p1.terms; k++) {
                        m4 = p1.listy.get(k); 
                        if (m3.exp > m4.exp) {
                            p1.listy.add(k, m3); 
                            p1.terms++;
                            break;
                        }
                        else if (m3.exp == m4.exp) {
                            m4.coef += m3.coef;
                            break; 
                        }
                        else if (m3.exp < m4.exp && k == p1.terms-1) {
                            p1.listy.add(m3); 
                            p1.terms++;  
                            break;
                        }
                    }
                }

            }
        }

        p1 = p1.removeZeros(); 

        return p1; 
    }

    /**
     * Divides the polynomial on which the method is called on (the "this" polynomial), by
     * the polynomial passed in as an argument, p, and returns a new polynomial
     * with the resulting quotient. I.e., it returns "this / p".
     * 
     * The division should be performed according to the polynomial long division algorithm
     * ( https://en.wikipedia.org/wiki/Polynomial_long_division ).
     * 
     * Polynomial division may end with a non-zero remainder, which means the polynomials are
     * indivisible. In this case the method should return null. A division by zero should also
     * yield a null return value.
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the quotient of the division, or null if they're indivisible.
     */    
    public Poly divide(Poly p){
        Poly res = new Poly();
        Poly sub;   
        Poly oDiv = new Poly();  
        Poly nDiv; 
        Mono p1;  
        Mono div1; 
        Mono res1;
        Mono sub1; 
        Mono p2; 
        Mono div2; 
        // idx for the Mono that will be dropped down
        int drop = 0; 
        Poly oldThis; 

        // check to see if person is trying to divide by zero
        if(p.terms == 1 && p.listy.get(0).coef == 0|| p.terms == 0) {
            return null; 
        }
        else if(this.terms == 0 || this.terms == 1 && this.listy.get(0).coef == 0) {
            p1 = new Mono(0, 0);
            res.listy.add(p1); 
            res.terms++;
            return res; 
        }
        else if (p.terms > this.terms) {
            return null; 
        }

        oldThis = this.copy(); 
        oldThis = oldThis.addPlaceHolders(); 

        //set this. to div
        oDiv = this.copy(); 
        // add the place holders for easier division 
        // (nothing will be added if not needed)
        oDiv = oDiv.addPlaceHolders(); 

        // get the first term of the divisor
        // (important as will always be divided by this)
        p1 = p.listy.get(0); 

        for(int i = 0; i < oldThis.terms; i++) {
            if (oDiv.terms == 0) {
                return res; 
            }
            // get first term of dividend
            div1 = oDiv.listy.get(0); 

            // make sure division is possible
            // (if an exp of bottem is bigger than its not possible)
            if (p1.exp > div1.exp) {
                return null; 
            }
            // division is possible 
            else {
                // new mono added to the result poly
                res1 = new Mono(div1.coef / p1.coef, div1.exp - p1.exp); 
                res.listy.add(res1); 
                res.terms++; 

                // multiply the answer terms to the divsor to create new Poly
                // that will be subtracted from dividend
                sub = new Poly();
                for(int j = 0; j < p.terms; j++) {
                    p2 = p.listy.get(j); 
                    sub1 = new Mono(res1.coef * p2.coef, res1.exp + p2.exp); 
                    sub.listy.add(sub1); 
                    sub.terms++; 
                }
                // add place holders if needed
                sub = sub.addPlaceHolders();
                if (oldThis.terms == 1) {
                    drop = 0; 
                }
                else if(i != 0) 
                    drop += i; 
                else 
                    drop = sub.terms; 

                // create the new dividend by subtracting the sub from the 
                // current dividend
                nDiv = new Poly(); 
                // loop throug sub terms as only want 
                // that times to subtract
                for(int k = 0; k < sub.terms; k++) { 
                    // get the term of the current divident
                    div2 = oDiv.listy.get(k); 
                    // get the terms from the sub 
                    sub1 = sub.listy.get(k); 

                    // create a new mono subtract the sub from the div
                    div1 = new Mono(div2.coef - sub1.coef, div2.exp); 

                    // if the coef is 0 than it is cancelled out so 
                    // do not add it 
                    if (div1.coef != 0) {
                        nDiv.listy.add(div1); 
                        nDiv.terms++; 
                    }

                }

                // bring down the term from the this. poly
                // and add it to nDiv
                if (drop < oldThis.terms) {
                    div1 = oldThis.listy.get(drop); 
                    nDiv.listy.add(div1); 
                    nDiv.terms++; 
                }

                oDiv = new Poly(); 
                // make new div the curr div
                oDiv = nDiv.copy();
            }
        }

        res = res.removeZeros(); 

        return res; 
    }

    /**
     * Add 0 polynomials place holders in between the gaps of exponent 
     * and returns the new polynomial
     * 
     * EX: x^4 + 2x^2 + 3 becomes x^4 + 0x^3 + 2x^2 + 3
     * 
     * @returns the new poly containing the 0 place holders
     */
    public Poly addPlaceHolders() {
        Poly newP = new Poly(); 
        Mono m1; 
        Mono m2; 
        Mono hold; 

        // get the first element and add it to the new list
        m1 = this.listy.get(0); 
        newP.listy.add(m1); 
        newP.terms++; 

        // loop through the terms starting at idx 1
        for(int i = 1; i < this.terms; i++) {
            // get the term and compare
            m2 = this.listy.get(i); 
            // if its exp is one less than the previous exp 
            // make new mono in form of 0x^prev.exp-1
            if(m2.exp < m1.exp - 1) {
                hold = new Mono(0, m1.exp -1 ); 
                newP.listy.add(i, hold); 
                newP.terms++; 
            }

            // otherwise just add that terms to the new list
            newP.listy.add(m2); 
            newP.terms++; 
            // reset the prev to the current node
            m1 = this.listy.get(i); 
        }

        return newP; 
    }

    /**
     * Compares the polynomial on which the method is called (the "this" polynomial), 
     * to the object passed in as argument, o. o is to be considered equal to the "this"
     * polynomial if they both represent equivalent polynomials.
     * 
     * E.g., "3.0x^4 + 0.0x^2 + 5.0" and "3.0x^4 + 5.0" should be considered equivalent.
     * "3.0x^4 + 5.0" and "3.0x^4 + 3.0" should not.
     * 
     * @param o the object to be compared against the polynomial the method is called on.
     * @return true if o is a polynomial equivalent to the one the method was called on,
     * and false otherwise.
     */
    public boolean equals(Object o){
        Poly p; 
        Poly p1; 
        Mono m1; 
        Mono m2;

        if(o instanceof Poly) {
            p = (Poly) o; 
        }
        else {
            return false; 
        }

        p = p.removeZeros(); 
        p1 = this.removeZeros(); 

        if (p1.terms == 0 && p.terms == 0) {
            return true; 
        }
        else if (p1.terms != p.terms) {
            return false; 
        }
        else {
            for(int i = 0; i < p.terms; i++) {
                m1 = p1.listy.get(i);
                m2 = p.listy.get(i); 

                if (m1.coef != m2.coef) {
                    return false; 
                }
                else if (m1.exp != m2.exp) {
                    return false; 
                }
            }

        }

        return true; 
    }

    /**
     * Returns a textual representation of the polynomial the method is called on.
     * The textual representation should be a sum of monomials, with each monomial 
     * being defined by a double coefficient, the letters "x^", and an integer exponent.
     * Exceptions to this rule: coefficients of 1.0 should be omitted, as should "^1",
     * and "x^0".
     * 
     * Terms should be listed in decreasing-exponent order. Terms with zero-coefficient
     * should be omitted. Each exponent can only appear once. 
     * 
     * Rules for separating terms, applicable to all terms other that the largest exponent one:
     *   - Terms with positive coefficients should be preceeded by " + ".
     *   - Terms with negative coefficients should be preceeded by " - ".
     * 
     * Rules for the highest exponent term (i.e., the first):
     *   - If the coefficient is negative it should be preceeded by "-". E.g. "-3.0x^5".
     *   - If the coefficient is positive it should not preceeded by anything. E.g. "3.0x^5".
     * 
     * The zero/empty polynomial should be represented as "0.0".
     * 
     * Examples of valid representations: 
     *   - "2.0x^2 + 3.0"
     *   - "3.5x + 3.0"
     *   - "4.0"
     *   - "-2.0x"
     *   - "4.0x - 3.0"
     *   - "0.0"
     *   
     * Examples of invalid representations: 
     *   - "+2.0x^2+3.0x^0"
     *   - "3.5x -3.0"
     *   - "- 4.0 + x"
     *   - "-4.0 + x^7"
     *   - ""
     * 
     * @return a textual representation of the polynomial the method was called on.
     */
    public String toString(){
        String res = ""; 
        Mono p;  

        if (this.terms == 0) {
            res += "0.0"; 
            return res; 
        }

        p = this.listy.get(0); 

        if (p.coef == 0) {
            res += "0.0"; 
        }
        else if (p.exp == 1 && p.coef == 1) {
            res += "x"; 
        }
        else if (p.coef == -1 && p.exp == 1) {
            res += "-x"; 
        }
        else if(p.exp == 1) {
            res += p.coef + "x"; 
        }
        else if(p.exp == 0) {
            res += p.coef; 
        }
        else if (p.coef == 1) {
            res += "x^" + p.exp;
        }
        else if(p.coef == -1) {
            res += "-x^" + p.exp;
        }
        else {
            res += p.coef + "x^" + p.exp;
        }

        for(int i = 1; i < this.terms; i++) {
            p = this.listy.get(i); 

            if (p.coef < 0) {
                res += " - ";
            }
            else 
                res += " + "; 

            res += p; 
        }

        if (res.equals("")) {
            res += "0.0";
        }

        return res; 
    }

    /**
     * removes the zero coefficiants in the polynomial 
     * 
     * @return a poly with all of the zero coeffcients removed
     */
    public Poly removeZeros() {
        Poly p = new Poly(); 
        Mono m; 

        for(int i = 0; i < this.terms; i++) {
            m = this.listy.get(i); 
            if (m.coef != 0) {
                p.listy.add(m); 
                p.terms++; 
            }
        }

        return p; 
    }

    /**
     * Returns a Poly that has had the this.poly terms copied over to it
     * 
     * @return a copy of the this. polynomial
     */
    public Poly copy() {
        Poly p1 = new Poly(); 
        Mono m1; 

        for(int i = 0; i< this.terms; i++) {
            m1 = this.listy.get(i);
            p1.listy.add(m1); 
            p1.terms++; 
        }

        return p1; 
    }

    /**
     * returns the number of terms in the this. poly
     * 
     * @return an int representing the number of terms
     * in this. poly 
     */
    public int getTerms() {
        return this.terms; 
    }
}
