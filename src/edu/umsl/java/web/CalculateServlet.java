package edu.umsl.java.web;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculate")
public class CalculateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ServletContext ctx = this.getServletContext();

		String cities = ctx.getInitParameter("cities");
		String rates = ctx.getInitParameter("tax rates");
		
		String city_index = request.getParameter("city");
		
		System.out.println(city_index);
		
		String[] cityArr = cities.split(",");
		String[] rateArr = rates.split(",");
		
		
		int idx = Integer.parseInt(city_index);
		//passing city and tax
		request.setAttribute("city", cityArr[idx]);
		request.setAttribute("tax", rateArr[idx]);
		
		//This is the user input, passed as a string parameter to BigInt
		String input = request.getParameter("userInput");
		BigInteger bigInt = new BigInteger(input);
		request.setAttribute("userInput", bigInt);
	 	
		//----------------------------------------//
		
	    ExecutorService service = Executors.newSingleThreadExecutor();
	    
	    //try catch block to time the execution
	    try {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                	
                
                	long startTimer=System.currentTimeMillis();
                	
                	BigInteger square = getSquareRootBigInteger(bigInt);
            		BigInteger factor1 = BigInteger.ONE;
            		BigInteger factor2 = null;
                	
                	for(BigInteger i = square.subtract(BigInteger.ONE); i.compareTo(BigInteger.ZERO)>0; i=i.subtract(BigInteger.ONE))
            		{
            			BigInteger m =bigInt.mod(i);
            			if(m.compareTo(BigInteger.ZERO)==0)
            			{
            				factor1=i;
            				break;
            			}	
            		}

            		factor2 = bigInt.divide(factor1);
            		long endTime=(System.currentTimeMillis()-startTimer)/1000;
            		
            		request.setAttribute("timer", endTime);
            		request.setAttribute("factor1", factor1);
            		request.setAttribute("factor2", factor2);
                	
            		
                }
            };
       
            Future<?> f = service.submit(r);
           
            f.get(10, TimeUnit.SECONDS);
        }
        catch (final TimeoutException e) {
            long endTime =11;
            request.setAttribute("timer", endTime);
        
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
		
		//where result will be displayed
		RequestDispatcher view = request.getRequestDispatcher("result.jsp");

		view.forward(request, response);

	}
	
	private BigInteger averageOfTwoBigIntegers(BigInteger big1, BigInteger big2) {
		BigInteger sum = big1.add(big2);
		BigInteger avg = sum.divide(new BigInteger("2"));

		return avg;
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

}

