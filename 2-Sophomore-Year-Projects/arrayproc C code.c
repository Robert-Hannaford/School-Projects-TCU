#include <stdio.h>

main() {
    printf("Please enter your TCU ID number: ");
    int tcu_id;
    scanf("%d", &tcu_id);
    int n = (tcu_id % 2 == 0) ? 56 : 53;
    int orig[n];
    int dup[n];
    init_array(orig, n);
    printf("Original array:\n");
    print_array(orig, n);
    copy_array(dup, orig, n);
    insertion_sort(dup, n);
    printf("Sorted array:\n");
    print_array(dup, n);
    int avg = average(dup, n);
    printf("Average of the sorted array: %d\n", avg);
    return 0;
}

void init_array(int arr[], int n) {
    for (int i = 0; i < n; i++) {
        arr[i] = rand() % 256;
    }
}

void print_array(int arr[], int n) {
    for (int i = 0; i < n; i++) {
        printf("%5d", arr[i]);
        if ((i + 1) % 5 == 0)
            printf("\n");
        else
            printf("\t");
    }
}

void copy_array(int dest[], int src[], int n) {
    for (int i = 0; i < n; i++) {
        dest[i] = src[i];
    }
}

void swap(int *a, int *b) {
    int temp;
    temp = *a;
    *a = *b;
    *b = temp;
}

void selection_sort(int arr[], int n) {
    int i, j, minIndex, temp;
    for (i = 0; i < n-1; i++) {
        minIndex = i;
        //find the index of the min element in the unsorted part of the array
        for (j = i+1; j < n; j++) {
            if (arr[j] < arr[minIndex]) {
                minIndex = j;
            }
        }
        //swap the min element with the first element of the unsorted part
        temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
    }
}

void selectionSort(int arr[], int n) {
    int i, j, minIndex;
    for (i = 0; i < n - 1; i++) {
        minIndex = i;
        for (j = i + 1; j < n; j++) {
            if (arr[j] < arr[minIndex]) {
                minIndex = j;
            }
        }
        //swap the found min element with the first element
        swap(&arr[minIndex], &arr[i]);
    }
}

int sum_array(int arr[], int startidx, int stopidx) {
    if (startidx > stopidx) {
        return 0;
    }
    return arr[startidx] + sum_array(arr, startidx + 1, stopidx);
}

int average(int arr[], int n) {
    int sum = sum_array(arr, 0, n - 1); //calc the sum of the whole array
    return sum / n; // calc and return the average
}