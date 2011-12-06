// Agent sample in project BoomerAgent

/* Initial beliefs and rules */

freelist([]).

/* Initial goals */

!start.

/* Plans */
+!start
<-
	makeArtifact("game","alice.mas.lab6.TheGame",[],G);
	newgame("localhost");
	focus(G);
	!play.
	
+!play
<-
	smell;
	!move;
	!clearlist;
	!!play.

+!move
:
	freelist([D,_,_|_])
<-
	bomb;
	!real_move(D).
	
+!move
:
	freelist([D|_])
<-
	!real_move(D).
	
+!check(R) : R = "alive".
+!check(R) : R = "died" <- .print("I died."); .fail.

+!real_move(D)
<-
	move(D,R);
	!check(R).

@listop[atomic]
+free(D)
<-
	-freelist(L);
	alice.mas.lab6.shake([D|L],R);
	+freelist(R).

@listclean[atomic]
+!clearlist
<-
	-freelist(_);
	+freelist([]).

