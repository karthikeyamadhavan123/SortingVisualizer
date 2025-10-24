# SortingVisualizer
# 🖥️ Sorting Visualizer - Java Swing Edition

A **Java Swing-based Sorting Visualizer** that animates multiple sorting algorithms, showing how arrays transform step by step. Perfect for learning, teaching, or demoing sorting algorithms in real time.

---

## 🌟 Features

- ✅ Visualizes multiple sorting algorithms:
  - Selection Sort
  - Insertion Sort
  - Bubble Sort
  - Merge Sort
  - Quick Sort
  - Heap Sort
- ✅ Dynamic array generation and reset functionality
- ✅ Color-coded visualization:
  - Green: sorted or primary focus
  - Purple: currently compared elements
  - Gray: default elements
- ✅ Adjustable array size and visualization speed
- ✅ GUI built entirely in Java Swing
- ✅ Smooth animations using thread-safe Swing repainting

---

## 🎨 Demo Screenshot

<img width="1177" height="948" alt="image" src="https://github.com/user-attachments/assets/0c3b7531-4acd-41d4-b09e-220827467305" />


---

## ⚡ How it Works

The program uses **Java Swing** to render the array bars and updates them in real-time during sorting:

- **Array Representation**: Each element is a vertical bar.
- **Visualization Logic**:
  - `highlightedX`: primary highlight (e.g., pivot, swapped element)
  - `highlightedY`: secondary highlight (e.g., comparison)
  - `highlightedZ`: tertiary highlight (sorted region)
- **Threading**: Sorting algorithms run on a separate thread to prevent GUI freezing.
- **Pausing**: `Thread.sleep()` is used to control animation speed.

---

## 🚀 Getting Started

### **Prerequisites**
- Java JDK 17+ (tested on 23)
- IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

### **Clone and Run**
```bash
[git clone https://github.com/yourusername/sorting-visualizer.git](https://github.com/karthikeyamadhavan123/SortingVisualizer.git)
cd sorting-visualizer
javac SortingVisualizer.java
java SortingVisualizer
