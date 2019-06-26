package es.uma.atenea.cepdm.function;

/**
 * A class to compute least square estimation using derivates. A different model is
 * fitted for each sensor measurement (temperature, humidity, light, CO2 and humidity ratio).
 *  
 * @author Aurora
 * @version 1 
 * */

public class LeastSquaresEstimation {

	/** Number of current samples */
	protected static int counterSamples = 0;

	/** Auxiliary data structure to compute power values in the x axis (ordinals) */
	protected static double [] xPow = new double[4];
	
	/** Auxiliary data structure to compute powers of temperature values */
	protected static double [] yPowTemperature = new double[3];
	
	/** Auxiliary data structure to compute powers of humidity values */
	protected static double [] yPowHumidity = new double[3];
	
	/** Auxiliary data structure to compute powers of light values */
	protected static double [] yPowLight = new double[3];
	
	/** Auxiliary data structure to compute powers of CO2 values */
	protected static double [] yPowCO2 = new double[3];
	
	/** Auxiliary data structure to compute powers of humidity values */
	protected static double [] yPowHumidityRatio = new double[3];
	
	/**
	 * Default constructor
	 * */
	public LeastSquaresEstimation() {
		super();
	}

	/**
	 * Get the next estimation for temperature, humidity, light, CO2 and humidity ratio.
	 * @return The estimated values for all sensor measurements.
	 * */
	public static double [] getEstimation() {

		double [] estimation = new double [] {Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN};

		if(counterSamples > 1) {
			// The next x
			double nextX = counterSamples+1;

			estimation[0] = getEstimation(xPow, yPowTemperature, nextX);
			estimation[1] = getEstimation(xPow, yPowHumidity, nextX);
			estimation[2] = getEstimation(xPow, yPowLight, nextX);
			if(estimation[2]<0)
				estimation[2]=0; // Light cannot be negative
			estimation[3] = getEstimation(xPow, yPowCO2, nextX);
			estimation[4] = getEstimation(xPow, yPowHumidityRatio, nextX);
			if(estimation[4]<0)
				estimation[4]=0; // Ratio cannot be negative
		}
		return estimation;
	}

	/**
	 * Add a new observation of the sensor measurements
	 * @param temperature New temperature value
	 * @param humidity New humidity value
	 * @param light New light value
	 * @param co2 New CO2 value
	 * @param humidityRatio New humidity ratio
	 * */
	public static void addObservation(double temperature, double humidity, double light, double co2, double humidityRatio) {
		counterSamples++;
		updateX(counterSamples);
		updateTemperature(temperature,counterSamples);
		updateHumidity(humidity,counterSamples);
		updateLight(light,counterSamples);
		updateCO2(co2,counterSamples);
		updateHumidityRatio(humidityRatio,counterSamples);		
	}
	
	/**
	 * Update x powers
	 * @param n Current number of samples
	 * */
	protected static void updateX(int n) {
		xPow[0] += n;
		xPow[1] += n*n;
		xPow[2] += n*n*n;
		xPow[3] += n*n*n*n;
	}
	
	/**
	 * Update temperature powers
	 * @param temperature New temperature value
	 * @param n Current number of samples
	 * */
	protected static void updateTemperature(double temperature, int n) {
		yPowTemperature[0] += temperature;
		yPowTemperature[1] += n*temperature;
		yPowTemperature[2] += n*n*temperature;
	}
	
	/**
	 * Update humidity powers
	 * @param humidity New humidity value
	 * @param n Current number of samples
	 * */
	protected static void updateHumidity(double humidity, int n) {
		yPowHumidity[0] += humidity;
		yPowHumidity[1] += n*humidity;
		yPowHumidity[2] += n*n*humidity;
	}
	
	/**
	 * Update light powers
	 * @param light New light value
	 * @param n Current number of samples
	 * */
	protected static void updateLight(double light, int n) {
		yPowLight[0] += light;
		yPowLight[1] += n*light;
		yPowLight[2] += n*n*light;
	}
	
	/**
	 * Update CO2 powers
	 * @param New CO2 value
	 * @param n Current number of samples
	 * */
	protected static void updateCO2(double co2, int n) {
		yPowCO2[0] += co2;
		yPowCO2[1] += n*co2;
		yPowCO2[2] += n*n*co2;
	}
	
	/**
	 * Update humidity ratio powers
	 * @param humidityRatio New humidity ratio
	 * @param n Current number of samples
	 * */
	protected static void updateHumidityRatio(double humidityRatio, int n) {
		yPowHumidityRatio[0] += humidityRatio;
		yPowHumidityRatio[1] += n*humidityRatio;
		yPowHumidityRatio[2] += n*n*humidityRatio;
	}
	
	/**
	 * Clear the previous data in all estimation models
	 * */
	public static void clearData() {
		xPow[0] = xPow[1] = xPow[2] = xPow[3] = 0;
		yPowTemperature[0] = yPowTemperature[1] = yPowTemperature[2] = 0;
		yPowHumidity[0] = yPowHumidity[1] = yPowHumidity[2] = 0;
		yPowCO2[0] = yPowCO2[1] = yPowCO2[2] = 0;
		yPowLight[0] = yPowLight[1] = yPowLight[2] = 0;
		yPowHumidityRatio[0] = yPowHumidityRatio[1] = yPowHumidityRatio[2] = 0;
		
		counterSamples = 0;
	}

	/**
	 * Compute the estimated measurements solving the equations after derivation.
	 * @param xPow Auxiliary array with precomputed powers in the X axis
	 * @param yPow Auxiliary array with precomputed powers in the Y axis
	 * @param newX The x value for which y value will be estimated
	 * @return y value estimated for the given x value
	 * */
	protected static double getEstimation(double [] xPow, double [] yPow, double newX) {
		double result = 0;
		
		// Solve coefficients
		double b = solveB(xPow, yPow, counterSamples);
		double a = solveA(xPow, yPow, b, counterSamples);
		double c = solveC(xPow, yPow, b, a, counterSamples);

		// Estimate next value
		result = a*newX*newX + b*newX + c;
		return result;
	}

	/**
	 * Solve b coefficient
	 * @param xPow Auxiliary array with precomputed powers in the X axis
	 * @param yPow Auxiliary array with precomputed powers in the Y axis
	 * @param newX The x value for which y value will be estimated
	 * @return The b coefficient
	 * */
	protected static double solveB(double [] xPow, double [] yPow, double n) {
		double b = 0;
		double I1 = xPow[0];
		double I2 = xPow[1];
		double I3 = xPow[2];
		double I4 = xPow[3];
		double X0 = yPow[0];
		double X1 = yPow[1];
		double X2 = yPow[2];

		double alpha = n*I3 - I1*I2;
		double beta = I1*I1 - n*I2;
		double gamma = n*X1 - I1*X0;
		double num = n*alpha*X2 - n*I4*gamma - alpha*I2*X0 + I2*I2*gamma;
		double den = n*I4*beta + alpha*n*I3 - I2*I2*beta - alpha*I1*I2;

		if(den!=0)
			b = num / den;

		return b;
	}

	/**
	 * Solve a coefficient
	 * @param xPow Auxiliary array with precomputed powers in the X axis
	 * @param yPow Auxiliary array with precomputed powers in the Y axis
	 * @param newX The x value for which y value will be estimated
	 * @return The a coefficient
	 * */
	protected static double solveA(double [] xPow, double [] yPow, double b, double n) {
		double a = 0;
		double num = n*yPow[1]  - xPow[0]*yPow[0] + b*(xPow[0]*xPow[0] - n*xPow[1]);
		double den = n*xPow[2] - xPow[0]*xPow[1];

		if(den!=0)
			a = num / den;
		return a;
	}

	/**
	 * Solve C coefficient
	 * @param xPow Auxiliary array with precomputed powers in the X axis
	 * @param yPow Auxiliary array with precomputed powers in the Y axis
	 * @param newX The x value for which y value will be estimated
	 * @return The c coefficient
	 * */
	protected static double solveC(double [] xPow, double [] yPow, double b, double a, double n) {
		double c = (yPow[0] - a*xPow[1] - b*xPow[0]) / n;
		return c;
	}
}
