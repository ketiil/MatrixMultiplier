import java.util.Arrays;

public class Application {

    double[][] C;

    public static void main(String[] args) {

        int seed = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);

        double[][] A = Oblig2Precode.generateMatrixA(seed, n);
        double[][] B = Oblig2Precode.generateMatrixB(seed, n);

        MatrixMultiplier m = new MatrixMultiplier(n);

        long startTime;
        long endTime;

        // Sequential not transposed
        double[][] C = new double[n][n];
        double[] times = new double[7];
        for(int i = 0; i < 7; i++){
            startTime = System.nanoTime();
            C = m.multiplyMatrices(A, B, 0, n);
            endTime = System.nanoTime();
            times[i] = (endTime - startTime) / 1000000.0;

        }
        Arrays.sort(times);
        System.out.println("Seq, not transposed: " + times[3]);
        Oblig2Precode.saveResult(seed, Oblig2Precode.Mode.SEQ_NOT_TRANSPOSED, C);

        // Sequential, B transposed
        for(int i = 0; i < 7; i++){
            startTime = System.nanoTime();
            B = m.transposeMatrix(B);
            C = m.multiplyMatricesBtransposed(A, B, 0, n);
            endTime = System.nanoTime();
            times[i] = (endTime - startTime) / 1000000.0;
        }
        Arrays.sort(times);
        System.out.println("Seq, B transposed " + times[3]);
        Oblig2Precode.saveResult(seed, Oblig2Precode.Mode.SEQ_B_TRANSPOSED, C);

        // Sequential, A transposed
        B = Oblig2Precode.generateMatrixB(seed, n);
        for(int i = 0; i < 7; i++){
            startTime = System.nanoTime();
            A = m.transposeMatrix(A);
            C = m.multiplyMatricesAtransposed(A, B, 0, n);
            endTime = System.nanoTime();
            times[i] = (endTime - startTime) / 1000000.0;
        }
        Arrays.sort(times);
        System.out.println("Seq, A transposed " + times[3]);
        Oblig2Precode.saveResult(seed, Oblig2Precode.Mode.SEQ_A_TRANSPOSED, C);

        // Parallel, not transposed
        A = Oblig2Precode.generateMatrixA(seed, n);
        B = Oblig2Precode.generateMatrixB(seed, n);
        C = new double[n][n];
        for(int i = 0; i < 7; i++){
            startTime = System.nanoTime();
            C = m.initParallel(A, B, Oblig2Precode.Mode.PARA_NOT_TRANSPOSED);
            endTime = System.nanoTime();
            times[i] = (endTime - startTime) / 1000000.0;
        }
        Arrays.sort(times);
        System.out.println("Para, not transposed " + times[3]);
        Oblig2Precode.saveResult(seed, Oblig2Precode.Mode.PARA_NOT_TRANSPOSED, C);

        // Parallel, B transposed
        A = Oblig2Precode.generateMatrixA(seed, n);
        B = Oblig2Precode.generateMatrixB(seed, n);
        C = new double[n][n];
        for(int i = 0; i < 7; i++){
            startTime = System.nanoTime();
            B = m.transposeMatrix(B);
            C = m.initParallel(A, B, Oblig2Precode.Mode.PARA_B_TRANSPOSED);
            endTime = System.nanoTime();
            times[i] = (endTime - startTime) / 1000000.0;
        }
        Arrays.sort(times);
        System.out.println("Para, B transposed " + times[3]);
        Oblig2Precode.saveResult(seed, Oblig2Precode.Mode.PARA_B_TRANSPOSED, C);

        // Parallel, A transposed
        A = Oblig2Precode.generateMatrixA(seed, n);
        B = Oblig2Precode.generateMatrixB(seed, n);
        C = new double[n][n];
        for(int i = 0; i < 7; i++){
            startTime = System.nanoTime();
            A = m.transposeMatrix(A);
            C = m.initParallel(A, B, Oblig2Precode.Mode.PARA_A_TRANSPOSED);
            endTime = System.nanoTime();
            times[i] = (endTime - startTime) / 1000000.0;
        }
        Arrays.sort(times);
        System.out.println("Para, A transposed " + times[3]);
        Oblig2Precode.saveResult(seed, Oblig2Precode.Mode.PARA_A_TRANSPOSED, C);
    }


}
