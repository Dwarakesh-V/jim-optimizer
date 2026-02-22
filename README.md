# Jimple Optimizer

An experimental Java bytecode optimizer built on top of the **Soot** framework. This project performs various static analysis and optimization passes on the **Jimple** intermediate representation to transform test code into a more efficient version.

## Project Overview

The Jimple Optimizer leverages the Soot framework to analyze and transform Java bytecode. The process involves:
1.  **Loading**: Converting Java class files into Jimple (a 3-address intermediate representation).
2.  **Transformation**: Applying a sequence of optimization passes registered in the `jtp` (Jimple Transformation Pack) pipeline.
3.  **Output**: Generating the optimized Jimple code for further analysis or execution.

## Core Components

-   `Main.java`: The entry point that configures the Soot environment, specifies input directories, and initializes the optimization pack.
-   `OptimizerPipeline.java`: Orchestrates the order in which individual optimization passes are executed.
-   `passes/`: A dedicated package containing the logic for each specific optimization technique.

## Optimization Passes Explained

Each pass targets a specific type of inefficiency in the code. Below is an explanation of the passes implemented in this project:

### 1. Function Inlining
Reduces method call overhead by replacing a call to a function with the actual code of the function. This can expose further optimization opportunities as the function's logic becomes part of the caller's body.

### 2. Constant Propagation
Identifies variables that are assigned a constant value and propagates that value to all subsequent uses of the variable, potentially enabling more constant folding.

### 3. Constant Folding
Evaluates expressions involving only constants (e.g., `10 * 5`) at compile-time and replaces the expression with the resulting value (`50`).

### 4. Copy Propagation
If a variable `y` is assigned the value of variable `x` (`y = x`), this pass replaces subsequent uses of `y` with `x`, which often helps in eliminating redundant variables.

### 5. Common Subexpression Elimination (CSE)
Detects redundant calculations where the same expression is evaluated multiple times. It computes the value once, stores it, and reuses it in subsequent instances.

### 6. Loop Invariant Code Motion (LICM)
Analyzes loops for computations that yield the same result in every iteration. These "invariants" are moved outside the loop, so they are only calculated once.

### 7. Strength Reduction
Replaces "expensive" operations with "cheaper" ones. For example, it might replace a multiplication by 2 (`x * 2`) with an addition (`x + x`).

### 8. Induction Variable Elimination
Simplifies loops by removing redundant variables that are purely used for loop indexing or counting, especially when they can be derived from other variables.

### 9. Loop Peeling
Removes a small number of iterations (usually the first or last) from a loop. This is often used to handle "cold" start cases or to align memory accesses.

### 10. Loop Unrolling
Grows the loop body by duplicating it and reducing the total number of iterations. This decreases the relative overhead of loop control (jumps and increments).

### 11. Loop Fusion
Combines two separate loops that iterate over the same range into one. This improves cache performance and reduces the overhead associated with loop management.

### 12. Dead Code Elimination (DCE)
Identifies and removes statements that compute values that are never used. This reduces the size of the code and speeds up execution.

### 13. Unreachable Code Elimination
Removes blocks of code that can never be executed (e.g., code following a `return` statement or inside an `if (false)` block).

### 14. Function Cloning
A specialized optimization that reuses the results of identical method calls within the same body, acting as a "Common Subexpression Elimination" for function results.

---

## Example Optimization

Consider the following input code in `Test.java`:

```java
public class Test {
    public static void main(String[] args) {
        int x = 3 + 5;
        int y = x;
        int z = 3 + 5;
        System.out.println(z);
    }
}
```

The optimizer will transform this into a more efficient version:
1.  **Constant Folding**: `3 + 5` is evaluated to `8`.
2.  **Copy Propagation**: `y = x` where `x` is `8` makes `y` and `x` interchangeable.
3.  **Dead Code Elimination**: The variable `y` is never used, and `x` might be eliminated if `z` is used instead.
4.  **Common Subexpression Elimination**: The second `3 + 5` is recognized as redundant.

The final optimized Jimple will reflect these efficiencies, reducing the number of instructions and removing redundant computations.

## How to Run

1.  Place your Java source files or classes in the `input` directory.
2.  Compile the input files to the `target/input-classes` directory.
3.  Run the `Main` class.
4.  Find the optimized Jimple output in the `sootOutput` directory.
