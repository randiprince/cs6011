public class Array {

    public static void main(String[] args) {
        int sum = 0;
        int[] intArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int element: intArray) {
            System.out.println(element);
            sum += element;
        }

        System.out.println(sum);
        System.out.println("Hello world!");
    }

}
