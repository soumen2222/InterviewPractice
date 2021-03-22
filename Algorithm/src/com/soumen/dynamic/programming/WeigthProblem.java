package com.soumen.dynamic.programming;

import java.util.*;

public class WeigthProblem {

    static class Box{
        private int length;
        private int Width;
        private int height;

        public Box(int length, int width, int height) {
            this.length = length;
            Width = width;
            this.height = height;
        }

        public int getLength() {
            return length;
        }

        public int getWidth() {
            return Width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
    public static int getMaxHeight(List<Box> boxes){
        Map<Box, Integer> mapBoxToHeight = new HashMap<>();
        for(int i =0; i <boxes.size() ;i++){
            mapBoxToHeight.put(boxes.get(i),boxes.get(i).getHeight());
        }
        maxHeight(boxes,mapBoxToHeight);

        int maxHeight =0;
        for (Map.Entry<Box, Integer> entry : mapBoxToHeight.entrySet()){
            if(maxHeight < entry.getValue()){
                maxHeight = entry.getValue();
            }
        }
        return maxHeight;
    }
    public static void maxHeight(List<Box> boxes,Map<Box, Integer> mapBoxToHeight){
        // sort the boxes based on length

        Collections.sort(boxes, Comparator.comparing(Box::getLength));
        for(int i=1; i< boxes.size(); i++){
            //Get Current Box
            Box currentBox = boxes.get(i);
            int maxHeight = 0;
            for(int j=i-1; j>=0; j--){
                // Value of array[i] is currentBoxHeight + Max( all the box that can be stacked on top of it)
                if(canBeStacked(currentBox,boxes.get(j)) && maxHeight < mapBoxToHeight.get(boxes.get(j))){
                    maxHeight = mapBoxToHeight.get(boxes.get(j));
                }
            }
            mapBoxToHeight.put(currentBox,currentBox.height +maxHeight);
        }
    }

    private static boolean canBeStacked(Box currentBox, Box box) {
        return (currentBox.getLength() > box.getLength() && currentBox.getWidth() >box.getWidth());
    }

    public static void main(String[] args){
        List<Box> boxes = new ArrayList<>();
        Box box1 = new Box(4,5,3);
        Box box2 = new Box(2,3,2);
        Box box3 = new Box(3,6,2);
        Box box4 = new Box(1,5,4);
        Box box5 = new Box(2,4,1);
        Box box6 = new Box(1,2,2);
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);
        boxes.add(box5);
        boxes.add(box6);
        System.out.println("Max Height:" + getMaxHeight(boxes));
    }
}
