Sorting Visualizer – Algorithm Animation Tool


Visualize sorting algorithms in action with Java Swing.

Overview

Sorting Visualizer is an interactive Java Swing application that visually demonstrates how various sorting algorithms work. Designed as an educational tool, it highlights comparisons, swaps, and sorted regions dynamically, allowing students and developers to gain an intuitive understanding of algorithm behavior.

Supported algorithms:

Bubble Sort

Selection Sort

Insertion Sort

Merge Sort

Quick Sort

Heap Sort

Features

Real-time animation: Shows the algorithm progress step by step with color-coded bars.

Customizable array size: Up to 130 elements for smooth visualization.

Thread-safe execution: Sorting happens in separate threads to avoid freezing the GUI.

Color-coded highlights:

Primary element / pivot: Green

Secondary element / comparison: Purple

Sorted area: Light green border

Reset & randomization: Generate new random arrays instantly.

Modular architecture: Easily extendable to include new algorithms.

Installation

Clone the repository:

git clone https://github.com/karthikeyamadhavan123/SortingVisualizer.git


Navigate to the project directory:

cd SortingVisualizer


Compile the Java files:

javac SortingVisualizer.java


Run the application:

java SortingVisualizer

Usage

Click Randomize (0) to generate a new array.

Choose a sorting algorithm button (1–6) to start visualizing:

Selection Sort (1)

Insertion Sort (2)

Bubble Sort (3)

Merge Sort (4)

Quick Sort (5)

Heap Sort (6)

Watch as the bars animate to show comparisons, swaps, and sorted sections.

Screenshots

<img width="1177" height="948" alt="image" src="https://github.com/user-attachments/assets/6a68275c-a2e0-47eb-9de5-67ce507f2b4b" />



Performance & Optimization

Smooth animations maintained by adjusting delay times per algorithm.

Efficient array management allows visualization of up to 130 elements without performance lag.

Thread-safe updates prevent GUI freeze during long-running sorts.

Future Enhancements

Add additional sorting algorithms like Radix Sort, Counting Sort.

Allow user-defined array sizes and custom element values.

Add speed control slider to adjust animation speed dynamically.

Implement JavaFX version for enhanced UI and graphics.

License

This project is open-source under the MIT License.
