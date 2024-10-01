.section .data
 msgTCU:                .asciz "Please enter the TCU ID number: "
                        .align 2  
 msgCreateArrays:       .asciz "Creating 2 arrays of %d values each.\n"
                        .align 2
          
 msgOrigArray:          .asciz "The original array is:\n"
                        .align 2

 msgSortedArray:        .asciz "The sorted duplicate array is:\n"
                        .align 2

 msgAverageArray:       .asciz "The average of the duplicate array is: %d\n"
                        .align 2

 TCUidlength:           .word 4

 oneint:		        .asciz "%5d"
		                .align 2 

 tabLine:               .asciz "\t"
                        .align 2

 newLine:               .asciz "\n"
                        .align 2

        .section .bss

 length:         .skip 4  

        .section .text
        .global main

main:
    //prologue
	stp	x29, x30, [sp, #-32]!	        // this saves X29 and X30 onto the stack
	mov	x29, sp			                // this moves the SP to the FP
	
    //for random number
    mov x0, #0
    bl time
    bl srand

	// printf("Please enter the TCU ID number: ");
	adr	x0, msgTCU	                    // address of msgTCU
	bl	printf

	// scanf("%d", &anyint);
	adr	x0, oneint	                    // address of oneint format string
	adr	x1, length	                    // address of anyint...just the address
	bl	scanf

    adr	x0, newLine	   // address of newLine
    bl	printf

 check_oddeven:
    //prologue
    sub sp, sp, #32
    str x19, [sp, #8]
    str x20, [sp, #16]
    str x21, [sp, #24]

    //load orig array and allocate space for orig array 
    mov x19, sp
    sub sp, sp, #256

    //load dup array and allocate space for dup array 
    mov x20, sp
    sub sp, sp, #256

    //checks if odd or even
    adr x1, length
    ldr x3, [x1]
    and x4, x3, #1
    cmp x4, #0
    beq even

 odd:        
    mov x21, #53                         // if TCU ID is odd n = 53
    b functionCalls                                 

 even: 
    mov x21, #56                        // if TCU ID is even n = 56

functionCalls:
 // Creating 2 arrays of %d values each
    adr x0, msgCreateArrays
    mov x1, x21
    bl printf

    adr	x0, newLine	                    // address of newLine
    bl	printf

    mov x0, msgOrigArray
    mov x1, x21

 // init_array(int arr[], int n)

    mov x0, x19                             // move orig array into x0
    mov x1, x21                             // move n into x1
    bl   init_array

 // print_array(int arr[], int n);

   //print "The original array is:"
    adr	x0, msgOrigArray	                // address of msgOrigArray
    bl	printf

    mov x0, x19                             // move orig array into x0
    mov x1, x21                             // move n into x1
    bl   print_array

    adr	x0, newLine	                        // address of newLine
    bl	printf

    adr	x0, newLine	                        // address of newLine
    bl	printf


 //copy_array(int dest[], int src[], int n) 

    mov x0, x19                             // move orig array into x0
    mov x1, x20                             // move dup array into x1
    mov x2, x21                             // move n into x2
    bl   copy_array

 // selection_sort(int arr[], int n)

    mov x0, x20                             // dup array
    mov x1, x21                             // n
    bl   selection_sort

 // print_array(int arr[], int n);  SORTED DUPLICATE ARRAY

   //print "The original array is:"
    adr	x0, msgSortedArray	                // address of msgOrigArray
    bl	printf

    mov x0, x20                             // move orig array into x0
    mov x1, x21                             // move n into x1
    bl   print_array

    adr	x0, newLine	                        // address of newLine
    bl	printf   
    
    adr    x0, newLine                     // address of newLine
    bl    printf

 //int compute_average(int arr[], int n) for dup array

    mov x0, x20
    mov x1, x21                            // move n into x1
    bl compute_average

    mov x1, x0
    adr x0, msgAverageArray	           
    bl	printf 

 b endProgram

init_array:

    //prologue 
    sub sp, sp, #32
    str x30, [sp]
    str x23, [sp, #8]        // orig array
    str x24, [sp, #16]       // n value
    str x25, [sp, #24]       // i value

    mov x23, x0             //move adress of orig array into x1
    mov x24, x1             //move address of size of array into x24
    mov x25, #0

 loopIA:                    // loopIA for init_array

    cmp x25, x24            // Compare current index x1 with the array size stored in x24
    bge endLoopIA           //if >= array size endLoopIA

    bl rand                 //call random number          
    and x0, x0, #255        //and to get random num up to 256
    str w0, [x23]           // Store the random number in the array at address   
    sub x23, x23, #4        // Add offset to base address of the array to get the address of the current element
   
    add x25, x25, #1        // Increment index
    b loopIA                // Go to start of the loop

 endLoopIA: 

    //epilogue init_array
    ldr x30, [sp]
    ldr x23, [sp, #8]
    ldr x24, [sp, #16]
    ldr x25, [sp, #24]
    add sp, sp, #32

    ret

print_array:
 //prologue 
   sub sp, sp, #48
   str x30, [sp]
   str x23, [sp, #8]      // orig array
   str x24, [sp, #16]     // n value
   str x25, [sp, #24]     // i value
   str x26, [sp, #32]     // counter value

   mov x23, x0             //move adress of orig array into x23
   mov x24, x1             //move address of size of array into x24
   mov x25, #0             // int i = 0
   mov x26, #0             // int counter = 0


 loopPA:                       // loopPA for print_array

   cmp x25, x24               // Compare current index x24 with the array size stored in x24
   bge endLoopPA              //if >= array size endLoopPA
     
   adr x0, oneint            // string value at index            
   ldr w1, [x23]		      // dereference the array pointer
   bl	printf

   sub x23, x23, #4           // increment orig array by 1
   add x25, x25, #1           // increment index i

   
   cmp x26, #4                // check if 5 values have been printed
   bne not_equal           

   mov x26, #0                //reset counter
   adr x0, newLine
   bl printf
   b loopPA

 not_equal:
   add x26, x26, #1           // increment counter

   adr x0, tabLine            // load tab string
   bl printf
   b loopPA

 endLoopPA:

   //epilogue init_array
   ldr x30, [sp]
   ldr x23, [sp, #8]
   ldr x24, [sp, #16]
   ldr x25, [sp, #24]
   ldr x26, [sp, #32]
   add sp, sp, #48

   ret

copy_array: 
    //prologue 
    sub sp, sp, #48
    str x30, [sp]
    str x23, [sp, #8]     // orig array
    str x24, [sp, #16]    // dup array
    str x25, [sp, #24]    // n value
    str x26, [sp, #32]    // i
    str x27, [sp, #40]    // temp

    mov x23, x0           // orig array
    mov x24, x1           // dup array
    mov x25, x2           // n value
    mov x26, #0           // int i = 0 

 loopCA:

    cmp x26, x25         
    bge endLoopCA        //if i >= branch endLoopCA

    ldr w27, [x23]       // take value from orig array
    str w27, [x24]       // store into value at dup array
             
    sub x23, x23, #4     // increment orig array by 1
    sub x24, x24, #4     // increment dup array by 1

    add x26, x26, #1     // Increment index
    b loopCA

 endLoopCA:

 //epilogue init_array
    ldr x30, [sp]
    ldr x23, [sp, #8]
    ldr x24, [sp, #16]
    ldr x25, [sp, #24]
    ldr x26, [sp, #32]
    ldr x27, [sp, #40]
    add sp, sp, #48

    ret

swap:

    //prologue swap
    sub sp, sp, #16
    str x23, [sp]
    str x24, [sp, #8]

    ldr w23, [x0]
    ldr w24, [x1]

    str w24, [x0]
    str w23, [x1]

    //epilogue endSwap
    ldr x24, [sp, #8]
    ldr x23, [sp]
    add sp, sp, #16
    ret

selection_sort:

    //prologue selection_sort
    sub sp, sp, #48
    str x30, [sp]
    str x23, [sp, #8]      // array
    str x24, [sp, #16]     // n
    str x25, [sp, #24]     // minIndex
    str x26, [sp, #32]     // i
    str x27, [sp, #40]     // j

    mov x23, x0            // duplicate array
    mov x24, x1            // value n
    mov x25, #0            // minIndex = 0
    mov x26, #0            // i = 0
    mov x27, #0            // j = 0

 loopSS:
   
    cmp x26, x24       
    bge endLoopSS         //if i >= n-1 endLoopSS
    mov x25, x26          // minIndex = i
    add x27, x26, #1      // j = i + 1

 loopSS2:

    cmp x27, x24
    bge endLoopSS2             //if j >= n endLoopSS2
   
 ifSS:

    mov x6, #4
    mul x2, x27, x6
    sub x2, x23, x2             // arr[j]

    mul x3, x25, x6
    sub x3, x23, x3             // arr[minIndex]

    ldr w2, [x2]                // load value
    ldr w3, [x3]                // load value
    cmp w2, w3                 
    bge endIfSS                 // if arr[j] >= arr[minIndex] goto endIfSS
    mov x25, x27                // minIndex = j

 endIfSS:
    add x27, x27, #1             //j++
    b loopSS2

 endLoopSS2:
    mov x6, #4
    mul x0, x25, x6
    sub x0, x23, x0             // &arr[minIndex]
    
    mul x1, x26, x6
    sub x1, x23, x1             // &arr[i]

    bl swap

    add x26, x26, #1            // i ++
    b loopSS

 endLoopSS:

    //epilogue
    ldr x30, [sp]
    ldr x23, [sp, #8]
    ldr x24, [sp, #16]
    ldr x25, [sp, #24]
    ldr x26, [sp, #32]
    ldr x27, [sp, #40]
    add sp, sp, #48

    ret

compute_average:
 // Prologue
    sub     sp, sp, #48
    str     x30, [sp, #0]        // Save return address
    str     x23, [sp, #8]        // Save array address
    str     x24, [sp, #16]       // Save n - size of array
    str     x25, [sp, #24]       // Save start index
    str     x26, [sp, #32]       // Save stop index

    mov     x23, x0              // Move array address into x23
    mov     x24, x1              // Move size of array into x24
    mov     x25, #0              // Start index is initially 0
    
    // Prepare for sum_array call
    mov     x0, x23              // array address
    mov     x1, x25              // start index
    mov     x2, x24              // stop index
    mov     x3, #0               // initialize sum to 0

    bl      sum_array            // call sum_array function

    // Calculate and return the average
    mov     x1, x0               // sum value            
    udiv    x0, x1, x24          // Return sum / n

 // Epilogue
    ldr     x30, [sp, #0]        // Restore return address
    ldr     x23, [sp, #8]        // Restore array address
    ldr     x24, [sp, #16]       // Restore n - size of array
    ldr     x25, [sp, #24]       // Restore start index
    ldr     x26, [sp, #32]       // Restore stop index
    add     sp, sp, #48          // Restore stack pointer
    ret

sum_array:
 // Prologue
    sub     sp, sp, #32
    str     x30, [sp, #0]        // Save return address
    str     x23, [sp, #8]        // Save array address
    str     x24, [sp, #16]       // Save start index
    str     x25, [sp, #24]       // Save stop index
   
    mov     x23, x0              // Move array address into x23
    mov     x24, x1              // Move start index into x24
    mov     x25, x2              // Move stop index into x25
  

 // Calculate sum

    // Base case: if start index > stop index, return 0
    cmp     x24, x25
    bge    endSumIf

    // Recursive case: sum = arr[startidx] + sum_array(arr, startidx + 1, stopidx)
    mov     x4, #4
    mul     x4, x4, x24
    sub     x23, x23, x4
    ldr     w5, [x23]                // Load value from array into w5
    
    sub     sp, sp, #16
    
    str     w5, [sp, #0]

    add     x24, x24, #1             // Increment start index by 1
    mov     x1, x24                  // Start index


    bl      sum_array                // Recursive call
   
    ldr     w5, [sp, #0]
    add     sp, sp, #16

    add     w0, w0, w5               // Add value to sum

   //epilogue
    ldr     x30, [sp, #0]       
    ldr     x23, [sp, #8]        
    ldr     x24, [sp, #16]           // Restore start index
    ldr     x25, [sp, #24]           // Restore stop index
    add     sp, sp, #32              // Restore stack pointer
    ret


 endSumIf:
    mov     x0, #0                  // base case
   
   //epilogue
    ldr     x30, [sp, #0]       
    ldr     x23, [sp, #8]        
    ldr     x24, [sp, #16]              // Restore start index
    ldr     x25, [sp, #24]              // Restore stop index
    add     sp, sp, #32                  // Restore stack pointer
    ret


endProgram:
	 // return(0);
	 // also the "epilog" code

    add sp, sp, #256
    add sp, sp, #256

    ldr x19, [sp, #8]
    ldr x20, [sp, #16]
    ldr x21, [sp, #24]

    add sp, sp, #32
  
	 mov	x0, #0			// exit code is 0
	 ldp	x29, x30, [sp], #32	// restore the X29 and X30 from the stack
	 ret



    
