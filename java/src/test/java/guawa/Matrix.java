package guawa;

import com.google.common.collect.HashBasedTable;
import org.junit.Test;

import java.util.Map;

public class Matrix {
    @Test
    public void matrixMultiply() throws Exception {
        HashBasedTable<Integer, Integer, Integer> A = HashBasedTable.create(2, 3);
        HashBasedTable<Integer, Integer, Integer> B = HashBasedTable.create(3, 2);
        A.put(0, 0, 1);
        A.put(0, 1, 2);
        A.put(0, 2, 3);
        A.put(1, 0, 4);
        A.put(1, 1, 5);
        A.put(1, 2, 6);

        B.put(0, 0, 7);
        B.put(0, 1, 8);
        B.put(1, 0, 9);
        B.put(1, 1, 10);
        B.put(2, 0, 11);
        B.put(2, 1, 12);

        int rows = A.rowKeySet().size(), columns = B.columnKeySet().size();
        HashBasedTable<Integer, Integer, Integer> C = HashBasedTable.create(rows, columns);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                C.put(r, c, rowColumnMultiply(A.row(r), B.column(c)));
            }
        }
    }

    private int rowColumnMultiply(Map<Integer, Integer> row, Map<Integer, Integer> column) {
        int item = 0;
        for (int i = 0, size = row.entrySet().size(); i < size; i++) {
            item += row.get(i) * column.get(i);
        }
        return item;
    }
}
