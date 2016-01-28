import java.util.ArrayList;
import java.util.List;

/**
 * This code actually only has this one single file to it (terrible, I know), but within it I have a main function
 * demonstrating the functioning of the sorts.
 *
 * Below the main function I have the various sorts fully implemented:
 *
 *     1: Quicksort (Simple pivot point chosen.)
 *     2: Bubblesort
 *
 *     *complimentary
 *     3: Binary search
 *
 * As a bonus I threw in a binary search.
 */
public class Main {

    public static void main(String[] args){

        //Create the list
        ArrayList<Integer> messyList = new ArrayList<>();

        //Populate the list.
        messyList.add(12893);
        messyList.add(15);
        messyList.add(7);
        messyList.add(132);
        messyList.add(-191);

        //Look at the contents of the list.
        System.out.println(messyList);

        //Perform a quicksort on the list.
        quickSort(messyList);

        //Look at the contents of the list now, sorted! :)
        System.out.println(messyList);

        //Perform a binary search on the sorted list, is 19 in there?
        //Should return false.
        System.out.println(binarySearch(19, messyList));

        //Perform a second binary search on the sorted list, is 7 in there?
        //Should return true.
        System.out.println(binarySearch(7,messyList));
    }


    /**
     * Basic quicksort done in Java. This is the main function to be called as it allows a very simple interface
     * between the main functions and the actual quicksort. Implementation is primarily through recursion.
     *
     * Runtime (Worst): O(n^2) if the pivot point is a very unlucky choice.
     * Runtime Average: O(nlogn).
     *
     * Ideas to improve pivot point: Sample perhaps the first 3 (or so) data points and then pick the median value and
     * use that as a pivot point.
     *
     * @param intList An ArrayList of Integer values.
     */
    public static void quickSort(List<Integer> intList){
        quickSortHelper(intList,0, intList.size()-1);

    }

    /**
     * The primary function of quicksort, this function will recursively call itself as well as call the
     * quicksortPartition function (defined below) which will tell this function where the splitpoint of the list
     * should be.
     *
     * @param intList A List of Integer values to be sorted.
     * @param first The index of the first item to be concered about.
     * @param last The index of the last item to be concerned about.
     */
    public static void quickSortHelper(List<Integer> intList,int first, int last){

        if (first < last){
            int splitPoint = quickSortPartition(intList, first, last);
            quickSortHelper(intList, first, splitPoint-1);
            quickSortHelper(intList, splitPoint+1, last);

        }
    }

    /**
     *
     * @param intlist A list of integers.
     * @param first The first index this function will be concerned of.
     * @param last The last index this function will be concerned of.
     * @return The splitpoint for the quicksortHelper function to divide the list at.
     */
    public static int quickSortPartition(List<Integer> intlist, int first, int last){
        int pivotValue = intlist.get(first); //Very boring implementation.

        int leftMark = first + 1;
        int rightMark = last;

        boolean complete = false;
        int temporaryHolder;

        while(complete != true){
            while( leftMark <= rightMark && intlist.get(leftMark) <= pivotValue){
                leftMark ++;
            }

            while( rightMark >= leftMark && intlist.get(rightMark) >= pivotValue){
                rightMark--;
            }

            if (rightMark < leftMark){
                complete = true;
            }

            else{

                temporaryHolder = intlist.get(leftMark);
                intlist.set(leftMark,intlist.get(rightMark));
                intlist.set(rightMark, temporaryHolder);

            }

        }
        temporaryHolder = intlist.get(first);
        intlist.set(first, intlist.get(rightMark));
        intlist.set(rightMark, temporaryHolder);

        return rightMark;

    }





    /**
     * Extremely simple sort. Constantly iterates over the list and swaps values.
     *
     * O(n^2) runtime.
     * @param intList A list of Integer values.
     */
    public static void bubbleSort(ArrayList<Integer> intList){
        boolean swapped = true;
        int j = 0;
        int temporaryHolder;

        while (swapped){
            swapped = false; //Assume it will be false for here.
            j++;
            for (int i = 0; i < intList.size()-j; i++){
                if( intList.get(i) > intList.get(i+1)){
                    temporaryHolder = intList.get(i);
                    intList.set(i, intList.get(i+1));
                    intList.set(i+1, temporaryHolder);
                    swapped = true; //We aren't done sorting yet.
                }
            }
        }

    }


    /**
     * Binary search which constantly divides the size of the list and compares the middle value of the list
     * to the target value being searched for in the list.
     *
     * @param targetValue The value that the user wants to search for in the list.
     * @param intList A list of integers (which may or may not have the target value in it).
     * @return Boolean flag stating if the target is (true) or is not (false) present.
     */
    public static boolean binarySearch(int targetValue, List<Integer> intList){

        //Until the size of the list of integers is 1, continue looping.
        while (intList.size() > 1){
            if (intList.get(intList.size()/2) == targetValue){
                return true;
            }

            //If the target value is less than the middle value, create a sublist of the lower half of the list.
            else if (intList.get(intList.size()/2) > targetValue){
                intList = intList.subList(0,intList.size()/2);
                //System.out.println(intList);
            }

            //If the target value is greater than the middle value, create a sublist of the upper half of the list.
            else if (intList.get(intList.size()/2) < targetValue){
                intList = intList.subList(intList.size()/2, intList.size());
                //System.out.println(intList);
            }

        }
        //If the search manages to get here return false.
        return false;
    }
}
