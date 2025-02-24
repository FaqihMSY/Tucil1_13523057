package object;
import java.util.*;
import java.io.*;

public class Board {
    int rows;
    int cols;
    private char[][] board;
    public Board(int N,int M) {
        this.rows= N;
        this.cols= M;
        this.board= new char[N][M];
        for (int i=0;i < N;i++) {
            for (int j=0;j < M;j++) {
                this.board[i][j]= '0';
            }
        }
    }

    public void setCustom(int row,char[] pattern) {
        this.board[row]= pattern;
    }

    public char[][] getBoard() {
        return board;
    }

    public char[][] getCustom() {
        return board;
    }

    public void placePuzzle(int x,int y,char aid,char[][] puzzleShape) {
        for (int i=0;i < puzzleShape.length;i++) {
            for (int j=0;j < puzzleShape[i].length;j++) {
                if (puzzleShape[i][j]== aid) {
                    board[x+ i][y+ j]= aid;
                }
            }
        }
    }

    public void removePuzzle(int x,int y,char[][] puzzleShape) {
        for (int i=0;i < puzzleShape.length;i++) {
            for (int j=0;j < puzzleShape[i].length;j++) {
                if (puzzleShape[i][j]!= '0' && puzzleShape[i][j]!='.') {
                    board[x+ i][y+ j]= '0';
                }
            }
        }
    }

    public void print() {
        String[] colors = {
            "\u001B[31m",
            "\u001B[32m",
            "\u001B[33m",
            "\u001B[34m",
            "\u001B[35m",
            "\u001B[36m", 
            "\u001B[91m",
            "\u001B[92m",
            "\u001B[93m", 
            "\u001B[94m",
            "\u001B[95m",
            "\u001B[96m",
            "\u001B[97m",
            "\u001B[90m",
            "\u001B[41m",
            "\u001B[42m",
            "\u001B[43m",
            "\u001B[44m",
            "\u001B[45m",
            "\u001B[46m",
            "\u001B[101m",
            "\u001B[102m",
            "\u001B[103m",
            "\u001B[104m",
            "\u001B[105m",
            "\u001B[106m"
        };
        for (char[] row: board) {
            for (char cell: row) {
                System.out.print(colors[cell - 'A'] + cell + "\u001B[0m"); 
            }
            System.out.println();
        }
    }
    

    public void saveSolution(String nama) {
        try {
            BufferedWriter writer= new BufferedWriter(new FileWriter(nama + "solusi.txt"));
            for (char[] row : board) {
                writer.write(String.valueOf(row));
                writer.newLine();
            }
            writer.close();
            System.out.println("Solusi berhasil disimpan.");
        } catch (IOException read) {
            System.out.println("Kesalahan saat menyimpan solusi: "+ read.getMessage());
        }
    }
}