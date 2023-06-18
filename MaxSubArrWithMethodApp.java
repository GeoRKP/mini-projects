package gr.aueb.cf.Projects;

public class MaxSubArrWithMethodApp {
    static int globalMax;

    public static void main(String[] args) {
        int[] arr = {-2, 1, -3, 4, -1, 2, 2, -10, 6};
        globalMax = Integer.MIN_VALUE;

        maxSubArray(arr, arr.length - 1);

        System.out.println("Max Subarray Sum: " + globalMax);
    }

    public static int maxSubArray(int[] arr, int index) {
        if (index == 0) {
            globalMax = arr[0];
            return arr[0];
        }

        int localMax = Math.max(arr[index], arr[index] + maxSubArray(arr, index - 1));
        globalMax = Math.max(localMax, globalMax);

        return localMax;
    }
}

