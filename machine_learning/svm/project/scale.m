function Xs = scale(X)
%Scale the dataset into zero mean and unit variance
%   Usage: Xs = scale(X)

Mu = mean(X);
Sd = std(X);
if any(Sd == 0)
    ind = find(Sd == 0);
    error(['Feature ' num2str(ind) ' of X1 cannot be scaled'])
end
for i = 1:size(X,2)
    Xs(:,i) = (X(:,i) - Mu(i))/Sd(i);
end
