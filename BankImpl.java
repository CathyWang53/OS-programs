/**
 * Created by a on 2017/5/1.
 */
public class BankImpl {
    int numberOfCustomers; // the number of customers
    int numberOfResources; // the number of resources
    int[] available; // the available amount of each resource
    int[][] maximum; // the maximum demand of each customer
    int[][] allocation; // the amount currently allocated
    // to each customer
    int[][] need; // the remaining needs of each customer

    BankImpl(int [] initialResource){
        numberOfResources = initialResource.length;
        numberOfCustomers = 0;
        available = initialResource;
        maximum = new int[100][numberOfResources];
        allocation = new int[100][numberOfResources];
        need = new int[100][numberOfResources];
        for (int i=0;i<100;++i)
            for(int j=0;j<numberOfResources;++j){
                maximum[i][j] = 0;
                allocation[i][j] = 0;
                need[i][j] = 0;
            }
    }

    public void addCustomer(int customerNum,
                            int[] maximumDemand){
       for (int i=0;i<numberOfResources;++i){
           maximum[customerNum][i] = maximumDemand[i];
           need[customerNum][i] = maximumDemand[i];
       }
        numberOfCustomers++;
    }

    public void getState(){
        System.out.println("当前系统可用：");
        //System.out.println(numberOfCustomers);
        for (int i=0;i<numberOfResources;++i){
            System.out.print(available[i]);
            System.out.print(' ');
        }
        System.out.print('\n');

        System.out.println("用户最大需要：");
        for (int i=0;i<numberOfCustomers;++i){
            for(int j=0;j<numberOfResources;++j) {
                System.out.print(maximum[i][j]);
                System.out.print(' ');
            }
            System.out.print('\n');//换行
        }

        System.out.println("此刻用户需要：");
        for (int i=0;i<numberOfCustomers;++i){
            for(int j=0;j<numberOfResources;++j) {
                System.out.print(need[i][j]);
                System.out.print(' ');
            }
            System.out.print('\n');//换行
        }

        System.out.println("此刻系统分配：");
        for (int i=0;i<numberOfCustomers;++i){
            for(int j=0;j<numberOfResources;++j) {
                System.out.print(allocation[i][j]);
                System.out.print(' ');
            }
            System.out.print('\n');//换行
        }
    }

    public boolean requestResources(int customerNumber,
                                    int[] request){
        boolean flag = true; //此函数的返回值
        boolean chgFlag = true;//有变化发生，如果某次循环中完全没有变化，说明已经进入死锁

        //找路径时的temp Available数组，以下类似
        int[] avlb2 = new int[numberOfResources];
        int[][] alloct2 = new int[numberOfCustomers][numberOfResources];
        int[][] need2 = new int[numberOfCustomers][numberOfResources];
        boolean[] finish = new boolean[numberOfCustomers];

        int i,j,k;

        //初始化temp变量
        for (i=0;i<numberOfCustomers;++i){
            finish[i] = false;
            for (j=0;j<numberOfResources;++j){
                alloct2[i][j] = allocation[i][j];
                need2[i][j] = need[i][j];
                avlb2[j] = available[j];
            }
        }

        //判断：如果大于need、如果大于available、如果小于0就驳回
        //更新数组,假如满足request后的分配情况
        for (i=0;i < numberOfResources;++i){
            if (request[i] > avlb2[i] || request[i] > need2[customerNumber][i]){
                System.out.println("申请不能大于系统当前值或最大需要值");
                return false;
            }
            if (request[i] < 0){
                System.out.println("申请不能小于0");
                return false;
            }
            avlb2[i] -= request[i];
            alloct2[customerNumber][i] += request[i];
            need2[customerNumber][i] -= request[i];
        }

        //找路 循环
        for (i=0;i<numberOfCustomers;++i){//一次循环至少只给一个进程finish，所以最多需要循环customers'Number次
            if (chgFlag == false) break;//上次循环什么都没变，已经进入死锁或者都已完成
            else chgFlag = false;

            for (j=0;j<numberOfCustomers;++j){
                if (finish[j] == false) {//如果这个进程没完成
                    for (k = 0; k < numberOfResources; ++k) {
                        if (need2[j][k] > avlb2[k]) break;
                    }
                    if ( k==numberOfResources ){//这个进程的所有需求都小于系统现在的可用资源
                        for (k = 0; k < numberOfResources; ++k) {
                            avlb2[k] += alloct2[j][k];//这个进程执行完，释放所有资源
                            alloct2[j][k] = 0;
                            need2[j][k] = 0;
                            finish[j] = true;
                            chgFlag = true;
                        }
                    }
                }
            }
        }

        for (i=0;i<numberOfCustomers;++i){
            if (finish[i] == false){//看是不是所有进程都能完成
                flag = false;
                break;
            }
        }

        if (flag){//改变系统现在的资源状态
            for (i=0;i < numberOfResources;++i){
                available[i] -= request[i];
                allocation[customerNumber][i] += request[i];
                need[customerNumber][i] -= request[i];
            }
        }
        return flag;
    }

    public void releaseResources(int customerNumber,
                                 int[] release){
        for (int i=0;i<numberOfResources;++i){
            available[i] += release[i];
            allocation[customerNumber][i] -= release[i];
            need[customerNumber][i] += release[i];
        }
    }


}
