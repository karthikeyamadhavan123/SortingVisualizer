import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class SortingVisualizer extends JFrame {

    private static final int SCREEN_WIDTH = 910;
    private static final int SCREEN_HEIGHT = 750;
    private static final int ARRAY_SIZE = 130;
    private static final int RECT_SIZE = 7;

    // Delays in milliseconds (Adjusted from C++ for smoother visualization in Java)
    private static final int BUBBLE_DELAY_MS = 1;
    private static final int INSERTION_DELAY_MS = 5;
    private static final int HEAP_DELAY_MS = 40;
    private static final int QUICK_PARTITION_DELAY_MS = 70;
    private static final int MERGE_COPY_DELAY_MS = 15;

    private int[] array;
    private final int[] baseArray; // Corresponds to Barr in C++
    private boolean complete = false;

    // Indices for visualization (Corresponds to x, y, z in visualize())
    private int highlightedX = -1; // Primary highlight (e.g., swapping/pivot)
    private int highlightedY = -1; // Secondary highlight (e.g., comparison)
    private int highlightedZ = -1; // Tertiary highlight (e.g., sorted area)

    private final VisualizationPanel panel;

    public SortingVisualizer() {
        // Initialize arrays
        array = new int[ARRAY_SIZE];
        baseArray = new int[ARRAY_SIZE];
        
        // Setup JFrame (window)
        setTitle("Sorting Visualizer (Java Swing)");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        
        // Setup panel for drawing
        panel = new VisualizationPanel();
        add(panel, BorderLayout.CENTER);
        
        // Setup controls (Buttons)
        JPanel controlPanel = new JPanel(new GridLayout(1, 7, 5, 5));
        
        // Button actions match the C++ key presses (0-6)
        controlPanel.add(createButton("Randomize (0)", this::randomizeAndLoadArray));
        controlPanel.add(createSortButton("Selection Sort (1)", this::selectionSort));
        controlPanel.add(createSortButton("Insertion Sort (2)", this::insertionSort));
        controlPanel.add(createSortButton("Bubble Sort (3)", this::bubbleSort));
        // Note on Merge/Quick Sort: They need lambdas to pass arguments
        controlPanel.add(createSortButton("Merge Sort (4)", () -> mergeSort(array, 0, ARRAY_SIZE - 1)));
        controlPanel.add(createSortButton("Quick Sort (5)", () -> quickSort(array, 0, ARRAY_SIZE - 1)));
        controlPanel.add(createSortButton("Heap Sort (6)", () -> inplaceHeapSort(array, ARRAY_SIZE)));

        add(controlPanel, BorderLayout.SOUTH);

        randomizeAndLoadArray();
        setVisible(true);
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    private JButton createSortButton(String text, Runnable sortMethod) {
        JButton button = new JButton(text);
        button.addActionListener(e -> startSort(text.split(" ")[0] + " SORT", sortMethod));
        return button;
    }

    // Corresponds to randomizeAndSaveArray() and part of loadArr()
    private void randomizeAndLoadArray() {
        Random rand = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            baseArray[i] = rand.nextInt(SCREEN_HEIGHT);
        }
        loadArrayFromBase();
        System.out.println("NEW RANDOM LIST GENERATED.");
    }
    
    // Corresponds to loadArr()
    private void loadArrayFromBase() {
        System.arraycopy(baseArray, 0, array, 0, ARRAY_SIZE);
        complete = false;
        highlightedX = highlightedY = highlightedZ = -1;
        panel.repaint();
    }

    private void startSort(String sortName, Runnable sortMethod) {
        // Reset array if needed
        if (complete) loadArrayFromBase(); 
        
        // Run sort in a separate thread to prevent GUI freeze (blocks EDT)
        new Thread(() -> {
            System.out.println("\n" + sortName + " STARTED.");
            sortMethod.run();
            complete = true;
            highlightedX = highlightedY = highlightedZ = -1;
            panel.repaint();
            System.out.println("\n" + sortName + " COMPLETE.");
        }).start();
    }

    // Corresponds to visualize(int x, int y, int z)
    private void visualize(int x, int y, int z) {
        highlightedX = x;
        highlightedY = y;
        highlightedZ = z;
        panel.repaint(); // Tell Swing to redraw the panel
    }
    
    private void visualize(int x, int y) {
        visualize(x, y, -1);
    }
    
    private void visualize(int x) {
        visualize(x, -1, -1);
    }

    private void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // -------------------------------------------------------------------------
    // --- SORTING ALGORITHMS ---
    // -------------------------------------------------------------------------

    // Corresponds to selectionSort()
    private void selectionSort() {
        int minIndex;
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < ARRAY_SIZE; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                    visualize(i, minIndex);
                }
                pause(BUBBLE_DELAY_MS); // Using same delay as C++ inner loop
            }
            // Swap
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    // Corresponds to insertionSort()
    private void insertionSort() {
        for (int i = 1; i < ARRAY_SIZE; i++) {
            int j = i - 1;
            int temp = array[i];
            while (j >= 0 && array[j] > temp) {
                array[j + 1] = array[j];
                j--;

                visualize(i, j + 1);
                pause(INSERTION_DELAY_MS);
            }
            array[j + 1] = temp;
        }
    }

    // Corresponds to bubbleSort()
    private void bubbleSort() {
        for (int i = 0; i < ARRAY_SIZE - 1; i++) {
            for (int j = 0; j < ARRAY_SIZE - 1 - i; j++) {
                if (array[j + 1] < array[j]) {
                    // Swap
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    // Visualize: j+1 (swapped), j (swapped), last sorted index
                    visualize(j + 1, j, ARRAY_SIZE - 1 - i); 
                }
                pause(BUBBLE_DELAY_MS);
            }
        }
    }
    
    // --- Merge Sort ---
    private void mergeSort(int a[], int si, int ei) {
        if (si >= ei) {
            return;
        }
        int mid = si + (ei - si) / 2;

        mergeSort(a, si, mid);
        mergeSort(a, mid + 1, ei);

        merge2SortedArrays(a, si, ei);
    }

    private void merge2SortedArrays(int a[], int si, int ei) {
        int size_output = (ei - si) + 1;
        int[] output = new int[size_output];

        int mid = si + (ei - si) / 2;
        int i = si, j = mid + 1, k = 0;

        while (i <= mid && j <= ei) {
            if (a[i] <= a[j]) {
                output[k] = a[i];
                visualize(i, j); 
                i++; k++;
            } else {
                output[k] = a[j];
                visualize(i, j); 
                j++; k++;
            }
        }

        while (i <= mid) {
            output[k] = a[i];
            visualize(-1, i); 
            i++; k++;
        }

        while (j <= ei) {
            output[k] = a[j];
            visualize(-1, j); 
            j++; k++;
        }

        int x = 0;
        for (int l = si; l <= ei; l++) {
            a[l] = output[x];
            visualize(l); 
            pause(MERGE_COPY_DELAY_MS);
            x++;
        }
    }
    
    // --- Quick Sort ---
    private void quickSort(int a[], int si, int ei) {
        if (si >= ei) {
            return;
        }

        int c = partitionArray(a, si, ei);
        quickSort(a, si, c - 1);
        quickSort(a, c + 1, ei);
    }

    private int partitionArray(int a[], int si, int ei) {
        int countSmall = 0;
        for (int i = (si + 1); i <= ei; i++) {
            if (a[i] <= a[si]) {
                countSmall++;
            }
        }
        int c = si + countSmall;

        // Swap pivot into final position
        int temp = a[c];
        a[c] = a[si];
        a[si] = temp;
        visualize(c, si);

        int i = si, j = ei;

        while (i < c && j > c) {
            if (a[i] <= a[c]) {
                i++;
            } else if (a[j] > a[c]) {
                j--;
            } else {
                // Swap a[i] and a[j]
                int temp1 = a[j];
                a[j] = a[i];
                a[i] = temp1;

                visualize(i, j);
                pause(QUICK_PARTITION_DELAY_MS);

                i++;
                j--;
            }
        }
        return c;
    }

    // --- Heap Sort ---
    private void inplaceHeapSort(int[] input, int n) {
        // Step 1: Build Max-Heap (Heapify Up)
        for (int i = 1; i < n; i++) {
            int childIndex = i;
            int parentIndex = (childIndex - 1) / 2;

            while (childIndex > 0) {
                if (input[childIndex] > input[parentIndex]) {
                    // Swap
                    int temp = input[parentIndex];
                    input[parentIndex] = input[childIndex];
                    input[childIndex] = temp;
                } else {
                    break;
                }

                visualize(parentIndex, childIndex);
                pause(HEAP_DELAY_MS);

                childIndex = parentIndex;
                parentIndex = (childIndex - 1) / 2;
            }
        }

        // Step 2: Sorting (Remove elements one by one)
        for (int heapLast = n - 1; heapLast >= 0; heapLast--) {
            // Swap root (max element) with the last element of the unsorted heap
            int temp = input[0];
            input[0] = input[heapLast];
            input[heapLast] = temp;

            int parentIndex = 0;
            int leftChildIndex = 2 * parentIndex + 1;
            int rightChildIndex = 2 * parentIndex + 2;

            // Down-heapify the new root element
            while (leftChildIndex < heapLast) {
                int maxIndex = parentIndex;

                if (input[leftChildIndex] > input[maxIndex]) {
                    maxIndex = leftChildIndex;
                }
                if (rightChildIndex < heapLast && input[rightChildIndex] > input[maxIndex]) {
                    maxIndex = rightChildIndex;
                }
                if (maxIndex == parentIndex) {
                    break;
                }

                // Swap parent with max child
                temp = input[parentIndex];
                input[parentIndex] = input[maxIndex];
                input[maxIndex] = temp;

                // Visualize: maxIndex (swapped child), parentIndex (swapped parent), heapLast (sorted boundary)
                visualize(maxIndex, parentIndex, heapLast);
                pause(HEAP_DELAY_MS);

                parentIndex = maxIndex;
                leftChildIndex = 2 * parentIndex + 1;
                rightChildIndex = 2 * parentIndex + 2;
            }
        }
    }

    // -------------------------------------------------------------------------
    // --- VISUALIZATION PANEL (Equivalent to SDL Rendering) ---
    // -------------------------------------------------------------------------

    private class VisualizationPanel extends JPanel {
        
        public VisualizationPanel() {
            setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Clear background (Black)
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            int xPos = 0;
            for (int j = 0; j < ARRAY_SIZE; j++) {
                int height = array[j];
                // Swing origin is top-left, so calculate y-position for bars to rise from the bottom
                int yPos = SCREEN_HEIGHT - height; 

                // Set color based on visualization state (Corresponds to C++ RGB values)
                if (complete) {
                    // Green Border (100, 180, 100)
                    g2d.setColor(new Color(100, 180, 100)); 
                    g2d.drawRect(xPos, yPos, RECT_SIZE, height);
                } else if (j == highlightedX || j == highlightedZ) {
                    // Green Fill (100, 180, 100) - Primary/Sorted highlight
                    g2d.setColor(new Color(100, 180, 100)); 
                    g2d.fillRect(xPos, yPos, RECT_SIZE, height);
                } else if (j == highlightedY) {
                    // Purple Fill (165, 105, 189) - Secondary/Current element highlight
                    g2d.setColor(new Color(165, 105, 189)); 
                    g2d.fillRect(xPos, yPos, RECT_SIZE, height);
                } else {
                    // Gray Border (170, 183, 184) - Default color
                    g2d.setColor(new Color(170, 183, 184)); 
                    g2d.drawRect(xPos, yPos, RECT_SIZE, height);
                }

                xPos += RECT_SIZE;
            }
        }
    }

    // -------------------------------------------------------------------------
    // --- MAIN METHOD ---
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        // Ensure GUI is created and updated on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            System.out.println("==============================Sorting Visualizer==============================");
            System.out.println("Visualization is running in a new window (Java Swing).");
            System.out.println("Use the buttons below to control the algorithms.");
            new SortingVisualizer();
        });
    }
}