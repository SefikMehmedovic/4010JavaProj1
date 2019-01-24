package edu.umsl.java.web;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bigcalculate")
public class BigIntegerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		BigInteger crtbig = this.generateBigIntegerWithNumberOfDigits(100);

		BigInteger crtbigsqrt = this.getSquareRootBigInteger(crtbig);

		request.setAttribute("onebigint", crtbig);
		request.setAttribute("crtbigsqrt", crtbigsqrt);
		
		BigInteger crtsquare1 = crtbigsqrt.multiply(crtbigsqrt);
		BigInteger diff1 = crtsquare1.subtract(crtbig);
		
		BigInteger crtbigsqrt2 = crtbigsqrt.subtract(new BigInteger("1"));
		BigInteger crtsquare2 = crtbigsqrt2.multiply(crtbigsqrt2);
		
		BigInteger diff2 = crtbig.subtract(crtsquare2);
		
		request.setAttribute("diff1", diff1);
		request.setAttribute("diff2", diff2);

		RequestDispatcher view = request.getRequestDispatcher("result.jsp");

		view.forward(request, response);

	}

	private BigInteger generateBigIntegerWithNumberOfDigits(int n) {
		BigInteger mybig = null;

		int initrand = (int) (Math.random() * 9 + 1);

		if (n > 0) {
			mybig = new BigInteger("" + initrand);

			for (int i = 1; i < n; i++) {
				int crtdigit = (int) (Math.random() * 10);

				BigInteger big1 = new BigInteger("" + crtdigit);

				mybig = mybig.multiply(new BigInteger("10"));
				mybig = mybig.add(big1);
			}
		} else {
			mybig = new BigInteger("0");
		}

		return mybig;
	}

	private BigInteger getSquareRootBigInteger(BigInteger big) {
		int cmp1 = 0;
		int cmp2 = 0;

		BigInteger upper = big;
		BigInteger lower = new BigInteger("0");

		BigInteger avg1 = null;
		BigInteger sqr1 = null;

		BigInteger avg2 = null;
		BigInteger sqr2 = null;

		avg1 = this.averageOfTwoBigIntegers(upper, lower);

		sqr1 = avg1.multiply(avg1);

		cmp1 = big.compareTo(sqr1);

		if (cmp1 == 0) {
			return avg1;
		} else if (cmp1 > 0) {
			avg1 = avg1.add(new BigInteger("1"));

			sqr1 = avg1.multiply(avg1);

			cmp1 = big.compareTo(sqr1);

			if (cmp1 <= 0) {
				return avg1;
			} else {
				lower = avg1;
			}
		} else {
			BigInteger avg0 = avg1.subtract(new BigInteger("1"));

			sqr1 = avg0.multiply(avg0);

			cmp1 = big.compareTo(sqr1);
			
			if (cmp1 >= 0) {
				return avg1;
			} else {
				upper = avg1;
			}
		}

		while (cmp1 * cmp2 != -1) {
			avg2 = this.averageOfTwoBigIntegers(upper, lower);

			sqr2 = avg2.multiply(avg2);

			cmp2 = big.compareTo(sqr2);

			if (cmp2 == 0) {
				return avg2;
			} else if (cmp2 > 0) {
				avg2 = avg2.add(new BigInteger("1"));

				sqr2 = avg2.multiply(avg2);

				cmp2 = big.compareTo(sqr2);

				if (cmp2 <= 0) {
					return avg2;
				} else {
					lower = avg2;
				}
			} else {
				BigInteger avg0 = avg2.subtract(new BigInteger("1"));

				sqr2 = avg0.multiply(avg0);

				cmp2 = big.compareTo(sqr2);
				
				if (cmp2 >= 0) {
					return avg2;
				} else {
					upper = avg2;
				}
			}

			avg1 = avg2;
			cmp1 = cmp2;
		}

		return big;
	}

	private BigInteger averageOfTwoBigIntegers(BigInteger big1, BigInteger big2) {
		BigInteger sum = big1.add(big2);
		BigInteger avg = sum.divide(new BigInteger("2"));

		return avg;
	}

}
