function [X,lambda,how]=qp(H,f,A,B,vlb,vub,X,neqcstr,verbosity,varargin)
%QP Quadratic programming. 
%   X=QP(H,f,A,b) solves the quadratic programming problem:
%
%            min 0.5*x'Hx + f'x   subject to:  Ax <= b 
%             x    
%
%       X=QP(H,f,A,b,VLB,VUB) defines a set of lower and upper
%       bounds on the design variables, X, so that the solution  
%       is always in the range VLB <= X <= VUB.
%
%       X=QP(H,f,A,b,VLB,VUB,X0) sets the initial starting point to X0.
%
%       X=QP(H,f,A,b,VLB,VUB,X0,N) indicates that the first N constraints 
%       defined by A and b are equality constraints.
%
%       X=QP(H,f,A,b,VLB,VUB,X0,N,DISPLAY) controls the level of warning
%       messages displayed.  Warning messages can be turned off with
%       DISPLAY = -1.
%
%       [x,LAMBDA]=QP(H,f,A,b) returns the set of Lagrangian multipliers,
%       LAMBDA, at the solution.
%
%       [X,LAMBDA,HOW] = QP(H,f,A,b) also returns a string HOW that 
%       indicates error conditions at the final iteration.
%
%       QP produces warning messages when the solution is either unbounded
%       or infeasible. 

%   Copyright (c) 1990-97 by The MathWorks, Inc.
%   $Revision: 1.30 $  $Date: 1997/04/08 14:39:44 $
%   Andy Grace 7-9-90. Mary Ann Branch 9-30-96.

% Note: the varargin is to capture two extra arguments that exist only for
%       backward compatibility reasons; their functionality is obsolete.

% Handle missing arguments
if nargin < 9, verbosity = 0; 
   if nargin < 8, neqcstr = 0; 
      if nargin < 7, X = []; 
         if nargin < 6, vub = []; 
           if nargin < 5, vlb = [];
end, end, end, end, end
[ncstr,nvars]=size(A);
nvars = max([length(f),length(H),nvars]); % In case A is empty

if isempty(verbosity), verbosity = 0; end
if isempty(neqcstr), neqcstr = 0; end
if isempty(X), X=zeros(nvars,1); end

if isempty(A), A=zeros(0,nvars); end
if isempty(B), B=zeros(0,1); end

% Expect vectors
f=f(:);
B=B(:);
X = X(:);

if  norm(H,'inf')==0 | isempty(H)
  H=[]; 
  % Really a lp problem
  caller = 'lp';
else
  caller = 'qp';
  % Make sure it is symmetric
  if norm(H-H') > eps
    if verbosity > -1
      warning('Your Hessian is not symmetric.  Resetting H=(H+H'')/2')
    end
  H = (H+H')*0.5;
  end
end

[X,lambda,how]=qpsub(H,f,A,B,vlb,vub,X,neqcstr,verbosity,caller,ncstr,nvars);


