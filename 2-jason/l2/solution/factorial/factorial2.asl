/*******Initial Goals and believes*******/
// Beliefs
fact(0,1).

// Goals
!calc.

/*******Plans*******/
//From the starting goal, it will execute in sequence!
+!calc 
<-
	!print_fact(100);
	!print_fact(50);
	!print_fact(5);
	!print_fact(1).

/* If you want to print a factorial, compute it and then print */
+!print_fact(N)
<-
	!fact(N,F);	.print("Factorial of ", N, " is ", F).

/* If you already have the solution in your KB, use it. */
//Condition
+!fact(N,F)
<-
	?fact(N,F).
	
/* If no solution is available and N>0, the result is N times the factorial of N-1 */
// Condition
+?fact(N,F)
:
	N > 0
<-
	.print("entered in calculation for ",N);
	!fact(N-1,F1);
	F = F1 * N;
	+fact(N,F).


