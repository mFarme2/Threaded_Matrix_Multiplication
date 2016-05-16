package original;
import java.util.Random;

/**
 * @author Morgan Farmer
 */
public class threaded_Matrix_Multiplication {
    public static void main(String Args[]) throws InterruptedException{
        final int M = 3;  //Row Length for matrix a AND Row Length for matrix c
        final int K = 4;  //Column Length for matrix a AND Row Length for matrix b
        final int N = 5;  //Column Length for matrix b AND Column Length for matrix c

        Integer[][] a = new Integer[M][K];
        Integer[][] b = new Integer[K][N];
        Integer[][] c = new Integer[M][N];
        for (int j = 0; j < M; j++){
            for (int i = 0; i < N; i++){
                c[j][i] = 0;
            }
        }

        //SET VALUES FOR MATRIX a and b
        a = MatrixGen(M,K);
        b = MatrixGen(K,N);
        
        //PRINT OUT MATRIX a, b, and c
        MatrixPrint(a, M, K);
        MatrixPrint(b, K, N);

        
        //Set up M threads............DO WORK HERE!
        for (int index = 0; index < M; index++){
            Thread rows = new Thread(new MatrixMult(a, b, c, index, M, K, N));
            rows.run();
        }
        
        MatrixPrint(c, M, N);
    }
    
    public static void MatrixPrint(Integer[][] matrixToPrint, int x, int y){
        for (int j = 0; j < x; j++){
            for (int i = 0; i < y; i++){
                System.out.print(matrixToPrint[j][i] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static Integer[][] MatrixGen(int x, int y){
        //Takes Paramaters x and y as variables of how large the matrix should be;
        Integer[][] temp = new Integer[x][y];
        Random rand = new Random();
        
        for (int j = 0; j < x; j++){
            for (int i = 0; i < y; i++){
                temp[j][i] = 1 + rand.nextInt(9);
            }
        }
        return temp;
    }
    
    public static class MatrixMult implements Runnable{
        private static Integer[][] a;
        private static Integer[][] b;
        private static Integer[][] c;
        private static int rowNumber;
        private int col;
        private int m, n, k;
        
        public MatrixMult(Integer[][] aMatrix, Integer[][] bMatrix, Integer[][] cMatrix, int indexRow, 
                int m, int k, int n){
            this.a = aMatrix;
            this.b = bMatrix;
            this.c = cMatrix;
            this.rowNumber = indexRow;
            this.m = m;
            this.k = k;
            this.n = n;
            this.col = indexRow;
        }
        
        public void run(){
            for (int i = 0; i < n; i++){
                
                int product = cartesianMult(a, b, k, rowNumber, col, 0);
                
                synchronized (c[rowNumber][col]){
                    //now we know nothing else is changing where we are going to write to.
                    c[rowNumber][col] = product;
                }
                if (col == n-1){
                    col = 0; //column will start at the same as the row index (which never changes) 
                             //this should allow each thread to iterate through n columns but not step on toes.
                } else {
                    col++;
                }
            }
            
            
        }
        
        private int cartesianMult(Integer[][] a, Integer[][] b, int kay, int index, int colPos, int pos){
            if (pos == kay) return 0;
            synchronized(a[index][pos]){
                synchronized(b[pos][colPos]){
                    if (pos < kay){
                        return (a[index][pos] * b[pos][colPos]) + cartesianMult(a, b, kay, index, colPos, (pos+1));
                    } else {
                        return 0;
                    }
                }
            }
        }
    }
}
