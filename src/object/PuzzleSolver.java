package object;
import java.util.*;
import java.io.*;

public class PuzzleSolver {
    private Board board;
    private static List<Piece> puzzles;
        private int count;
        public PuzzleSolver(int P,Board board) {
            this.board= board;
            this.puzzles= new ArrayList<>();
            this.count=0;
        }
        public int getLength() {
            return puzzles.size();
        }
    
        public void processPuzzle(char aid,List<String> lines) {
            int height= lines.size();
            int width=0;
            for (String line: lines) {
                width= Math.max(width,line.length());
            }
    
            char[][] shape= new char[height][width];
            for (int i=0;i< height;i++) {
                char[] chars= lines.get(i).toCharArray();
                for (int j=0;j < chars.length;j++) {
                    if (chars[j]== ' ') {
                        shape[i][j]= '.'; 
                    } else {
                        shape[i][j]= chars[j];
                    }
                }
                for (int j= chars.length;j < width;j++) {
                    shape[i][j]= '.';
                }
            }
            puzzles.add(new Piece(aid,shape));
        }
    
        public static void printPuzzles() {
            for (Piece puzzle : puzzles) {
                for (char[] row : puzzle.shape) {
                    System.out.println(row);
                }
                System.out.println();
            }
        }

    public boolean solvePuzzle(int x,int y) {
        if (y== board.cols) {
            x++;
            y= 0;
        }
        if (x== board.rows) {
            return isValidSolution();
        }
        
        if (board.getBoard()[x][y] != '0') {
            return solvePuzzle(x,y+ 1);
        }
        
        for (Piece piece : puzzles) {
            if (piece.isUsed()) continue;
            List<char[][]> variations= searchVariations(piece.getShape());
            for (char[][] variation : variations) {
                if (checkPuzzle(x,y,variation,piece.getaid())) {
                    board.placePuzzle(x,y,piece.getaid(),variation);
                    piece.setUsed(true);
                    count++;
                    if (solvePuzzle(x,y+ 1)) {
                        return true;
                    }
                
                    board.removePuzzle(x,y,variation);
                    piece.setUsed(false);
                }
            }
        }

        return false;
    }

    private boolean isValidSolution() {
        for (Piece Piece : puzzles) {
            if (!Piece.isUsed()) return false;
        }
        return true;
    }

    private static List<char[][]> searchVariations(char[][] original) {
        List<char[][]> variations= new ArrayList<>();
        Set<String> newVariation= new HashSet<>();
        char[][] current= original;
        for (int i= 0;i < 4;i++) {
            addNew(current,newVariation,variations);
            addNew(reflect(current),newVariation,variations);
            current= rotate90(current);
        }
        // for (int index= 0;index < variations.size();index++) {
        //     char[][] grid= variations.get(index);
        //     for (char[] row : grid) {
        //         System.out.println(new String(row)); 
        //     }
        //     System.out.println();
        // }
        return variations;
    }

    private static void addNew(char[][] variation,Set<String> newVariation,List<char[][]> variations) {
        String key= Arrays.deepToString(variation);//note : pengganti stringBuilder
        if (newVariation.add(key)) {
            variations.add(variation);
        }
    }

    private static char[][] rotate90(char[][] piece) {
        int rows= piece.length;
        int cols= piece[0].length;
        char[][] rotated= new char[cols][rows];
        for (int r= 0;r < rows;r++) {
            for (int c= 0;c < cols;c++) {
                rotated[c][rows - 1 - r]= piece[r][c];
            }
        }
        return rotated;
    }

    private static char[][] reflect(char[][] piece) {
        int rows= piece.length;
        int cols= piece[0].length;
        char[][] reflected= new char[rows][cols];
        for (int r= 0;r < rows;r++) {
            for (int c= 0;c < cols;c++) {
                reflected[r][cols - 1 - c]= piece[r][c];
            }
        }
        return reflected;
    }

    private boolean checkPuzzle(int x,int y,char[][] Piece,char aid) {
        int cols= Piece[0].length;
        int rows= Piece.length;
        for (int i= 0;i < rows;i++) {
            for (int j= 0;j < cols;j++) {
                if (Piece[i][j]== aid) {
                    int newY= y+ j;
                    int newX= x+ i;
                    if (newX >= board.rows || newY >= board.cols || board.getCustom()[newX][newY] != '0' || board.getBoard()[newX][newY] != '0') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int getSolutionCount() {
        return count;
    }
}
