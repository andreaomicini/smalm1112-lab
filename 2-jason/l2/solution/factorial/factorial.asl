/*******Initial Goals and believes*******/
// Believes
fact(0,1).

// Goals, will be executed concurrently!
!print_fact(0).
!print_fact(1).
!print_fact(5).
!print_fact(50).
!print_fact(-5). //fails

/*******Plans*******/
/* If you want to print a factorial, compute it and then print */
+!print_fact(N)
<-
	!fact(N,F);	.print("Factorial of ", N, " is ", F).

/* If you already have the solution in your KB, use it. */
//Condition
+!fact(N,F)
:
	fact(N,F).
	
/* Factorial of N, where N>0, is N times the factorial of N-1 */
// Condition
+!fact(N,F)
:
	N > 0
<-
	!fact(N-1,F1);
	F = F1 * N;
	+fact(N,F).

