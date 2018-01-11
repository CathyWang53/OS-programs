/**
 * Created by a on 2017/4/24.
 */
public interface Bank {

    /**
     * Add a customer
     * customerNumber - the number of the customer
     * maximumDemand - the maximum demand for this customer
     */
    public void addCustomer(int customerNum,
                            int[] maximumDemand) ;
    /**
     * Output the value of available, maximum,
     * allocation, and need
     */
    public void getState();
    /**
     * Request resources
     * customerNumber - the customer requesting resources
     * request - the resources being requested
     */
    public boolean requestResources(int customerNumber,
                                    int[] request);
    /**
     * Release resources
     * customerNumber - the customer releasing resources
     * release - the resources being released
     */
    public void releaseResources(int customerNumber,
                                 int[] release);

}




