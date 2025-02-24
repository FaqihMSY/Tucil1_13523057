import object.*;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.print("Masukkan nama file test case (.txt): ");
        Scanner ScanInput= new Scanner(System.in);
        String filename= ScanInput.nextLine();
        int N=0,M=0,P=0;

        try (Scanner scanFile= new Scanner(new File("../test/"+filename))) {
            String firstLine= scanFile.nextLine();
            String[] tcLine= firstLine.split(" ");
            if (tcLine.length< 3) {
                throw new IllegalArgumentException("Baris pertama harus memiliki 3 angka.");
            }
            try {
                N= Integer.parseInt(tcLine[0]);
                M= Integer.parseInt(tcLine[1]);
                P= Integer.parseInt(tcLine[2]);
            }catch(NumberFormatException e) {
                throw new IllegalArgumentException("Baris pertama harus berisi angka integer yang valid.");
            }
            String boardShape= scanFile.nextLine().trim();
            Board board= new Board(N,M);
            // if (boardShape.equals("CUSTOM")) {
            //     for (int i= 0;i < N;i++) {
            //         String shape= scanFile.nextLine();
            //         board.setCustom(i,shape.toCharArray());
            //     }
            // }
            // char[][] boardArray= board.getBoard();
            // for (int i= 0;i < boardArray.length;i++) {
            //     for (int j= 0;j < boardArray[i].length;j++) {
            //         System.out.print(boardArray[i][j]+ " ");
            //     }
            //     System.out.println();
            // }

            PuzzleSolver puzzleSolver= new PuzzleSolver(P,board);
            String line;
            List<String> curLine= new ArrayList<>();
            char curAid= ' ';
            while (scanFile.hasNextLine()) {
                line= scanFile.nextLine();
                char firstChar= line.charAt(0);
                if (Character.isUpperCase(firstChar)) {
                    if (firstChar != curAid && curAid != ' ') {
                        puzzleSolver.processPuzzle(curAid,curLine);
                        curLine.clear();
                    }
                    curAid= firstChar;
                }
                curLine.add(line);
            }
            // puzzleSolver.printPuzzles();
            if (!curLine.isEmpty()) {
                puzzleSolver.processPuzzle(curAid,curLine);
            }
            // System.err.println(puzzleSolver.getLength());
            long startTime= System.currentTimeMillis();
            if (puzzleSolver.solvePuzzle(0,0)&&puzzleSolver.getLength()==P) {
                board.print();
            } else {
                System.out.println("solusi tidak ditemukan.");
            }
            long endTime= System.currentTimeMillis();
            System.out.println("Waktu pencarian: "+ (endTime - startTime)+ " ms");
            System.out.println("Banyak kasus yang ditinjau: "+ puzzleSolver.getSolutionCount());
            System.out.print("Apakah anda ingin menyimpan solusi? (ya/tidak): ");
            String save= ScanInput.nextLine();
            if (save=="ya") {
                board.saveSolution(filename);
            }
            ScanInput.close();
        } catch (IOException read) {
            System.out.println("Kesalahan saat membaca file: "+ read.getMessage());
        } 
    }
}