/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

@init
+!start[source(self)]
<-
	.send(df,tell,new_player);
	.send(df,tell,players);
	.wait({+playerlist(L)[source(df)]});
	.print("found players: ",L);
	!play_against_all(L).

@noothers[atomic]
+!play_against_all([]).
@searchplayers[atomic]
+!play_against_all([P|L])
<-
	!play_against_all(L);
	!start_match(P).


+!start_match(P)[source(self)]
:
	not playing_with(P)
<-
	.random(Rnd);
	+propose(P,Rnd);
	.send(P,tell,propose(Rnd)).


+propose(N)[source(P)]
:
	propose(P,R) &
	R > N
<-
	-propose(N)[source(P)];
	!play(P).


+propose(N)[source(P)]
:
	not propose(P,_)
<-
	-propose(N)[source(P)];
	!play(P).


+propose(N)[source(P)]
:
	propose(P,R) &
	R <= N
<-
	-propose(N)[source(P)].


+!play(P)
<-
	words.random_letter(L);
	!obtain_word(L,P).


@newplayerjoins
+!challenge(W)[source(P)]
:
	not match(P,_)
<-
	.print(P," sent me ",W);
	+playing_with(P);
	words.last_letter(W,L);
	!obtain_word(L,P).


+!challenge(W)[source(P)]
:
	match(P,Previous)
<-
	-match(P, Previous);
	.print(P," should have sent me a reply for ",Previous);
	words.last_letter(Previous, L);
	words.first_letter(W,L);
	.print(W," matches ",Previous," now ask broker");
	.send(broker,askOne,property(P,W),property(P,W));
	words.last_letter(W,N);
	!obtain_word(N,P).


-!challenge(W)[source(P)]
<-
	.print(P, " is a cheater").


+!obtain_word(L, P)[source(self)]
:
	db(L, W)
<-
	.print("I have ", W," in cache!");
	!send(P,W).


+!obtain_word(L, P)[source(self)]
<-
	.print("asking broker for ",L);
	.send(broker,tell,want_word(L));
	.wait({+word(L,W)[source(broker)]},1000);
	+db(L, W);
	!send(P,W).

-!obtain_word(_,P)
<-
	.print("I'm a loser, I probably finished my credit. ", P, " won.").

+!send(P,W)[source(self)]
<-
	.print("sending ",W," to ",P);
	+match(P,W);
	.send(P,achieve,challenge(W)).


	