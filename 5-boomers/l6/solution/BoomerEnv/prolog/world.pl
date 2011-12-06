x(20).
y(20).

adj(X,Y,X1,Y) :- X is X1+1, X>=0, x(L), X<L.
adj(X,Y,X1,Y) :- X is X1-1, X>=0, x(L), X<L.
adj(X,Y,X,Y1) :- Y is Y1-1, Y>=0, y(L), Y<L.
adj(X,Y,X,Y1) :- Y is Y1+1, Y>=0, y(L), Y<L.

free(X,Y) :- not(block(X,Y)), X>=0, Y>=0, x(Lx), y(Ly), X<Lx, Y<Ly.

next(X1,Y1,X,Y) :- x(Lx), y(Ly), X is X1+1, X<Lx, Y is Y1, Y<Ly.
next(X1,Y1,0,Y) :- x(Lx), y(Ly), Xt is X1+1, Xt is Lx, Y is Y1+1, Y<Ly.

nextfree(X,Y) :- nextfree(X,Y,0,0).
nextfree(X,Y,X,Y) :- free(X, Y).
nextfree(X,Y,X1,Y1) :- next(X1, Y1, X2, Y2), nextfree(X,Y,X2,Y2).

playermatch(P,X,Y) :- !, running(P), player(P,X,Y).

move(s, S) :- playermatch(P,X,Y), NY is Y-1, adj(X,Y,X,NY), free(X,NY), retractall(player(P,X,Y)),  assert(player(P,X,NY)), bomb(P, X, NY, S).
move(n, S) :- playermatch(P,X,Y), NY is Y+1, adj(X,Y,X,NY), free(X,NY), retractall(player(P,X,Y)), assert(player(P,X,NY)), bomb(P, X, NY, S).
move(w, S) :- playermatch(P,X,Y), NX is X-1, adj(X,Y,NX,Y), free(NX,Y), retractall(player(P,X,Y)), assert(player(P,NX,Y)), bomb(P, NX, Y, S).
move(e, S) :- playermatch(P,X,Y), NX is X+1, adj(X,Y,NX,Y), free(NX,Y), retractall(player(P,X,Y)), assert(player(P,NX,Y)), bomb(P, NX, Y, S).

bomb(P, X, Y, died) :- bomb(X,Y), retractall(bomb(X,Y)), die(P).
bomb(_, X, Y, alive) :- not(bomb(X,Y)).

bomb :- playermatch(_,X,Y), assert(bomb(X,Y)).

smell(X,Y,Z) :- playermatch(_,X1,Y1), adj(X,Y,X1,Y1), lookat(X,Y,Z).
lookat(X,Y,bomb) :- bomb(X,Y).
lookat(X,Y,block) :- block(X,Y).
lookat(X,Y,free) :- free(X,Y).

new :- running(P), not(player(P,_,_)), nextfree(X,Y), assert(player(P,X,Y)), !.

die(P) :- retractall(player(P,_,_)).

pos(X,Y) :- running(P), player(P,X,Y).

op(P,C) :- interface(L), member(C,L), assert(running(P)), callop(P,C).

callop(P,C) :- call(C), retract(running(P)).
callop(P,_) :- !, retract(running(P)), fail.

interface([new, bomb, move(_,_), smell(_,_,_), pos(_,_)]) :- !.
