//=============================================
// Robert Hannaford
//=============================================

// This program implements a queue supporting both FIFO and LIFO operations.
// It uses a singly-linked list to represent the set of queue elements


//=============================================
// Includes and Defines
//=============================================
#include <stdlib.h>
#include <stdio.h>
#include "harness.h"
#include "queue.h"


//=============================================
// Queue New Function
//
// Create empty queue.
// Return NULL if could not allocate space.
//=============================================
Queue_t *q_new()
{
    Queue_t *q =  malloc(sizeof(Queue_t));
    /* What if malloc returned NULL? */
    if(q == NULL){
        return NULL;
    }
    q->size = 0;
    q->tail = NULL;
    q->head = NULL;
    return q;
}


//=============================================
// Queue Free Function
//
/* Free all storage used by queue */
//=============================================
void q_free(Queue_t *q)
{
    /* How about freeing the list elements? */
    if (q == NULL) return;
    Node_t *current = q->head;
    while(current!= NULL){
        Node_t *next = current->next;
        free(current);
        current = next;
    }
    q->head = NULL;
    q->tail = NULL;
    q->size = 0;
    /* Free queue structure */
    free(q);
}


//=============================================
// Queue Insert Head Function
//
// Attempt to insert element at head of queue.
// Return true if successful.
// Return false if q is NULL or could not allocate space.
//=============================================
bool q_insert_head(Queue_t *q, int v)
{
    Node_t *newh;
    /* What should you do if the q is NULL? */
    if(q == NULL)return false;
    newh = malloc(sizeof(Node_t));
    /* What if malloc returned NULL? */
    if(newh == NULL)return false;
    if (q->tail == NULL)q->tail = newh;
    q->size ++;  
    newh->value = v;
    newh->next = q->head;
    q->head = newh;
    return true;
}


//=============================================
// Queue Insert Tail Function
//
// Attempt to insert element at tail of queue.
// Return true if successful.
// Return false if q is NULL or could not allocate space.
//=============================================
bool q_insert_tail(Queue_t *q, int v)
{
    /* You need to write the complete code for this function */
    /* Remember: It should operate in O(1) time */
    Node_t *newt;
    if(q == NULL) return false;
    newt = malloc(sizeof(Node_t));
    if(newt == NULL) return false;

    newt->value = v;
    newt->next = NULL;

    if(q->tail == NULL) {
        q->head = newt;
        q->tail = newt;
        q->size++;
        return true;
        
    } else {
        q->tail->next = newt;
        q->tail = newt;
        q->size++;
        return true;
}
}


//=============================================
// Queue Remove Head Function
//
// Attempt to remove element from head of queue.
// Return true if successful.
// Return false if queue is NULL or empty.
// If vp non-NULL and element removed, store removed value at *vp.
// Any unused storage should be freed
//=============================================
bool q_remove_head(Queue_t *q, int *vp)
{
    /* You need to fix up this code. */
    if(q == NULL || q->size == 0) return false;

    Node_t *temp = q->head;
    if(vp != NULL) *vp = temp->value;

    q->head = q->head->next;
    free(temp);

    q->size--;
    if(q->size == 0) q->tail = NULL;
    return true;

}


//=============================================
// Queue Size Function
//
// Return number of elements in queue.
// Return 0 if q is NULL or empty
//=============================================
int q_size(Queue_t *q)
{
    /* You need to write the code for this function */
    /* Remember: It should operate in O(1) time */
    if(q == NULL || q->size ==0) return 0;
    return q->size;
}


//=============================================
// Queue Reverse Function
//
// Reverse elements in queue.
// No effect if q is NULL or empty.
// No effect if q has only one element.
// Your implementation must not allocate or free any elements (e.g., by
// calling q_insert_head or q_remove_head).  Instead, it should modify
// the pointers in the existing data structure.
//=============================================
void q_reverse(Queue_t *q)
{ 
    /* You need to write the code for this function */
    if((q == NULL) || (q->size == 0) || (q->size == 1)) return;

    Node_t *prev = NULL;
    Node_t *current = q->head;
    Node_t *next;
    q->tail = q->head; // To maintain the tail pointer intact after reversal

    while(current != NULL){
        next = current->next;
        current->next = prev;
        prev = current;
        current = next;
    }
    q->head = prev;

}