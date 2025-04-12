% Fun for calculating the sqrt with help of cal_sqrt fun
sqrt(X,Result,Accuracy) :-
    Low is 0,
    High is max(X,1),
    Initial is max(X/3,0.000001),
    cal_sqrt(X,Low,High,Initial,Accuracy,Result).

% when difference is less than accuracy we got the ans
cal_sqrt(X,_Low,_High,Mid,Accuracy,Result) :-
    Square is Mid*Mid,
    Diff is abs(Square - X),
    Diff < Accuracy,
    Result = Mid.

% Recursivaly call cal_sqrt when current mid val not satisfy (here answer is in right half) and modify low along with mid
cal_sqrt(X,_Low,High,Mid,Accuracy,Result) :-
    Square is Mid * Mid,
    Square < X,
    NewLow is Mid,
    NewMid is (NewLow + High) / 2,
    cal_sqrt(X,NewLow,High,NewMid,Accuracy,Result).

% Recursivaly call cal_sqrt when current mid val not satisfy (here answer is in left half) and modify high along with mid
cal_sqrt(X,Low,_High,Mid,Accuracy,Result) :-
    Square is Mid * Mid,
    Square > X,
    NewHigh is Mid,
    NewMid is (Low + NewHigh) / 2,
    cal_sqrt(X,Low,NewHigh,NewMid,Accuracy,Result).