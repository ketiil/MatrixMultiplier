public class Worker implements Runnable {

    private int threadNumb;
    private int endInd;
    private int startInd;
    private double[][] A, B;
    private Oblig2Precode.Mode mode;
    private MatrixMultiplier matrixMul;


    public Worker(MatrixMultiplier mm, int ind, int start, int end, double[][] A, double[][] B, Oblig2Precode.Mode mode) {
        threadNumb = ind;
        startInd = start;
        endInd = end;
        this.A = A;
        this.B = B;
        this.mode = mode;
        matrixMul = mm;
    }

    @Override
    public void run() {
        double[][] tmp = null;
        switch (mode){
            case PARA_NOT_TRANSPOSED: tmp = matrixMul.multiplyMatrices(A, B, startInd, endInd); break;
            case PARA_B_TRANSPOSED: tmp = matrixMul.multiplyMatricesBtransposed(A, B, startInd,  endInd); break;
            case PARA_A_TRANSPOSED: tmp = matrixMul.multiplyMatricesAtransposed(A, B, startInd, endInd); break;
            default: break;
        }
        if(tmp != null){
            for(int i = startInd; i < endInd; i++){
                for (int j = 0; j < matrixMul.C[0].length; j++){
                    matrixMul.C[i][j] = tmp[i][j];
                }
            }
        }

        try {
            matrixMul.b.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

