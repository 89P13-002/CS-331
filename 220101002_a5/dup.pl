% base case for the empty list
dup([])    :- false.  

% check for the current head element appears in tail ? 
dup([H|T]) :- search(H,T).

% otherwise in rest list does it has duplicate element ?
dup([_|T]) :- dup(T).


% this for search a element mathed to X ?
% base case when empty list then no match
search(_,[])    :- false.

% head element matches to searching element then true
search(X,[X|_]) :- true.

% if head element not matches then look in the tail
search(X,[_|T]) :- search(X,T).
