package deneme;

import java.util.*;

public class sudoku {

    public static void main(String [] args) {

      nine();



    }

    public static List<String> numGen(){
        List<String> nums =new LinkedList<>( Arrays.asList("1","2","3","4","5","6","7","8","9"));
        Collections.shuffle(nums);
        return nums;
    }

    public static void nine(){
        String [][] sudo = new String[9][9];

        List<List<String>> buckets = new ArrayList<>();

        for(int i =0; i< 27;i++){
            buckets.add(numGen());
        }

        for(int i =80; i>=0;i--){
            int row = i/9;
            int column = i%9 + 9;
            int cell = (i%9)/3  + ((i/9)/3)*3 +18;

            for(String s: buckets.get(row)){
                if(buckets.get(column).contains(s) && buckets.get(cell).contains(s)){
                    buckets.get(row).remove(s);
                    buckets.get(column).remove(s);
                    buckets.get(cell).remove(s);
                    sudo[i%9][i/9]=s;
                    break;
                }
            }
        }

        for(int i = 0; i < 9; i++){
            for(int j =0; j < 9; j++) {
                System.out.print(sudo[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    public static void four(){
        String [][] sudo = new String[4][4];

        List<List<String>> buckets = new ArrayList<>();

        for(int i =0; i< 12;i++){
            buckets.add(numGenFour());
        }

        for(int i =15; i>=0;i--){
            int row = i/4;
            int column = i%4 + 4;
            int cell = (i%4)/2  + ((i/4)/2)*2 +8;

            String selected = "0";
            for(int j=0; j<buckets.get(row).size();j++){
                selected = buckets.get(row).get(j);
                if(buckets.get(column).contains(selected) && buckets.get(cell).contains(selected)){
                    buckets.get(row).remove(selected);
                    buckets.get(column).remove(selected);
                    buckets.get(cell).remove(selected);
                    break;
                }
            }
            sudo[i%4][i/4]=selected;

            System.out.print(selected+" ");
            if(i%4 == 0){
                System.out.println(" ");
            }
        }
    }

    public static List<String> numGenFour(){
        List<String> nums =new LinkedList<>( Arrays.asList("1","2","3","4"));
        Collections.shuffle(nums);
        return nums;
    }
}
