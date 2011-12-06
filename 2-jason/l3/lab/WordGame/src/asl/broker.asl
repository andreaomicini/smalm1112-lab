/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
// Agent broker in project WordGame

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

@requestReceived[atomic]
+want_word(L)[source(P)]
:
	credit(P,C) &
	C > 0
<-
	-want_word(L)[source(P)];
	!send_word(P,L).

@playerCreation[atomic]
+want_word(L)[source(P)]
:
	not credit(P,_)
<-
	-want_word(L)[source(P)];
	+credit(P,20);
	!send_word(P,L).

	
@wordrequest[atomic]
+!send_word(P,L)
<-
	.print(P," required a word starting with ",L);
	words.get_word(L,W);
	-credit(P,C);
	+credit(P,C-1);
	.print("sending ",P,": ",W,", credit left ",C-1);
	+property(P,W);
	.send(P,tell,word(L,W)).
