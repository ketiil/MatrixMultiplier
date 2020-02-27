import java.util.concurrent.CyclicBarrier;

public class MatrixMultiplier {

    private int cores = Runtime.getRuntime().availableProcessors();
    private int n;
    double[][] C;
    CyclicBarrier b;

    public MatrixMultiplier(int n){
        this.n = n;
        C = new double[n][n];
    }

    public double[][] multiplyMatrices(double[][] A, double[][] B, int start, int end){
        double[][] C = new double[n][n];

        for (int i = start; i < end; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k < n; k++){
                    C[i][j] += A[i][k]*B[k][j];
                }
            }

        }

        return C;
    }

    public double[][] multiplyMatricesBtransposed(double[][] A, double[][] B, int start, int end){
        double[][] C = new double[n][n];

        for (int i = start; i < end; i++){
            for(int j = 0; j < n; j++){
                for (int k = 0; k < n; k++){
                    C[i][j] += A[i][k]*B[j][k];
                }
            }
        }

        return C;
    }

    public double[][] multiplyMatricesAtransposed(double[][] A, double[][] B, int start,  int end){
        double[][] C = new double[n][n];

        for (int i = start; i < end; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k < n; k++){
                    C[i][j] += A[k][i]*B[k][j];
                }
            }
        }

        return C;
    }

    public double[][] transposeMatrix(double[][] m){
        double[][] transposedMatrix = new double[n][n];

        for(int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                transposedMatrix[i][j] = m[j][i];
            }
        }

        return transposedMatrix;
    }

    public double[][] initParallel(double[][] A, double[][] B, Oblig2Precode.Mode mode) {
        b = new CyclicBarrier(cores + 1);
        int partOfMatrix = n/cores;
        int remainder = n%cores;


        for (int i = 0; i < cores; i++){
            if((remainder > 0 && i == cores - 1 )){
                new Thread(new Worker(this, i, i * partOfMatrix,  (i + 1) * partOfMatrix + remainder, A, B, mode)).start();

            }else{
                new Thread(new Worker(this, i, i * partOfMatrix, (i + 1) * partOfMatrix, A, B, mode)).start();
            }
        }

        try {
            b.await();
        }catch (Exception e){
            e.printStackTrace();
        }

        return C;
    }

    public boolean checkMatrices(double[][] A, double[][] B){
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A[0].length; j++){
                if(A[i][j] != B[i][j]){
                    return false;
                }
            }
        }

        return true;
    }
}
