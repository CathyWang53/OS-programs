/**
 * Created by a on 2017/4/23.
 */
/*class globalVrb{
    public int M=3;
    public int K = 2;
    public int N = 3;
    public int A[][] = { {1,4}, {2,5}, {3,6} };
    public int B[][] = { {8,7,6}, {5,4,3} };
    public int[][] C;
}*/

public class MatMulti {
    public static void main(String[] args){
        int M = 2;//A的行数
        int K = 2;//A的列数 B的行数
        int N = 4;//B的列数
        int threadNum = M * N;
        int k = 0;
        int A[][] = { {5,2}, {-3,1} };
        int B[][] = { {14,0,5,7}, {-5,14,13,16} };
        int[][] C;
        C = new int[M][N];//M行N列

        //System.out.println("");

        MatMulti work1 = new MatMulti();//MatMulti为主类
        WorkerThread[] workers = new WorkerThread[threadNum];

        for (int i=0;i < M; ++i)
            for (int j=0; j<N; ++j){
                workers[k] = work1.new WorkerThread(i,j,A,B,C,M,K,N);
                workers[k].run();
                k++;
            }

        for (int i = 0; i < threadNum; i++) {
            try {
                workers[i].join();//等待线程们运行完
            } catch (InterruptedException ie) {
                System.out.println("第"+i+"个线程冻结");
            }
        }

        for(int i = 0; i < M; i++){
            for(int j = 0; j <N; j++){
                System.out.print(C[i][j]+" ");
            }
            System.out.println();
        }
    }

    class WorkerThread extends Thread
    {
        private int row;
        private int col;
        private int[][] A;
        private int[][] B;
        private int[][] C;
        private int K;
        private int M;
        private int N;
        public WorkerThread(int row, int col, int[][] A,
                            int[][] B, int[][] C,int M, int K, int N) {
            this.row = row;
            this.col = col;
            this.A = A;
            this.B = B;
            this.C = C;
            this.K = K;
            this.M = M;
            this.N = N;
        }
        public void run() {
            /* calculate the matrix product in C[row] [col] */
            C[row][col] = 0;
            for (int i=0;i<K;++i){
                C[row][col] += A[row][i] * B[i][col];
            }
        }
    }

}


