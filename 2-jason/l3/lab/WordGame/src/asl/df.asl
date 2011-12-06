/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
// Agent df in project WordGame

/* Initial beliefs and rules */
playerlist([]).
ready.

/* Initial goals */

/* Plans */
@addplayer[atomic]
+new_player[source(A)]
<-
	-playerlist(OldList);
	+playerlist([A|OldList]);
	.print("added player ",A,".");
	.print("Current players: ",[A|OldList]).


@removeplayer[atomic]
+player_leaves[source(A)]
<-
	-playerlist(L);
	!removeElement(A,L,R);
	.print("removed player ",A);
	+playerlist(R);
	.print("Current players: ",R).


+!removeElement(A,[A|List],List).
+!removeElement(A,[T|L],[T|R])
:
	A \== T
<-
	!removeElement(A,L,R).


+players[source(Sender)]
<-
	?playerlist(L);
	!removeElement(Sender,L,R);
	.print("sending ", Sender, " ", R);
	.send(Sender,tell,playerlist(R)).
